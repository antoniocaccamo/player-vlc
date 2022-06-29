package it.antanysavage.sm.player.swt.views;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.ftp.FTPDownloader;
import it.antanysavage.sm.player.jface.ProgramTab;
import it.antanysavage.sm.player.jface.ftp.MediaFTPViewFilter;
import it.antanysavage.sm.player.jface.ftp.RemotePathTableLabelProvider;
import it.antanysavage.sm.player.jface.ftp.RemotePathTableSorter;
import it.antanysavage.sm.player.jface.ftp.RemotePathTableStructuredContentProvider;
import it.antanysavage.sm.player.jface.ftp.RemoteTableLayout;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.schema.Video;
import it.antanysavage.sm.player.sequences.schema.types.AcceptedVideoTypes;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
//import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class MediaNewComposite extends Composite {


	private static Logger logger = LoggerFactory.getLogger(MediaNewComposite.class);

	// private static final String[] VIDEO_FILTERS = {
	// "*.avi;*.mov;*.mpeg;*.mpg" ,
	// "*.avi" ,
	// "*.mov" ,
	// "*.mpeg" ,
	// "*.mpg" ,
	// "*.*"
	// };
	//
	// private static final String[] PHOTO_FILTERS = {
	// "*.bmp;*.jpeg;*.jpg;*.png" ,
	// "*.bmp" ,
	// "*.jpeg" ,
	// "*.jpg" ,
	// "*.png" ,
	// "*.*"
	// };

	private Group mediaGroup;
	private Group timedGroup;
	private Label fromLabel;
	private Button mondayCheckBox;
	private Group limitedComposite;
	private Button limitedCheckbox;
	private Table pathTable;
	private Group typeGroup;
	private Label durationLabel;
	private Group durationGroup;
	// private Group previewGroup;
	private Button searchButton;
	private Composite buttonsComposite;
	private Group daysGroup;
	private Button daysButton;
	private Group fileGroup;
	private Button closeButton;
	private Button addButton;
	private Label toLabel;
	private Button timedButton;
	private Combo typeCombo;
	private DateTime mediaFromDateTime;
	private DateTime mediaToDateTime;
	private boolean searchFileNeeded;
	private ProgramTab programTab;
	// private Program program;
	private Media media;

	private int mediaType;

	private Spinner durationSpinner;

	private ArrayList<File> file;

	// private SequenceManager sequenceManager;

	private DateTime startCalendarCombo;

	private DateTime endCalendarCombo;
	private Composite localPathComposite;
	private Composite remoteFTPComposite;
	private TableViewer remotePathTableViewer;

	private StackLayout fileGroupStackLayout;

	private FTPDownloader client = new FTPDownloader();

	private Spinner limitedSpinner;

	private Group allDaysGroup;

	private Button allDaysCheckButton;

	private Button tuesdayCheckBox;

	private Button wednesdayCheckBox;

	private Button thursdayCheckBox;

	private Button fridayCheckBox;

	private Button saturdayCheckBox;

	private Button sundayCheckBox;
	private Group mediaGroupGroup;
	private Label enableMediaGroupButton;
	private Combo mediaGroupCombo;
	private Composite composite;
	private Label enableMediaScreenButton;
	private Combo mediaScreenCombo;

	private Composite remoteURLComposite;
	private Text webUrlPathText;


	private HashMap<Integer, AcceptedVideoTypes> mediaTypeHashMap = new HashMap<Integer, AcceptedVideoTypes>();


	/**
	 * Auto-generated main method to display this
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	 * Overriding checkSubclass allows this class to extend
	 * org.eclipse.swt.widgets.Composite
	 */
	protected void checkSubclass() {
	}

	/**
	 * Auto-generated method to display this org.eclipse.swt.widgets.Composite
	 * inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		MediaNewComposite inst = new MediaNewComposite(shell, null);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if (size.x == 0 && size.y == 0) {
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

	public MediaNewComposite(Composite parent, ProgramTab programTab) {
		super(parent, SWT.NONE);

		this.programTab = programTab;

		// String ftp = System.getenv("FTP_HOME");
		// String user = System.getenv("FTP_USER");
		// String pass = System.getenv("FTP_PASS");
		// if ( Utils.isAnEmptyString(ftp) || Utils.isAnEmptyString(user) ||
		// Utils.isAnEmptyString(pass) ) {
		// logger.error("Environment variables not correctly setted");
		// } else {
		// client = new FTPClient();
		// try {
		// client.connect(ftp);
		// client.login( user, pass );
		// } catch ( Exception e ) {
		// client = null;
		// logger.error("error occurred ", e);
		// }
		// }

		initGUI();
	}

	// public void setProgram(Program program) {
	// this.program = program;
	// }

	public void setMedia(Media videoSMP) {
		this.media = videoSMP;
	}

	private void initGUI() {

		GridLayout thisLayout = new GridLayout();
		thisLayout.makeColumnsEqualWidth = false;
		this.setLayout(thisLayout);

		mediaGroup = new Group(this, SWT.NONE);
		GridLayout mediaGroupLayout = new GridLayout(2, true);

		mediaGroup.setLayout(mediaGroupLayout);
		GridData mediaGroupLData = new GridData();
		mediaGroupLData.grabExcessHorizontalSpace = true;
		mediaGroupLData.horizontalAlignment = GridData.FILL;
		mediaGroupLData.grabExcessVerticalSpace = true;
		mediaGroupLData.verticalAlignment = GridData.BEGINNING;
		mediaGroup.setLayoutData(mediaGroupLData);
		mediaGroup.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA));

		// media type
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

		// media duration
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




		fileGroup = new Group(mediaGroup, SWT.NONE);
		fileGroupStackLayout = new StackLayout();
		fileGroup.setLayout(fileGroupStackLayout);
		GridData fileGroupLData = new GridData();
		fileGroupLData.grabExcessVerticalSpace = true;
		fileGroupLData.verticalAlignment = SWT.FILL;
		fileGroupLData.horizontalSpan = 2;
		fileGroupLData.grabExcessHorizontalSpace = true;
		fileGroupLData.horizontalAlignment = GridData.FILL;
		fileGroup.setLayoutData(fileGroupLData);
		fileGroup.setText("File");

		// local file

		TableLayout tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(100, true));

		localPathComposite = new Composite(fileGroup, SWT.NONE);
		localPathComposite.setLayout(new GridLayout(1, true));
		pathTable = new Table(localPathComposite, SWT.BORDER | SWT.V_SCROLL	| SWT.H_SCROLL);
		GridData gd_pathTable = new GridData(SWT.FILL, SWT.CENTER, true, false,	1, 1);
		gd_pathTable.heightHint = 74;
		pathTable.setLayoutData(gd_pathTable);
		TableColumn column = new TableColumn(pathTable, SWT.NONE);
		column.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_3));
		column.pack();
		pathTable.setHeaderVisible(true);
		pathTable.setLayout(tableLayout);

		// remote files
		remoteFTPComposite = new Composite(fileGroup, SWT.NONE);
		GridLayout gl_remoteFTPComposite = new GridLayout(1, false);
		gl_remoteFTPComposite.makeColumnsEqualWidth = true;
		remoteFTPComposite.setLayout(gl_remoteFTPComposite);

		remotePathTableViewer = new TableViewer(remoteFTPComposite, SWT.BORDER	| SWT.MULTI | SWT.FULL_SELECTION);

		GridData remotePathTableGData = new GridData();
		remotePathTableGData.grabExcessHorizontalSpace = true;
		remotePathTableGData.horizontalAlignment = GridData.FILL;
		remotePathTableGData.grabExcessVerticalSpace = true;
		remotePathTableGData.verticalAlignment = GridData.FILL;
		remotePathTableViewer.getControl().setLayoutData(remotePathTableGData);
		remotePathAdapter(remotePathTableViewer);

		GridData searchButtonLData = new GridData();
		searchButtonLData.horizontalAlignment = GridData.CENTER;
		searchButtonLData.grabExcessHorizontalSpace = true;

		// web url 
		remoteURLComposite =new Composite(fileGroup, SWT.NONE);
		remoteURLComposite.setLayout(new GridLayout(1, false));

		webUrlPathText = new Text(remoteURLComposite, SWT.BORDER);
		webUrlPathText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));


		// days & times media check button

		daysButton = new Button(mediaGroup, SWT.CHECK | SWT.LEFT);
		daysButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_DAY_CALENDAR));		
		timedButton = new Button(mediaGroup, SWT.CHECK | SWT.LEFT);
		timedButton.setText(LocaleManager.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED));

		// days group
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
		startCalendarCombo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		//		startCalendarCombo.setDependingCombo(endCalendarCombo);
		GridData activeComboControlLData = new GridData();
		//		startCalendarCombo.getActiveComboControl().setLayoutData(activeComboControlLData);

		Label endLabel = new Label(daysGroup, SWT.NONE);
		endLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		endLabel.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_DAY_END));

		GridData activeComboControlLData1 = new GridData();
		endCalendarCombo = new DateTime(daysGroup, SWT.DROP_DOWN);
		endCalendarCombo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		//		endCalendarCombo.getActiveComboControl().setLayoutData(activeComboControlLData1);

		// time group
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

		// media duration 
		Calendar today = Calendar.getInstance();

		fromLabel = new Label(timedGroup, SWT.NONE);
		GridData fromLabelLData = new GridData();
		fromLabelLData.grabExcessHorizontalSpace = true;
		fromLabelLData.horizontalAlignment = GridData.FILL;
		fromLabel.setLayoutData(fromLabelLData);
		fromLabel.setText(LocaleManager
				.getText(LocaleManager.APP_GROUP_ACTIVATION_TIMING_TIMED_FROM));
		GridData mediaFromDateTimeLData = new GridData();
		mediaFromDateTimeLData.grabExcessHorizontalSpace = true;
		mediaFromDateTimeLData.horizontalAlignment = GridData.CENTER;
		mediaFromDateTime = new DateTime(timedGroup, SWT.TIME);
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
		mediaToDateTime = new DateTime(timedGroup, SWT.TIME);
		mediaToDateTime.setLayoutData(mediaToDateTimeLData);

		// week media
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
		mondayCheckBox.setText(Utils.getDayOfWeek(Calendar.MONDAY));
		mondayCheckBox.setSelection(true);

		tuesdayCheckBox = new Button(allDaysGroup, SWT.CHECK | SWT.LEFT);
		tuesdayCheckBox.setText(Utils.getDayOfWeek(Calendar.TUESDAY));
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

		// media limitation
		GridData limitedCompositeLData = new GridData();
		limitedCompositeLData.horizontalAlignment = GridData.FILL;
		limitedCompositeLData.grabExcessHorizontalSpace = true;
		limitedCompositeLData.grabExcessVerticalSpace = true;
		limitedCompositeLData.verticalAlignment = GridData.FILL;
		limitedComposite = new Group(mediaGroup, SWT.NONE);
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
		limitedCheckbox.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_9));

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

		// media group
		mediaGroupGroup = new Group(mediaGroup, SWT.NONE);
		mediaGroupGroup.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_10));
		mediaGroupGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_groupGroup = new GridLayout();
		gl_groupGroup.numColumns = 2;
		gl_groupGroup.makeColumnsEqualWidth = false;
		mediaGroupGroup.setLayout(gl_groupGroup);

		enableMediaGroupButton = new Label(mediaGroupGroup, SWT.CHECK);


		enableMediaGroupButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		enableMediaGroupButton.setText(LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_TABLE_COLUMN_10));

		mediaGroupCombo = new Combo(mediaGroupGroup, SWT.NONE);

		mediaGroupCombo.setEnabled(false);
		mediaGroupCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		enableMediaScreenButton = new Label(mediaGroupGroup, SWT.CHECK);
		enableMediaScreenButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		enableMediaScreenButton.setText("Screen");

		mediaScreenCombo = new Combo(mediaGroupGroup, SWT.NONE);
		mediaScreenCombo.setEnabled(false);
		mediaScreenCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		// previewGroup = new Group(this, SWT.NONE);
		// StackLayout previewGroupLayout = new StackLayout();
		// previewGroupLayout.topControl = null;
		// previewGroup.setLayout(previewGroupLayout);
		// GridData previewGroupLData = new GridData();
		// previewGroupLData.horizontalAlignment = GridData.FILL;
		// previewGroupLData.grabExcessHorizontalSpace = true;
		// previewGroup.setLayoutData(previewGroupLData);
		// previewGroup.setText("Preview");

		// buttons
		GridData buttonsCompositeLData = new GridData();
		buttonsCompositeLData.grabExcessHorizontalSpace = true;
		buttonsCompositeLData.grabExcessVerticalSpace = true;
		buttonsCompositeLData.verticalAlignment = GridData.BEGINNING;
		buttonsCompositeLData.horizontalAlignment = GridData.FILL;
		buttonsComposite = new Composite(this, SWT.NONE);
		GridLayout buttonsCompositeLayout = new GridLayout(4, false);
		buttonsCompositeLayout.marginRight = 20;
		buttonsCompositeLayout.marginLeft = 20;
		buttonsCompositeLayout.horizontalSpacing = 40;
		buttonsCompositeLayout.makeColumnsEqualWidth = true;
		buttonsComposite.setLayout(buttonsCompositeLayout);
		buttonsComposite.setLayoutData(buttonsCompositeLData);

		addButton = new Button(buttonsComposite, SWT.CENTER);
		GridData addButtonLData = new GridData();
		addButtonLData.grabExcessHorizontalSpace = true;
		addButtonLData.horizontalSpan = 2;
		addButtonLData.horizontalAlignment = SWT.FILL;
		addButton.setLayoutData(addButtonLData);
		addButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_ADD));

		closeButton = new Button(buttonsComposite, SWT.CENTER);
		GridData button1LData = new GridData();
		button1LData.horizontalAlignment = SWT.FILL;
		button1LData.grabExcessHorizontalSpace = true;
		button1LData.horizontalSpan = 2;
		closeButton.setLayoutData(button1LData);
		closeButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CLOSE));

		fileGroupStackLayout.topControl = localPathComposite;

		composite = new Composite(localPathComposite, SWT.NONE);
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.marginRight = 100;
		gl_composite.marginLeft = 100;
		composite.setLayout(gl_composite);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		searchButton = new Button(composite, SWT.PUSH | SWT.CENTER);
		searchButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		searchButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CHOOSE_FILE));



		createControlsListeners();
		init();
		pack();

	}

	private void createControlsListeners() {
		typeCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				typeComboWidgetSelected(evt);
			}
		});

		searchButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				FileDialog fileDialog = new FileDialog(Display.getDefault()
						.getActiveShell(), SWT.OPEN | SWT.MULTI);
				if (mediaType == AcceptedVideoTypes.PHOTO_TYPE)
					fileDialog.setFilterExtensions(Player.PHOTO_FILTERS);
				if (mediaType == AcceptedVideoTypes.VIDEO_TYPE)
					fileDialog.setFilterExtensions(Player.VIDEO_FILTERS);
				fileDialog.open();
				String[] files = fileDialog.getFileNames();
				if (files != null && files.length > 0) {
					String parentPath = fileDialog.getFilterPath();
					if (parentPath.charAt(parentPath.length() - 1) != File.separatorChar) {
						parentPath += File.separatorChar;
					}
					ArrayList filesList = new ArrayList();
					for (int i = 0; i < files.length; i++) {
						File f = new File(parentPath + files[i]);
						if (f.exists() && f.isFile() && f.canRead()) {
							filesList.add(f);
						}
					}
					updatePathLabel(filesList);
				}
			}
		});

		daysButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				enableDayGroup(daysButton.getSelection());
			}
		});

		timedButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				enableTimeGroup(timedButton.getSelection());
			}
		});

		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				addButtonSelected(arg0);
			}
		});
		closeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				dispose();
			}
		});

		allDaysCheckButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				enableAllDaysGroup(!allDaysCheckButton.getSelection());
				if (allDaysCheckButton.getSelection()) {
					anyDays();
				}
			}

		});

		limitedCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				limitedSpinner.setEnabled(limitedCheckbox.getSelection());
			}
		});

		mediaGroupCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			
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

	private void addButtonSelected(SelectionEvent arg0) {

		boolean insert = true;

		ArrayList<Video> videos = new ArrayList<Video>();

		Video v = null;

		switch (this.mediaType) {

		case AcceptedVideoTypes.BLACKWINDOW_TYPE:
			v = new Video();
			v.setType(AcceptedVideoTypes.BLACKWINDOW);
			v.setDuration(durationSpinner.getSelection());
			videos.add(v);
			break;

		case AcceptedVideoTypes.HIDDENWINDOW_TYPE:
			v = new Video();
			v.setType(AcceptedVideoTypes.HIDDENWINDOW);
			v.setDuration(durationSpinner.getSelection());
			videos.add(v);
			break;

		case AcceptedVideoTypes.PHOTO_TYPE:
			if (this.file == null || this.file.size() == 0) {
				MessageDialog
				.openError(
						getShell(),
						getShell().getText(),
						LocaleManager
						.getText(LocaleManager.ERRORS_NO_SELECTED_FILE_PHOTO));
				insert = false;
			} else {
				Iterator<File> it = file.iterator();
				while (it.hasNext()) {
					File f = it.next();
					v = new Video();
					v.setPath(f.getAbsolutePath());
					v.setType(AcceptedVideoTypes.PHOTO);
					v.setDuration(durationSpinner.getSelection());
					videos.add(v);
				}

			}
			break;

		case AcceptedVideoTypes.VIDEO_TYPE:
			if (this.file == null || this.file.size() == 0) {
				MessageDialog
				.openError(
						getShell(),
						getShell().getText(),
						LocaleManager
						.getText(LocaleManager.ERRORS_NO_SELECTED_FILE_VIDEO));
				insert = false;
			} else {
				Iterator<File> it = file.iterator();
				while (it.hasNext()) {
					File f = it.next();
					v = new Video();
					v.setPath(f.getAbsolutePath());
					v.setType(AcceptedVideoTypes.VIDEO);
					videos.add(v);
				}
			}
			break;

		case AcceptedVideoTypes.WATCH_TYPE:
			v = new Video();
			v.setType(AcceptedVideoTypes.WATCH);
			v.setDuration(durationSpinner.getSelection());
			videos.add(v);		
			break;

		case AcceptedVideoTypes.BROWSER_TYPE :
			v = new Video();
			v.setType(AcceptedVideoTypes.BROWSER);
			v.setDuration(durationSpinner.getSelection());
			v.setPath( StringUtils.trim(webUrlPathText.getText()) );
			videos.add(v);
			break;

		case AcceptedVideoTypes.WEATHER_TYPE :
			v = new Video();
			v.setType(AcceptedVideoTypes.WEATHER);
			v.setDuration(durationSpinner.getSelection());
			videos.add(v);
			break;

		case AcceptedVideoTypes.FTPVIDEO_TYPE:

			String path = Player.getMediaLocalePath( programTab.getProgram().getName());
			File f = new File(path);
			if (!f.exists() && !f.mkdirs()) {
				MessageDialog.openError(getShell(), getShell().getText(),
						"Can't create directory : " + path);
				return;
			}

			String remotePath = null;
			try {
				remotePath = client.printWorkingDirectory();
			} catch (Exception e) {
				MessageDialog.openError(getShell(), getShell().getText(),
						e.getLocalizedMessage());
				return;
			}

			IStructuredSelection selection = (IStructuredSelection) remotePathTableViewer
					.getSelection();
			if (selection != null) {
				Iterator<FTPFile> iterator = selection.iterator();
				while (iterator.hasNext()) {
					FTPFile ftpFile = iterator.next();
					v = new Video();
					v.setType(AcceptedVideoTypes.FTPVIDEO);
					v.setPath(f.getAbsolutePath()
							+ System.getProperty("file.separator")
							+ ftpFile.getName());
					v.setRemotePath(remotePath + "/" + ftpFile.getName());
					videos.add(v);
				}
			}

			break;

		case AcceptedVideoTypes.FTPIMAGE_TYPE:
			path = Player.getMediaLocalePath( programTab.getProgram().getName());
			f = new File(path);
			if (!f.exists() && !f.mkdirs()) {
				MessageDialog.openError(getShell(), getShell().getText(),
						"Can't create directory : " + path);
				return;
			}

			remotePath = null;
			try {
				remotePath = client.printWorkingDirectory();
			} catch (Exception e) {
				MessageDialog.openError(getShell(), getShell().getText(),
						e.getLocalizedMessage());
				return;
			}

			selection = (IStructuredSelection) remotePathTableViewer
					.getSelection();
			if (selection != null) {
				Iterator<FTPFile> iterator = selection.iterator();
				while (iterator.hasNext()) {
					FTPFile ftpFile = iterator.next();
					v = new Video();
					v.setType(AcceptedVideoTypes.FTPIMAGE);
					v.setPath(f.getAbsolutePath()
							+ System.getProperty("file.separator")
							+ ftpFile.getName());
					v.setRemotePath(remotePath + "/" + ftpFile.getName());
					v.setDuration(this.durationSpinner.getSelection());
					videos.add(v);
				}
			}

			break;

		default:
			MessageDialog
			.openError(
					getShell(),
					getShell().getText(),
					LocaleManager
					.getText(LocaleManager.ERRORS_NO_SELECTED_MEDIA_TYPE));
			insert = false;
			break;
		}
		if (!insert) {
			return;
		}

		if (insert) {
			try {
				Iterator<Video> iterator = videos.iterator();
				while (iterator.hasNext()) {
					Video vv = iterator.next();

					if (this.timedButton.getSelection()) {

						int hhF = mediaFromDateTime.getHours();
						int mmF = mediaFromDateTime.getMinutes();
						int ssF = mediaFromDateTime.getSeconds();
						vv.setFrom(Utils.getTimeAsString(hhF, mmF, ssF));
						int hhT = mediaToDateTime.getHours();
						int mmT = mediaToDateTime.getMinutes();
						int ssT = mediaToDateTime.getSeconds();
						vv.setTo(Utils.getTimeAsString(hhT, mmT, ssT));
						if (!checkHours(vv)) {
							// MessageBox mb = new MessageBox(getShell(), SWT.OK
							// | SWT.ICON_WARNING);
							// mb.setText(LocaleManager.getText(LocaleManager.APP_TITLE));
							// mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_TIME));
							// mb.open();
							MessageDialog.openError(getShell(), getShell()
									.getText(), LocaleManager
									.getText(LocaleManager.ERRORS_TIME));
							return;
						}
					}

					if (insert && daysButton.getSelection()) {
						Calendar start = Calendar.getInstance();
						start.set(Calendar.YEAR , startCalendarCombo.getYear());
						start.set(Calendar.MONTH, startCalendarCombo.getMonth());
						start.set(Calendar.DATE , startCalendarCombo.getDay());

						// Calendar end = endCalendarCombo.getDate();
						Calendar end = Calendar.getInstance();
						end.set(Calendar.YEAR , endCalendarCombo.getYear());
						end.set(Calendar.MONTH, endCalendarCombo.getMonth());
						end.set(Calendar.DATE , endCalendarCombo.getDay());

						if (start != null)
							Utils.setStartOfDay(start);
						if (end != null)
							Utils.setEndOfDay(end);
						if (start == null || end == null || !start.before(end)) {
							MessageDialog.openError(getShell(), getShell()
									.getText(), LocaleManager
									.getText(LocaleManager.ERRORS_DATE));
							return;
						}
						vv.setStart(Utils.getDateAsString(start.getTime()));
						vv.setEnd(Utils.getDateAsString(end.getTime()));
					}

					if (insert) {
						if (allDaysCheckButton.getSelection()) {
							vv.setDaysOfWeek("1111111");
						} else {
							char[] days = { '-', '-', '-', '-', '-', '-', '-' };
							if (mondayCheckBox.getSelection())
								days[0] = '1';
							if (tuesdayCheckBox.getSelection())
								days[1] = '1';
							if (wednesdayCheckBox.getSelection())
								days[2] = '1';
							if (thursdayCheckBox.getSelection())
								days[3] = '1';
							if (fridayCheckBox.getSelection())
								days[4] = '1';
							if (saturdayCheckBox.getSelection())
								days[5] = '1';
							if (sundayCheckBox.getSelection())
								days[6] = '1';
							vv.setDaysOfWeek(new String(days));
						}
					}

					if (insert && limitedCheckbox.getSelection()) {
						vv.setLimited(limitedSpinner.getSelection());
					}

					vv.setId(programTab.getProgram().getVideos()
							.getVideoCount() + 1);
					Media media = new Media(programTab.getProgram(), vv);
					programTab.getProgram().getVideos().addVideo(media);
					programTab.getVideosOfSequencesTableViewer().setInput(programTab.getProgram().getVideos());
					programTab.getVideosOfSequencesTableViewer().refresh();
				}

				MessageDialog.openInformation(getShell(), getShell().getText(),
						LocaleManager
						.getText(LocaleManager.SM_MENU_MEDIA_ADDED));

				logger.info("resource manager : "
						+ programTab.getProgram().getManager());

			} catch (Exception e) {
				logger.error("Error adding media " + e);
				MessageDialog.openError(getShell(), getShell().getText(),
						LocaleManager
						.getText(LocaleManager.ERRORS_INSERT_MEDIA));
			}
		}

	}

	private void updatePathLabel(final ArrayList filesList) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (filesList.size() > 0) {
					pathTable.removeAll();
					StringBuffer sb = new StringBuffer();
					Iterator iterator = filesList.iterator();
					while (iterator.hasNext()) {
						File f = (File) iterator.next();
						TableItem item = new TableItem(pathTable, SWT.BORDER);
						item.setText(0, f.getAbsolutePath());
					}
					pathTable.pack();
					file = filesList;
				}

			}
		});
	}

	private void init() {
		mediaType = -1;



		typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_0));
		mediaTypeHashMap.put(new Integer(0), AcceptedVideoTypes.VIDEO);

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

		if (Player.FTP_ENABLED) {
			typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_7));
			mediaTypeHashMap.put(new Integer(7), AcceptedVideoTypes.FTPVIDEO);

			typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_8));
			mediaTypeHashMap.put(new Integer(8), AcceptedVideoTypes.FTPIMAGE);

		}

		//		if ( Player.WEATHER_ENABLED) {
		//			typeCombo.add(LocaleManager.getText(LocaleManager.MODEL_SEQUECE_MEDIA_8));
		//		}

		enableFileGroup(false);
		enableDurationGroup(false);
		enableDayGroup(false);
		enableTimeGroup(false);
		enableAllDaysGroup(false);	


	}

	private void typeComboWidgetSelected(SelectionEvent evt) {
				
		this.mediaType = mediaTypeHashMap.get(typeCombo.getSelectionIndex()).getType() ;

		switch (mediaType) {

		case AcceptedVideoTypes.FTPIMAGE_TYPE:
		case AcceptedVideoTypes.FTPVIDEO_TYPE:


			if (!client.isConnected()) {
				try {
					client.connect();
				} catch (Exception e) {
					logger.error("error occurred ", e);
					MessageDialog.openError(getShell(), getShell().getText(),
							e.getLocalizedMessage());
					return;
				}
			}
			fileGroupStackLayout.topControl = remoteFTPComposite;

			try {
				remotePathTableViewer.setInput(client.listFiles() );
				if (this.mediaType != AcceptedVideoTypes.FTPVIDEO_TYPE) {
					enableDurationGroup(true);
				} else {
					enableDurationGroup(false);
				}
			} catch (Exception e) {
				MessageDialog.openError(getShell(), getShell().getText(),
						e.getLocalizedMessage());
				logger.error("error occurred ", e);
			}
			break;

		case AcceptedVideoTypes.BROWSER_TYPE:
		
			fileGroupStackLayout.topControl = remoteURLComposite;
			enableDurationGroup(true);
			
			break;

		default:
			fileGroupStackLayout.topControl = localPathComposite;

			boolean enableFG = false;
			if (mediaType == AcceptedVideoTypes.PHOTO_TYPE
					|| mediaType == AcceptedVideoTypes.VIDEO_TYPE) {
				enableFG = true;
			}
			this.searchFileNeeded = enableFG;
			enableFileGroup(enableFG);
			if (this.mediaType != AcceptedVideoTypes.VIDEO_TYPE) {
				enableDurationGroup(true);
			} else {
				enableDurationGroup(false);
			}
			break;
		}


		//		if (mediaType == AcceptedVideoTypes.FTPIMAGE_TYPE
		//				|| mediaType == AcceptedVideoTypes.FTPVIDEO_TYPE) {
		//
		//			if (!client.isConnected()) {
		//				try {
		//					client.connect();
		//				} catch (Exception e) {
		//					logger.error("error occurred ", e);
		//					MessageDialog.openError(getShell(), getShell().getText(),
		//							e.getLocalizedMessage());
		//					return;
		//				}
		//			}
		//			fileGroupStackLayout.topControl = remoteFTPComposite;
		//
		//			try {
		//				remotePathTableViewer.setInput(client.listFiles() );
		//				if (this.mediaType != AcceptedVideoTypes.FTPVIDEO_TYPE) {
		//					enableDurationGroup(true);
		//				} else {
		//					enableDurationGroup(false);
		//				}
		//			} catch (Exception e) {
		//				MessageDialog.openError(getShell(), getShell().getText(),
		//						e.getLocalizedMessage());
		//				logger.error("error occurred ", e);
		//			}
		//
		//		} else {
		//
		//			fileGroupStackLayout.topControl = localPathComposite;
		//
		//			boolean enableFG = false;
		//			if (mediaType == AcceptedVideoTypes.PHOTO_TYPE
		//					|| mediaType == AcceptedVideoTypes.VIDEO_TYPE) {
		//				enableFG = true;
		//			}
		//			this.searchFileNeeded = enableFG;
		//			enableFileGroup(enableFG);
		//			if (this.mediaType != AcceptedVideoTypes.VIDEO_TYPE) {
		//				enableDurationGroup(true);
		//			} else {
		//				enableDurationGroup(false);
		//			}
		//		}

		fileGroup.layout();
	}

	private void enableFileGroup(final boolean b) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {

				localPathComposite.setEnabled(b);

				// fileGroup.setEnabled(b);
				Control[] cc = localPathComposite.getChildren();
				if (cc != null) {
					for (int i = 0; i < cc.length; i++) {
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
				if (cc != null) {
					for (int i = 0; i < cc.length; i++) {
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
				if (cc != null) {
					for (int i = 0; i < cc.length; i++) {
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
				if (cc != null) {
					for (int i = 0; i < cc.length; i++) {
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
				if (cc != null) {
					for (int i = 0; i < cc.length; i++) {
						cc[i].setEnabled(enabled);
					}
				}
			}
		});

	}

	@Override
	public void dispose() {
		if (client != null) {
			try {
				client.disconnect();
			} catch (Exception e) {
				logger.equals(e);
			}
		}
		getShell().dispose();
	}

	private boolean checkHours(Video v) {
		Date f = Utils.getTimeMedium(v.getFrom());
		Date t = Utils.getTimeMedium(v.getTo());
		return f.before(t);
	}

	private void remotePathAdapter(final TableViewer tableViewer) {
		Table remotePathTable = tableViewer.getTable();
		remotePathTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		/*
		TableLayout tableLayout = new TableLayout();
		TableColumn tc = new TableColumn(remotePathTable, SWT.LEFT);
		tc.setText(LocaleManager.getText("app.video.sequence.table.column.1"));
		tableLayout.addColumnData(new ColumnWeightData(50, true));

		tc = new TableColumn(remotePathTable, SWT.LEFT);
		tc.setText(LocaleManager.getText("app.video.sequence.table.column.3"));
		tableLayout.addColumnData(new ColumnWeightData(50, true));

		tc = new TableColumn(remotePathTable, SWT.LEFT);
		tc.setText("Size");
		tableLayout.addColumnData(new ColumnWeightData(50, true));

		tc = new TableColumn(remotePathTable, SWT.LEFT);
		tc.setText("Date");
		tableLayout.addColumnData(new ColumnWeightData(50, true));
		 */

		remotePathTable.setLayout( new RemoteTableLayout(remotePathTable));
		remotePathTable.setHeaderVisible(true);

		tableViewer.setContentProvider( new RemotePathTableStructuredContentProvider() );
		tableViewer.setLabelProvider(new RemotePathTableLabelProvider() );		
		tableViewer.setSorter(new RemotePathTableSorter() );
		tableViewer.addFilter( new MediaFTPViewFilter(this));
		/*
		tableViewer.setContentProvider(new IStructuredContentProvider() {

			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
				// TODO Auto-generated method stub

			}

			public void dispose() {
				// TODO Auto-generated method stub

			}

			public Object[] getElements(Object arg0) {
				FTPFile[] filesFTP = (FTPFile[]) arg0;
				FTPFile parentDir = new FTPFile();
				parentDir.setName("..");
				parentDir.setType(FTPFile.DIRECTORY_TYPE);				
				ArrayList<FTPFile> files = new ArrayList<FTPFile>();
				files.add(parentDir);
				files.addAll(Arrays.asList(filesFTP));
				return files.toArray();
			}
		});

		tableViewer.setLabelProvider(new ITableLabelProvider() {
			public Image getColumnImage(Object element, int columnIndex) {
				// if(columnIndex == 0)
				// return FileIconUtil.getIcon((FTPFile)element);
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				FTPFile ftpFile = (FTPFile) element;
				switch (columnIndex) {
				case 0:

					if (ftpFile.isDirectory())
						return "Dir";
					if (ftpFile.isFile())
						return "File";
					return "";

				case 1:
					if (Utils.isAnEmptyString(ftpFile.getName()))
						return "";
					return ((FTPFile) element).getName();
				case 2:
					return ((FTPFile) element).getSize() + "";
				case 3:
					Calendar cal = ((FTPFile) element).getTimestamp();					
					if (cal == null)
						return "";
					return 
//							cal.get(Calendar.YEAR) + "-"
//							+ cal.get(Calendar.MONTH) + "-"
//							+ cal.get(Calendar.DAY_OF_MONTH) + " "
//							+ cal.get(Calendar.HOUR_OF_DAY) + ":"
//							+ cal.get(Calendar.MINUTE) + ":"
//							+ cal.get(Calendar.SECOND)
							Utils.debugDate(cal);

				default:
					return "";
				}
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public void dispose() {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void removeListener(ILabelProviderListener listener) {
			}
		});

		tableViewer.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer arg0, Object parentElement,
					Object element) {

				FTPFile ftpFile = (FTPFile) element;

				if (ftpFile.isDirectory())				
					return true;

				if (mediaType == AcceptedVideoTypes.FTPVIDEO_TYPE) {
					for (int i = 1; i < Player.VIDEO_FILTERS.length; i++) {
						if (ftpFile.getName().indexOf(
								Player.VIDEO_FILTERS[i].substring(2)) != -1)
							return true;
					}
				}
				if (mediaType == AcceptedVideoTypes.FTPIMAGE_TYPE) {
					for (int i = 1; i < Player.PHOTO_FILTERS.length; i++) {
						if (ftpFile.getName().indexOf(
								Player.PHOTO_FILTERS[i].substring(2)) != -1)
							return true;
					}
				}
				return false;
			}
		});

		tableViewer.setSorter(new RemotePathTableSorter());

		 */


		remotePathTable.addMouseListener(new MouseAdapter() {

			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				IStructuredSelection selection = (IStructuredSelection) tableViewer
						.getSelection();
				if (selection.size() != 1)
					return;
				FTPFile ftpFile = (FTPFile) selection.getFirstElement();
				if (ftpFile.isDirectory()) {				
					try {
						client.changeWorkingDirectory(ftpFile.getName());
						tableViewer.setInput(client.listFiles());
						tableViewer.refresh();
					} catch (Exception e) {
						MessageDialog.openError(getShell(), getShell()
								.getText(), e.getLocalizedMessage());
					}
				}

			}
		});

	}


	/*
	private class RemotePathTableSorter extends ViewerSorter {
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			FTPFile ftpFile1 = (FTPFile) e1;
			FTPFile ftpFile2 = (FTPFile) e2;
			if (ftpFile1.equals(ftpFile2)) {
				return 0;
			}

			if (ftpFile1.isDirectory() && ftpFile2.isDirectory()) {
				return ftpFile1.getName().compareTo(ftpFile2.getName());
			}
			if (ftpFile1.isDirectory() && ftpFile2.isFile()) {
				return -1;
			}

			if (ftpFile1.isFile() && ftpFile2.isDirectory()) {
				return 1;
			}

			if (ftpFile1.isFile() && ftpFile2.isFile()) {
				return ftpFile1.getName().compareTo(ftpFile2.getName());
			}

			return 0;
		}
	}
	 */
	public int getMediaType() {
		return mediaType;
	}


}
