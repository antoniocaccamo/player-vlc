package it.antanysavage.sm.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Timer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.email.EmailSender;
//import it.antanysavage.sm.player.qtj.QTSessionCheck;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.model.MediaPlayer;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.types.AcceptedVideoTypes;
import it.antanysavage.sm.player.swt.players.BlackWindowPlayer;
import it.antanysavage.sm.player.swt.players.HiddenWindowPlayer;
import it.antanysavage.sm.player.swt.players.IPlayer;
import it.antanysavage.sm.player.swt.players.MPlayerVideoPlayer;
import it.antanysavage.sm.player.swt.players.PhotoPlayer;
import it.antanysavage.sm.player.swt.players.VLCVideoPlayer;
//import it.antanysavage.sm.player.swt.players.VideoMPlayer;
//import it.antanysavage.sm.player.swt.players.QTMoviePlayer;
import it.antanysavage.sm.player.swt.players.WatchPlayer;
import it.antanysavage.sm.player.swt.players.WeatherPlayer;
import it.antanysavage.sm.player.swt.players.WebUrlPlayer;
import it.antanysavage.sm.player.swt.views.BlackComposite;
import it.antanysavage.sm.player.swt.views.MPlayerVideoComposite;
import it.antanysavage.sm.player.swt.views.PhotoComposite;
import it.antanysavage.sm.player.swt.views.VLCVideoComposite;
//import it.antanysavage.sm.player.swt.views.QTMovieComposite;
import it.antanysavage.sm.player.swt.views.WatchComposite;
import it.antanysavage.sm.player.swt.views.WebUrlComposite;
import it.antanysavage.sm.player.timertasks.DemoTimerTask;
import it.antanysavage.sm.player.util.Utils;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class PlayerMaster extends ApplicationWindow implements ActionListener, IMaster {

	protected static Color black = new Color(Display.getCurrent(), 0, 0, 0);

	private static final Logger logger = LoggerFactory.getLogger(PlayerMaster.class);

	protected ArrayList<MediaPlayer> mediaPlayersList = new ArrayList<MediaPlayer>();

	//	private HashMap compositesHashMap = new HashMap();
	//
	//	private HashMap playersHashMap = new HashMap();
	private Program program;

	private ScreenManagerComposite screenManager;

	protected StackLayout programCompositeLayout;

	private int status = Status.NOT_ACTIVE;

	private IPlayer thePlayer;

	private Media theMedia;

	private Timer counter = new Timer(30000, this);

	private Date now = null;

	private Composite parent;

	protected PhotoComposite demoPhotoComposite;

	private boolean visibility;

	protected StackLayout playerCompositeLayout;

	protected Composite programComposite;

	protected Composite demoOnlineComposite;

	private static Media BLACK_DEFALUT;

	private static Media WATCH_DEFALUT;

	protected java.util.Timer demoTimer;

	private DemoTimerTask demoTimerTask;

	private FileLock lock;

	{
		BLACK_DEFALUT = new Media();
		BLACK_DEFALUT.setId(0);
		BLACK_DEFALUT.setDuration(5.0f);
		BLACK_DEFALUT.setType(AcceptedVideoTypes.BLACKWINDOW);

		WATCH_DEFALUT = new Media();
		WATCH_DEFALUT.setId(0);
		WATCH_DEFALUT.setDuration(5.0f);
		WATCH_DEFALUT.setType(AcceptedVideoTypes.WATCH);
	}

	private synchronized void count() {
		//		this.now = Utils.getTime( Utils.getTimeAsString( Calendar.getInstance().getTime())); 
		//this.now = Calendar.getInstance().getTime();
		Runtime r = Runtime.getRuntime();
		logger.debug("ratio free / total  memory before running gc() : " + r.freeMemory() + " / " + r.totalMemory());
		r.gc();
		logger.debug("ratio free / total  memory after  running gc() : " + r.freeMemory() + " / " + r.totalMemory());
	}

	public void actionPerformed(ActionEvent e) {
		count();
	}
	/*
     private synchronized Date getNow() {

     Date now = Calendar.getInstance().getTime();
     logger.debug("now is : " + Utils.debugDate(now));
     return now;
     }
	 */

	public PlayerMaster(ScreenManagerComposite videoManagerComposite) {
		super(null);
		//		setShellStyle(SWT.SHADOW_ETCHED_IN | SWT.ON_TOP );
		setShellStyle(SWT.NO_TRIM | SWT.ON_TOP);
		this.screenManager = videoManagerComposite;       
	}

	public synchronized int getStatus() {
		return status;
	}

	protected Control createContents(Composite parent) {

		demoPhotoComposite = new PhotoComposite(parent);
		if (Player.DEMO_MODE) {
			//			demoPhotoComposite.setDemoPhoto(Player.DEMO_IMAGE);
			//			demoPhotoComposite.set(Player.DEMO_IMAGE_MEDIA);
			demoTimer = new java.util.Timer();
		}

		getShell().setBackgroundMode(SWT.INHERIT_DEFAULT);
		getShell().setBackground(black);

		programComposite = new Composite(parent, SWT.NONE);

		playerCompositeLayout = new StackLayout();
		programCompositeLayout = new StackLayout();

		demoOnlineComposite = programComposite;
		parent.setLayout(playerCompositeLayout);
		programComposite.setLayout(programCompositeLayout);

		playerCompositeLayout.topControl = demoOnlineComposite;
		parent.layout();

		createMediaPlayers();

		/*
		 * set on top black window for programComposite
		 */
		programCompositeLayout.topControl = mediaPlayersList.get(AcceptedVideoTypes.BLACKWINDOW_TYPE).getComposite();
		parent.layout();

		getShell().setImage(Player.LOGO_IMAGE);

		parent.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				logger.debug("-----------------------------------------focus");
				if (screenManager != null) {
					screenManager.getPlayer().setFocusOnTabItem(screenManager.getIndex());
				}
			}
		});

		//		getShell().addControlListener(new ControlAdapter() {
		//			public void controlResized(ControlEvent evt) {						
		//				Point location = getShell().getLocation();
		//				Point size = getShell().getSize();
		//				videoManager.changeSpinners(size, location);
		//			}       
		//		});

		Player.moveMouse(getShell());
		Player.moveMouse(parent.getShell());
		Player.moveMouse(demoPhotoComposite.getShell());
		Player.moveMouse(programComposite.getShell());

		for ( MediaPlayer mp : mediaPlayersList ){
			Player.moveMouse( mp.getComposite() );
			Player.moveMouse( mp.getComposite().getShell() );
		}


		onTop();

		getShell().addControlListener( new ControlListener() {

			@Override
			public void controlResized(ControlEvent arg0) {
				onTop();
			}

			@Override
			public void controlMoved(ControlEvent arg0) {
				onTop();
			}
		});

		return parent;
	}

	private void onTop(){

		Display.getDefault().asyncExec( new Runnable() {

			@Override
			public void run() {
				try {
					Point location = getShell().getLocation();
					Point dimension = getShell().getSize();
					String handle = String.valueOf(getShell().getClass().getField("handle").getLong(PlayerMaster.this.screenManager));
					OS.SetWindowPos(getShell().handle, OS.HWND_TOPMOST ,location.x, location.y, dimension.x, dimension.y, 0);
					logger.debug("shell handle : " + handle + " : setted on top" );
				} catch (Exception e) {
					logger.error("error setting on_top", e);
					MessageDialog.openError(
							Display.getDefault().getActiveShell(), 
							"error setting on_top", 								
							"Error : " + e.getLocalizedMessage()
							);			
				}
			}
		} );


	}

	//	public void testPlay() {
	//		logger.info(" init test play");
	//		try {
	//			Program smp = ModelManager.getDefault();
	//			this.setSequence(smp);
	//			this.play();
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//
	//	}
	//	public void addControllerAdapter( ControlAdapter myControlAdapter ) {
	//		getShell().addControlListener(myControlAdapter);
	//	}
	//				
	//	public void setSequence(SequenceModel sequence) {
	//		this.sequence = sequence;
	//	}

	/* (non-Javadoc)
	 * @see it.antanysavage.sm.player.IMaster#setSequence(it.antanysavage.sm.player.sequences.model.Program)
	 */
	public void setProgram(Program smp) {
		this.program = smp;
	}

	public Program getProgram() {
		return program;
	}

	public void block(boolean block) {
		//		
		//		Shell oldShell = getShell();		
		//		Shell shell = null;
		//
		//		//		logger.info(" block : " + block );
		//
		//		int style = SWT.ON_TOP;
		//		if ( block )
		//			shell = new Shell( oldShell, SWT.NO_TRIM );
		//		else
		//			shell = new Shell( oldShell,SWT.SHADOW_ETCHED_IN);

		//		oldShell.dispose();
		//		final int theStyle = style;
		//		Display.getDefault().syncExec(
		//				new Runnable() {					
		//					public void run() {
		//						
		//						logger.debug("Player Master shell style changed");
		//						getShell().redraw();
		//					}
		//				}
		//		);
		//		getShell().setEnabled( ! block );
	}

	/* (non-Javadoc)
	 * @see it.antanysavage.sm.player.IMaster#hide(boolean)
	 */
	public void hide(final boolean hide) {

		if (visibility) {
			Display.getDefault().syncExec(
					new Runnable() {
						public void run() {
							getShell().setVisible(!hide);
						}
					}
					);
		}

	}

	/* (non-Javadoc)
	 * @see it.antanysavage.sm.player.IMaster#setVisibility(boolean)
	 */
	public void setVisibility(final boolean visibility) {
		this.visibility = visibility;
		Display.getDefault().syncExec(
				new Runnable() {
					public void run() {
						getShell().setVisible(visibility);
					}
				}
				);

	}

	/* (non-Javadoc)
	 * @see it.antanysavage.sm.player.IMaster#setVideoManager(it.antanysavage.sm.player.ScreenManagerComposite)
	 */
	public void setScreenManager(ScreenManagerComposite videoManager) {
		this.screenManager = videoManager;
	}

	public boolean isPlaying() {
		return this.status == Status.PLAYNG;
	}

	/* (non-Javadoc)
	 * @see it.antanysavage.sm.player.IMaster#play()
	 */
	public void play() {

		if (this.program == null) {
			//			MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
			//			mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
			//			mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_NO_SELECTION));	
			//			mb.open();
			MessageDialog.openError(screenManager.getShell(),
					LocaleManager.getText(LocaleManager.APP_TITLE),
					LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_NO_SELECTION)
					);

			this.screenManager.stop();

			return;
		}

		if (this.program.isEmpty()) {
			//			MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
			//			mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
			//			mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_EMPTY));	
			//			mb.open();
			MessageDialog.openError(screenManager.getShell(),
					LocaleManager.getText(LocaleManager.APP_TITLE),
					LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_EMPTY)
					);
			return;
		}

		//		if ( status == Status.PLAYNG ) {
		//			MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
		//			mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
		//			mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_PLAYER_ALREADY_PLAYING));	
		//			mb.open();
		//			return;
		//		}
		//		this.now = Utils.getTime( Utils.getTimeAsString( Calendar.getInstance().getTime()));
		count();
		this.counter.start();

		if (this.status == Status.NOT_ACTIVE && this.programCompositeLayout.topControl instanceof WatchComposite) {
			WatchComposite watchComposite = (WatchComposite) this.programCompositeLayout.topControl;
			//			watchComposite.stop();
		}
		this.program.setPlayed(true);
		this.status = Status.PLAYNG;
		this.screenManager.getSequenceCombo().setEnabled(false);
		this.screenManager.getPlayButton().setEnabled(false);
		this.screenManager.getPauseButton().setEnabled(true);
		//		this.videoManager.getPlayButton().setToolTipText(LocaleManager.getText(LocaleManager.ERRORS_PLAYER_ALREADY_PLAYING));
		if (Player.DEMO_MODE) {
			demoTimerTask = new DemoTimerTask(this);
			demoTimer.schedule(this.demoTimerTask, 5000, 4000);
		}
		this.next();
	}

	/* (non-Javadoc)
	 * @see it.antanysavage.sm.player.IMaster#stop()
	 */
	public void stop() {
		logger.info("stopping PlayerMaster [" + this.screenManager.getIndex() + "] ..");
		if (this.thePlayer != null) {
			this.thePlayer.stop();
		}
		this.status = Status.STOPPED;

		if (this.program != null) {
			this.program.setPlayed(false);
			this.program.goToInit();
		}
		this.counter.stop();
		logger.warn("test de active !!");
		this.deActive();
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				screenManager.getPlayButton().setEnabled(true);
				screenManager.getPauseButton().setEnabled(false);
				screenManager.resetVideoProgressBarMaximum();
				screenManager.resetMediaLabels();
				//	getShell().setAlpha(screenManager.getPlayerSetting().getAlpha() );
			}
		});
		logger.info("PlayerMaster [" + (this.screenManager.getIndex() + 1) + "] stopped");
	}

	/* (non-Javadoc)
	 * @see it.antanysavage.sm.player.IMaster#deActive()
	 */
	public void deActive() {

		this.status = Status.NOT_ACTIVE;

		Composite composite = null;

		if (this.screenManager.isBlackWindowWhenNotActive()) {
			composite = mediaPlayersList.get(AcceptedVideoTypes.BLACKWINDOW_TYPE).getComposite();
			//			composite = (Composite) this.compositesHashMap.get(AcceptedVideoTypes.BLACKWINDOW);
		}

		if (this.screenManager.isWatchWindowWhenNotActive()) {
			WatchComposite wcomposite = (WatchComposite) mediaPlayersList.get(AcceptedVideoTypes.WATCH_TYPE).getComposite();
			//			AnalogClockComposite wcomposite = (AnalogClockComposite) mediaPlayersList.get(AcceptedVideoTypes.WATCH_TYPE).getComposite();
			//			wcomposite.start();
			composite = wcomposite;
		}

		if (this.screenManager.isImageWindowWhenNotActive()) {
			WatchComposite wcomposite = (WatchComposite) mediaPlayersList.get(AcceptedVideoTypes.WATCH_TYPE).getComposite();
			//			AnalogClockComposite wcomposite = (AnalogClockComposite) mediaPlayersList.get(AcceptedVideoTypes.WATCH_TYPE).getComposite();
			//			wcomposite.start();

			composite = wcomposite;
		}

		if (Player.DEMO_MODE) {
			if (this.demoTimerTask != null) {
				demoTimerTask.cancel();
			}
			this.demoTimer.purge();
			demoSwitcher();
		}

		this.setTopControl(composite);
	}

	/* (non-Javadoc)
	 * @see it.antanysavage.sm.player.IMaster#next()
	 */
	public void next() {

		Player.moveMouse();

		try {
			if (theMedia != null) {
				switch (theMedia.getType().getType()) {
				case AcceptedVideoTypes.FTPIMAGE_TYPE:
				case AcceptedVideoTypes.FTPVIDEO_TYPE:
					if (lock != null) {
						try {
							theMedia.unlock(lock);
						} catch (Exception e) {
							logger.error("error on release lock for media : " + Utils.getMediaDescription(theMedia), e);
						}
					}
					break;

				default:
					break;
				}
			}

			if (status != Status.PLAYNG) {
				deActive();
				return;
			}
			/*
             Date now = Calendar.getInstance().getTime();
             logger.debug("now is : " + Utils.debugDate(now));
			 */
			theMedia = program.next();
			if (this.theMedia == null) {
				if (screenManager.isBlackWindowWhenNotActive()) {
					theMedia = BLACK_DEFALUT;
				} else {
					theMedia = WATCH_DEFALUT;
				}
			}
			//else {
				screenManager.updateMediaLabels(theMedia);
				//			}

				if (theMedia.isAvailable()) {

					boolean ftpResource = false;

					//			if ( this.theMedia == null ) {
					//				if ( videoManager.isBlackWindowWhenNotActive() )
					//					theMedia = BLACK_DEFALUT;
					//				else 
					//					theMedia = WATCH_DEFALUT;
					//			} 
					//			else {
					switch (theMedia.getType().getType()) {
					case AcceptedVideoTypes.FTPIMAGE_TYPE:
					case AcceptedVideoTypes.FTPVIDEO_TYPE:
						try {
							lock = theMedia.lock();
							ftpResource = true;
						} catch (Exception e) {
							logger.error("error on get lock for media : " + Utils.getMediaDescription(theMedia), e);
						}
						break;

					default:
						break;
					}

					//				videoManager.updateMediaLabels(theMedia);
					//			}
					if (!ftpResource || (ftpResource && lock != null)) {

						//		Composite onTopComposite = (Composite) this.compositesHashMap.get( this.theMovie.getType() ); 
						Composite onTopComposite = mediaPlayersList.get(theMedia.getType().getType()).getComposite();

						logger.info("Player [" + (this.screenManager.getIndex() + 1) + "] | Media " + Utils.getMediaDescription(theMedia));

						//		this.thePlayer = (IPlayer)  this.playersHashMap.get( this.theMovie.getType() ); 
						thePlayer = mediaPlayersList.get(theMedia.getType().getType()).getPlayer();
						this.thePlayer.setMedia(this.theMedia);

						applyAlpha();
						this.thePlayer.play();
						this.setTopControl(onTopComposite);

						if ( getScreenManager().getPlayerSetting().getLoopNumber() > 0 ){
							if ( program.getCycle() > getScreenManager().getPlayerSetting().getLoopNumber() ) {
								logger.info("Player [" + (this.screenManager.getIndex() + 1) + "] | max loop  reached : " + getScreenManager().getPlayerSetting().getLoopNumber() );
								getScreenManager().stop();
							}
						}

					} else {
						next();
					}
				} else {
					next();
				}
		} catch ( IndexOutOfBoundsException e ){
			logger.error("PlayerMaster [" + screenManager.getIndex() + "] received a IndexOutOfBoundsException error  when playing " + Utils.getMediaDescription(theMedia) + " => program.goToInit()", e);
			program.goToInit();
			next();
		} 
		
		catch (Throwable t) {
			try {
				logger.error("PlayerMaster [" + screenManager.getIndex() + "] received a fatal error  when playing " + Utils.getMediaDescription(theMedia), t);
				theMedia.setError(true);
				screenManager.stop();
				logger.error("... PlayerMaster [" + screenManager.getIndex() + "] stopped");
				logger.error("restarting PlayerMaster [" + screenManager.getIndex() + "] ...", t);				
				logger.error("... PlayerMaster [" + screenManager.getIndex() + "] restarted");
				EmailSender.getInstance().send("Warning Error", "PlayerMaster [" + screenManager.getIndex() + "] received a fatal error  when playing " + Utils.getMediaDescription(theMedia) + " but restarted\n" + t.getMessage());
				screenManager.play();
			} catch (Throwable tt) {
				logger.error("can't restart PlayerMaster [" + screenManager.getIndex() + "] ", t);
				EmailSender.getInstance().send("Fatal Error", "Can't restart PlayerMaster  \n" +  t.getMessage());

			}
		}

	}

	protected void setTopControl(Composite topComposite) {

		this.programCompositeLayout.topControl = topComposite;
		//logger.error("QUIIIIIIIIII");
		getShell().getDisplay().asyncExec(new Runnable() {

			public void run() {
				programComposite.layout();

			}
		});
	}

	/* -------------------------------------------------------------------------------------------------
	 * 								
	 * -------------------------------------------------------------------------------------------------
	 */
//	public static void main(String[] args) {
//
//		try {
//
//			URL url = ClassLoader.getSystemResource(Player.LOG4J);
//			PropertyConfigurator.configure(url);
//
//			//			QTSessionCheck.check();
//			PlayerMaster wwin = new PlayerMaster(null);
//
//			wwin.setBlockOnOpen(true);
//			//		wwin.setShellStyle(SWT.SHADOW_ETCHED_IN);
//			wwin.open();
//
//			Display.getCurrent().dispose();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.exit(1);
//		}
//
//	}

	public ScreenManagerComposite getScreenManager() {
		return this.screenManager;
	}


	/* (non-Javadoc)
	 * @see it.antanysavage.sm.player.IMaster#demoSwitcher()
	 */
	public void demoSwitcher() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (demoOnlineComposite == programComposite) {
					demoOnlineComposite = demoPhotoComposite;
					//					demoPhotoComposite.setDemoPhoto( Player.DEMO_IMAGE );					
					//					demoPhotoComposite.resizeDemo();
					demoPhotoComposite.set(Player.DEMO_IMAGE_MEDIA);
				} else {
					demoOnlineComposite = programComposite;
				}
				playerCompositeLayout.topControl = demoOnlineComposite;
				getShell().layout();
			}
		});
	}

	/* (non-Javadoc)
	 * @see it.antanysavage.sm.player.IMaster#pause()
	 */
	public void pause() {
		if (this.status == Status.PLAYNG) {
			this.thePlayer.pause();
			this.status = Status.PAUSED;
		}

	}

	/* (non-Javadoc)
	 * @see it.antanysavage.sm.player.IMaster#resume()
	 */
	public void resume() {
		if (this.status == Status.PAUSED) {
			this.thePlayer.resume();
			this.status = Status.PLAYNG;
		}

	}

	protected void createMediaPlayers() {
		/*
		 * movie
		 */
		MediaPlayer mp = new MediaPlayer();
		mp.setType(AcceptedVideoTypes.VIDEO);
//		mp.setComposite(new MPlayerVideoComposite(programComposite));
//		mp.setPlayer( new VideoMPlayer(this,  mp.getComposite()));	
//		mp.setPlayer( new MPlayerVideoPlayer(this,  mp.getComposite()));
		VLCVideoComposite vlcVideoComposite = new VLCVideoComposite(programComposite);
		VLCVideoPlayer    vlcVideoPlayer    = new VLCVideoPlayer(this, vlcVideoComposite);
		mp.setComposite(vlcVideoComposite);
		mp.setPlayer(vlcVideoPlayer);		
		mediaPlayersList.add(mp);

		/*
		 * hidden window
		 */
		BlackComposite blackComposite = new BlackComposite(programComposite);
		mp = new MediaPlayer();
		mp.setType(AcceptedVideoTypes.HIDDENWINDOW);
		mp.setComposite(blackComposite);
		mp.setPlayer(new HiddenWindowPlayer(this));
		mediaPlayersList.add(mp);

		/*
		 * black
		 */
		mp = new MediaPlayer();
		mp.setType(AcceptedVideoTypes.BLACKWINDOW);
		mp.setComposite(blackComposite);
		mp.setPlayer(new BlackWindowPlayer(this, blackComposite));
		mediaPlayersList.add(mp);


		/*
		 * watch 
		 */
		mp = new MediaPlayer();
		mp.setType(AcceptedVideoTypes.WATCH);
		WatchComposite watchComposite = new WatchComposite(screenManager, programComposite);
		Thread thread = new Thread(watchComposite);
		thread.start();
		mp.setComposite(watchComposite);
		mp.setPlayer(new WatchPlayer(this, watchComposite));
		//		AnalogClockComposite clockComposite = new AnalogClockComposite(programComposite);
		//		mp.setComposite(clockComposite);
		//		mp.setPlayer( new AnalogClockPlayer(this, clockComposite) );
		mediaPlayersList.add(mp);

		/*
		 * photo
		 */
		mp = new MediaPlayer();
		mp.setType(AcceptedVideoTypes.PHOTO);
		PhotoComposite photoComposite = new PhotoComposite(programComposite);
		mp.setComposite(photoComposite);
		mp.setPlayer(new PhotoPlayer(this, photoComposite));
		mediaPlayersList.add(mp);

		/*
		 * weburl
		 */
		mp = new MediaPlayer();
		mp.setType(AcceptedVideoTypes.BROWSER);
		WebUrlComposite webUrlComposite = new WebUrlComposite(programComposite);
		mp.setComposite(webUrlComposite);
		mp.setPlayer( new WebUrlPlayer(this, webUrlComposite) );
		mediaPlayersList.add(mp);

		/*
		 * weather
		 */
		mp = new MediaPlayer();
		mp.setType(AcceptedVideoTypes.WEATHER);
		WebUrlComposite webSpotComposite = new WebUrlComposite(programComposite);
		mp.setComposite(webSpotComposite);
		WeatherPlayer wp = new WeatherPlayer(this, webSpotComposite)  ;
		mp.setPlayer( wp );
		mediaPlayersList.add(mp);
		wp.setWeatherLatlng( getScreenManager().getPlayer().getWeatherLatlng());
		/*
		 * ftp movie
		 */
		mp = new MediaPlayer();
		mp.setType(AcceptedVideoTypes.FTPVIDEO);
//		mp.setComposite(new MPlayerVideoComposite(programComposite));
//		mp.setPlayer( new VideoMPlayer(this,  mp.getComposite()));
//		mp.setPlayer( new MPlayerVideoPlayer(this,  mp.getComposite()));	
		vlcVideoComposite = new VLCVideoComposite(programComposite);
		vlcVideoPlayer    = new VLCVideoPlayer(this, vlcVideoComposite);
		mp.setComposite(vlcVideoComposite);
		mp.setPlayer(vlcVideoPlayer);	
		mediaPlayersList.add(mp);
		

		/*
		 * ftp photo
		 */
		mp = new MediaPlayer();
		mp.setType(AcceptedVideoTypes.FTPIMAGE);
		mp.setComposite(photoComposite);
		mp.setPlayer(new PhotoPlayer(this, photoComposite));
		mediaPlayersList.add(mp);

		//        mp = new MediaPlayer();
		//        mp.setType(AcceptedVideoTypes.WEATHER);
		//        Composite weatherComposite = new WeatherComposite(programComposite);
		//        //weatherComposite.setWeatherLatLng(screenManager.getPlayerSetting().getWeatherLatLng()  );
		//        mp.setComposite(weatherComposite);
		//        mp.setPlayer(new WeatherPlayer(this, weatherComposite));
		//        mediaPlayersList.add(mp);

	}

	public void setAlpha(final int selection) {
		//		Display.getDefault().asyncExec( new Runnable() {			
		//			public void run() {
		//				getShell().setAlpha(selection);
		//			}			
		//		});					
	}

	public void applyAlpha() {

		setAlpha(getScreenManager().getPlayerSetting().getAlpha());
	}

	public void setWeatherLatlng(String weatherLatlng) {
		WeatherPlayer wp = (WeatherPlayer) mediaPlayersList.get(AcceptedVideoTypes.WEATHER.getType()).getPlayer();
		wp.setWeatherLatlng(weatherLatlng);	
	}

}
