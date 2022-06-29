package it.antanysavage.sm.player.swt.views;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.jface.ProgramTab;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Video;
import it.antanysavage.sm.player.sequences.schema.types.AcceptedVideoTypes;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

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
public class MediaModifyComposite extends Composite {

	private static Logger logger = LoggerFactory.getLogger(MediaModifyComposite.class);

	//	private static final String[] VIDEO_FILTERS = {
	//		"*.avi;*.mov;*.mpeg;*.mpg" ,
	//		"*.avi"  ,
	//		"*.mov"  ,
	//		"*.mpeg" ,
	//		"*.mpg"  ,
	//		"*.*"
	//	};
	//
	//	private static final String[] PHOTO_FILTERS = {
	//		"*.bmp;*.jpeg;*.jpg;*.png" ,
	//		"*.bmp"  ,
	//		"*.jpeg" ,
	//		"*.jpg"  ,
	//		"*.png"  ,
	//		"*.*"
	//	};

	private Group mediaGroup;
	private Group timedGroup;
	private Label fromLabel;
	private Table pathTable;
	private Group typeGroup;
	private Label durationLabel;
	private Group durationGroup;
	//	private Group previewGroup;
	private Button searchButton;
	private Composite buttonsComposite;
	private Group daysGroup;
	private Button daysButton;
	private Group fileGroup;
	private Button closeButton;
	private Button modfiyButton;
	private Label toLabel;
	private Button timedButton;
	private Combo typeCombo;
	private DateTime mediaFromDateTime;
	private DateTime mediaToDateTime;
	private boolean searchFileNeeded;
	private Program program;
	private Media media;

	private int mediaType;

	private Spinner durationSpinner;

	private File file;

	//	private SequenceManager sequenceManager;

	private DateTime startCalendarCombo;

	private DateTime endCalendarCombo;

	private Button limitedCheckbox;

	private Spinner limitedSpinner;

	private ProgramTab programTab;

	private Button allDaysCheckButton;

	private Group allDaysGroup;

	private Button mondayCheckBox;

	private Button tuesdayCheckBox;

	private Button wednesdayCheckBox;

	private Button thursdayCheckBox;

	private Button fridayCheckBox;

	private Button saturdayCheckBox;

	private Button sundayCheckBox;
	private Composite searchComposite;
	private Group mediaGroupGroup;
	private Label enableMediaGroupButton;
	private Combo mediaGroupCombo;
	private Label enableMediaScreenButton;
	private Combo mediaScreenCombo;

	private Composite werbUrlComposite;
	private Composite pathComposite;
	private Text webUrlPathText;

	private StackLayout fileGroupStackLayout;

	private HashMap<Integer, AcceptedVideoTypes> mediaTypeHashMap = new HashMap<Integer, AcceptedVideoTypes>();

	/**
	 * Auto-generated main method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	 * Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	 */	
	protected void checkSubclass() {
	}

	/**
	 * Auto-generated method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		MediaModifyComposite inst = new MediaModifyComposite(shell,  null,  null);
		Point size = inst.getSize();
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

	public MediaModifyComposite(Composite parent, ProgramTab programTab, Media media) {
		super(parent, SWT.NONE);		
		this.programTab = programTab; 
		this.media = media;
		
		initGUI();
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	private void initGUI() {

		GridLayout thisLayout = new GridLayout();
		thisLayout.makeColumnsEqualWidth = false;
		this.setLayout(thisLayout);

		mediaGroup = new Group(this, SWT.NONE);
		GridLayout mediaGroupLayout = new GridLayout(2, false);

		mediaGroup.setLayout(mediaGroupLayout);
		GridData mediaGroupLData = new GridData();
		mediaGroupLData.grabExcessHorizontalSpace = true;
		mediaGroupLData.horizontalAlignment = GridData.FILL;
		mediaGroupLData.grabExcessVerticalSpace = true;
		mediaGroupLData.verticalAlignment = GridData.BEGINNING;
		mediaGroupLayout.makeColumnsEqualWidth = true;
		mediaGroup.setLayoutData(mediaGroupLData);
		mediaGroup.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA));

		typeGroup = new Group(mediaGroup, SWT.NONE);
		GridLayout typeGroupLayout = new GridLayout();
		typeGroupLayout.makeColumnsEqualWidth = true;
		typeGroup.setLayout(typeGroupLayout);
		GridData typeGroupLData = new GridData();
		typeGroupLData.grabExcessHorizontalSpace = true;
		typeGroupLData.horizontalAlignment = GridData.FILL;
		typeGroupLData.verticalAlignment = GridData.FILL;
		typeGroupLData.grabExcessVerticalSpace = true;
		typeGroup.setLayoutData(typeGroupLData);
		typeGroup.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_1));

		typeCombo = new Combo(typeGroup, SWT.NONE);
		GridData typeComboLData = new GridData();
		typeComboLData.grabExcessHorizontalSpace = true;
		typeComboLData.horizontalAlignment = GridData.FILL;
		typeCombo.setLayoutData(typeComboLData);
		typeCombo.setText(" --- ");

		durationGroup = new Group(mediaGroup, SWT.NONE);
		GridLayout durationGroupLayout = new GridLayout(2, false);
		durationGroupLayout.makeColumnsEqualWidth = true;
		GridData durationGroupLData = new GridData();
		durationGroupLData.grabExcessHorizontalSpace = true;
		durationGroupLData.horizontalAlignment = GridData.FILL;
		durationGroupLData.verticalAlignment = GridData.FILL;
		durationGroupLData.grabExcessVerticalSpace = true;
		durationGroup.setLayout(durationGroupLayout);
		durationGroup.setLayoutData(durationGroupLData);
		durationGroup.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_2));

		durationLabel = new Label(durationGroup, SWT.NONE);
		GridData durationLabelLData = new GridData();
		durationLabelLData.grabExcessHorizontalSpace = true;
		durationLabelLData.horizontalAlignment = GridData.FILL;
		durationLabel.setLayoutData(durationLabelLData);
		durationLabel.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_2));

		durationSpinner = new Spinner(durationGroup, SWT.NONE);
		GridData durationSpinnerLData = new GridData();
		durationSpinnerLData.grabExcessHorizontalSpace = true;
		durationSpinnerLData.horizontalAlignment = GridData.CENTER;
		durationSpinner.setLayoutData(durationSpinnerLData);
		durationSpinner.setMinimum(1);
		durationSpinner.setMaximum( Integer.MAX_VALUE);
		durationSpinner.setSelection(5);

		typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_0));
		mediaTypeHashMap .put(new Integer(0), AcceptedVideoTypes.VIDEO);

		typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_1));
		mediaTypeHashMap.put(new Integer(1), AcceptedVideoTypes.HIDDENWINDOW);

		typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_2));
		mediaTypeHashMap.put(new Integer(2), AcceptedVideoTypes.BLACKWINDOW);

		typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_3));
		mediaTypeHashMap.put(new Integer(3), AcceptedVideoTypes.WATCH);

		typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_4));
		mediaTypeHashMap.put(new Integer(4), AcceptedVideoTypes.PHOTO);

		typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_5));
		mediaTypeHashMap.put(new Integer(5), AcceptedVideoTypes.BROWSER);
		
		typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_6));
		mediaTypeHashMap.put(new Integer(6), AcceptedVideoTypes.WEATHER);

		typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_7));
		mediaTypeHashMap.put(new Integer(7), AcceptedVideoTypes.FTPVIDEO);

		typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_8));
		mediaTypeHashMap.put(new Integer(8), AcceptedVideoTypes.FTPIMAGE);


		fileGroup = new Group(mediaGroup, SWT.NONE);
		fileGroupStackLayout = new StackLayout();
		fileGroup.setLayout(fileGroupStackLayout);
		GridData fileGroupLData = new GridData();
		fileGroupLData.horizontalSpan = 2;
		fileGroupLData.grabExcessHorizontalSpace = true;
		fileGroupLData.horizontalAlignment = GridData.FILL;
		fileGroup.setLayoutData(fileGroupLData);
		fileGroup.setText("File");

		daysButton = new Button(mediaGroup, SWT.CHECK | SWT.LEFT);
		daysButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_DAY_CALENDAR));

		timedButton = new Button(mediaGroup, SWT.CHECK | SWT.LEFT);
		timedButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED));


		daysGroup = new Group(mediaGroup, SWT.NONE);
		GridLayout daysGroupLayout = new GridLayout(2, false);
		daysGroupLayout.makeColumnsEqualWidth = true;
		daysGroup.setLayout(daysGroupLayout);
		GridData daysGroupLData = new GridData();
		daysGroupLData.grabExcessVerticalSpace = true;
		daysGroupLData.grabExcessHorizontalSpace = true;
		daysGroupLData.verticalAlignment = GridData.FILL;
		daysGroupLData.horizontalAlignment = GridData.FILL;

		daysGroup.setLayoutData(daysGroupLData);
		daysGroup.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_DAY_DATES));

		Label startLabel = new Label(daysGroup, SWT.NONE);
		startLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		startLabel.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_DAY_START));

		startCalendarCombo = new DateTime(daysGroup, SWT.DROP_DOWN);		

		Label endLabel = new Label(daysGroup, SWT.NONE);
		endLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		endLabel.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_DAY_END));

		GridData activeComboControlLData1 = new GridData();
		endCalendarCombo = new DateTime(daysGroup, SWT.DROP_DOWN);
		GridData activeComboControlLData = new GridData();
		TableLayout tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData( 100 , true));

		pathComposite = new Composite(fileGroup, SWT.NONE);
		pathComposite.setLayout(new GridLayout(1, false));
		pathTable = new Table(pathComposite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		pathTable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		TableColumn column = new TableColumn(pathTable, SWT.NONE);
		column.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_3));
		column.pack();			
		pathTable.setHeaderVisible(true);
		pathTable.setLayout(tableLayout);

		searchComposite = new Composite(pathComposite, SWT.NONE);
		searchComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		GridLayout gl_searchComposite = new GridLayout(1, false);
		gl_searchComposite.marginRight = 100;
		gl_searchComposite.marginLeft = 100;
		searchComposite.setLayout(gl_searchComposite);

		searchButton = new Button(searchComposite, SWT.PUSH | SWT.CENTER);
		searchButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		searchButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CHOOSE_FILE));

		werbUrlComposite = new Composite(fileGroup, SWT.NONE);
		werbUrlComposite.setLayout(new GridLayout(1, false));

		webUrlPathText = new Text(werbUrlComposite, SWT.BORDER);
		webUrlPathText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		GridData searchButtonLData = new GridData();
		searchButtonLData.horizontalAlignment = GridData.CENTER;
		searchButtonLData.grabExcessHorizontalSpace = true;

		timedGroup = new Group(mediaGroup, SWT.NONE);
		GridLayout timedGroupLayout = new GridLayout(2, false);
		timedGroupLayout.makeColumnsEqualWidth = true;
		timedGroup.setLayout(timedGroupLayout);
		GridData timedGroupLData = new GridData();
		timedGroupLData.horizontalAlignment = GridData.FILL;
		timedGroupLData.grabExcessHorizontalSpace = true;
		timedGroupLData.horizontalSpan = 1;
		GridData timedGroupLData1 = new GridData();
		timedGroupLData1.horizontalAlignment = GridData.FILL;
		timedGroupLData1.grabExcessHorizontalSpace = true;
		timedGroupLData1.grabExcessVerticalSpace = true;
		timedGroupLData1.verticalAlignment = GridData.FILL;
		timedGroup.setLayoutData(timedGroupLData1);
		timedGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING));

		allDaysCheckButton = new Button(mediaGroup, SWT.CHECK);
		GridData allDaysCheckButtonLData = new GridData();
		allDaysCheckButtonLData.horizontalSpan = 2;
		allDaysCheckButtonLData.horizontalAlignment = GridData.FILL;
		allDaysCheckButton.setLayoutData(allDaysCheckButtonLData);
		allDaysCheckButton.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_8));
		allDaysCheckButton.setSelection(true);

		GridData allDaysGroupLData = new GridData();
		allDaysGroupLData.horizontalSpan = 2;
		allDaysGroupLData.horizontalAlignment = GridData.FILL;
		allDaysGroupLData.grabExcessHorizontalSpace = true;
		allDaysGroup = new Group(mediaGroup, SWT.NONE);
		GridLayout allDaysGroupLayout = new GridLayout();
		allDaysGroupLayout.makeColumnsEqualWidth = true;
		allDaysGroupLayout.numColumns = 7;
		allDaysGroup.setLayout(allDaysGroupLayout);
		allDaysGroup.setLayoutData(allDaysGroupLData);
		allDaysGroup.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_8));

		mondayCheckBox = new Button(allDaysGroup, SWT.CHECK | SWT.LEFT);
		mondayCheckBox.setText( Utils.getDayOfWeek(Calendar.MONDAY) );
		mondayCheckBox.setSelection(true);

		tuesdayCheckBox = new Button(allDaysGroup, SWT.CHECK | SWT.LEFT);
		tuesdayCheckBox.setText( Utils.getDayOfWeek(Calendar.TUESDAY) );
		tuesdayCheckBox.setSelection(true);

		wednesdayCheckBox = new Button(allDaysGroup, SWT.CHECK | SWT.LEFT);
		wednesdayCheckBox.setText(Utils.getDayOfWeek(Calendar.WEDNESDAY));
		wednesdayCheckBox.setSelection(true);

		thursdayCheckBox = new Button(allDaysGroup, SWT.CHECK | SWT.LEFT);
		thursdayCheckBox.setText(Utils.getDayOfWeek(Calendar.THURSDAY));
		thursdayCheckBox.setSelection(true);

		fridayCheckBox = new Button(allDaysGroup, SWT.CHECK | SWT.LEFT);
		fridayCheckBox.setText(Utils.getDayOfWeek(Calendar.FRIDAY));
		fridayCheckBox.setSelection(true);

		saturdayCheckBox = new Button(allDaysGroup, SWT.CHECK | SWT.LEFT);
		saturdayCheckBox.setText(Utils.getDayOfWeek(Calendar.SATURDAY));
		saturdayCheckBox.setSelection(true);

		sundayCheckBox = new Button(allDaysGroup, SWT.CHECK | SWT.LEFT);
		sundayCheckBox.setText(Utils.getDayOfWeek(Calendar.SUNDAY));
		sundayCheckBox.setSelection(true);


		GridData limitedCompositeLData = new GridData();
		limitedCompositeLData.horizontalAlignment = GridData.FILL;
		limitedCompositeLData.grabExcessHorizontalSpace = true;
		limitedCompositeLData.grabExcessVerticalSpace = true;
		limitedCompositeLData.verticalAlignment = GridData.FILL;
		Group limitedComposite = new Group(mediaGroup, SWT.NONE);
		limitedComposite.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_9));
		GridLayout limitedCompositeLayout = new GridLayout();
		limitedCompositeLayout.numColumns = 2;
		limitedCompositeLayout.makeColumnsEqualWidth = false;
		limitedComposite.setLayout(limitedCompositeLayout);
		limitedComposite.setLayoutData(limitedCompositeLData);



		limitedCheckbox = new Button(limitedComposite, SWT.CHECK | SWT.LEFT);
		GridData limitedCheckboxLData = new GridData();
		limitedCheckboxLData.horizontalAlignment = GridData.FILL;
		limitedCheckboxLData.grabExcessHorizontalSpace = true;
		limitedCheckboxLData.grabExcessVerticalSpace = true;
		limitedCheckboxLData.verticalAlignment = GridData.FILL;
		limitedCheckbox.setLayoutData(limitedCheckboxLData);
		limitedCheckbox.setText( LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_9));

		GridData limitedSpinnerLData = new GridData();
		limitedSpinnerLData.grabExcessHorizontalSpace = true;
		limitedSpinnerLData.grabExcessVerticalSpace = true;
		limitedSpinnerLData.verticalAlignment = GridData.FILL;
		limitedSpinnerLData.horizontalAlignment = GridData.END;
		limitedSpinner = new Spinner(limitedComposite, SWT.NONE);
		limitedSpinner.setLayoutData(limitedSpinnerLData);
		limitedSpinner.setMinimum(1);
		limitedSpinner.setMaximum(1000);
		limitedSpinner.setEnabled(false);

		Calendar today = Calendar.getInstance();

		fromLabel = new Label(timedGroup, SWT.NONE);
		GridData fromLabelLData = new GridData();
		fromLabelLData.grabExcessHorizontalSpace = true;
		fromLabelLData.horizontalAlignment = GridData.FILL;
		fromLabel.setLayoutData(fromLabelLData);
		fromLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED_FROM));
		GridData mediaFromDateTimeLData = new GridData();
		mediaFromDateTimeLData.grabExcessHorizontalSpace = true;
		mediaFromDateTimeLData.horizontalAlignment = GridData.CENTER;
		mediaFromDateTime = new DateTime(timedGroup, SWT.DROP_DOWN | SWT.TIME);
		mediaFromDateTime.setLayoutData(mediaFromDateTimeLData);

		toLabel = new Label(timedGroup, SWT.NONE);
		GridData toLabelLData = new GridData();
		toLabelLData.grabExcessHorizontalSpace = true;
		toLabelLData.horizontalAlignment = GridData.FILL;
		toLabel.setLayoutData(toLabelLData);
		toLabel.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED_TO));

		GridData mediaToDateTimeLData = new GridData();
		mediaToDateTimeLData.horizontalAlignment = GridData.CENTER;
		mediaToDateTimeLData.grabExcessHorizontalSpace = true;
		mediaToDateTime = new DateTime(timedGroup, SWT.DROP_DOWN | SWT.TIME);
		mediaToDateTime.setLayoutData(mediaToDateTimeLData);

		mediaGroupGroup = new Group(mediaGroup, SWT.NONE);
		mediaGroupGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		mediaGroupGroup.setText("Gruppo");
		GridLayout gl_mediaGroupGroup = new GridLayout();
		gl_mediaGroupGroup.numColumns = 2;
		gl_mediaGroupGroup.makeColumnsEqualWidth = false;
		mediaGroupGroup.setLayout(gl_mediaGroupGroup);

		enableMediaGroupButton = new Label(mediaGroupGroup, SWT.CHECK);
		enableMediaGroupButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		enableMediaGroupButton.setText("Gruppo");

		mediaGroupCombo = new Combo(mediaGroupGroup, SWT.NONE);

		mediaGroupCombo.setEnabled(false);
		mediaGroupCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		enableMediaScreenButton = new Label(mediaGroupGroup, SWT.CHECK);
		enableMediaScreenButton.setText("Screen");

		mediaScreenCombo = new Combo(mediaGroupGroup, SWT.NONE);
		mediaScreenCombo.setEnabled(false);
		mediaScreenCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		//		previewGroup = new Group(this, SWT.NONE);
		//		StackLayout previewGroupLayout = new StackLayout();
		//		previewGroupLayout.topControl = null;
		//		previewGroup.setLayout(previewGroupLayout);
		//		GridData previewGroupLData = new GridData();
		//		previewGroupLData.horizontalAlignment = GridData.FILL;
		//		previewGroupLData.grabExcessHorizontalSpace = true;
		//		previewGroup.setLayoutData(previewGroupLData);
		//		previewGroup.setText("Preview");

		GridData buttonsCompositeLData = new GridData();
		buttonsCompositeLData.grabExcessHorizontalSpace = true;
		buttonsCompositeLData.grabExcessVerticalSpace = true;
		buttonsCompositeLData.verticalAlignment = GridData.BEGINNING;
		buttonsCompositeLData.horizontalAlignment = GridData.FILL;
		buttonsComposite = new Composite(this, SWT.NONE);
		GridLayout buttonsCompositeLayout = new GridLayout(4,false);
		buttonsCompositeLayout.marginRight = 20;
		buttonsCompositeLayout.marginLeft = 20;
		buttonsCompositeLayout.horizontalSpacing = 40;
		buttonsCompositeLayout.makeColumnsEqualWidth = true;
		buttonsComposite.setLayout(buttonsCompositeLayout);
		buttonsComposite.setLayoutData(buttonsCompositeLData);

		modfiyButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		GridData addButtonLData = new GridData();
		addButtonLData.grabExcessHorizontalSpace = true;
		addButtonLData.horizontalSpan = 2;
		addButtonLData.horizontalAlignment = SWT.FILL;
		modfiyButton.setLayoutData(addButtonLData);
		modfiyButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CHANGE));

		closeButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		GridData button1LData = new GridData();
		button1LData.horizontalAlignment = SWT.FILL;
		button1LData.grabExcessHorizontalSpace = true;
		button1LData.horizontalSpan = 2;
		closeButton.setLayoutData(button1LData);
		closeButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CLOSE));

		createControlsListeners();
		init();
		pack();
	}

	private void createControlsListeners() {
		typeCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				typeComboWidgetSelected();
			}
		});

		searchButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				FileDialog fileDialog = new FileDialog( Display.getDefault().getActiveShell() , SWT.OPEN );
				if ( mediaType == AcceptedVideoTypes.PHOTO_TYPE )
					fileDialog.setFilterExtensions(Player.PHOTO_FILTERS);
				if ( mediaType == AcceptedVideoTypes.VIDEO_TYPE )
					fileDialog.setFilterExtensions(Player.VIDEO_FILTERS);
				String path = fileDialog.open();				
				if ( ! Utils.isAnEmptyString(path) ) {								
					File f = new File( path );
					if ( f.exists() && f.isFile() && f.canRead() ) {
						updatePathLabel(f);	
					}						         	
				}
			}
		});

		daysButton.addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						enableDayGroup( daysButton.getSelection() );						
					}
				}
				);

		timedButton.addSelectionListener(
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						enableTimeGroup( timedButton.getSelection() );						
					}
				}
				);

		modfiyButton.addSelectionListener( 
				new SelectionAdapter() {
					public void widgetSelected(SelectionEvent arg0) {
						modfiyButtonSelected(arg0);
					}
				}
				);
		closeButton.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				getShell().dispose();
			}
		});

		allDaysCheckButton.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				enableAllDaysGroup( ! allDaysCheckButton.getSelection() );
				if ( allDaysCheckButton.getSelection() ) {
					anyDays();
				}
			}


		}
				);


		limitedCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				limitedSpinner.setEnabled( limitedCheckbox.getSelection() );
			}
		});

		mediaGroupCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
			}
		});
	}

	private void modfiyButtonSelected(SelectionEvent arg0) {		

		if ( mediaType != AcceptedVideoTypes.VIDEO_TYPE )  {
			media.setDuration( durationSpinner.getSelection() );
		}

		if ( mediaType == AcceptedVideoTypes.VIDEO_TYPE || mediaType == AcceptedVideoTypes.PHOTO_TYPE)  {
			media.setFile( this.file);

		}

		if (    mediaType == AcceptedVideoTypes.BROWSER_TYPE 	) {
			media.setPath( StringUtils.trim( webUrlPathText.getText()) );
		}

		if ( this.timedButton.getSelection() ) {

			int hhF = mediaFromDateTime.getHours();
			int mmF = mediaFromDateTime.getMinutes();	
			int ssF = mediaFromDateTime.getSeconds();
			media.setFrom(Utils.getTimeAsString(hhF, mmF, ssF));			
			int hhT = mediaToDateTime.getHours();
			int mmT = mediaToDateTime.getMinutes();	
			int ssT = mediaToDateTime.getSeconds();
			media.setTo(Utils.getTimeAsString(hhT, mmT,ssT));			
			if ( ! checkHours(media) ) {
				MessageDialog.openError(
						getShell(), 
						getShell().getText(), 
						LocaleManager.getText(LocaleManager.ERRORS_TIME)
						);
				return;
			}
			media.setFromDate( Utils.getTimeMedium( media.getFrom()));
			media.setToDate( Utils.getTimeMedium( media.getTo()));
		}
		media.setTimed( timedButton.getSelection() );

		if ( daysButton.getSelection() ) {

			Calendar start = Calendar.getInstance();
			start.set(Calendar.YEAR , startCalendarCombo.getYear());
			start.set(Calendar.MONTH, startCalendarCombo.getMonth());
			start.set(Calendar.DATE , startCalendarCombo.getDay());

			Calendar end = Calendar.getInstance();
			end.set(Calendar.YEAR , endCalendarCombo.getYear());
			end.set(Calendar.MONTH, endCalendarCombo.getMonth());
			end.set(Calendar.DATE , endCalendarCombo.getDay());
			if(start != null)
				Utils.setStartOfDay(start);
			if(end != null)
				Utils.setEndOfDay(end);
			if ( start == null || end == null || ! start.before(end) ) {
				MessageDialog.openError(
						getShell(), 
						getShell().getText(), 
						LocaleManager.getText(LocaleManager.ERRORS_DATE)
						);
				return;
			}
			media.setStartDate( start.getTime() );
			media.setEndDate ( end.getTime()  );
			media.setStart(Utils.getDateAsString(start.getTime()));
			media.setEnd(Utils.getDateAsString(end.getTime()));





			logger.debug(
					"media.getStartDate " + Utils.debugDate(media.getStartDate()) + 
					" - media.getEndDate " + Utils.debugDate(media.getEndDate()) 
					);
		}
		media.setDated( daysButton.getSelection());


		if ( allDaysCheckButton.getSelection() ) {
			media.setDaysOfWeek("1111111");
		} else {
			char[] days =  { '-', '-', '-', '-', '-', '-', '-' };
			if ( mondayCheckBox.getSelection() )
				days[0]  = '1';
			if ( tuesdayCheckBox.getSelection() )
				days[1]  = '1';
			if ( wednesdayCheckBox.getSelection() )
				days[2]  = '1';
			if ( thursdayCheckBox.getSelection() )
				days[3]  = '1';
			if ( fridayCheckBox.getSelection() )
				days[4]  = '1';
			if ( saturdayCheckBox.getSelection() )
				days[5]  = '1';
			if ( sundayCheckBox.getSelection() )
				days[6]  = '1';
			media.setDaysOfWeek( new String(days) );
		}

		if (  limitedCheckbox.getSelection() ) {
			media.setLimited(limitedSpinner.getSelection());
		} else {
			media.setLimited(0);
		}

		media.setDated( daysButton.getSelection() );
		programTab.getVideosOfSequencesTableViewer().setInput(programTab.getProgram().getVideos());
		programTab.getVideosOfSequencesTableViewer().refresh();
		logger.info("Edited video with id :" + media.getId() + " : " + Utils.getMediaDescription(media));
		logger.info("resource manager : " + programTab.getProgram().getManager() );
	}



	private void updatePathLabel(final File path) {
		Display.getDefault().syncExec(new Runnable() {			
			public void run() {
				if ( path != null ) {
					pathTable.removeAll();
					StringBuffer sb = new StringBuffer();					
					TableItem item = new TableItem(pathTable, SWT.BORDER);
					item.setText(0, path.getAbsolutePath());
					file = path;
				}
			}
		});		
	}

	private void  init() {
		mediaType = -1;

		typeCombo.select( media.getType().getType() );
		typeCombo.setEnabled( false );

		if ( media.getFile() != null )  {
			enableFileGroup(true);
			String s = "";
			switch (media.getType().getType()) {

			case AcceptedVideoTypes.FTPIMAGE_TYPE:
			case AcceptedVideoTypes.FTPVIDEO_TYPE:
				s = StringUtils.isNotEmpty( media.getRemotePath() ) ? media.getRemotePath() : " ";
				break;
			default :				
				s = StringUtils.isNotEmpty( media.getPath() ) ? media.getPath() : "";				
				break;

			}
			this.file = media.getFile();
			updatePathLabel(media.getFile());			
		} else {		
			enableFileGroup(false);
		}		

		typeComboWidgetSelected();

		switch (mediaType) {

		case AcceptedVideoTypes.VIDEO_TYPE:
			enableDurationGroup(false);
			break;

		default:
			enableDurationGroup(true);
			this.durationSpinner.setSelection(  Float.valueOf( media.getDuration() ).intValue() );
			break;
		} 

		//		if ( mediaType != AcceptedVideoTypes.VIDEO_TYPE )  {
		//			enableDurationGroup(true);
		//			this.durationSpinner.setSelection(  Float.valueOf( media.getDuration() ).intValue() );
		//		} else {
		//			enableDurationGroup(false);
		//		}

		if ( media.isDated() ) {
			enableDayGroup(true);
			this.daysButton.setSelection(true);
			//			this.startCalendarCombo.setDate( media.getStartDate() );
			//			this.endCalendarCombo.setDate( media.getEndDate() );

			Calendar cc = Calendar.getInstance();
			cc.setTimeInMillis( media.getStartDate().getTime());

			startCalendarCombo.setYear( cc.get(Calendar.YEAR));
			startCalendarCombo.setMonth(cc.get(Calendar.MONTH));
			startCalendarCombo.setDay(cc.get(Calendar.DAY_OF_MONTH));

			cc.setTimeInMillis( media.getEndDate().getTime());

			endCalendarCombo.setYear(cc.get(Calendar.YEAR));
			endCalendarCombo.setMonth(cc.get(Calendar.MONTH));
			endCalendarCombo.setDay(cc.get(Calendar.DAY_OF_MONTH));

		} else {
			enableDayGroup(false);
		}

		if ( media.isTimed() ) {
			enableTimeGroup(true);
			timedButton.setSelection(true);
			this.mediaFromDateTime.setHours(media.getFromDate().getHours());
			this.mediaFromDateTime.setMinutes(media.getFromDate().getMinutes());
			this.mediaFromDateTime.setSeconds(media.getFromDate().getSeconds());
			this.mediaToDateTime.setHours(media.getToDate().getHours());
			this.mediaToDateTime.setMinutes(media.getToDate().getMinutes());
			this.mediaToDateTime.setSeconds(media.getToDate().getSeconds());
		} else {
			enableTimeGroup(false);
		}

		if ( "1111111".equals(media.getDaysOfWeek()) ) {
			allDaysCheckButton.setSelection(true);
			anyDays();
			enableAllDaysGroup(false);			
		} else {
			allDaysCheckButton.setSelection(false);
			enableAllDaysGroup(true);
			if ( '1' == media.getDaysOfWeek().charAt(0) ){
				mondayCheckBox.setSelection(true);
			} else{
				mondayCheckBox.setSelection(false);
			}
			if ( '1' == media.getDaysOfWeek().charAt(1) ){
				tuesdayCheckBox.setSelection(true);
			}else{
				tuesdayCheckBox.setSelection(false);
			}
			if ( '1' == media.getDaysOfWeek().charAt(2) ){
				wednesdayCheckBox.setSelection(true);
			}else{
				wednesdayCheckBox.setSelection(false);
			}
			if ( '1' == media.getDaysOfWeek().charAt(3) ){
				thursdayCheckBox.setSelection(true);
			}else{
				thursdayCheckBox.setSelection(false);
			}
			if ( '1' == media.getDaysOfWeek().charAt(4) ){
				fridayCheckBox.setSelection(true);
			}else{
				fridayCheckBox.setSelection(false);
			}
			if ( '1' == media.getDaysOfWeek().charAt(5) ){
				saturdayCheckBox.setSelection(true);
			}else{
				saturdayCheckBox.setSelection(false);
			}
			if ( '1' == media.getDaysOfWeek().charAt(6) ){
				sundayCheckBox.setSelection(true);
			}else{
				sundayCheckBox.setSelection(false);
			}
		}

		if ( media.hasLimited() && media.getLimited() > 0 ){
			limitedCheckbox.setSelection(true);
			limitedSpinner.setEnabled(true);
			limitedSpinner.setSelection(media.getLimited());
		}


		switch (mediaType) {		

		
		case AcceptedVideoTypes.BROWSER_TYPE:			
			fileGroupStackLayout.topControl = werbUrlComposite;
			webUrlPathText.setText( media.getPath() );
			enableFileGroup(true);
			break;

		default:
			fileGroupStackLayout.topControl = pathComposite;			
		} 
		fileGroup.layout();
	}

	private void typeComboWidgetSelected() {
		this.mediaType = mediaTypeHashMap.get(typeCombo.getSelectionIndex()).getType() ;
		boolean enableFG = false;
		if (  mediaType == AcceptedVideoTypes.PHOTO_TYPE || mediaType == AcceptedVideoTypes.VIDEO_TYPE ) {
			enableFG = true;			
		} 
		this.searchFileNeeded = enableFG;
		enableFileGroup(enableFG);
		if ( this.mediaType != AcceptedVideoTypes.VIDEO_TYPE ) {
			enableDurationGroup(true);
		} else {
			enableDurationGroup(false);
		}
	}

	private void enableFileGroup(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				fileGroup.setEnabled(b);
				Control[] cc = fileGroup.getChildren();
				if ( cc != null ) {
					for(int i = 0; i < cc.length; i++) {
						cc[i].setEnabled(b);
					}
				}
			}
		});
	}

	private void enableDurationGroup(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				durationGroup.setEnabled(b);
				Control[] cc = durationGroup.getChildren();
				if ( cc != null ) {
					for(int i = 0; i < cc.length; i++) {
						cc[i].setEnabled(b);
					}
				}
			}
		});
	}

	private void enableTimeGroup(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {							
			public void run() {
				timedGroup.setEnabled(b);
				Control[] cc = timedGroup.getChildren();
				if ( cc != null ) {
					for(int i = 0; i < cc.length; i++) {
						cc[i].setEnabled(b);
					}
				}
			}
		});
	}

	private void enableDayGroup(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {							
			public void run() {
				daysGroup.setEnabled(b);
				Control[] cc = daysGroup.getChildren();
				if ( cc != null ) {
					for(int i = 0; i < cc.length; i++) {
						cc[i].setEnabled(b);
					}
				}
			}
		});
	}

	private void enableAllDaysGroup(final boolean enabled) {
		Display.getDefault().syncExec(new Runnable() {							
			public void run() {
				allDaysGroup.setEnabled(enabled);
				Control[] cc = allDaysGroup.getChildren();
				if ( cc != null ) {
					for(int i = 0; i < cc.length; i++) {
						cc[i].setEnabled(enabled);													
					}
				}
			}
		});

	}

	private void anyDays() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				mondayCheckBox.setSelection(true);
				tuesdayCheckBox.setSelection(true);
				wednesdayCheckBox.setSelection(true);
				thursdayCheckBox.setSelection(true);
				fridayCheckBox.setSelection(true);
				saturdayCheckBox.setSelection(true);
				sundayCheckBox.setSelection(true);
			}
		});
	}

	private boolean checkHours(Video v) {
		Date f =  Utils.getTimeMedium(v.getFrom());
		Date t =  Utils.getTimeMedium(v.getTo());
		return f.before(t);
	}

}
