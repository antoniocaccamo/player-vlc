package it.antanysavage.sm.player.swt.views;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.SequenceManager;
import it.antanysavage.sm.player.actions.AddVideoAction;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.exception.SequenceMoviePlayerException;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Video;
import it.antanysavage.sm.player.sequences.schema.types.AcceptedVideoTypes;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;
import java.util.Date;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

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
public class VideoWindow extends ApplicationWindow {
	private Composite buttonsComposite;
	private Label label3;
	private Label label2;
	private Group photoTimingGroup;
	private Button photoTimingButton;
	private Label toLabel;
	private Label fromLabel;
	private Button videoTimingButton;
	private Group videoTimingroup;
	private Button videoSearchButton;
	private Label videoPath;
	private Label label;
	private Button photoFileButton;
	private Label label1;
	private Group photoGroup;
	private Button photoButton;
	private Label photoLabel;
	private Group watchGroup;
	private Button blackButton;
	private Button watchButton;
	private Group hiddenGroup;
	private Button hiddenButton;
	private Group blackGroup;
	private Group videoGroup;
	private Composite composite;
	private Button videoRadioButton;
	private Button closeButton;
	private Button addButton;
	private Spinner blackSpinner;
	private Spinner hiddenSpinner;
	private Spinner watchSpinner;
	private Spinner photoSpinner;
	private Label photoPathFileLabel;
	protected File  videoFile;
	protected File photoFile;

	private Program sequenceSMP;
	private Media videoSMP;
	private SequenceManager sequenceManager;
	private DateTime videoFromDateTime;
	private DateTime videoToDateTime;
	private DateTime photoFromDateTime;
	private DateTime photoToDateTime;
	
	private StatusLineManager slm = new StatusLineManager();

	/**
	 * Auto-generated main method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		VideoWindow wwin = new VideoWindow(null);
		wwin.setBlockOnOpen(true);
		wwin.open();
		Display.getCurrent().dispose();
	}

	/**
	 * Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	 */	
	protected void checkSubclass() {
	}

	/**
	 * Auto-generated method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 * @param sequenceManager 
	 */


	public VideoWindow(SequenceManager sequenceManager) {
		super(null);
		this.sequenceManager = sequenceManager;
		
		addStatusLine();
		
	}

	public void setSequenceSMP(Program sequenceSMP) {
		this.sequenceSMP = sequenceSMP;
	}

	public void setVideoSMP(Media videoSMP) {
		this.videoSMP = videoSMP;
	}

	private Composite initGUI(Composite parent) {
		

		getShell().setImage(Player.LOGO_IMAGE);

		GridLayout thisLayout = new GridLayout(1, false);
		thisLayout.makeColumnsEqualWidth = true;
		parent.setLayout(thisLayout);

		GridData compositeLData = new GridData();
		compositeLData.horizontalAlignment = GridData.FILL;
		compositeLData.grabExcessHorizontalSpace = true;
		compositeLData.verticalAlignment = GridData.BEGINNING;
		composite = new Composite(parent, SWT.NONE);
		GridLayout compositeLayout = new GridLayout();
		compositeLayout.makeColumnsEqualWidth = true;
		composite.setLayout(compositeLayout);
		composite.setLayoutData(compositeLData);

		videoRadioButton = new Button(composite, SWT.RADIO | SWT.LEFT);
		GridData videoRadioButtonLData = new GridData();
		videoRadioButton.setLayoutData(videoRadioButtonLData);
		videoRadioButton.setText("Video");


		videoGroup = new Group(composite, SWT.NONE);
		GridLayout videoGroupLayout = new GridLayout(2, false);

		videoGroup.setLayout(videoGroupLayout);
		GridData videoGroupLData = new GridData();
		videoGroupLData.grabExcessHorizontalSpace = true;
		videoGroupLData.horizontalAlignment = GridData.FILL;
		videoGroup.setLayoutData(videoGroupLData);
		videoGroup.setText("Video");
		{
			label = new Label(videoGroup, SWT.NONE);
			GridData labelLData = new GridData();
			labelLData.horizontalAlignment = GridData.FILL;
			label.setLayoutData(labelLData);
			label.setText("File");
		}
		{
			GridData videoPathLData = new GridData();
			videoPathLData.grabExcessHorizontalSpace = true;
			videoPathLData.horizontalAlignment = GridData.FILL;
			videoPath = new Label(videoGroup, SWT.NONE);
			videoPath.setLayoutData(videoPathLData);
		}
		{
			videoTimingButton = new Button(videoGroup, SWT.CHECK | SWT.LEFT);
			GridData videoTimingButtonLData = new GridData();
			videoTimingButtonLData.horizontalAlignment = GridData.FILL;
			videoTimingButtonLData.horizontalSpan = 2;
			videoTimingButton.setLayoutData(videoTimingButtonLData);
			videoTimingButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED));



		}
		{
			videoTimingroup = new Group(videoGroup, SWT.NONE);
			GridLayout group1Layout = new GridLayout(2, false);
			group1Layout.makeColumnsEqualWidth = true;
			videoTimingroup.setLayout(group1Layout);
			GridData group1LData = new GridData();
			group1LData.horizontalAlignment = GridData.FILL;
			group1LData.horizontalSpan = 2;
			videoTimingroup.setLayoutData(group1LData);
			videoTimingroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING));
			{
				fromLabel = new Label(videoTimingroup, SWT.NONE);
				GridData fromLabelLData = new GridData();
				fromLabel.setLayoutData(fromLabelLData);
				fromLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED_FROM));

				GridData videoFromDateTimeLData = new GridData();
				videoFromDateTimeLData.grabExcessHorizontalSpace = true;
				videoFromDateTimeLData.horizontalAlignment = GridData.CENTER;
				videoFromDateTime = new DateTime(videoTimingroup, SWT.TIME | SWT.SHORT);
				videoFromDateTime.setLayoutData(videoFromDateTimeLData);
			}
			{
				toLabel = new Label(videoTimingroup, SWT.NONE);
				GridData toLabelLData = new GridData();
				toLabel.setLayoutData(toLabelLData);
				toLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED_TO));
				GridData videoToDateTimeLData = new GridData();
				videoToDateTimeLData.grabExcessHorizontalSpace = true;
				videoToDateTimeLData.horizontalAlignment = GridData.CENTER;
				videoToDateTime = new DateTime(videoTimingroup, SWT.TIME | SWT.SHORT);
				videoToDateTime.setLayoutData(videoToDateTimeLData);
			}
		}


		{
			videoSearchButton = new Button(videoGroup, SWT.PUSH | SWT.CENTER);
			GridData videoPButtonLData = new GridData();
			videoPButtonLData.grabExcessHorizontalSpace = true;
			videoPButtonLData.horizontalAlignment = GridData.CENTER;
			videoPButtonLData.horizontalSpan = 2;
			videoSearchButton.setLayoutData(videoPButtonLData);
			videoSearchButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CHOOSE_FILE));

		}

		blackButton = new Button(composite, SWT.RADIO | SWT.LEFT);
		GridData blackButtonLData = new GridData();
		blackButtonLData.horizontalAlignment = GridData.FILL;
		blackButton.setLayoutData(blackButtonLData);
		blackButton.setText("Black Window");



		blackGroup = new Group(composite, SWT.NONE);
		GridLayout blackWindowGroupLayout = new GridLayout(2, false);
		blackWindowGroupLayout.makeColumnsEqualWidth = true;
		blackGroup.setLayout(blackWindowGroupLayout);
		GridData blackWindowGroupLData = new GridData();
		blackWindowGroupLData.grabExcessHorizontalSpace = true;
		blackWindowGroupLData.horizontalAlignment = GridData.FILL;
		blackGroup.setLayoutData(blackWindowGroupLData);
		blackGroup.setText("Black Window");

		Label durationLabel = new Label(blackGroup, SWT.NONE);
		GridData durationLabelLData = new GridData();
		durationLabelLData.horizontalAlignment = GridData.FILL;
		durationLabelLData.grabExcessHorizontalSpace = true;
		durationLabel.setLayoutData(durationLabelLData);
		durationLabel.setText("Duration");

		GridData blackSpinnerLData = new GridData();
		blackSpinnerLData.grabExcessHorizontalSpace = true;
		blackSpinnerLData.horizontalAlignment = GridData.CENTER;
		blackSpinner = new Spinner(blackGroup, SWT.NONE);
		blackSpinner.setLayoutData(blackSpinnerLData);
		blackSpinner.setMinimum(1);


		hiddenButton = new Button(composite, SWT.RADIO | SWT.LEFT);
		GridData hiddenLabelLData = new GridData();
		hiddenLabelLData.horizontalAlignment = GridData.FILL;
		hiddenButton.setLayoutData(hiddenLabelLData);
		hiddenButton.setText("Hidden Window");



		hiddenGroup = new Group(composite, SWT.NONE);
		GridLayout hiddenGroupLayout = new GridLayout(2,false);
		hiddenGroupLayout.makeColumnsEqualWidth = true;
		hiddenGroup.setLayout(hiddenGroupLayout);
		GridData hiddenGroupLData = new GridData();
		hiddenGroupLData.grabExcessHorizontalSpace = true;
		hiddenGroupLData.horizontalAlignment = GridData.FILL;
		hiddenGroup.setLayoutData(hiddenGroupLData);
		hiddenGroup.setText("Hidden Window");

		durationLabel = new Label(hiddenGroup, SWT.NONE);
		durationLabelLData = new GridData();
		durationLabelLData.horizontalAlignment = GridData.FILL;
		durationLabelLData.grabExcessHorizontalSpace = true;
		durationLabel.setLayoutData(durationLabelLData);
		durationLabel.setText("Duration");
		hiddenSpinner = new Spinner(hiddenGroup, SWT.NONE);

		GridData hiddenSpinnerLData1 = new GridData();
		hiddenSpinnerLData1.grabExcessHorizontalSpace = true;
		hiddenSpinnerLData1.horizontalAlignment = GridData.CENTER;
		hiddenSpinner.setLayoutData(hiddenSpinnerLData1);
		hiddenSpinner.setMinimum(1);


		watchButton = new Button(composite, SWT.RADIO | SWT.LEFT);
		GridData watchButtonLData = new GridData();
		watchButtonLData.grabExcessHorizontalSpace = true;
		watchButtonLData.horizontalAlignment = GridData.FILL;
		watchButton.setLayoutData(watchButtonLData);
		watchButton.setText("Watch");



		watchGroup = new Group(composite, SWT.NONE);
		GridLayout watchGroupLayout = new GridLayout();
		watchGroupLayout.numColumns = 2;
		watchGroupLayout.makeColumnsEqualWidth = true;
		watchGroup.setLayout(watchGroupLayout);
		GridData watchGroupLData = new GridData();
		watchGroupLData.grabExcessHorizontalSpace = true;
		watchGroupLData.horizontalAlignment = GridData.FILL;
		watchGroup.setLayoutData(watchGroupLData);
		watchGroup.setText("Watch");

		durationLabel = new Label(watchGroup, SWT.NONE);
		durationLabelLData = new GridData();
		durationLabelLData.horizontalAlignment = GridData.FILL;
		durationLabel.setLayoutData(durationLabelLData);
		durationLabel.setText("Duration");
		watchSpinner = new Spinner(watchGroup, SWT.NONE);
		GridData watchSpinnerLData = new GridData();
		watchSpinnerLData.grabExcessHorizontalSpace = true;
		watchSpinnerLData.horizontalAlignment = GridData.CENTER;
		watchSpinner.setLayoutData(watchSpinnerLData);
		watchSpinner.setMinimum(1);


		photoButton = new Button(composite, SWT.RADIO | SWT.LEFT);
		GridData photoButtonLData = new GridData();
		photoButtonLData.grabExcessHorizontalSpace = true;
		photoButtonLData.horizontalAlignment = GridData.FILL;
		photoButton.setLayoutData(photoButtonLData);
		photoButton.setText("Photo");



		photoGroup = new Group(composite, SWT.NONE);
		GridLayout photoGroupLayout = new GridLayout(2,false);
		photoGroup.setLayout(photoGroupLayout);
		GridData photoGroupLData = new GridData();
		photoGroupLData.grabExcessHorizontalSpace = true;
		photoGroupLData.horizontalAlignment = GridData.FILL;
		photoGroup.setLayoutData(photoGroupLData);
		photoGroup.setText("Photo");

		Label label1 = new Label(photoGroup, SWT.NONE);
		GridData label1LData = new GridData();
		label1LData.grabExcessHorizontalSpace = true;
		label1LData.horizontalAlignment = GridData.FILL;
		label1.setLayoutData(label1LData);
		label1.setText("Duration");
		GridData photoSpinnerLData = new GridData();
		photoSpinnerLData.grabExcessHorizontalSpace = true;
		photoSpinnerLData.horizontalAlignment = GridData.CENTER;
		photoSpinner = new Spinner(photoGroup, SWT.NONE);
		photoSpinner.setLayoutData(photoSpinnerLData);
		photoSpinner.setMinimum(1);

		Label photoFileLabel = new Label(photoGroup, SWT.NONE);
		GridData pFileLData = new GridData();
		photoFileLabel.setLayoutData(pFileLData);
		photoFileLabel.setText("File");

		photoPathFileLabel = new Label(photoGroup, SWT.NONE);
		GridData pPathFileLData = new GridData();
		pPathFileLData.grabExcessHorizontalSpace = true;
		pPathFileLData.horizontalAlignment = GridData.FILL;
		photoPathFileLabel.setLayoutData(pPathFileLData);
		photoPathFileLabel.setText("");
		{
			photoTimingButton = new Button(photoGroup, SWT.CHECK | SWT.LEFT);
			GridData photoTimingButtonLData = new GridData();
			photoTimingButtonLData.grabExcessHorizontalSpace = true;
			photoTimingButtonLData.horizontalAlignment = GridData.FILL;
			photoTimingButtonLData.horizontalSpan = 2;
			photoTimingButton.setLayoutData(photoTimingButtonLData);
			photoTimingButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED));
		}
		{
			photoTimingGroup = new Group(photoGroup, SWT.NONE);
			GridLayout photoTimingGroupLayout = new GridLayout(2, false);
			photoTimingGroupLayout.makeColumnsEqualWidth = true;
			photoTimingGroup.setLayout(photoTimingGroupLayout);
			GridData photoTimingGroupLData = new GridData();
			photoTimingGroupLData.horizontalSpan = 2;
			photoTimingGroupLData.grabExcessHorizontalSpace = true;
			photoTimingGroupLData.horizontalAlignment = GridData.FILL;
			photoTimingGroup.setLayoutData(photoTimingGroupLData);
			photoTimingGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING));
			{
				Label label2 = new Label(photoTimingGroup, SWT.NONE);
				GridData label2LData = new GridData();
				label2.setLayoutData(label2LData);
				label2.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED_FROM));
				
				GridData photoFromDateTimeLData = new GridData();
				photoFromDateTimeLData.grabExcessHorizontalSpace = true;
				photoFromDateTimeLData.horizontalAlignment = GridData.CENTER;
				photoFromDateTime = new DateTime(photoTimingGroup, SWT.TIME | SWT.SHORT);
				photoFromDateTime.setLayoutData(photoFromDateTimeLData);

			}
			{
				Label label3 = new Label(photoTimingGroup, SWT.NONE);
				GridData label3LData = new GridData();
				label3.setLayoutData(label3LData);
				label3.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED_TO));
				
				GridData photoToDateTimeLData = new GridData();
				photoToDateTimeLData.horizontalAlignment = GridData.CENTER;
				photoToDateTimeLData.grabExcessHorizontalSpace = true;
				photoToDateTime = new DateTime(photoTimingGroup, SWT.TIME | SWT.SHORT);
				photoToDateTime.setLayoutData(photoToDateTimeLData);
			}
		}
		



		photoFileButton = new Button(photoGroup, SWT.PUSH | SWT.CENTER);
		GridData photoFileButtonLData = new GridData();
		photoFileButtonLData.verticalAlignment = GridData.FILL;
		photoFileButtonLData.horizontalAlignment = GridData.CENTER;
		photoFileButtonLData.horizontalSpan = 2;
		photoFileButton.setLayoutData(photoFileButtonLData);
		photoFileButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CHOOSE_FILE));

		GridData buttonsCompositeLData = new GridData();
		buttonsCompositeLData.grabExcessHorizontalSpace = true;
		buttonsCompositeLData.grabExcessVerticalSpace = true;
		buttonsCompositeLData.horizontalAlignment = GridData.FILL;
		buttonsCompositeLData.verticalAlignment = GridData.FILL;
		buttonsComposite = new Composite(parent, SWT.NONE);
		GridLayout buttonsCompositeLayout = new GridLayout(4, false);
		buttonsCompositeLayout.makeColumnsEqualWidth = true;
		buttonsComposite.setLayout(buttonsCompositeLayout);
		buttonsComposite.setLayoutData(buttonsCompositeLData);

		addButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER );
		GridData saveButtonLData = new GridData();
		saveButtonLData.grabExcessHorizontalSpace = true;
		saveButtonLData.horizontalAlignment = GridData.CENTER;
		saveButtonLData.horizontalSpan = 2;
		addButton.setLayoutData(saveButtonLData);
		addButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_ADD));


		closeButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		GridData cancelButtonLData = new GridData();
		cancelButtonLData.grabExcessHorizontalSpace = true;
		cancelButtonLData.horizontalAlignment = GridData.CENTER;
		cancelButtonLData.horizontalSpan = 2;
		closeButton.setLayoutData(cancelButtonLData);
		closeButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CLOSE));

		createControlListeners();
		
		init();

		
		parent.pack();

		return parent;
	}
	
	public void init() {
		videoRadioButton.setSelection(true);
		videoTimingButton.setSelection(false);
		videoTimingroup.setEnabled(false);
		

		photoTimingButton.setSelection(false);
		photoTimingGroup.setEnabled(false);
	}

	protected Control createContents(Composite parent) {
		return this.initGUI( parent );
	}

	private void enableGroup(final Group group) {
		Display.getCurrent().syncExec(new Runnable() {			
			public void run() {
				blackGroup.setEnabled(false);
				hiddenGroup.setEnabled(false);
				photoGroup.setEnabled(false);
				watchGroup.setEnabled(false);
				videoGroup.setEnabled(false);
				group.setEnabled(true);
			}
		});
	}

	private void updateVideoLabel(final File f) {
		Display.getCurrent().syncExec(new Runnable() {

			public void run() {
				videoPath.setText(f.getName());
				videoFile = f;
			}
		});
	}

	private void updatePhotoLabel(final File f) {
		Display.getCurrent().syncExec(new Runnable() {

			public void run() {
				photoPathFileLabel.setText(f.getName());
				photoFile = f;
			}
		});
	}

	private void createControlListeners() {
		videoRadioButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				if ( videoRadioButton.getSelection()) {
					enableGroup(videoGroup);

					Display.getDefault().syncExec( new Runnable() {			
						public void run() {

							videoTimingroup.setEnabled(videoRadioButton.getSelection());
						}
					});
				}

			}
		});
		
		

		videoTimingButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				final boolean b = videoTimingButton.getSelection();
				Display.getDefault().syncExec(new Runnable() {						
					public void run() {
						videoTimingroup.setEnabled(b);
					}
				});
			}
		});

		videoSearchButton.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog( getShell() );
				String videoFilePath = fd.open();
				if ( ! Utils.isAnEmptyString(videoFilePath) ) {
					File f = new File(videoFilePath);
					if ( ! f.exists() ) {
						MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
						mb.setMessage("File ["+videoFilePath+"] not exists ");
					}
					updateVideoLabel(f);
				}
			}
		});

		blackButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				if ( blackButton.getSelection()) {
					enableGroup(blackGroup);
				}

			}
		});

		hiddenButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				if ( hiddenButton.getSelection()) {
					enableGroup(hiddenGroup);
				}

			}
		});

		watchButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				if ( watchButton.getSelection()) {
					enableGroup(watchGroup);
				}

			}
		});

		photoButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				if ( photoButton.getSelection()) {
					enableGroup(photoGroup);
				}

			}
		});

		photoFileButton.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog( getShell() );
				String videoFilePath = fd.open();
				if ( ! Utils.isAnEmptyString(videoFilePath) ) {
					File f = new File(videoFilePath);
					if ( ! f.exists() ) {
						MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
						mb.setMessage("File ["+videoFilePath+"] not exists ");
					}
					updatePhotoLabel(f);
				}
			};
		});

		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				Video  v = null;
				if ( videoRadioButton.getSelection()){ 
					if ( videoFile != null ) {
						v = new Video();
						v.setType(AcceptedVideoTypes.VIDEO);
						v.setPath(videoFile.getPath());
						if ( videoTimingButton.getSelection() ) {
							int hhF = videoFromDateTime.getHours();
							int mmF = videoFromDateTime.getMinutes();
							
							v.setFrom(Utils.getTimeAsString(hhF, mmF));
							
							int hhT = videoToDateTime.getHours();
							int mmT = videoToDateTime.getMinutes();
							
							v.setTo(Utils.getTimeAsString(hhT, mmT));
							
						}

					} else {
						MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
						mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
						mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_NO_SELECTED_FILE_VIDEO));
						mb.open();
					}
				}

				if ( photoButton.getSelection() ) {
					if ( photoFile != null ) {
						v = new Video();
						v.setType(AcceptedVideoTypes.PHOTO);
						v.setDuration( photoSpinner.getSelection());
						v.setPath(photoFile.getPath());
						if ( photoTimingButton.getSelection() ) {
							int hhF = photoFromDateTime.getHours();
							int mmF = photoFromDateTime.getMinutes();
							
							v.setFrom(Utils.getTimeAsString(hhF, mmF));
							
							int hhT = photoToDateTime.getHours();
							int mmT = photoToDateTime.getMinutes();
							
							v.setTo(Utils.getTimeAsString(hhT, mmT));
							
							v.setTo(Utils.getTimeAsString(hhT, mmT));
							
							
						}
					} else {
						MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
						mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_NO_SELECTED_FILE_PHOTO));
						mb.open();
					}
				}


				if ( watchButton.getSelection() ) {
					v = new Video();
					v.setType(AcceptedVideoTypes.WATCH);
					v.setDuration( watchSpinner.getSelection() );

				} 

				if ( hiddenButton.getSelection() ) {
					v = new Video();
					v.setType(AcceptedVideoTypes.HIDDENWINDOW);
					v.setDuration( hiddenSpinner.getSelection() );

				} 

				if ( blackButton.getSelection() ) {
					v = new Video();
					v.setType(AcceptedVideoTypes.BLACKWINDOW);
					v.setDuration( blackSpinner.getSelection() );
				} 

				if ( v != null ) {
					try {
						Media vv = new Media( sequenceSMP,  v);	
						boolean insert = true;
						if ( vv.isDateOrTimed() ) {
							if ( ! checkHours(v) ) {
								MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
								mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
								mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_TIME));	
								mb.open();
								insert = false;
							}
						}
						if ( insert ) {
							if ( videoSMP != null ) {
								videoSMP = vv;
							} else {					
								vv.setId(sequenceSMP.getVideos().getVideoCount() + 1);
								sequenceSMP.getVideos().addVideo(vv);						
							}
							sequenceManager.getSequecensListViewer().refresh();
						sequenceManager.getVideosOfSequencesTableViewer().add(vv);
							slm.setMessage(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_ADDED) + " " + Utils.getMediaDescription(vv) );
						}

					} catch (SequenceMoviePlayerException e) {
						MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
						mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
						mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_INSERT_MEDIA));			
						mb.open();
					}

				}

			}
		});


		closeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				Display.getCurrent().syncExec(new Runnable() {					
					public void run() {
						close();
					}
				});
			}
		});
		
		
		photoTimingButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				final boolean b = photoTimingButton.getSelection();
				Display.getDefault().syncExec(new Runnable() {						
					public void run() {
						photoTimingGroup.setEnabled(b);
					}
				});
			}
		});



	}
	
	
//	protected MenuManager createMenuManager() {
//		MenuManager menu = new MenuManager();
//		
//		MenuManager video_menu = new MenuManager(LocaleManager.getText(LocaleManager.APP_MENU_FILE));
//		AddVideoAction addVideoAction = new AddVideoAction();
//		video_menu.add(addVideoAction);
//		
//		menu.add(video_menu);	
//		
//		return menu;
//	}

	private boolean checkHours(Video v) {
		Date f =  Utils.getTimeMedium(v.getFrom());
		Date t =  Utils.getTimeMedium(v.getFrom());
		return f.before(t);
	}
	
	protected StatusLineManager createStatusLineManager() {
		return slm;
	}

}


