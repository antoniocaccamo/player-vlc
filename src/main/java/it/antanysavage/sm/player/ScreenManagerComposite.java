package it.antanysavage.sm.player;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.exception.SequenceMoviePlayerException;
import it.antanysavage.sm.player.model.ScreenSetting;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.timertasks.ScreenCompositeTimerTask;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ExpandAdapter;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;


/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ScreenManagerComposite extends Composite {


	private static Logger logger = LoggerFactory.getLogger(ScreenManagerComposite.class);

	/**
	 * Auto-generated main method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	 * Auto-generated method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		ScreenManagerComposite inst = new ScreenManagerComposite(shell, null, 0);
		Point size = inst.getSize();
		shell.setText("Shell # 1");
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private Button screenLockCheckedBox;
	private Button stopButton;
	private Button pauseButton;
	private Button playButton;
	private Label mediaIDLabel;
	private Label mediaTypeLabel;
	private Label mediaDurationLabel;
	private Label mediaLabel;
	private ProgressBar videoProgressBar;
	private Combo sequenceCombo;
	private Label numberOfVideosLabel;
	private Label durationLabel;
	private Label pathLabel;
	private Button allDayRadioButton;
	private Button timedRadioButton;
	private DateTime fromDateTime;
	private DateTime toDateTime;
	private Button blackWindowRadioButton;
	private Button watchRadioButton;
	private Button activeTimingButton;
	private Group sizeGroup;
	private Spinner widthSpinner;
	private Spinner heightSpinner;
	private Group positionGroup;
	private Button viewScreenCheckedBox;
	private Button referCheckedBox;
	private Spinner leftSpinner;
	private Spinner topSpinner;
	private ReferComposite referWindow;
	private Player player;
	private int index;
	private DimensionAndPositionModifyListener widthSpinnerModifyListener;
	private DimensionAndPositionModifyListener heightSpinnerModifyListener;
	private PositionModifyListener leftSpinnerModifyListener;
	private PositionModifyListener topSpinnerModifyListener;

	//	private IMaster playerMaster = new PlayerMaster(this);

	private IMaster playerMaster ;


	private ScreenSetting setting;

	private Calendar start;

	private Calendar end;

	private ScreenCompositeTimerTask screenCompositeTimerTask;

	private Timer screenCompositeTimer = new Timer();

	private int status = Status.PAUSED;

	private boolean running;

	private Composite mediaComposite ;
	private Group watchFontGroup;
	private Group watchBackGroudGroup;
	private Button watchBackGroudCheckedBox;
	private Button watchBackGroudButton;
	private Label videoProgressLabel;
	private Button screenFadeCheckBox;
	private Group screenFadeGroup;
	private Scale screenFadeScale;
	private Button imageRadioButton;
	private Label label_1;
	private Spinner loopNumberSpinner;


	public ScreenManagerComposite(Composite parent, Player player, int i) {
		super(parent, SWT.NONE);

		this.player = player;
		this.index = i;

		GridLayout thisLayout = new GridLayout();
		thisLayout.makeColumnsEqualWidth = true;
		thisLayout.marginHeight = 0;
		thisLayout.horizontalSpacing = 0;
		thisLayout.marginWidth = 0;
		thisLayout.verticalSpacing = 0;
		setLayout(thisLayout);
		/*
		ExpandBar expandBar = new ExpandBar( this, SWT.V_SCROLL );

		expandBar.addExpandListener(new ExpandAdapter() {
			@Override
			public void itemCollapsed(ExpandEvent arg0) {
				layout();
			}

			@Override
			public void itemExpanded(ExpandEvent arg0) {
				layout();
			}
		});


		GridData expandBarData = new GridData();
		expandBarData.horizontalAlignment = GridData.FILL;
		expandBarData.grabExcessVerticalSpace = true;
		expandBarData.verticalAlignment = GridData.FILL;
		expandBarData.grabExcessHorizontalSpace = true;
		expandBar.setLayoutData(expandBarData);
		expandBar.setBackgroundMode(SWT.INHERIT_FORCE);
		 */

		if( Player.ENABLE_NEWS ){
			this.playerMaster = new NewsPlayerMaster(this);
		}else {
			this.playerMaster = new PlayerMaster(this);
		}


		CTabFolder sectionTabFolder = new CTabFolder(this, SWT.NONE);	
		GridData tabFolder1LData = new GridData();
		tabFolder1LData.verticalAlignment = GridData.FILL;
		tabFolder1LData.horizontalAlignment = GridData.FILL;
		tabFolder1LData.grabExcessVerticalSpace = true;
		tabFolder1LData.grabExcessHorizontalSpace = true;
		sectionTabFolder.setLayoutData(tabFolder1LData);
		sectionTabFolder.setBorderVisible(true);

		populateTabFolder ( sectionTabFolder );

		/*
		ExpandItem itemPlayer = new ExpandItem(expandBar , SWT.NONE, 0);
		itemPlayer.setText("Player");
		itemPlayer.setHeight(sectionTabFolder.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		itemPlayer.setControl(sectionTabFolder);

		populateExpandBar( this );
		layout();
		 */

		//referWindow = new ReferComposite(this);

	}
	

	void block(final boolean block) {
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				
				loopNumberSpinner.setEnabled( ! block );

				//				screenFadeCheckBox.setEnabled(! block);

				getPlayerMaster().block( block  );
				sizeGroup.setEnabled( ! block );
				Control[] children = sizeGroup.getChildren();
				if ( children != null ) {
					for (int i = 0 ; i < children.length; i++) {
						children[i].setEnabled(! block);
					}
				}
				positionGroup.setEnabled( ! block );
				children = positionGroup.getChildren();
				if ( children != null ) {
					for (int i = 0 ; i < children.length; i++) {
						children[i].setEnabled(! block);
					}
				}
				viewScreenCheckedBox.setEnabled( ! block );
				sequenceCombo.setEnabled( ! block );
				//				applyScreenModifyButton.setEnabled(!block);
				referCheckedBox.setEnabled(!block);

				watchBackGroudGroup.setEnabled( ! block );
				children = watchBackGroudGroup.getChildren();
				if ( children != null ) {
					for (int i = 0 ; i < children.length; i++) {
						children[i].setEnabled(! block);
					}
				}

				watchFontGroup.setEnabled( ! block );
				children = watchFontGroup.getChildren();
				if ( children != null ) {
					for (int i = 0 ; i < children.length; i++) {
						children[i].setEnabled(! block);
					}
				}

				//				screenFadeGroup.setEnabled( ! block );
				//				children = screenFadeGroup.getChildren();
				//				if ( children != null ) {
				//					for (int i = 0 ; i < children.length; i++) {
				//						children[i].setEnabled(! block);
				//					}
				//				}
			}
		});

	}

	private void createControlsListeners() {

		//		widthSpinner.addFocusListener(new FocusListener() {
		//
		//			public void focusLost(FocusEvent arg0) {
		//				changePlayerLocation();
		//				changePlayerSize();
		//
		//			}
		//
		//			public void focusGained(FocusEvent arg0) {
		//				// TODO Auto-generated method stub
		//
		//			}
		//		});
		//
		//		heightSpinner.addFocusListener(new FocusListener() {
		//
		//			public void focusLost(FocusEvent arg0) {
		//				changePlayerLocation();
		//				changePlayerSize();
		//
		//			}
		//
		//			public void focusGained(FocusEvent arg0) {
		//				// TODO Auto-generated method stub
		//
		//			}
		//		});
		//
		//		leftSpinner.addFocusListener(new FocusListener() {
		//
		//			public void focusLost(FocusEvent arg0) {
		//				changePlayerLocation();
		//
		//
		//			}
		//
		//			public void focusGained(FocusEvent arg0) {
		//				// TODO Auto-generated method stub
		//
		//			}
		//		});
		//
		//		topSpinner.addFocusListener(new FocusListener() {
		//
		//			public void focusLost(FocusEvent arg0) {
		//				changePlayerLocation();
		//
		//
		//			}
		//
		//			public void focusGained(FocusEvent arg0) {
		//				// TODO Auto-generated method stub
		//
		//			}
		//		});

		//		applyScreenModifyButton.addSelectionListener( new SelectionAdapter() {
		//			@Override
		//			public void widgetSelected(SelectionEvent arg0) {
		//				Display.getCurrent().syncExec(new Runnable() {					
		//					public void run() {						
		//						changePlayerLocation();
		//						changePlayerSize();
		//					}
		//				});
		//			}
		//		});
		//
		//
		widthSpinnerModifyListener = new DimensionAndPositionModifyListener();
		widthSpinner.addModifyListener(
				//				new ModifyListener() {
				//					public void modifyText(ModifyEvent evt) {
				//				changePlayerLocation();
				//				changePlayerSize();
				//				}				
				//			}
				widthSpinnerModifyListener
				);
		heightSpinnerModifyListener = new DimensionAndPositionModifyListener();
		heightSpinner.addModifyListener(
				//			new ModifyListener() {
				//				public void modifyText(ModifyEvent evt) {				
				//					changePlayerLocation();
				//					changePlayerSize();
				//				}
				//			}
				heightSpinnerModifyListener
				);
		//
		//
		//		widthSpinner.addSelectionListener( new SelectionAdapter() {
		//			public void widgetSelected(SelectionEvent arg0) {
		//				Spinner sp = (Spinner) arg0.getSource();
		//				changePlayerSize();
		//			}
		//		});
		//
		//		heightSpinner.addSelectionListener( new SelectionAdapter() {
		//			public void widgetSelected(SelectionEvent arg0) {
		//				changePlayerSize();
		//			}
		//		});
		//
		leftSpinnerModifyListener = new PositionModifyListener();
		leftSpinner.addModifyListener(
				//				new ModifyListener() {
				//					public void modifyText(ModifyEvent evt) {				
				//						changePlayerLocation();
				//					}
				//				}
				leftSpinnerModifyListener
				);
		//
		topSpinnerModifyListener = new PositionModifyListener();
		topSpinner.addModifyListener(
				//				new ModifyListener() {
				//					public void modifyText(ModifyEvent evt) {				
				//						changePlayerLocation();
				//					}
				//				}
				topSpinnerModifyListener
				);

		sequenceCombo.addModifyListener(new ModifyListener()
		{
			public void modifyText(org.eclipse.swt.events.ModifyEvent e)
			{
				if ( getPlayerMaster().getStatus() == Status.PLAYNG ) 
					return;

				String seq = sequenceCombo.getItem(sequenceCombo.getSelectionIndex());
				if  ( ! Utils.isAnEmptyString(seq)) {
					final Program smp = (Program) player.getSequenceSMP(seq);
					getPlayerMaster().setProgram(smp);
					Display.getDefault().syncExec(
							new Runnable() {
								public void run() {									
									updatePlayLabel(smp);
								}
							}
							);
					setting.setProgramFile(smp.getFilepath());
				}

			}



		});


		screenLockCheckedBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				boolean b = screenLockCheckedBox.getSelection();
				block(b);
				if ( setting != null ) {
					setting.setLock(b);
				}
			}


		});

		//		screenFadeCheckBox.addSelectionListener(
		//				new SelectionAdapter() {
		//					public void widgetSelected(SelectionEvent arg0) {
		//						if ( setting != null) {
		//								setting.setFade(screenFadeCheckBox.getSelection());
		//						}
		//					}
		//				}
		//		);




		viewScreenCheckedBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				//					System.out.println("hideScreenCheckedBox.widgetSelected " + viewScreenCheckedBox.getSelection() +", event="+evt);

				getPlayerMaster().setVisibility(viewScreenCheckedBox.getSelection());

				if ( setting != null ) {
					setting.setViewScreen( viewScreenCheckedBox.getSelection());
				}

			}


		});


		allDayRadioButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				//				setting.setAllDay(allDayRadioButton.getSelection());
			}
		}
				);

		timedRadioButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				//				setting.setTimed(timedRadioButton.getSelection());
				final boolean b = timedRadioButton.getSelection(); 
				if ( b ) {
					if ( setting.getFrom() == null ) {
						setting.setFrom(new Date());
					}
					if ( setting.getTo() == null ) {
						setting.setTo(new Date());
					}
				}
				updateTiming(b);
			}


		}
				);

		fromDateTime.addSelectionListener(  new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if ( setting != null ) {
					setting.getFrom().setHours(fromDateTime.getHours());
					setting.getFrom().setMinutes(fromDateTime.getMinutes());
				}
			}
		}
				);

		toDateTime.addSelectionListener(  new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if ( setting != null ) {
					setting.getTo().setHours(toDateTime.getHours());
					setting.getTo().setMinutes(toDateTime.getMinutes());
				}
			}
		}
				);

		blackWindowRadioButton.addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						if ( setting != null ) {
							setting.setWhenNotActiveBlack(blackWindowRadioButton.getSelection());
						}
					};
				}
				);

		watchRadioButton.addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						if ( setting != null ) {
							setting.setWhenNotActiveWatch(watchRadioButton.getSelection());
						}
					};
				}
				);

		imageRadioButton.addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						if ( setting != null ) {
							setting.setWhenNotActiveImage(imageRadioButton.getSelection());
						}
					};
				}
				);

		stopButton.addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						//						setRunning(false);
						stop();
					};
				}
				);	

		playButton.addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						//						setRunning(true);
						play();
					};
				}
				);

		referCheckedBox.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if ( referCheckedBox.getSelection() ) {
					widthSpinner.removeModifyListener(widthSpinnerModifyListener);
					heightSpinner.removeModifyListener(leftSpinnerModifyListener);
					topSpinner.removeModifyListener(topSpinnerModifyListener);
					leftSpinner.removeModifyListener(leftSpinnerModifyListener);					
					viewScreenCheckedBox.setEnabled(false);
					Point size = new Point(widthSpinner.getSelection(), heightSpinner.getSelection());				
					Point location = new Point(leftSpinner.getSelection(), topSpinner.getSelection());
					referWindow.getShell().setSize(size);
					referWindow.getShell().setLocation(location);
					referWindow.getShell().setVisible(true);
					referWindow.addAdpter();



					getPlayerMaster().getShell().setVisible(false);

				} else {
					Point size     = referWindow.getShell().getSize();				
					Point location = referWindow.getShell().getLocation();


					widthSpinner.setSelection(size.x);
					heightSpinner.setSelection(size.y);
					topSpinner.setSelection(location.y);
					leftSpinner.setSelection(location.x);

					referWindow.getShell().setVisible(false);
					referWindow.removeAdpter();


					getPlayerMaster().getShell().setVisible(true);
					widthSpinner.addModifyListener(widthSpinnerModifyListener);
					heightSpinner.addModifyListener(leftSpinnerModifyListener);
					topSpinner.addModifyListener(topSpinnerModifyListener);
					leftSpinner.addModifyListener(leftSpinnerModifyListener);
					viewScreenCheckedBox.setEnabled(true);
					viewScreenCheckedBox.setSelection(true);
				}
				logger.debug("player master bounds : " + getPlayerMaster().getShell().getBounds());
			}
		});

		//		applyScreenModifyButton.addSelectionListener( new SelectionAdapter() {
		//			@Override
		//			public void widgetSelected(SelectionEvent arg0) {

		//				Rectangle bounds = null;
		//				if ( referCheckedBox.getSelection() ) {
		//					bounds = new Rectangle(leftSpinner.getSelection(),
		//							topSpinner.getSelection(),
		//							widthSpinner.getSelection(), 
		//							heightSpinner.getSelection()
		//						);
		//				} else {
		//					bounds = getPlayerMaster().getShell().computeTrim(
		//							leftSpinner.getSelection(),
		//							topSpinner.getSelection(),
		//							widthSpinner.getSelection(), 
		//							heightSpinner.getSelection()
		//					);
		//				}
		//				
		//				Point     size = new Point(widthSpinner.getSelection(), heightSpinner.getSelection());
		//				Point location = new Point(leftSpinner.getSelection(), topSpinner.getSelection());
		//				
		//				setting.setSize(size);
		//				setting.setLocation(location);
		//				
		//				getPlayerMaster().getShell().setBounds(bounds);
		//
		//				if ( ! setting.getSize().equals(size) ) {
		//					setting.setSize(size);
		//					Point sizePlayer = calculatePlayerSize(setting.getSize(),size);
		//					getPlayerMaster().getShell().setSize(sizePlayer);					
		//				}
		//
		//				if ( ! setting.getLocation().equals(location) )  {
		//					setting.setLocation(location);
		//					Point locationPlayer = calculatePlayerLocation(setting.getLocation(), location);
		//					getPlayerMaster().getShell().setLocation(locationPlayer);
		//				}
		//				logger.info("player master bounds : " + getPlayerMaster().getShell().getBounds() + " | player master clientArea : " +getPlayerMaster().getShell().getClientArea() );
		//			}
		//		});
		//
		//		this.myControlAdapter = new MyControlAdpater();
		//		getPlayerMaster().addControllerAdapter(myControlAdapter);

		activeTimingButton.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateTimingActivation();
			}

		});

		pauseButton.addSelectionListener( new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if ( pauseButton.getSelection() ) {
					getPlayerMaster().pause();
				} else {
					getPlayerMaster().resume();							
				}
			}
		});

		watchBackGroudCheckedBox.addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						boolean check = watchBackGroudCheckedBox.getSelection();
						watchBackGroudButton.setEnabled(check);
						if ( ! check ) {
							getPlayerSetting().setWatchImageFile(null);
						}

					}
				}
				);

		watchBackGroudButton.addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						FileDialog fileDialog = new FileDialog( Display.getDefault().getActiveShell() , SWT.OPEN );
						if ( getPlayerSetting().getWatchImageFile() != null ) {
							fileDialog.setFileName(getPlayerSetting().getWatchImageFile().getAbsolutePath());
						}
						fileDialog.setFilterExtensions(Player.PHOTO_FILTERS);
						String string = fileDialog.open();
						if ( ! Utils.isAnEmptyString(string) ) {
							File f = new File(string);
							if ( f.exists() && f.isFile() && f.canRead() ) {
								getPlayerSetting().setWatchImageFile(f);
							}
						}	
					}
				}
				);

		//		screenFadeScale.addSelectionListener(
		//				new SelectionAdapter() {
		//					@Override
		//					public void widgetSelected(SelectionEvent arg0) {
		//						final int alpha = screenFadeScale.getSelection();
		//						screenFadeScale.setToolTipText( String.valueOf(alpha));						
		//						Display.getDefault().syncExec(
		//								new Runnable() { 
		//									public void run() {									
		//										getPlayerMaster().setAlpha( alpha );
		//									}
		//								}
		//						);
		//						getPlayerSetting().setAlpha(alpha);
		//					}
		//				}
		//		);
		
		loopNumberSpinner.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getPlayerSetting().setLoopNumber( loopNumberSpinner.getSelection() );
			}
		} );

	}

	public IMaster getPlayerMaster() {
		return playerMaster;
	}

	public ScreenSetting getPlayerSetting() {
		return this.setting;
	}

	public boolean isRunning() {
		return running;
	}






	private void populateActivationComposite(Composite activationComposite) {

		GridLayout activationGroupLayout = new GridLayout(2, false);
		activationGroupLayout.makeColumnsEqualWidth = true;
		activationComposite.setLayout(activationGroupLayout);
		GridData activationLData = new GridData();
		activationLData.grabExcessHorizontalSpace = true;
		activationLData.horizontalAlignment = GridData.FILL;
		activationLData.verticalAlignment = GridData.BEGINNING;
		//		activationLData.grabExcessVerticalSpace = true;
		activationComposite.setLayoutData(activationLData);

		Group timingGroup = new Group(activationComposite, SWT.NONE);
		GridLayout timingGroupLayout = new GridLayout();
		timingGroupLayout.makeColumnsEqualWidth = true;
		timingGroup.setLayout(timingGroupLayout);
		timingGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING));
		GridData timingGroupLData = new GridData();
		timingGroupLData.grabExcessHorizontalSpace = true;
		timingGroupLData.horizontalAlignment = GridData.FILL;
		timingGroupLData.verticalAlignment = GridData.BEGINNING;
		timingGroupLData.grabExcessVerticalSpace = true;
		timingGroupLData.verticalSpan = 2;
		timingGroup.setLayoutData(timingGroupLData);

		allDayRadioButton = new Button(timingGroup, SWT.RADIO | SWT.LEFT);
		GridData allDayRadioButtonLData = new GridData();
		allDayRadioButtonLData.grabExcessHorizontalSpace = true;
		allDayRadioButtonLData.horizontalAlignment = GridData.FILL;
		allDayRadioButton.setLayoutData(allDayRadioButtonLData);
		allDayRadioButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_ALLDAY));


		GridData seperatorLData = new GridData();
		seperatorLData.grabExcessHorizontalSpace = true;
		seperatorLData.horizontalAlignment = GridData.FILL;
		Label separator = new Label(timingGroup, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.SHADOW_OUT | SWT.CENTER);
		separator.setLayoutData(seperatorLData);

		timedRadioButton = new Button(timingGroup, SWT.RADIO | SWT.LEFT);
		GridData timedRadioButtonLData = new GridData();
		timedRadioButtonLData.grabExcessHorizontalSpace = true;
		timedRadioButtonLData.horizontalAlignment = GridData.FILL;
		timedRadioButton.setLayoutData(timedRadioButtonLData);
		timedRadioButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED));


		GridData timingCompositeLData = new GridData();
		timingCompositeLData.grabExcessHorizontalSpace = true;
		timingCompositeLData.horizontalAlignment = GridData.FILL;
		//		timingCompositeLData.grabExcessVerticalSpace = false;
		timingCompositeLData.verticalAlignment = GridData.BEGINNING;
		Composite timingComposite = new Composite(timingGroup, SWT.NONE);
		GridLayout timingCompositeLayout = new GridLayout(2, false);
		timingCompositeLayout.makeColumnsEqualWidth = true;
		timingComposite.setLayout(timingCompositeLayout);
		timingComposite.setLayoutData(timingCompositeLData);

		Label fromLabel = new Label(timingComposite, SWT.NONE);
		GridData fromLabelLData = new GridData();
		fromLabelLData.grabExcessHorizontalSpace = true;
		fromLabelLData.horizontalAlignment = GridData.FILL;
		//		fromLabelLData.grabExcessVerticalSpace = true;
		fromLabelLData.verticalAlignment = GridData.FILL;
		fromLabel.setLayoutData(fromLabelLData);
		fromLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED_FROM));

		GridData fromDateTimeLData = new GridData();
		fromDateTimeLData.grabExcessHorizontalSpace = true;
		fromDateTimeLData.horizontalAlignment = GridData.FILL;
		//		fromDateTimeLData.grabExcessVerticalSpace = true;
		fromDateTimeLData.verticalAlignment = GridData.FILL;
		fromDateTime = new DateTime(timingComposite, SWT.TIME | SWT.SHORT);
		fromDateTime.setLayoutData(fromDateTimeLData);



		Label toLabel = new Label(timingComposite, SWT.NONE);
		GridData toLabelLData = new GridData();
		toLabelLData.grabExcessHorizontalSpace = true;
		toLabelLData.horizontalAlignment = GridData.FILL;
		toLabel.setLayoutData(toLabelLData);
		toLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED_TO));

		GridData toDateTimeLData = new GridData();
		toDateTimeLData.grabExcessHorizontalSpace = true;
		toDateTimeLData.horizontalAlignment = GridData.FILL;
		//		toDateTimeLData.grabExcessVerticalSpace = true;
		toDateTimeLData.verticalAlignment = GridData.FILL;
		toDateTime = new DateTime(timingComposite, SWT.TIME | SWT.SHORT);
		toDateTime.setLayoutData(toDateTimeLData);


		Group disactiveGroup = new Group(activationComposite, SWT.NONE);
		GridLayout disactiveGroupLayout = new GridLayout();
		disactiveGroupLayout.makeColumnsEqualWidth = true;
		disactiveGroup.setLayout(disactiveGroupLayout);
		GridData disactiveGroupLData = new GridData();
		disactiveGroupLData.horizontalAlignment = GridData.FILL;
		disactiveGroupLData.grabExcessHorizontalSpace = true;
		disactiveGroupLData.verticalAlignment = GridData.BEGINNING;
		//		disactiveGroupLData.grabExcessVerticalSpace = true;
		disactiveGroup.setLayoutData(disactiveGroupLData);
		disactiveGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_WHEN_NOT_ACTIVE));

		blackWindowRadioButton = new Button(disactiveGroup, SWT.RADIO | SWT.LEFT);
		GridData blackWindowRadioButtonLData = new GridData();
		blackWindowRadioButtonLData.horizontalAlignment = GridData.FILL;
		blackWindowRadioButtonLData.grabExcessHorizontalSpace = true;
		blackWindowRadioButtonLData.verticalAlignment = GridData.FILL;
		blackWindowRadioButton.setLayoutData(blackWindowRadioButtonLData);
		blackWindowRadioButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_WHEN_NOT_ACTIVE_BLACK));


		GridData separator2LData = new GridData();
		separator2LData.horizontalAlignment = GridData.FILL;
		separator2LData.grabExcessHorizontalSpace = true;
		separator = new Label(disactiveGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator.setLayoutData(separator2LData);

		watchRadioButton = new Button(disactiveGroup, SWT.RADIO | SWT.LEFT);
		GridData watchRadioButtonLData = new GridData();
		watchRadioButtonLData.horizontalAlignment = GridData.FILL;
		watchRadioButtonLData.grabExcessHorizontalSpace = true;
		watchRadioButtonLData.verticalAlignment = GridData.FILL;
		watchRadioButton.setLayoutData(watchRadioButtonLData);
		watchRadioButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_WHEN_NOT_ACTIVE_WATCH));


		separator = new Label(disactiveGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator2LData = new GridData();
		separator2LData.horizontalAlignment = GridData.FILL;
		separator2LData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(separator2LData);


		imageRadioButton = new Button(disactiveGroup, SWT.RADIO | SWT.LEFT);
		GridData imageRadioButtonLData = new GridData();
		imageRadioButtonLData.horizontalAlignment = GridData.FILL;
		imageRadioButtonLData.grabExcessHorizontalSpace = true;
		imageRadioButtonLData.verticalAlignment = GridData.FILL;
		imageRadioButton.setLayoutData(imageRadioButtonLData);
		imageRadioButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_WHEN_NOT_ACTIVE_IMAGE));


		timingGroup.pack();
		disactiveGroup.pack();

		activeTimingButton = new Button(activationComposite, SWT.PUSH);		
		activeTimingButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_ACTIVE));
		activationLData.horizontalAlignment = GridData.FILL;

	}

	private void populatePlayerComposite(Composite playerComposite) {

		//		populateSequenceComposite(playerComposite);

		GridData playerCompositeLData = new GridData();
		playerCompositeLData.horizontalAlignment = GridData.FILL;
		playerCompositeLData.grabExcessVerticalSpace = true;
		playerCompositeLData.verticalAlignment = GridData.FILL;
		playerCompositeLData.grabExcessHorizontalSpace = true;


		GridLayout playerCompositeLayout = new GridLayout(2, false);
		playerCompositeLayout.makeColumnsEqualWidth = false;
		playerComposite.setLayout(playerCompositeLayout);
		playerComposite.setLayoutData(playerCompositeLData);

		Composite buttonsComposite = new Composite(playerComposite, SWT.NONE);
		GridLayout buttonsCompositeLayout = new GridLayout(1, false);
		buttonsCompositeLayout.makeColumnsEqualWidth = true;
		GridData buttonsCompositeLData = new GridData();
		buttonsCompositeLData.grabExcessVerticalSpace = true;
		buttonsCompositeLData.verticalAlignment = GridData.BEGINNING;
		buttonsComposite.setLayoutData(buttonsCompositeLData);
		buttonsComposite.setLayout(buttonsCompositeLayout);


		//		firstButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		//		GridData firstButtonLData = new GridData();
		//		firstButtonLData.grabExcessHorizontalSpace = true;
		//		firstButtonLData.horizontalAlignment = GridData.CENTER;
		//
		//		firstButton.setLayoutData(firstButtonLData);		
		//		firstButton.setImage( new Image(getDisplay(), ClassLoader.getSystemResourceAsStream("images/player/first.png") ) );
		//
		//		prevButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		//		GridData prevButtonLData = new GridData();
		//		prevButtonLData.grabExcessHorizontalSpace = true;
		//		prevButtonLData.horizontalAlignment = GridData.CENTER;
		//		prevButton.setLayoutData(prevButtonLData);		
		//		prevButton.setImage( new Image(getDisplay(), ClassLoader.getSystemResourceAsStream("images/player/prev.png") ) );

		stopButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		GridData stopButtonLData = new GridData();		
		stopButtonLData.horizontalAlignment = GridData.CENTER;
		stopButton.setLayoutData(stopButtonLData);
		//		logger.info("SystemUtils.IS_OS_UNIX is " + SystemUtils.IS_OS_UNIX );
		//		if ( SystemUtils.IS_OS_UNIX ) {
		//			stopButton.setText("Stop");			
		//		}
		//		else {
		stopButton.setImage( new Image(getDisplay(), ClassLoader.getSystemResourceAsStream("images/player/stop.png") ) );
		//		}


		pauseButton = new Button(buttonsComposite, SWT.TOGGLE | SWT.CENTER);
		GridData pauseButtonLData = new GridData();		
		pauseButtonLData.horizontalAlignment = GridData.CENTER;
		pauseButton.setLayoutData(pauseButtonLData);
		//		if ( SystemUtils.IS_OS_UNIX )
		//			pauseButton.setText("Pause");
		//		else
		pauseButton.setImage( new Image(getDisplay(), ClassLoader.getSystemResourceAsStream("images/player/pause.png") ) );
		pauseButton.setEnabled(false);

		playButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		GridData applyButtonLData = new GridData();
		applyButtonLData.grabExcessHorizontalSpace = true;
		applyButtonLData.horizontalAlignment = GridData.CENTER;
		playButton.setLayoutData(applyButtonLData);
		//		if ( SystemUtils.IS_OS_UNIX )
		//			playButton.setText("Play");
		//		else
		playButton.setImage( new Image(getDisplay(), ClassLoader.getSystemResourceAsStream("images/player/play.png") ) );

		// player - media composite

		mediaComposite = new Composite(playerComposite, SWT.NONE);

		GridLayout mediaCompositeGridLayout = new GridLayout(2,false);

		GridData mediaCompositeLData = new GridData();
		mediaCompositeLData.verticalAlignment = GridData.FILL;
		mediaCompositeLData.grabExcessHorizontalSpace = true;
		mediaCompositeLData.grabExcessVerticalSpace = true;
		mediaCompositeLData.horizontalAlignment = GridData.FILL;
		mediaCompositeLData.verticalAlignment = GridData.FILL;

		mediaComposite.setLayout( mediaCompositeGridLayout);
		mediaComposite.setLayoutData(mediaCompositeLData);

		Label l = new Label(mediaComposite, SWT.NONE);
		l.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_9));
		
		loopNumberSpinner = new Spinner(mediaComposite, SWT.BORDER);
		loopNumberSpinner.setMinimum(0);
		loopNumberSpinner.setMaximum(Integer.MAX_VALUE);
		loopNumberSpinner.setIncrement(1);


		label_1 = new Label(mediaComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData mediaLabelLData = new GridData();
		mediaLabelLData.grabExcessHorizontalSpace = true;
		mediaLabelLData.horizontalAlignment = GridData.FILL;
		mediaLabelLData.horizontalSpan = 2;
		label_1.setLayoutData(mediaLabelLData);


		l = new Label(mediaComposite, SWT.NONE);
		l.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_0));		
		mediaLabelLData = new GridData();
		mediaLabelLData.grabExcessHorizontalSpace = true;
		mediaLabelLData.horizontalAlignment = GridData.FILL;
		mediaIDLabel = new Label (mediaComposite, SWT.NONE);
		mediaIDLabel.setLayoutData(mediaLabelLData);


		l = new Label(mediaComposite, SWT.NONE);
		l.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_1));		
		mediaTypeLabel = new Label (mediaComposite, SWT.NONE);
		mediaLabelLData = new GridData();
		mediaLabelLData.grabExcessHorizontalSpace = true;
		mediaLabelLData.horizontalAlignment = GridData.FILL;
		mediaTypeLabel.setLayoutData(mediaLabelLData);

		l = new Label(mediaComposite, SWT.NONE);
		l.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_2));	
		mediaDurationLabel = new Label (mediaComposite, SWT.NONE);
		mediaLabelLData = new GridData();
		mediaLabelLData.grabExcessHorizontalSpace = true;
		mediaLabelLData.horizontalAlignment = GridData.FILL;
		mediaDurationLabel.setLayoutData(mediaLabelLData);

		l = new Label(mediaComposite, SWT.NONE);
		l.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_3));
		mediaLabel = new Label (mediaComposite, SWT.NONE);
		mediaLabelLData = new GridData();
		mediaLabelLData.grabExcessHorizontalSpace = true;
		mediaLabelLData.horizontalAlignment = GridData.FILL;
		mediaLabel.setLayoutData(mediaLabelLData);




		/*

		Composite mediaComposite = new Composite(playerComposite, SWT.NONE);
		GridLayout mediaCompositeGridLayout = new GridLayout();
		mediaCompositeGridLayout.numColumns = 2;
		mediaComposite.setLayout( mediaCompositeGridLayout);

		GridData mediaCompositeLData = new GridData();
		mediaCompositeLData.verticalAlignment = GridData.FILL;
		mediaCompositeLData.grabExcessHorizontalSpace = true;
		mediaCompositeLData.grabExcessVerticalSpace = true;
		mediaCompositeLData.horizontalAlignment = GridData.FILL;
		mediaCompositeLData.verticalAlignment = GridData.FILL;
		mediaComposite.setLayoutData(mediaCompositeLData);

		GridData labelLData = new GridData();
		labelLData.horizontalAlignment = GridData.END;
		//labelLData.grabExcessHorizontalSpace = true;

		GridData mediaLabelLData = new GridData();
		mediaLabelLData.grabExcessHorizontalSpace = true;
		mediaLabelLData.horizontalAlignment = GridData.FILL;
		//		labelLData.grabExcessVerticalSpace = true;
		//		labelLData.verticalAlignment = GridData.BEGINNING;

		Label l = new Label(mediaComposite, SWT.NONE);
		l.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_0));
		l.setLayoutData(labelLData);
		mediaIDLabel = new Label (mediaComposite, SWT.NONE);
		mediaIDLabel.setLayoutData(mediaLabelLData);

		l = new Label(mediaComposite, SWT.NONE);
		l.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_1));
		l.setLayoutData(labelLData);
		mediaTypeLabel = new Label (mediaComposite, SWT.NONE);
		mediaTypeLabel.setLayoutData(mediaLabelLData);


		l = new Label(mediaComposite, SWT.NONE);
		l.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_2));
		l.setLayoutData(labelLData);
		mediaDurationLabel = new Label (mediaComposite, SWT.NONE);
		mediaDurationLabel.setLayoutData(mediaLabelLData);

		l = new Label(mediaComposite, SWT.NONE);
		l.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_3));
		l.setLayoutData(labelLData);
		GridData labelMediaLData = new GridData();
		labelMediaLData.grabExcessHorizontalSpace = true;
		labelMediaLData.horizontalAlignment = GridData.FILL;

		mediaLabel = new Label (mediaComposite, SWT.NONE);
		mediaLabel.setLayoutData(labelMediaLData);

		 */
		videoProgressLabel = new Label(playerComposite, SWT.CENTER);
		GridData videoProgressLabelLData = new GridData();
		videoProgressLabelLData.grabExcessVerticalSpace = true;
		videoProgressLabelLData.horizontalSpan = 2;
		videoProgressLabelLData.grabExcessHorizontalSpace = true;
		videoProgressLabelLData.horizontalAlignment = GridData.FILL;
		videoProgressLabelLData.verticalAlignment = SWT.TOP;
		videoProgressLabel.setLayoutData(videoProgressLabelLData);

		videoProgressBar = new ProgressBar(playerComposite, SWT.SMOOTH);
		GridData videoProgressBarLData = new GridData();
		videoProgressBarLData.grabExcessVerticalSpace = true;
		videoProgressBarLData.horizontalSpan = 2;
		videoProgressBarLData.grabExcessHorizontalSpace = true;
		videoProgressBarLData.horizontalAlignment = GridData.FILL;
		videoProgressBarLData.verticalAlignment = SWT.CENTER;
		videoProgressBar.setLayoutData(videoProgressBarLData);


		//		nextButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		//		GridData nextButtonLData = new GridData();
		//		nextButtonLData.grabExcessHorizontalSpace = true;
		//		nextButtonLData.horizontalAlignment = GridData.CENTER;
		//		nextButton.setLayoutData(nextButtonLData);		
		//		nextButton.setImage( new Image(getDisplay(), ClassLoader.getSystemResourceAsStream("images/player/next.png") ) );
		//
		//		lastButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		//		GridData lasttButtonLData = new GridData();
		//		lasttButtonLData.grabExcessHorizontalSpace = true;
		//		lasttButtonLData.horizontalAlignment = GridData.CENTER;
		//		lastButton.setLayoutData(lasttButtonLData);		
		//		lastButton.setImage( new Image(getDisplay(), ClassLoader.getSystemResourceAsStream("images/player/last.png") ) );


	}

	private void populateScreenComposite(Composite screenComposite) {

		GridData compositeLData = new GridData();
		compositeLData.horizontalAlignment = GridData.FILL;
		compositeLData.grabExcessHorizontalSpace = true;
		compositeLData.verticalAlignment = GridData.FILL;
		screenComposite.setLayoutData(compositeLData);
		screenComposite.setLayout( new GridLayout(2, true));  

		screenLockCheckedBox = new Button(screenComposite, SWT.CHECK | SWT.LEFT);
		GridData screenLockCheckBoxLData = new GridData();
		screenLockCheckBoxLData.grabExcessHorizontalSpace = true;
		screenLockCheckBoxLData.grabExcessVerticalSpace = true;
		screenLockCheckBoxLData.horizontalAlignment = SWT.FILL;
		//		screenLockCheckBoxLData.horizontalSpan = 2;
		screenLockCheckedBox.setLayoutData(screenLockCheckBoxLData);
		screenLockCheckedBox.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_LOCK));

		//		screenFadeGroup = new Group(screenComposite, SWT.NONE);
		//		screenFadeGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_FADE));
		//		GridLayout screenFadeGroupLayout = new GridLayout(1, false) ;
		//		screenFadeGroupLayout.makeColumnsEqualWidth = false;
		//		screenFadeGroup.setLayout( screenFadeGroupLayout);		
		//		GridData screenFadeGroupLData = new GridData();
		//		screenFadeGroupLData.grabExcessHorizontalSpace = true;
		//		screenFadeGroupLData.horizontalAlignment = GridData.FILL;
		//		
		//		screenFadeGroup.setLayoutData(screenFadeGroupLData);
		//		
		//		screenFadeScale = new Scale(screenFadeGroup, SWT.HORIZONTAL);
		//		GridData screenFadeScaleLData = new GridData();
		//		screenFadeScaleLData.grabExcessHorizontalSpace = true;
		//		screenFadeScaleLData.grabExcessVerticalSpace = true;
		//		screenFadeScaleLData.horizontalAlignment = GridData.FILL;		
		//		
		//		screenFadeScale.setLayoutData(screenFadeScaleLData);
		//		screenFadeScale.setMinimum(0);
		//		screenFadeScale.setIncrement(5);
		//		screenFadeScale.setMaximum(255);


		//		screenFadeCheckBox = new Button(screenFadeGroup, SWT.CHECK | SWT.LEFT);
		//		GridData screenFadeCheckBoxLData = new GridData();
		//		screenFadeCheckBoxLData.grabExcessHorizontalSpace = false;
		//		screenFadeCheckBoxLData.grabExcessVerticalSpace = true;
		//		screenFadeCheckBoxLData.horizontalAlignment = GridData.END;
		//		screenLockCheckBoxLData.horizontalSpan = 2;
		//		screenFadeCheckBox.setLayoutData(screenFadeCheckBoxLData);
		//		screenFadeCheckBox.setText("Satura");



		// watch background
		watchBackGroudGroup = new Group(screenComposite, SWT.NONE);
		watchBackGroudGroup.setText( LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_WATCH_BACKGROUND));
		GridLayout watchBackGroudLayout = new GridLayout(2, false);
		GridData watchBackGroudLData = new GridData();
		watchBackGroudLData.verticalAlignment = SWT.FILL;
		watchBackGroudLData.grabExcessVerticalSpace = true;
		watchBackGroudLData.grabExcessHorizontalSpace = true;
		watchBackGroudLData.horizontalAlignment = GridData.FILL;
		watchBackGroudGroup.setLayout(watchBackGroudLayout);
		watchBackGroudGroup.setLayoutData(watchBackGroudLData); 

		watchBackGroudCheckedBox = new Button(watchBackGroudGroup, SWT.CHECK | SWT.LEFT);
		watchBackGroudCheckedBox.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_WATCH_BACKGROUND_IMAGE));
		GridData watchBackGroudCheckedBoxLData = new GridData();
		watchBackGroudCheckedBoxLData.grabExcessHorizontalSpace = true;
		watchBackGroudCheckedBoxLData.grabExcessVerticalSpace = true;
		watchBackGroudCheckedBoxLData.horizontalAlignment = SWT.FILL;
		watchBackGroudCheckedBox.setLayoutData(watchBackGroudCheckedBoxLData);

		watchBackGroudButton = new Button(watchBackGroudGroup, SWT.PUSH | SWT.LEFT);
		watchBackGroudButton.setText( LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CHOOSE_FILE));
		GridData watchBackGroudButtonLData = new GridData();
		watchBackGroudButtonLData.grabExcessHorizontalSpace = true;
		watchBackGroudButtonLData.grabExcessVerticalSpace = true;
		watchBackGroudButtonLData.horizontalAlignment = SWT.FILL;
		watchBackGroudButton.setLayoutData(watchBackGroudButtonLData);


		// watch font button 
		watchFontGroup = new Group(screenComposite, SWT.NONE);
		watchFontGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_FONT));
		GridLayout watchFontGroupLayout = new GridLayout(2, false);
		watchFontGroupLayout.makeColumnsEqualWidth = true;
		GridData watchFontGroupLData = new GridData();
		watchFontGroupLData.verticalAlignment = SWT.FILL;
		watchFontGroupLData.grabExcessVerticalSpace = true;
		watchFontGroupLData.grabExcessHorizontalSpace = true;
		watchFontGroupLData.horizontalAlignment = GridData.FILL;
		watchFontGroup.setLayout(watchFontGroupLayout);
		watchFontGroup.setLayoutData(watchFontGroupLData); 
		Button timeFontButton = new Button(watchFontGroup, SWT.PUSH);
		timeFontButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_FONT_TIME));
		GridData timeFontButtonLData = new GridData();
		timeFontButtonLData.grabExcessVerticalSpace = true;
		timeFontButtonLData.grabExcessHorizontalSpace = true;
		timeFontButtonLData.horizontalAlignment = GridData.FILL;
		timeFontButton.setLayoutData(timeFontButtonLData );
		timeFontButton.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {						
						FontDialog fontDialog = new FontDialog( Display.getDefault().getActiveShell() );
						fontDialog.setFontData( setting.getTimeLabelFontData() );
						fontDialog.setRGB(setting.getTimeLabelFontColor());
						FontData fontData = fontDialog.open();
						if ( fontData != null ) {
							logger.info("time font changed to : " + fontData);
							setting.setTimeLabelFontData(fontData);	
							setting.setTimeLabelFontColor(fontDialog.getRGB());
						}						
					}
				}
				);
		Button dateFontButton = new Button(watchFontGroup, SWT.PUSH);
		dateFontButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_FONT_DATE));
		GridData dateFontButtonLData = new GridData();
		dateFontButtonLData.horizontalAlignment = GridData.FILL;
		dateFontButtonLData.grabExcessHorizontalSpace = true;
		dateFontButton.setLayoutData(dateFontButtonLData );
		dateFontButton.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {						
						FontDialog fontDialog = new FontDialog( Display.getDefault().getActiveShell() );
						fontDialog.setFontData( setting.getDateLabelFontData() );
						fontDialog.setRGB(setting.getDateLabelFontColor());
						FontData fontData = fontDialog.open();
						if ( fontData != null ) {
							logger.info("date font changed to : " + fontData);
							setting.setDateLabelFontData(fontData);	
							setting.setDateLabelFontColor(fontDialog.getRGB());
						}						
					}
				}
				);

		//		applyScreenModifyButton = new Button(screenGroup, SWT.PUSH);
		//		GridData applyScreenModifyButtonLData = new GridData();
		//		//		applyScreenModifyButtonLData.horizontalSpan = 2;
		//		applyScreenModifyButtonLData.horizontalAlignment = GridData.CENTER;
		//		applyScreenModifyButtonLData.grabExcessHorizontalSpace = true;
		//		applyScreenModifyButton.setLayoutData(applyScreenModifyButtonLData);
		//		applyScreenModifyButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_APPLY_MODIFIES));






		sizeGroup = new Group(screenComposite, SWT.NONE);
		GridLayout sizeGroupLayout = new GridLayout(2, false);
		sizeGroupLayout.makeColumnsEqualWidth = true;
		sizeGroup.setLayout(sizeGroupLayout);
		GridData sizeGroupLData = new GridData();
		sizeGroupLData.grabExcessHorizontalSpace = true;
		sizeGroupLData.horizontalAlignment = GridData.FILL;
		sizeGroupLData.grabExcessVerticalSpace = true;
		sizeGroupLData.verticalAlignment = GridData.FILL;
		sizeGroup.setLayoutData(sizeGroupLData);
		sizeGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_SIZE));

		Label widthLabel = new Label(sizeGroup, SWT.NONE);
		GridData widthLabelLData = new GridData();
		widthLabelLData.horizontalAlignment = GridData.FILL;
		widthLabelLData.grabExcessHorizontalSpace = true;
		widthLabel.setLayoutData(widthLabelLData);
		widthLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_SIZE_WIDTH));


		GridData spinnerWidthLData = new GridData();
		spinnerWidthLData.horizontalAlignment = GridData.FILL;
		spinnerWidthLData.grabExcessHorizontalSpace = true;
		widthSpinner = new Spinner(sizeGroup, SWT.NONE);
		widthSpinner.setLayoutData(spinnerWidthLData);
		widthSpinner.setMinimum(0);

		widthSpinner.setMaximum(Display.getDefault().getClientArea().width );


		Label heightLabel = new Label(sizeGroup, SWT.NONE);
		GridData heightLabelLData = new GridData();
		heightLabelLData.horizontalAlignment = GridData.FILL;
		heightLabelLData.grabExcessHorizontalSpace = true;
		heightLabel.setLayoutData(heightLabelLData);
		heightLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_SIZE_HEIGHT));


		GridData spinnerHeightLData = new GridData();
		spinnerHeightLData.horizontalAlignment = GridData.FILL;
		spinnerHeightLData.grabExcessHorizontalSpace = true;
		heightSpinner = new Spinner(sizeGroup, SWT.NONE);
		heightSpinner.setLayoutData(spinnerHeightLData);
		heightSpinner.setMinimum(0);
		heightSpinner.setMaximum(Display.getDefault().getClientArea().height);



		positionGroup = new Group(screenComposite, SWT.NONE);
		GridLayout positionGroupLayout = new GridLayout(2, false);
		positionGroupLayout.makeColumnsEqualWidth = true;
		positionGroup.setLayout(positionGroupLayout);
		GridData positionGroupLData = new GridData();
		positionGroupLData.grabExcessHorizontalSpace = true;
		positionGroupLData.horizontalAlignment = GridData.FILL;
		positionGroupLData.verticalAlignment = GridData.FILL;
		positionGroupLData.grabExcessVerticalSpace = true;
		positionGroup.setLayoutData(positionGroupLData);
		positionGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_LOCATION));

		viewScreenCheckedBox = new Button(screenComposite, SWT.CHECK | SWT.LEFT);
		GridData hideScreenCheckedBoxLData = new GridData();
		hideScreenCheckedBoxLData.horizontalIndent = 10;
		hideScreenCheckedBoxLData.grabExcessHorizontalSpace = true;
		hideScreenCheckedBoxLData.grabExcessVerticalSpace = true;
		hideScreenCheckedBoxLData.horizontalAlignment = GridData.FILL;
		hideScreenCheckedBoxLData.horizontalSpan = 1;
		viewScreenCheckedBox.setLayoutData(hideScreenCheckedBoxLData);
		viewScreenCheckedBox.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_VIEW));
		viewScreenCheckedBox.setSelection(true);

		referCheckedBox = new Button(screenComposite, SWT.CHECK | SWT.LEFT);
		GridData gd_referCheckedBox = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_referCheckedBox.horizontalIndent = 10;
		referCheckedBox.setLayoutData(gd_referCheckedBox);
		referCheckedBox.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_REFER));
		referCheckedBox.setSelection(false);


		Label leftLabel = new Label(positionGroup, SWT.NONE);
		GridData leftLData = new GridData();
		leftLData.horizontalAlignment = GridData.FILL;
		leftLData.grabExcessHorizontalSpace = true;
		leftLabel.setLayoutData(leftLData);
		leftLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_LOCATION_LEFT));

		GridData spinnerLeftLData = new GridData();
		spinnerLeftLData.horizontalAlignment = GridData.FILL;
		spinnerLeftLData.grabExcessHorizontalSpace = true;
		leftSpinner = new Spinner(positionGroup, SWT.NONE);
		leftSpinner.setLayoutData(spinnerLeftLData);
		leftSpinner.setMinimum(0);
		leftSpinner.setMaximum(Display.getDefault().getClientArea().width);



		Label topLabel = new Label(positionGroup, SWT.NONE);
		GridData topabelLData = new GridData();
		topabelLData.horizontalAlignment = GridData.FILL;
		topabelLData.grabExcessHorizontalSpace = true;
		topLabel.setLayoutData(topabelLData);
		topLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_LOCATION_TOP));


		GridData spinnerTopLData = new GridData();
		spinnerTopLData.horizontalAlignment = GridData.FILL;
		spinnerTopLData.grabExcessHorizontalSpace = true;
		topSpinner = new Spinner(positionGroup, SWT.NONE);
		topSpinner.setLayoutData(spinnerTopLData);
		topSpinner.setMinimum(0);
		topSpinner.setMaximum(Display.getDefault().getClientArea().height);
	}

	private void populateSequenceComposite(Composite sequenceComposite) {

		GridLayout sequenceCompositeLayout = new GridLayout(2,false);
		sequenceComposite.setLayout(sequenceCompositeLayout);

		GridData sequenceCompositeLData = new GridData();
		sequenceCompositeLData.horizontalAlignment = GridData.FILL;
		sequenceCompositeLData.grabExcessVerticalSpace = true;
		sequenceCompositeLData.verticalAlignment = GridData.FILL;
		sequenceCompositeLData.grabExcessHorizontalSpace = true;

		sequenceComposite.setLayoutData(sequenceCompositeLData);


		Label sequenceLabel = new Label(sequenceComposite, SWT.NONE);
		sequenceLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCE_CHOOSE));


		GridData sequenceComboLData = new GridData();
		sequenceComboLData.grabExcessHorizontalSpace = true;
		sequenceComboLData.horizontalAlignment = GridData.FILL;
		sequenceComboLData.heightHint = 21;
		sequenceCombo = new Combo(sequenceComposite, SWT.NONE);
		sequenceCombo.setLayoutData(sequenceComboLData);


		GridData gData = new GridData();
		gData.grabExcessHorizontalSpace = true;
		gData.horizontalAlignment = GridData.FILL;
		Label label = new Label(sequenceComposite, SWT.NONE);
		label.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCE_NUMBER_OF_VIDEO));
		//		label.setLayoutData(gData);

		numberOfVideosLabel = new Label(sequenceComposite, SWT.NONE);
		numberOfVideosLabel.setText("");
		numberOfVideosLabel.setLayoutData(gData);

		//		if ( ! Player.MPLAYER_MODE ) { 

		label = new Label(sequenceComposite, SWT.NONE);
		label.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCE_DURATION));
		//		label.setLayoutData(gData);

		durationLabel = new Label(sequenceComposite, SWT.NONE);
		durationLabel.setText("");
		gData = new GridData();
		gData.grabExcessHorizontalSpace = true;
		gData.horizontalAlignment = GridData.FILL;
		durationLabel.setLayoutData(gData);
		//		}

		label = new Label(sequenceComposite, SWT.NONE);
		label.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCE_NAME));
		//		label.setLayoutData(gData);

		pathLabel = new Label(sequenceComposite, SWT.NONE);
		pathLabel.setText("");
		gData = new GridData();
		gData.grabExcessHorizontalSpace = true;
		gData.horizontalAlignment = GridData.FILL;
		pathLabel.setLayoutData(gData);

	}

	private void populateTabFolder(CTabFolder sectionTabFolder) {


		//		player tab
		CTabItem playerTabItem = new CTabItem(sectionTabFolder, SWT.NONE);
		playerTabItem.setText( "Player");
		Composite playerComposite = new Composite(sectionTabFolder, SWT.NONE);				
		populatePlayerComposite( playerComposite );
		playerTabItem.setControl(playerComposite);

		//		sequence tab
		CTabItem sequenceTabItem = new CTabItem(sectionTabFolder, SWT.NONE);
		sequenceTabItem.setText( LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCE));
		Composite sequenceComposite = new Composite(sectionTabFolder, SWT.NONE);				
		populateSequenceComposite( sequenceComposite );
		sequenceTabItem.setControl(sequenceComposite);		


		//		activation tab
		CTabItem activationTabItem = new CTabItem(sectionTabFolder, SWT.NONE);
		activationTabItem.setText( LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION));
		Composite activationComposite = new Composite(sectionTabFolder, SWT.NONE);				
		populateActivationComposite( activationComposite );
		activationTabItem.setControl(activationComposite);

		// news tab
		if( Player.ENABLE_NEWS ){
			CTabItem newsTabItem = new CTabItem(sectionTabFolder, SWT.NONE);
			newsTabItem.setText( "News Bar");			
		}

		//		screen tab
		CTabItem screenTabItem = new CTabItem(sectionTabFolder, SWT.NONE);
		screenTabItem.setText( LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN));
		Composite screenComposite = new Composite(sectionTabFolder, SWT.NONE);				
		populateScreenComposite( screenComposite );
		screenTabItem.setControl(screenComposite);
		new Label(screenComposite, SWT.NONE);




		sectionTabFolder.setSelection(0);

	}

	private void populateExpandBar( Composite composite) {
		ExpandBar expandBar = new ExpandBar(composite, SWT.V_SCROLL );

		expandBar.addExpandListener(new ExpandAdapter() {
			@Override
			public void itemCollapsed(ExpandEvent arg0) {
				layout();
			}

			@Override
			public void itemExpanded(ExpandEvent arg0) {
				layout();
			}
		});

		GridData expandBarData = new GridData();
		expandBarData.horizontalAlignment = GridData.FILL;
		expandBarData.grabExcessVerticalSpace = true;
		expandBarData.verticalAlignment = GridData.FILL;
		expandBarData.grabExcessHorizontalSpace = true;
		expandBar.setLayoutData(expandBarData);
		expandBar.setBackgroundMode(SWT.INHERIT_FORCE);

		Composite playerComposite = new Composite(expandBar, SWT.NONE);
		populatePlayerComposite( playerComposite );
		ExpandItem itemPlayer = new ExpandItem(expandBar , SWT.NONE, 0);
		itemPlayer.setText("Player");
		itemPlayer.setHeight(playerComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		itemPlayer.setControl(playerComposite);
		//
		//		Composite sequenceComposite = new Composite(expandBar, SWT.NONE);				
		//		populateSequenceComposite( sequenceComposite );
		//		ExpandItem itemSequence = new ExpandItem(expandBar , SWT.NONE, 1);
		//		itemSequence.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCE));
		//		itemSequence.setHeight(sequenceComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		//		itemSequence.setControl(sequenceComposite);




		Composite activationComposite = new Composite(expandBar, SWT.NONE);
		populateActivationComposite( activationComposite );
		ExpandItem activationPlayer = new ExpandItem(expandBar , SWT.NONE, 1);
		activationPlayer.setText( LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION));
		activationPlayer.setHeight(activationComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		activationPlayer.setControl(activationComposite);

		Composite screenComposite = new Composite(expandBar, SWT.NONE);
		populateScreenComposite( screenComposite );
		ExpandItem screenPlayer = new ExpandItem(expandBar , SWT.NONE, 2);
		screenPlayer.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN));
		screenPlayer.setHeight(screenComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		screenPlayer.setControl(screenComposite);
	}

	public void resetMediaLabels() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				mediaLabel.setText("");
				mediaIDLabel.setText("");
				mediaDurationLabel.setText("");
				mediaTypeLabel.setText("");
			}
		});
	}

	public void setPlayerMaster(PlayerMaster playerMaster) {
		this.playerMaster = playerMaster;
	}

	@SuppressWarnings("deprecation")
	public void setPlayerSetting(ScreenSetting playerSetting) {


		this.setting = playerSetting ;

		playerMaster.setAlpha(setting.getAlpha());
		//		screenFadeScale.setSelection(setting.getAlpha());



		this.widthSpinner.setSelection( setting.getSize().x );
		this.heightSpinner.setSelection( setting.getSize().y );		


		this.topSpinner.setSelection( setting.getLocation().y );
		this.leftSpinner.setSelection( setting.getLocation().x );

		//		logger.info(" bw " + this.getPlayerMaster().getShell().getBorderWidth());

		//		Point     size = calculatePlayerSize(new Point(0,0), playerSetting.getSize());
		//		Point location = calculatePlayerLocation( new Point(0,0), playerSetting.getLocation());

		//		Rectangle bounds = getPlayerMaster().getShell().computeTrim(
		//				leftSpinner.getSelection(),
		//				topSpinner.getSelection(),
		//				widthSpinner.getSelection(), 
		//				heightSpinner.getSelection()
		//		);
		//		getPlayerMaster().getShell().setBounds(bounds);

		if ( playerSetting.getWatchImageFile() != null ) {
			watchBackGroudCheckedBox.setSelection(true);
			watchBackGroudButton.setEnabled(true);
		} else {
			watchBackGroudCheckedBox.setSelection(false);
			watchBackGroudButton.setEnabled(false);
		}

		this.getPlayerMaster().create();
		this.getPlayerMaster().setScreenManager(this);

		this.getPlayerMaster().getShell().setSize(playerSetting.getSize());
		this.getPlayerMaster().getShell().setLocation(playerSetting.getLocation());


		this.playerMaster.getShell().setLocation(playerSetting.getLocation());
		this.playerMaster.getShell().setSize( playerSetting.getSize());

		//		logger.info("player master bounds : " + getPlayerMaster().getShell().getBounds() + " | player master clientArea : " +getPlayerMaster().getShell().getClientArea() );

		logger.debug("player master [" + index + "]" +
				" setting size : " +  setting.getSize()  +
				" setting locaiton : " +  setting.getLocation());

		logger.debug("player master [" + index + "]" +
				" size : " + getPlayerMaster().getShell().getSize() + 
				" location : "  + getPlayerMaster().getShell().getLocation());

		//		screenFadeCheckBox.setSelection( setting.isFade() );

		this.screenLockCheckedBox.setSelection(setting.isLock());
		this.getPlayerMaster().block(setting.isLock());

		this.viewScreenCheckedBox.setSelection( setting.isViewScreen() );
		this.getPlayerMaster().setVisibility( setting.isViewScreen() );

		if ( setting.getProgramFile() != null ) {

			try {
				if (setting.getProgramFile().exists() ) {
					Program smp = Utils.getProgram( SequenceFileManager.read(setting.getProgramFile() ), setting.getProgramFile().getPath());
					//				if ( smp != null ) {
					player.addSequence(smp);
					this.getPlayerMaster().setProgram(smp);
					//				} else {

					//				}
				} else {
					MessageBox mb = new MessageBox( getShell() , SWT.OK | SWT.ICON_WARNING);
					mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
					mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_NOT_FOUND) + "File ["+setting.getProgramFile().getPath() +"]");
					mb.open();
					stop();
				}
			} catch (SequenceMoviePlayerException e) {
				//			this.playerMaster.deActive();
				MessageBox mb = new MessageBox( getShell() , SWT.OK | SWT.ICON_WARNING);
				mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
				mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_LOADING) + "File ["+setting.getProgramFile().getPath() +"]");
				mb.open();
				logger.error("error occurred ", e);
			}
			catch (Exception e) {
				//			this.playerMaster.deActive();
				MessageBox mb = new MessageBox( getShell() , SWT.OK | SWT.ICON_WARNING);
				mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
				mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_LOADING) + "File ["+setting.getProgramFile().getPath() +"]");
				mb.open();
				logger.error("error occurred ", e);
			}
		}

		this.allDayRadioButton.setSelection(setting.isAllDay());

		if ( setting.isAllDay() && this.getPlayerMaster().getProgram() != null) {						
			this.sequenceCombo.setText(this.getPlayerMaster().getProgram().getName());
			this.updatePlayLabel(this.getPlayerMaster().getProgram());
			this.updateTiming(false);
			play();
			this.pauseButton.setEnabled(true);
			this.block(true);
		}

		this.allDayRadioButton.setSelection(setting.isAllDay());

		this.timedRadioButton.setSelection(setting.isTimed());

		if ( setting.getFrom() != null ){
			this.fromDateTime.setHours(setting.getFrom().getHours());
			this.fromDateTime.setMinutes(setting.getFrom().getMinutes());
			this.updateTiming(true);
		}

		if ( setting.getTo() != null ){
			this.toDateTime.setHours(setting.getTo().getHours());
			this.toDateTime.setMinutes(setting.getTo().getMinutes());
		}

		if ( setting.isTimed() && setting.getFrom() != null && setting.getTo() != null && this.getPlayerMaster().getProgram() != null) {
			logger.info("timed : from [" + Utils.getTimeAsString(setting.getFrom()) +"] to [" + Utils.getTimeAsString(setting.getTo())+"]");
			this.screenLockCheckedBox.setSelection(true);
			String s = this.getPlayerMaster().getProgram().getName();
			this.sequenceCombo.setText(s);
			this.updatePlayLabel(this.getPlayerMaster().getProgram());
			this.block(true);


			setTimer();

		}

		this.watchRadioButton.setSelection(setting.isWhenNotActiveWatch());
		this.blackWindowRadioButton.setSelection(setting.isWhenNotActiveBlack());

		this.imageRadioButton.setSelection(setting.isWhenNotActiveImage());

		this.referCheckedBox.setSelection(false);

		this.loopNumberSpinner.setSelection(setting.getLoopNumber());

		createControlsListeners();

		block( this.screenLockCheckedBox.getSelection() );



		setRunning(true);

		screenCompositeTimerTask = new ScreenCompositeTimerTask(this);
		screenCompositeTimer.schedule(screenCompositeTimerTask, 0, Player.TIMING_PERIOD);

	}

	public void setRunning(boolean running) {
		logger.info("setting running : " + running );
		this.running = running;
	}

	public void updateMediaLabels( final Media media) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				File f = null;
				if ( ! Utils.isAnEmptyString(media.getPath()) ) {
					f = new File( media.getPath() );
				}
				if ( media.getId() > 0)
					mediaIDLabel.setText( "" + media.getId() + " / " + media.getMyProgram().getVideos().getVideoCount());

				mediaLabel.setText( f == null ? "" : f.getName() );
				mediaDurationLabel.setText( Utils.getDurationString(media.getDuration()));
				mediaTypeLabel.setText( media.getId() == 0 ? "Default ": "" + LocaleManager.getText("model.sequece.media." + media.getType().getType() ));

			}
		});
	}

	public void updatePlayLabel(final Program smp) {
		numberOfVideosLabel.setText( "" + smp.getVideos().getVideoCount() );
		//		if ( ! Player.MPLAYER_MODE ) {
		durationLabel.setText(smp.getDuration());
		//		}

		pathLabel.setText(smp.getFilepath().getName());

		mediaComposite.layout();
	}

	private void updateTiming(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {					
			public void run() {
				fromDateTime.setEnabled(b);
				toDateTime.setEnabled(b);

			}
		});

	}

	public void play () {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				screenLockCheckedBox.setSelection(true);
				block( true );
				setRunning(true);
				viewScreenCheckedBox.setSelection(true);
				getPlayerMaster().play();
				getPauseButton().setEnabled(true);
				status = Status.PLAYNG;
			}
		});
	}

	public Control getPauseButton() {
		return pauseButton;
	}

	public Button getStopButton() {
		return stopButton;
	}

	public Button getPlayButton() {
		return playButton;
	}


	public void stop() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {	
				setRunning(false);
				screenLockCheckedBox.setSelection(false);
				block(false);
				status = Status.STOPPED;
				getPlayerMaster().stop();
				sequenceCombo.setEnabled(true);
				pauseButton.setSelection(false);
			}
		});
	}

	public int getStatus() {
		return status;
	}

	public void pause() {

		Display.getDefault().syncExec(new Runnable() {
			public void run() {		
				status = Status.PAUSED;
				getPlayerMaster().stop();
			}
		});

	}

	private void updateTimingActivation() {
		// applico tutto il giorno 
		if ( allDayRadioButton.getSelection() ) {
			// se era temporizzato
			logger.info("timing changed to ALL DAY" );
			if ( status != Status.PLAYNG ) {
				play();			
			}	

			setting.setTimed(false);
			setting.setAllDay(true);		

		} 
		if ( timedRadioButton.getSelection() ) {

			Calendar now = Calendar.getInstance();

			setStart(Calendar.getInstance());
			getStart().set(Calendar.HOUR_OF_DAY, this.fromDateTime.getHours());
			getStart().set(Calendar.MINUTE, this.fromDateTime.getMinutes());
			getStart().set(Calendar.SECOND, 0);

			setEnd(Calendar.getInstance());
			getEnd().set(Calendar.HOUR_OF_DAY, this.toDateTime.getHours());
			getEnd().set(Calendar.MINUTE, this.toDateTime.getMinutes());
			getEnd().set(Calendar.SECOND, 0);

			if ( ! getStart().before( getEnd())) {
				logger.info("star time after end time");
				long diff = getEnd().getTimeInMillis() - getStart().getTimeInMillis();
				if ( diff >= (1000 * 60 * 60 * 24) ) {
					logger.error("difference bigger than a day");
					MessageDialog.openError(
							getShell(), 
							getShell().getText(), 
							LocaleManager.getText(LocaleManager.ERRORS_TIME)
							);
					return;
				} else {
					logger.info("add a day to end time");
					getEnd().add(Calendar.DATE, 1);
				}
			}

			setting.setAllDay(false);
			setting.setTimed(true);			
			setting.getFrom().setHours( this.fromDateTime.getHours() );
			setting.getFrom().setMinutes( this.fromDateTime.getMinutes() );

			setting.getTo().setHours( this.toDateTime.getHours() );
			setting.getTo().setMinutes( this.toDateTime.getMinutes() );

			//			logger.info("timing changed to TIMED : now " + Utils.getTimeAsString(now) + " from [" + Utils.getTimeAsString(setting.getFrom()) + "] to [" + Utils.getTimeAsString(setting.getTo())+"]");
			logger.info("timing changed to TIMED : now " + Utils.getTimeAsString(now) + " from [" + Utils.getTimeAsString( getStart() ) + "] to [" + Utils.getTimeAsString( getEnd() )+"]");

			setRunning(true);		

			setTimer();
		}


	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getStart() {
		return start;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}

	public Calendar getEnd() {
		return end;
	}

	private void setTimer() {

		Calendar now = Calendar.getInstance(LocaleManager.getLocale());

		setStart((Calendar)now.clone());
		getStart().set(Calendar.HOUR_OF_DAY, this.setting.getFrom().getHours());
		getStart().set(Calendar.MINUTE, this.setting.getFrom().getMinutes());
		getStart().set(Calendar.SECOND, 0);

		logger.info("start : " + Utils.debugDate(getStart()) );

		setEnd((Calendar)now.clone());
		getEnd().set(Calendar.HOUR_OF_DAY, this.setting.getTo().getHours());
		getEnd().set(Calendar.MINUTE, this.setting.getTo().getMinutes());
		getEnd().set(Calendar.SECOND, 0);



		if ( getEnd().before(getStart()) ) {
			getEnd().add(Calendar.DATE, 1);
			logger.info("adding a day to end   : " + Utils.debugDate(getEnd()) );
		}



		logger.info("new timer : now [" + Utils.debugDate(now) + "] start [" + Utils.debugDate(getStart()) + "] end [" + Utils.debugDate(getEnd())+ "]");

		if ( now.before(getStart()) || now.after(getEnd())) {
			logger.info("start time [" + Utils.debugDate( getStart()) + "]");
			setRunning(true);
			pause();
		}  else {		
			if ( ! isRunning() ) {
				setRunning(true);
				play();
			}
		}




		//		setupActivationTimerSchedulation(getStart(), getEnd() );
	}

	public class DimensionAndPositionModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent arg0) {
			changePlayerLocation();
			changePlayerSize();

		}
	}

	public class PositionModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent arg0) {
			changePlayerLocation();			
		}
	}

	protected void changePlayerLocation() {
		Point location = new Point(leftSpinner.getSelection(), topSpinner.getSelection());	
		if ( referCheckedBox.getSelection() ) {
			referWindow.getShell().setLocation(location);
		} else {
			getPlayerMaster().getShell().setLocation(location);
		}
		setting.setLocation(location);
		//		logger.info(" client area : " + getPlayerMaster().getShell().getClientArea());
	}

	public void changeSpinners(final Point size, final Point location) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {

				widthSpinner.setSelection(size.x);
				heightSpinner.setSelection(size.y);
				topSpinner.setSelection(location.y);
				leftSpinner.setSelection(location.x);

				setting.setLocation(location);
				setting.setSize(size);

			}

		});


	}

	protected void changePlayerSize() {
		Point size = new Point(widthSpinner.getSelection(), heightSpinner.getSelection());	
		if ( referCheckedBox.getSelection() ) {
			referWindow.getShell().setSize(size);
		} else {
			this.getPlayerMaster().getShell().setSize(size);
		}
		setting.setSize(size);
		//		logger.info(" client area : " + getPlayerMaster().getShell().getClientArea() + " | bounds : " +getPlayerMaster().getShell().getBounds() );
	}

	public Combo getSequenceCombo() {
		return sequenceCombo;
	}

	public boolean isBlackWindowWhenNotActive() {
		return setting.isWhenNotActiveBlack();
	}

	public boolean isWatchWindowWhenNotActive() {
		return setting.isWhenNotActiveWatch();
	}

	public boolean isImageWindowWhenNotActive() {
		return setting.isWhenNotActiveImage();
	}

	public Player getPlayer() {
		return player;
	}

	public int getIndex() {
		return index;
	}
	public boolean hasPlayerSetting() {
		if ( setting != null)
			return true;
		return false;
	}

	public void updateVideoProgressBarSelection( final double actual, final double duration/*final int percentage*/) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if ( duration < 0 )
					return;
				int percentage = (int) (actual  * 100 / duration);
				videoProgressBar.setSelection(percentage);
				logger.debug("updating updateVideoProgressBarSelection [" + percentage +"]");
				StringBuffer sb = new StringBuffer();
				sb.append(Utils.getDurationString(actual));
				sb.append(" / ");
				sb.append(Utils.getDurationString(duration));
				logger.debug( sb.toString() );
				videoProgressLabel.setText( sb.toString() );
			}
		});
	}




	public void resetVideoProgressBarMaximum() {

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				videoProgressBar.setSelection(0);
				videoProgressBar.setMaximum(100);
				videoProgressLabel.setText("");
			}
		});
	}


	public void run() {
		// TODO Auto-generated method stub

	}

	public void setWeatherLatlng(String weatherLatlng) {
		this.playerMaster.setWeatherLatlng(weatherLatlng);
	}

}
