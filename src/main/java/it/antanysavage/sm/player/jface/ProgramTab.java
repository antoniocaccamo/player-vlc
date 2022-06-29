package it.antanysavage.sm.player.jface;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Videos;
import it.antanysavage.sm.player.util.Utils;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;


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
public class ProgramTab extends Composite {

	private static final Logger logger = LoggerFactory.getLogger(ProgramTab.class);

	private Group videosOfSequencesGroup;
	private TableViewer videosOfSequencesTableViewer;
	private Button downButton;
	private Button upButton;
	private Program program;
	
	private int[] versus = {
				1, 1, 1,
				1, 1, 1,
				1, 1, 1
			};

	private ProgramManager manager;
	
	public ProgramTab(Composite parent, ProgramManager manager, Program program) {
		super(parent, SWT.NONE);
		this.program = program;
		this.manager = manager;
		initGUI();
	}
	
	public Program getProgram() {
		return program;
	}
	
	public TableViewer getVideosOfSequencesTableViewer() {
		return videosOfSequencesTableViewer;
	}

	

	

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			this.setSize(398, 306);

			videosOfSequencesGroup = new Group(this, SWT.SHADOW_ETCHED_OUT);
			GridLayout videosOfSequencesGroupLayout = new GridLayout();
			videosOfSequencesGroupLayout.numColumns = 1;
			videosOfSequencesGroup.setLayout(videosOfSequencesGroupLayout);
			videosOfSequencesGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_VIDEOS_SEQUENCE));
			GridData videosOfSequencesGroupLData = new GridData();
			videosOfSequencesGroupLData.horizontalAlignment = GridData.FILL;
			videosOfSequencesGroupLData.verticalAlignment = GridData.FILL;
			videosOfSequencesGroupLData.grabExcessVerticalSpace = true;
			videosOfSequencesGroupLData.grabExcessHorizontalSpace = true;
			videosOfSequencesGroup.setLayoutData(videosOfSequencesGroupLData);
//			videosOfSequencesGroup.setBounds(-93, 0, 491, 322);

			Composite composite = new Composite(videosOfSequencesGroup, SWT.NONE);
			GridLayout compositeLayout = new GridLayout();
			compositeLayout.numColumns = 2;
			GridData compositeLData = new GridData();
			compositeLData.horizontalAlignment = GridData.CENTER;
			compositeLData.grabExcessHorizontalSpace = true;
			composite.setLayoutData(compositeLData);
			composite.setLayout(compositeLayout);

			upButton = new Button(composite, SWT.ARROW | SWT.UP | SWT.BORDER_DOT);
			GridData upButtonLData = new GridData();
			upButtonLData.verticalAlignment = GridData.END;
			upButtonLData.widthHint = 25;
			upButtonLData.heightHint = 25;
			upButtonLData.horizontalAlignment = GridData.CENTER;
			upButtonLData.horizontalIndent = 10;
			upButton.setLayoutData(upButtonLData);
			upButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent arg0) {
					upButtonSelected();
				}
			});
			upButton.setToolTipText( LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_BUTTON_UP_TOOLTIP) );

			downButton = new Button(composite, SWT.ARROW | SWT.DOWN | SWT.BORDER_DOT);
			GridData downButtonLData = new GridData();
			downButtonLData.widthHint = 25;
			downButtonLData.heightHint = 25;
			downButtonLData.horizontalAlignment = GridData.CENTER;
			downButtonLData.horizontalIndent = 10;
			downButton.setLayoutData(downButtonLData);
			downButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent arg0) {
					downButtonSelected();
				}
			});
			downButton.setToolTipText( LocaleManager.getText(LocaleManager.APP_VIDEO_SEQUENCE_BUTTON_DOWN_TOOLTIP) );

			videosOfSequencesTableViewer = new TableViewer(videosOfSequencesGroup, SWT.BORDER | SWT.NONE | SWT.FULL_SELECTION);
			GridData videoOfSequenceGData = new GridData();
			videoOfSequenceGData.verticalAlignment = GridData.FILL;
			videoOfSequenceGData.horizontalAlignment = GridData.FILL;
			videoOfSequenceGData.grabExcessHorizontalSpace = true;
			videoOfSequenceGData.grabExcessVerticalSpace = true;
			videosOfSequencesTableViewer.getControl().setLayoutData(videoOfSequenceGData);			
			adaptVideosOfSequencesTableViewer();
			videosOfSequencesTableViewer.setInput(program.getVideos());
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void downButtonSelected() {
		IStructuredSelection selection = (IStructuredSelection) videosOfSequencesTableViewer.getSelection() ;
		if ( selection.size() != 1)
			return;
		Media media = (Media) selection.getFirstElement();
		int idx = media.getId() -1;
		if ( media != null && program.getVideos().getVideo(idx) == media) {
			program.moveDown( idx );
			videosOfSequencesTableViewer.setInput(program.getVideos());
			videosOfSequencesTableViewer.setSelection(selection);
			videosOfSequencesTableViewer.refresh();
		}
	}
	
	private void tableRowDoubleClick() {
		IStructuredSelection selection = (IStructuredSelection) videosOfSequencesTableViewer.getSelection() ;
		if ( selection.size() != 1)
			return;
		Media media = (Media) selection.getFirstElement();
		if ( media != null ) {
			logger.info("Edit video with id : " + media.getId());
			Utils.modifyMedia(getShell(), this, media);
			videosOfSequencesTableViewer.refresh();
		}
		
	}

	private void upButtonSelected() {
		IStructuredSelection selection = (IStructuredSelection) videosOfSequencesTableViewer.getSelection() ;
		if ( selection.size() != 1)
			return;
		Media media = (Media) selection.getFirstElement();
		int idx = media.getId() -1;
		if ( media != null && program.getVideos().getVideo(idx) == media) {
			program.moveUp( idx );
			videosOfSequencesTableViewer.setInput(program.getVideos());
			videosOfSequencesTableViewer.setSelection(selection);
			videosOfSequencesTableViewer.refresh();			
		}		
	}

	private void adaptVideosOfSequencesTableViewer() {

		videosOfSequencesTableViewer.setContentProvider( new IStructuredContentProvider() {
			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
			}
			
			public void dispose() {
			}
			
			public Object[] getElements(Object arg0) {
				Videos videos = (Videos) arg0;				
				return videos.getVideo();
			}
		});

		videosOfSequencesTableViewer.setLabelProvider( new ProgramTableLabelProvider() );

		Table table = videosOfSequencesTableViewer.getTable();

		table.addMouseListener( new MouseAdapter() {
			
			public void mouseDown(MouseEvent arg0) {
				IStructuredSelection selection = (IStructuredSelection) videosOfSequencesTableViewer.getSelection() ;
				Media v = (Media) selection.getFirstElement();
				if ( v != null ) {
					manager.getStatusLineManager().setMessage(Utils.getMediaDescription(v) );					
				}
			}
			
			public void mouseDoubleClick(MouseEvent arg0) {
				tableRowDoubleClick();
			}
		});

		TableLayout tableLayout = new TableLayout();
		for ( int i = 0 ; i <= 9 ; i++ ) {
			TableColumn tc = new TableColumn(table, SWT.LEFT);
			tc.setText( LocaleManager.getText("app.video.sequence.table.column." + i) );
			tableLayout.addColumnData(new ColumnWeightData( 100 , true));
			final int sort = i; 
			tc.addSelectionListener( new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					//				tableViewer.setSorter(new SyndSorter(SyndSorter.TITLE, versus[0]));
					videosOfSequencesTableViewer.setSorter(
							new ProgramManagerTableColumnSorter(sort, versus[0])
						);
					versus[0] *= -1;
				}
			});
		}
		table.setLayout(tableLayout);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}	
	
	/**
	 * test purpose
	 * @param args
	 */
	public static void main(String[] args) {
		ProgramTab.showGUI();
	}
	
	/**
	 * Auto-generated method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		ProgramTab inst = new ProgramTab(shell, null,  null);
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
}
