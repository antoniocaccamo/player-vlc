package it.antanysavage.sm.player;


import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.exception.SequenceMoviePlayerException;
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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;


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
public class ScreenManagerCompositeOld extends Composite  {
	/*

//	private static long CYCLES_REBOOT = 100;


	public Button getStopButton() {
		return stopButton;
	}

	public Button getPlayButton() {
		return playButton;
	}

	private static Logger logger = LoggerFactory.getLogger(ScreenManagerComposite.class);

	protected int index;
	private Button screenLockCheckedBox;
	private Spinner widthSpinner;
	private Spinner heightSpinner;
	private Spinner leftSpinner;
	private Spinner topSpinner;
	private Button allDayRadioButton;
	private Button timedRadioButton;
	private DateTime fromDateTime;
	private DateTime toDateTime;
	private Button blackWindowRadioButton;
	private Button watchRadioButton;

	private Table videoTable;
	private ProgressBar videoProgressBar;
	private Button stopButton;
	private Button playButton;
	private Button viewScreenCheckedBox;

	private PlayerMaster playerMaster = new PlayerMaster(this);
	private Group sizeGroup;
	private Group positionGroup;
	private Button firstButton;
	private Button prevButton;
	private Button nextButton;
	private Button lastButton;
	private Button pauseButton;

	private Player smPlayer;
	private Label numberOfVideosLabel;
	private Label durationLabel;
	private Label pathLabel;

	private Group sequenceGroup ;//= new Group(this, SWT.NONE);
	private Combo sequenceCombo ;//= new Combo(sequenceGroup, SWT.NONE);

	private PlayerSetting setting ;

	private Timer screenCompositeTimer = new Timer();

	private Calendar start;

	private Calendar end;

	private boolean running;

	private Button applyScreenModifyButton;

	private Button referCheckedBox;

	//	private MyControlAdpater myControlAdapter;

	private Label mediaLabel;

	private Label mediaIDLabel;

	private Label mediaTypeLabel;

	private Label mediaDurationLabel;

	private Button activeTimingButton;

	private ScreenCompositeTimerTask screenCompositeTimerTask;

	private ReferComposite referWindow;

	private DimensionAndPositionModifyListener widthSpinnerModifyListener;

	private DimensionAndPositionModifyListener heightSpinnerModifyListener;

	private PositionModifyListener leftSpinnerModifyListener;

	private PositionModifyListener topSpinnerModifyListener;

	private Group screenGroup;
	private Rectangle screenGroupBounds;
	
	private int status = Status.PAUSED;
*/
	public ScreenManagerCompositeOld(Composite parent,  Player smPlayer, int i) {
		super(parent, SWT.EMBEDDED);
/*		this.smPlayer = smPlayer;
		this.index = i;
		GridLayout thisLayout = new GridLayout();
		this.setLayout(thisLayout);
		GridData gridData = new GridData();
		gridData.horizontalSpan = GridData.FILL;
		gridData.verticalSpan = GridData.FILL;		
		this.setLayoutData(gridData);
		this.createControls();

		this.getPlayerMaster().create();
		this.getPlayerMaster().setVideoManager(this);



		

		pack();
		*/
	}

/*

	public int getIndex() {
		return index;
	}

	public Player getSmPlayer() {
		return smPlayer;
	}



	private void createControls() {

//		Screen


		screenGroup = new Group(this, SWT.NONE);
		screenGroup.setText( LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN));

		screenLockCheckedBox = new Button(screenGroup, SWT.CHECK | SWT.LEFT);
		GridData screenLockCheckBoxLData = new GridData();
		screenLockCheckBoxLData.grabExcessHorizontalSpace = true;
		screenLockCheckBoxLData.grabExcessVerticalSpace = true;
		screenLockCheckBoxLData.horizontalAlignment = GridData.BEGINNING;
		screenLockCheckBoxLData.horizontalSpan = 2;
		screenLockCheckedBox.setLayoutData(screenLockCheckBoxLData);
		screenLockCheckedBox.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_LOCK));

		//		applyScreenModifyButton = new Button(screenGroup, SWT.PUSH);
		//		GridData applyScreenModifyButtonLData = new GridData();
		//		//		applyScreenModifyButtonLData.horizontalSpan = 2;
		//		applyScreenModifyButtonLData.horizontalAlignment = GridData.CENTER;
		//		applyScreenModifyButtonLData.grabExcessHorizontalSpace = true;
		//		applyScreenModifyButton.setLayoutData(applyScreenModifyButtonLData);
		//		applyScreenModifyButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_APPLY_MODIFIES));



		GridData compositeLData = new GridData();
		compositeLData.horizontalAlignment = GridData.FILL;
		compositeLData.grabExcessHorizontalSpace = true;
		compositeLData.verticalAlignment = GridData.FILL;
		screenGroup.setLayoutData(compositeLData);
		screenGroup.setLayout( new GridLayout(2, true));  


		sizeGroup = new Group(screenGroup, SWT.NONE);
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



		positionGroup = new Group(screenGroup, SWT.NONE);
		GridLayout positionGroupLayout = new GridLayout(2, false);
		positionGroupLayout.makeColumnsEqualWidth = true;
		positionGroup.setLayout(positionGroupLayout);
		GridData positionGroupLData = new GridData();
		positionGroupLData.horizontalAlignment = GridData.FILL;
		positionGroupLData.verticalAlignment = GridData.FILL;
		positionGroupLData.grabExcessVerticalSpace = true;
		positionGroup.setLayoutData(positionGroupLData);
		positionGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_LOCATION));

		viewScreenCheckedBox = new Button(screenGroup, SWT.CHECK | SWT.LEFT);
		GridData hideScreenCheckedBoxLData = new GridData();
		hideScreenCheckedBoxLData.grabExcessHorizontalSpace = true;
		hideScreenCheckedBoxLData.grabExcessVerticalSpace = true;
		hideScreenCheckedBoxLData.horizontalAlignment = GridData.FILL;
		hideScreenCheckedBoxLData.horizontalSpan = 1;
		viewScreenCheckedBox.setLayoutData(hideScreenCheckedBoxLData);
		viewScreenCheckedBox.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_VIEW));
		viewScreenCheckedBox.setSelection(true);

		referCheckedBox = new Button(screenGroup, SWT.CHECK | SWT.LEFT);
		GridData referCheckedBoxLData = new GridData();
		referCheckedBox.setLayoutData(referCheckedBoxLData);
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


		sizeGroup.pack();
		positionGroup.pack();
		screenGroup.pack();

//		Activation

		Group activationGroup = new Group(this, SWT.NONE);
		GridLayout activationGroupLayout = new GridLayout(2, false);
		activationGroupLayout.makeColumnsEqualWidth = true;
		activationGroup.setLayout(activationGroupLayout);
		GridData activationLData = new GridData();
		activationLData.grabExcessHorizontalSpace = true;
		activationLData.horizontalAlignment = GridData.FILL;
		activationLData.verticalAlignment = GridData.BEGINNING;
		//		activationLData.grabExcessVerticalSpace = true;
		activationGroup.setLayoutData(activationLData);
		activationGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION));

		Group timingGroup = new Group(activationGroup, SWT.NONE);
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


		Group disactiveGroup = new Group(activationGroup, SWT.NONE);
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


		timingGroup.pack();
		disactiveGroup.pack();

		activeTimingButton = new Button(activationGroup, SWT.PUSH);		
		activeTimingButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_ACTIVE));
		GridData activeTimingButtonLData = new GridData();
		activationLData.horizontalAlignment = GridData.FILL;
		activeTimingButton.setLayoutData(activeTimingButtonLData);

		activationGroup.pack();

		
//		 Sequence
		 


		sequenceGroup = new Group(this, SWT.NONE);		
		GridData sequenceGroupLData = new GridData();
		sequenceGroupLData.horizontalAlignment = GridData.FILL;
		GridLayout sequenceLayout = new GridLayout(2, false);
		sequenceGroup.setLayout(sequenceLayout);
		sequenceGroup.setLayoutData(sequenceGroupLData);
		sequenceGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCE));

		Label sequenceLabel = new Label(sequenceGroup, SWT.NONE);
		GridData sequenceLabelLData = new GridData();
		sequenceLabel.setLayoutData(sequenceLabelLData);
		sequenceLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCE_CHOOSE));


		GridData sequenceComboLData = new GridData();
		sequenceComboLData.grabExcessHorizontalSpace = true;
		sequenceComboLData.horizontalAlignment = GridData.FILL;
		sequenceComboLData.heightHint = 21;
		sequenceCombo = new Combo(sequenceGroup, SWT.NONE);
		sequenceCombo.setLayoutData(sequenceComboLData);


		GridData gData = new GridData();
		gData.grabExcessHorizontalSpace = true;
		gData.horizontalAlignment = GridData.FILL;
		Label label = new Label(sequenceGroup, SWT.NONE);
		label.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCE_NUMBER_OF_VIDEO));
		//		label.setLayoutData(gData);

		numberOfVideosLabel = new Label(sequenceGroup, SWT.NONE);
		numberOfVideosLabel.setText("");
		numberOfVideosLabel.setLayoutData(gData);
		
		if ( ! Player.MPLAYER_MODE ) {

			label = new Label(sequenceGroup, SWT.NONE);
			label.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCE_DURATION));
			//		label.setLayoutData(gData);

			durationLabel = new Label(sequenceGroup, SWT.NONE);
			durationLabel.setText("");
			durationLabel.setLayoutData(gData);
		}

		label = new Label(sequenceGroup, SWT.NONE);
		label.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCE_NAME));
		//		label.setLayoutData(gData);

		pathLabel = new Label(sequenceGroup, SWT.NONE);
		pathLabel.setText("");
		pathLabel.setLayoutData(gData);


		//player

		GridData playerGroupLData = new GridData();
		playerGroupLData.horizontalAlignment = GridData.FILL;
		playerGroupLData.grabExcessVerticalSpace = true;
		playerGroupLData.verticalAlignment = GridData.FILL;
		playerGroupLData.grabExcessHorizontalSpace = true;

		Group playerGroup =new Group(this, SWT.NONE);		
		GridLayout playerGroupLayout = new GridLayout(2, false);
		playerGroupLayout.makeColumnsEqualWidth = false;
		playerGroup.setLayout(playerGroupLayout);
		playerGroup.setLayoutData(playerGroupLData);
		playerGroup.setText("Player");
		
		// player - buttons
		
		Composite buttonsComposite = new Composite(playerGroup, SWT.NONE);
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
		stopButton.setImage( new Image(getDisplay(), ClassLoader.getSystemResourceAsStream("images/player/stop.png") ) );


		pauseButton = new Button(buttonsComposite, SWT.TOGGLE | SWT.CENTER);
		GridData pauseButtonLData = new GridData();		
		pauseButtonLData.horizontalAlignment = GridData.CENTER;
		pauseButton.setLayoutData(pauseButtonLData);
		pauseButton.setImage( new Image(getDisplay(), ClassLoader.getSystemResourceAsStream("images/player/pause.png") ) );
		pauseButton.setEnabled(false);

		playButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		GridData applyButtonLData = new GridData();
		applyButtonLData.grabExcessHorizontalSpace = true;
		applyButtonLData.horizontalAlignment = GridData.CENTER;
		playButton.setLayoutData(applyButtonLData);

		playButton.setImage( new Image(getDisplay(), ClassLoader.getSystemResourceAsStream("images/player/play.png") ) );
		
		// player - media composite

		Composite mediaComposite = new Composite(playerGroup, SWT.NONE);
		GridLayout mediaCompositeGridLayout = new GridLayout(2, false);
		mediaComposite.setLayout( mediaCompositeGridLayout);
		GridData mediaCompositeLData = new GridData();
		mediaCompositeLData.verticalAlignment = GridData.FILL;
		mediaCompositeLData.grabExcessHorizontalSpace = true;
		mediaCompositeLData.grabExcessVerticalSpace = true;
		mediaCompositeLData.horizontalAlignment = GridData.FILL;
		mediaCompositeLData.verticalAlignment   = GridData.CENTER;
		mediaComposite.setLayoutData(mediaCompositeLData);

		GridData labelLData = new GridData();
		labelLData.horizontalAlignment = GridData.FILL_HORIZONTAL;
		labelLData.grabExcessHorizontalSpace = false;
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

		videoProgressBar = new ProgressBar(playerGroup, SWT.SMOOTH);
		GridData videoProgressBarLData = new GridData();
		videoProgressBarLData.horizontalSpan = 2;
		videoProgressBarLData.grabExcessHorizontalSpace = true;
		videoProgressBarLData.horizontalAlignment = GridData.FILL;
		videoProgressBarLData.grabExcessVerticalSpace = true;
		videoProgressBarLData.verticalAlignment = GridData.CENTER;
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


		sequenceGroup.pack();

		referWindow = new ReferComposite(this);		
		referWindow.create();
	

	}

	public void updatePlayLabel(final Program smp) {
		numberOfVideosLabel.setText( "" + smp.getVideos().getVideoCount() );
		if ( ! Player.MPLAYER_MODE ) {
			durationLabel.setText(smp.getDuration());
		}
		
		pathLabel.setText(smp.getFilepath().getName());
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

	public Combo getSequenceCombo() {
		return sequenceCombo;
	}

	public boolean isBlackWindowWhenNotActive() {
		return setting.isWhenNotActiveBlack();
	}

	public boolean isWatchWindowWhenNotActive() {
		return setting.isWhenNotActiveWatch();
	}

	private void updateTiming(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {					
			public void run() {
				fromDateTime.setEnabled(b);
				toDateTime.setEnabled(b);

			}
		});

	}
	
	public PlayerSetting getPlayerSetting() {
		return this.setting;
	}

	@SuppressWarnings("deprecation")
	public void setPlayerSetting(PlayerSetting playerSetting) {


		this.setting = playerSetting ;



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

		this.screenLockCheckedBox.setSelection(setting.isLock());
		this.getPlayerMaster().block(setting.isLock());

		this.viewScreenCheckedBox.setSelection( setting.isViewScreen() );
		this.getPlayerMaster().setVisibility( setting.isViewScreen() );

		if ( setting.getSequenceFile() != null ) {

			try {
				if (setting.getSequenceFile().exists() ) {
					SequenceFileManager sfm = new SequenceFileManager();
					Program smp = Utils.getSequenceSMP( sfm.read(setting.getSequenceFile() ), setting.getSequenceFile().getPath());
					//				if ( smp != null ) {
					this.smPlayer.addSequence(smp);
					this.getPlayerMaster().setSequence(smp);
					//				} else {

					//				}
				} else {
					MessageBox mb = new MessageBox( getShell() , SWT.OK | SWT.ICON_WARNING);
					mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
					mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_NOT_FOUND) + "File ["+setting.getSequenceFile().getPath() +"]");
					mb.open();
				}
			} catch (SequenceMoviePlayerException e) {
				//			this.playerMaster.deActive();
				MessageBox mb = new MessageBox( getShell() , SWT.OK | SWT.ICON_WARNING);
				mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
				mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_LOADING) + "File ["+setting.getSequenceFile().getPath() +"]");
				mb.open();
				logger.error("error occurred ", e);
			}
		}

		this.allDayRadioButton.setSelection(setting.isAllDay());

		if ( setting.isAllDay() && this.getPlayerMaster().getSequence() != null) {						
			this.sequenceCombo.setText(this.getPlayerMaster().getSequence().getName());
			this.updatePlayLabel(this.getPlayerMaster().getSequence());
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

		if ( setting.isTimed() && setting.getFrom() != null && setting.getTo() != null && this.getPlayerMaster().getSequence() != null) {
			logger.info("timed : from [" + Utils.getTimeAsString(setting.getFrom()) +"] to [" + Utils.getTimeAsString(setting.getTo())+"]");
			this.screenLockCheckedBox.setSelection(true);
			String s = this.getPlayerMaster().getSequence().getName();
			this.sequenceCombo.setText(s);
			this.updatePlayLabel(this.getPlayerMaster().getSequence());
			this.block(true);
						
			
			setTimer();

		}

		this.watchRadioButton.setSelection(setting.isWhenNotActiveWatch());
		this.blackWindowRadioButton.setSelection(setting.isWhenNotActiveBlack());

		this.referCheckedBox.setSelection(false);

		createControlsListeners();

		block( this.screenLockCheckedBox.getSelection() );
		
		setRunning(true);
		
		screenCompositeTimerTask = new ScreenCompositeTimerTask(this);
		screenCompositeTimer.schedule(screenCompositeTimerTask, 0, Player.TIMING_PERIOD);

	}

	private void setTimer() {

		Calendar now = Calendar.getInstance(LocaleManager.getLocale());
		
		setStart((Calendar)now.clone());
		getStart().set(Calendar.HOUR_OF_DAY, this.setting.getFrom().getHours());
		getStart().set(Calendar.MINUTE, this.setting.getFrom().getMinutes());
		getStart().set(Calendar.SECOND, 0);
		
		logger.info("start : " + Utils.getTimeAsString(getStart()) );

		setEnd((Calendar)now.clone());
		getEnd().set(Calendar.HOUR_OF_DAY, this.setting.getTo().getHours());
		getEnd().set(Calendar.MINUTE, this.setting.getTo().getMinutes());
		getEnd().set(Calendar.SECOND, 0);
		
		logger.info("end   : " + Utils.getTimeAsString(getEnd()) );

		logger.info("new timer : now [" + Utils.getTimeAsString(now) + "] start [" + Utils.getTimeAsString(getStart()) + "] end [" + Utils.getTimeAsString(getEnd())+ "]");
		
		if ( now.before(getStart()) || now.after(getEnd())) {
			logger.info("start time [" + Utils.getTimeAsString( getStart()) + "]");
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


//	private void setupActivationTimerSchedulation( Calendar start, Calendar end ) {
//		Calendar now = Calendar.getInstance(LocaleManager.getLocale());
//		long delay = 0;
//
//		logger.info("initial schedulation : now " + Utils.debugDate(now) + " start " + Utils.debugDate(getStart()) + " end " + Utils.debugDate(getEnd()));
//
//		if ( now.before(getStart()) ) {
//			this.setRunnig(false);
//			//			this.playerMaster.play();
//			delay = getStart().getTimeInMillis() - now.getTimeInMillis();
//			logger.info("start date " + Utils.debugDate(getStart()));
//			this.getPlayerMaster().deActive();
//		}
//		else {
//			if ( getStart().before(now) && getEnd().after(now)) {
//				this.setRunnig(true);
//				this.getPlayerMaster().play();
//				this.pauseButton.setEnabled(true);
//				delay = getEnd().getTimeInMillis() - now.getTimeInMillis();
//				logger.info("end date " + Utils.debugDate(getEnd()));
//			} else {
//				this.setRunnig(false);
//				getStart().add(Calendar.DAY_OF_MONTH, 1);
//				delay =  getStart().getTimeInMillis() - now.getTimeInMillis();
//				logger.info("start date " + Utils.debugDate(getStart()));
//				this.getPlayerMaster().deActive();
//			}
//		}
//		setupActivationTimerScheduleDelay(delay);
//	}
//
//	private void setupActivationTimerScheduleDelay( long delay ) {
//		logger.info("next delay [ ms ] : " + delay );
//		if ( screenCompositeTimerTask != null  ) {
//			screenCompositeTimerTask.cancel();			
//		}
//		this.screenCompositeTimerTask = new ScreenCompositeTimerTask( this);
//		screenCompositeTimer.purge();		
//		this.screenCompositeTimer.schedule( screenCompositeTimerTask, delay);
//	}


	void block(final boolean block) {
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
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
					final Program smp = (Program) smPlayer.getSequenceSMP(seq);
					getPlayerMaster().setSequence(smp);
					Display.getDefault().syncExec(
							new Runnable() {
								public void run() {									
									updatePlayLabel(smp);
								}
							}
					);
					setting.setSequenceFile(smp.getFilepath());
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

		watchRadioButton.addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						if ( setting != null ) {
							setting.setWhenNotActiveWatch(watchRadioButton.getSelection());
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
				MessageDialog.openError(
						getShell(), 
						getShell().getText(), 
						LocaleManager.getText(LocaleManager.ERRORS_TIME)
				);
				return;
			}
			
			setting.setAllDay(false);
			setting.setTimed(true);			
			setting.getFrom().setHours( this.fromDateTime.getHours() );
			setting.getFrom().setMinutes( this.fromDateTime.getMinutes() );

			setting.getTo().setHours( this.toDateTime.getHours() );
			setting.getTo().setMinutes( this.toDateTime.getMinutes() );
			
			logger.info("timing changed to TIMED : now " + Utils.getTimeAsString(now) + " from [" + Utils.getTimeAsString(setting.getFrom()) + "] to [" + Utils.getTimeAsString(setting.getTo())+"]");
			
			setRunning(true);		

			setTimer();
		}


	}



	public void setTimer(Timer timer) {
		this.screenCompositeTimer = timer;
	}

	public Timer getTimer() {
		return screenCompositeTimer;
	}

	public void setRunning(boolean runnig) {
		logger.info("setting running : " + running );
		this.running = runnig;
	}

	public boolean isRunning() {
		return running;
	}

	public void setPlayerMaster(PlayerMaster playerMaster) {
		this.playerMaster = playerMaster;
	}

	public PlayerMaster getPlayerMaster() {
		return playerMaster;
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

	public void resetVideoProgressBarMaximum() {
		
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				videoProgressBar.setSelection(0);
				videoProgressBar.setMaximum(100);
			}
		});
	}

	public void updateVideoProgressBarSelection(final int i) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				videoProgressBar.setSelection(i);
//				logger.info("updating updateVideoProgressBarSelection [" + i +"]");
			}
		});
	}

	public void updateMediaLabels( final Media media) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				File f = null;
				if ( ! Utils.isAnEmptyString(media.getPath()) ) {
					f = new File( media.getPath() );
				}
				mediaLabel.setText( f == null ? "" : f.getName() );
				mediaIDLabel.setText( "" + media.getId() + "/" + media.getMyProgram().getVideos().getVideoCount());
				mediaDurationLabel.setText("" + Utils.getDurationString(media.getDuration()));
				mediaTypeLabel.setText( LocaleManager.getText("model.sequece.media." + media.getType().getType() ));
			}
		});
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

	//	public class MyControlAdpater extends ControlAdapter {
	//		public void controlResized(ControlEvent evt) {						
	//			Point location = getPlayerMaster().getShell().getLocation();
	//			Point size     = getPlayerMaster().getShell().getSize();
	//			changeSpinners(size, location);
	//		}
	//	}

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

	public boolean hasPlayerSetting() {
		if ( setting != null)
			return true;
		return false;
	}

	public Control getPauseButton() {
		return pauseButton;
	}

	
	public void play () {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				block( true );
				setRunning(true);
				viewScreenCheckedBox.setSelection(true);
				getPlayerMaster().play();
				getPauseButton().setEnabled(true);
				status = Status.PLAYNG;
			}
		});
	}


	public void stop() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {	
				setRunning(false);
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
		
	
 */
}
