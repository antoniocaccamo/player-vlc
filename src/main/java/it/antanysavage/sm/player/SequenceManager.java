package it.antanysavage.sm.player;

import it.antanysavage.sm.player.actions.DeleteMediaAction;
import it.antanysavage.sm.player.actions.NewMediaAction;
import it.antanysavage.sm.player.actions.NewSequenceAction;
import it.antanysavage.sm.player.actions.OpenSequenceAction;
import it.antanysavage.sm.player.actions.SaveSequenceAction;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Videos;
import it.antanysavage.sm.player.swt.views.MediaModifyComposite;
import it.antanysavage.sm.player.swt.views.MediaNewComposite;
import it.antanysavage.sm.player.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
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
public class SequenceManager extends ApplicationWindow {

	private static final Logger logger = LoggerFactory.getLogger(SequenceManager.class);	

	private Player smPlayer ;

	private StatusLineManager slm = new StatusLineManager();

	//	private Combo comboSequences;

	private ListViewer sequecensListViewer;
	private Button downButton;
	private Button upButton;
	private Composite composite1;

	private TableViewer videosOfSequencesTableViewer;

	public SequenceManager(Player smPlayer) {
		super( null );

		this.smPlayer = smPlayer;

		addMenuBar();
		addStatusLine();


	}

	public boolean close() {
		getShell().setVisible(false);
		return false;
	}

	public TableViewer getVideosOfSequencesTableViewer() {
		return videosOfSequencesTableViewer;
	}


	public Player getSmPlayer() {
		return smPlayer;
	}

	public ListViewer getSequecensListViewer() {
		return sequecensListViewer;
	}

	protected MenuManager createMenuManager() {
		MenuManager main_menu = new MenuManager(null);


		MenuManager sequence_menu = new MenuManager(LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE));
		NewSequenceAction newSequenceAction = new NewSequenceAction(this);
		sequence_menu.add(newSequenceAction);

		sequence_menu.add(new Separator() );

		OpenSequenceAction openSequenceAction = new OpenSequenceAction( this );
		sequence_menu.add(openSequenceAction);

		SaveSequenceAction saveSequenceAction = new SaveSequenceAction( this );
		sequence_menu.add(saveSequenceAction);

		sequence_menu.add(new Separator() );

		Action quitAction = new Action(LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_QUIT)) {
			public void run() {
				close();
			}

		};
		sequence_menu.add(quitAction);


		main_menu.add(sequence_menu);

		MenuManager video_menu = new MenuManager(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA));

		NewMediaAction newMediaAction = new NewMediaAction(this);
		video_menu.add(newMediaAction);

		video_menu.add(new Separator());
		
		video_menu.add( new Action(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_MODIFY)) {
			@Override
			public void run() {
				Media media = null;
				IStructuredSelection	selection = 
					(IStructuredSelection) getVideosOfSequencesTableViewer().getSelection();
				if ( selection != null && selection.size() == 1) {
					media = (Media) selection.getFirstElement();
					if ( media != null ) {
						logger.info("Edit video with id : " + media.getId());
						modifyMedia( media );					
					}
				}
			}
		});
		
		video_menu.add(new Separator());

		DeleteMediaAction deleteMediaAction = new DeleteMediaAction(this);  
		video_menu.add(deleteMediaAction);

		main_menu.add(video_menu);

		return main_menu;
	}

	protected Control createContents(Composite parent) {

		int[] weight =  new int [2];
		weight[0] = 1;
		weight[1] = 4;


		getShell().setText( LocaleManager.getText( LocaleManager.APP_TITLE ) + " | " + LocaleManager.getText( LocaleManager.APP_MENU_SEQUENCE_MANAGER));


		getShell().setImage(Player.LOGO_IMAGE );

		SashForm composite = new SashForm(parent, SWT.HORIZONTAL );
		//		composite.SASH_WIDTH = 20;
		//		composite.setWeights(weight);


		GridLayout gridLayout = new GridLayout(1,false);
		gridLayout.numColumns = 3;
		gridLayout.horizontalSpacing = 5;
		gridLayout.verticalSpacing = 5;
		composite.setLayout(gridLayout);

		Group sequencesGroup = new Group(composite, SWT.SHADOW_ETCHED_IN | SWT.LEFT );
		sequencesGroup.setLayout( new GridLayout(1,false) );
		sequencesGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SEQUENCES));
		GridData gridData = new GridData(GridData.FILL_VERTICAL | GridData.HORIZONTAL_ALIGN_CENTER | GridData.VERTICAL_ALIGN_CENTER);

		sequencesGroup.setLayoutData(gridData);    

		GridData sequecensListViewerLData = new GridData();
		sequecensListViewerLData.grabExcessHorizontalSpace = true;
		sequecensListViewerLData.horizontalAlignment = GridData.FILL;
		sequecensListViewerLData.verticalAlignment = GridData.FILL;
		sequecensListViewerLData.grabExcessVerticalSpace = true;
		sequecensListViewer = new ListViewer(sequencesGroup, SWT.BORDER |SWT.CHECK | SWT.V_SCROLL);
		sequecensListViewer.getControl().setLayoutData(sequecensListViewerLData);

		this.adaptSequecensListViewer();

		Group videosOfSequencesGroup = new Group(composite, SWT.SHADOW_ETCHED_IN | SWT.RIGHT);
		videosOfSequencesGroup.setLayout( new GridLayout(2,false) );
		videosOfSequencesGroup.setText(LocaleManager.getText(LocaleManager.APP_GROUP_VIDEOS_SEQUENCE));		
		GridData sequenceListViewerGData = new GridData();
		sequenceListViewerGData.grabExcessHorizontalSpace = true;
		videosOfSequencesGroup.setLayoutData(sequenceListViewerGData);

		GridData composite1LData = new GridData();
		composite1LData.horizontalAlignment = GridData.FILL;
		composite1LData.verticalAlignment = GridData.FILL;
		composite1LData.grabExcessVerticalSpace = true;
		composite1 = new Composite(videosOfSequencesGroup, SWT.NONE);
		GridLayout composite1Layout = new GridLayout(1,false);
		composite1Layout.makeColumnsEqualWidth = true;
		composite1.setLayout(composite1Layout);
		composite1.setLayoutData(composite1LData);

		upButton = new Button(composite1, SWT.ARROW | SWT.UP);
		GridData upButtonLData = new GridData();
		upButtonLData.widthHint = 25;
		upButtonLData.heightHint = 25;
		upButtonLData.grabExcessVerticalSpace = true;
		upButtonLData.verticalAlignment = GridData.END;
		upButton.setLayoutData(upButtonLData);

		GridData downButtonLData = new GridData();
		downButtonLData.grabExcessVerticalSpace = true;
		downButtonLData.grabExcessHorizontalSpace = true;
		downButtonLData.verticalAlignment = GridData.BEGINNING;
		downButtonLData.widthHint = 25;
		downButtonLData.heightHint = 25;
		downButton = new Button(composite1, SWT.ARROW | SWT.DOWN);
		downButton.setLayoutData(downButtonLData);
		

		this.videosOfSequencesTableViewer = new TableViewer(videosOfSequencesGroup, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION  );
		GridData videoOfSequenceGData = new GridData();
		videoOfSequenceGData.grabExcessHorizontalSpace = true;
		videoOfSequenceGData.horizontalAlignment = GridData.FILL;
		videoOfSequenceGData.grabExcessVerticalSpace = true;
		videoOfSequenceGData.verticalAlignment = GridData.FILL;
		videosOfSequencesTableViewer.getControl().setLayoutData(videoOfSequenceGData);

		this.adaptVideosOfSequencesTableViewer();
		
		upButton.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				upButtonSelected(arg0);
			}
		});
		downButton.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				downButtonSelected(arg0);
			}
		});


		//		
		//		Table table = new Table(videosOfSequencesGroup, SWT.MULTI | SWT.FULL_SELECTION);
		//		table.setLayoutData(gridData);
		//	    for (int i = 0; i < 5; i++) {
		//	      TableColumn column = new TableColumn(table, SWT.NONE);
		//	    }
		//	    for (int i = 0; i < 10; i++) {
		//	      TableItem item = new TableItem(table, SWT.NONE);
		//	      for (int j = 0; j < 5; j++) {
		//	        item.setText(j, "Row " + i + ", Column " + j);
		//	      }
		//	    }
		//	    for (int i = 0, n = table.getColumnCount(); i < n; i++) {
		//	      table.getColumn(i).pack();
		//	    }
		//		

		//		------------------------------------------


		composite.setWeights( weight );


		parent.setSize(500, 350);





		return parent;
	}



	private void upButtonSelected(SelectionEvent arg0) {
		IStructuredSelection selection = 
			(IStructuredSelection) getSequecensListViewer().getSelection();
		if ( selection != null) {			
			Program smp = (Program) selection.getFirstElement();
			if ( smp != null ) {
				
				selection = 
					(IStructuredSelection) getVideosOfSequencesTableViewer().getSelection();
				if ( selection != null ) {
					Media vv = (Media) selection.getFirstElement();
					if ( vv != null ) {
						int idx = vv.getId() -1 ;
						if ( smp.getVideos().getVideo(idx) == vv) {
							smp.moveUp(idx);
							getVideosOfSequencesTableViewer().setInput(smp.getVideos());
							getVideosOfSequencesTableViewer().refresh();
						} else {
							MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
							mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) );
							mb.setMessage("The movie with id ["+vv.getId()+"] is not in the sequence ["+smp.getName()+"]");
							mb.open();
						}
					}
					else {
						MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
						mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) );
						mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_VIDEO_NO_SELECTION));
						mb.open();
					}
				}

			} else {
				MessageBox mb = new MessageBox( getShell(), SWT.OK | SWT.ICON_WARNING);
				mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) );
				mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_NO_SELECTION));
				mb.open();
			}
		}
		
	}
	
	private void downButtonSelected(SelectionEvent arg0) {
		IStructuredSelection selection = 
			(IStructuredSelection) getSequecensListViewer().getSelection();
		if ( selection != null) {			
			Program smp = (Program) selection.getFirstElement();
			if ( smp != null ) {
				
				selection = 
					(IStructuredSelection) getVideosOfSequencesTableViewer().getSelection();
				if ( selection != null ) {
					Media vv = (Media) selection.getFirstElement();
					if ( vv != null ) {
						int idx = vv.getId() -1 ;
						if ( smp.getVideos().getVideo(idx) == vv) {
							smp.moveDown(idx);
							getVideosOfSequencesTableViewer().setInput(smp.getVideos());
							getVideosOfSequencesTableViewer().refresh();
						} else {
							MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
							mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) );
							mb.setMessage("The movie with id ["+vv.getId()+"] is not in the sequence ["+smp.getName()+"]");
							mb.open();
						}
					}
					else {
						MessageBox mb = new MessageBox(getShell(), SWT.OK | SWT.ICON_WARNING);
						mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) );
						mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_VIDEO_NO_SELECTION));
						mb.open();
					}
				}

			} else {
				MessageBox mb = new MessageBox( getShell(), SWT.OK | SWT.ICON_WARNING);
				mb.setText(LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) );
				mb.setMessage(LocaleManager.getText(LocaleManager.ERRORS_SEQUENCE_NO_SELECTION));
				mb.open();
			}
		}
		
	}

	private void adaptVideosOfSequencesTableViewer() {

		this.videosOfSequencesTableViewer.setContentProvider( new IStructuredContentProvider() {

			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
			}

			public void dispose() {
			}

			public Object[] getElements(Object arg0) {
				Videos videos = (Videos) arg0;				
				return videos.getVideo();

				//				ArrayList list = (ArrayList) arg0;
				//				return list.toArray();

			}
		});

		this.videosOfSequencesTableViewer.setLabelProvider( new SequencesTableLabelProvider());

		Table table = this.videosOfSequencesTableViewer.getTable();


		table.addMouseListener( new MouseAdapter() {

			public void mouseDown(MouseEvent arg0) {
				IStructuredSelection selection = (IStructuredSelection) videosOfSequencesTableViewer.getSelection() ;
				Media v = (Media) selection.getFirstElement();
				if ( v != null ) {
					slm.setMessage(Utils.getMediaDescription(v));
				}
			}

			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				IStructuredSelection selection = (IStructuredSelection) videosOfSequencesTableViewer.getSelection() ;
				if ( selection.size() != 1)
					return;
				Media media = (Media) selection.getFirstElement();
				if ( media != null ) {
					logger.info("Edit video with id : " + media.getId());
//					Utils.modifyMedia(this, this., media)
//					modifyMedia( media );					
				}

			}
		});

		TableLayout tableLayout = new TableLayout();

		for ( int i = 0 ; i <= 8 ; i++ ) {

			TableColumn tc = new TableColumn(table, SWT.LEFT);
			tc.setText( LocaleManager.getText("app.video.sequence.table.column." + i) );
			tableLayout.addColumnData(new ColumnWeightData( 50 , true));
		}

		table.setLayout(tableLayout);




		table.setHeaderVisible(true);
		table.setLinesVisible(true);


	}
	
	private void modifyMedia( Media media ) {
//		Point p  = getShell().getLocation();
//		p.x += 10;
//		p.y += 10;
//		
//		Display display = Display.getDefault();
//		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
//		ModifyMediaComposite inst = new ModifyMediaComposite(shell,  this,  media);
//		shell.setLayout(new FillLayout());
//		shell.layout();
//		
//		shell.setText( LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CHANGE));
//		Point size = inst.getSize();
//		Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
//		shell.setSize(shellBounds.width, shellBounds.height);
//		shell.setLocation(p);
//		inst.pack();
//		shell.pack();
//		inst.forceFocus();
//		shell.setImage(Player.LOGO_IMAGE);
//		shell.open();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
//		videosOfSequencesTableViewer.refresh();
	}

	private void adaptSequecensListViewer( ) {
		this.sequecensListViewer.setContentProvider( 				
				new IStructuredContentProvider() {
					public Object[] getElements(Object element) {
						if ( element != null ) {
							HashMap hm = (HashMap) element;
							return hm.values().toArray();
							//							ArrayList list = (ArrayList) element;
							//							return list.toArray();
						} else {
							return null;
						}
					}
					public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
					}
					public void dispose() {	
					}
				}
		);

		this.sequecensListViewer.setLabelProvider(new LabelProvider() {
			public Image getImage(Object element) {
				return null;
			}

			public String getText(Object element) {
				return ((Program)element).getName();
			}
		});

		this.sequecensListViewer.setSorter(new ViewerSorter(){
			public int compare(Viewer viewer, Object e1, Object e2) {

				return ((Program)e1).getName().compareTo(((Program)e2).getName());
			}

		});

		this.sequecensListViewer.getList().addMouseListener( new MouseAdapter () {

			public void mouseDown(MouseEvent arg0) {
				IStructuredSelection selection = (IStructuredSelection) sequecensListViewer.getSelection();
				Program smp = (Program) selection.getFirstElement();
				if ( smp != null ) {
					videosOfSequencesTableViewer.setInput( smp.getVideos() );
					logger.debug("sequence ["+smp.getName()+"] choosed");
					slm.setMessage( Utils.getProgramDescription(smp));
				}
			}

			public void mouseDoubleClick(MouseEvent arg0) {



//				IStructuredSelection selection = (IStructuredSelection) sequecensListViewer.getSelection();
				//				String sequenceSelected = (String)selection.getFirstElement();
				//				if ( ! SequenceMoviePlayerUtils.isAnEmptyString(sequenceSelected) ) {
				//					Videos videos = (Videos) sequencesHashMap.get(sequenceSelected);
				//					videosOfSequencesTableViewer.setInput(videos);
				////					videosOfSequencesTableViewer.getTable().pack();
				//					logger.info("sequence ["+sequenceSelected+"] choosed");
				//					
				//				}
//				Program smp = (Program) selection.getFirstElement();
//				if ( smp != null ) {
//
//					
//					slm.setMessage( Utils.getSequenceDescription(smp));
//				}
			}
		});
	}


	protected StatusLineManager createStatusLineManager() {
		return slm;
	}


	//	public void setSequences(HashMap sequencesHashMap) {
	//		this.sequencesHashMap = sequencesHashMap;
	//		this.sequecensListViewer.setInput( this.sequencesHashMap.keySet() );
	//		//		this.sequecensListViewer.update(this.sequencesHashMap.keySet(), null);
	//		logger.info("sequences list loaded");
	//	}

	public void updateSequences() {
		this.sequecensListViewer.setInput( this.smPlayer.getSequencesLoaded() );
	}

	//	public HashMap getSequencesHashMap() {
	//		return sequencesHashMap;
	//	}

	public StatusLineManager getStatusLineManager() {
		return this.slm;
	}
	/*

	public static void main(String[] args) {


		SequenceVideoManager wwin = new SequenceVideoManager();
		wwin.setBlockOnOpen(true);
		wwin.setShellStyle(SWT.SHADOW_ETCHED_IN);
		wwin.open();
		Display.getCurrent().dispose();

	}

	 */
	
	class SequencesTableLabelProvider implements ITableLabelProvider , IColorProvider {

		public Image getColumnImage(Object arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		public String getColumnText(Object arg0, int arg1) {
			Media  video = (Media) arg0;
			String s = "";
			switch (arg1) {

			case 0:
				s = "" +video.getId();
				break;

			case 1:
				s = LocaleManager.getText("model.sequece.media." + video.getType().getType() );
				break;

			case 2:
				s =  Utils.getDurationString( video.getDuration() );
				break;

			case 3 :
				s = ( Utils.isAnEmptyString(video.getPath()) ? "" : video.getPath() );
				break;

			case 4 :
				s = ( ! video.isDated()  ? "" : Utils.getWatchDate(video.getStartDate()) );
				break;
			
			case 5 :
				s = ( ! video.isDated() ? "" : Utils.getWatchDate(video.getEndDate()) );					
				break;
				
			case 6 :
				s = ( ! video.isTimed() ? "" : video.getFrom() );
				break;

			case 7 :
				s = ( ! video.isTimed() ? "" : video.getTo() );
				break;
				
			case 8 :
				s = ( video.isLimited() ? ""+video.getLimited() : "-");
				break;
			
			default :
				s = "";

			}
			return s;
		}

		public void addListener(ILabelProviderListener arg0) {
			// TODO Auto-generated method stub
			
		}

		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		public boolean isLabelProperty(Object arg0, String arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		public void removeListener(ILabelProviderListener arg0) {
			// TODO Auto-generated method stub
			
		}

		public Color getBackground(Object arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		public Color getForeground(Object arg0) {
			Media  media = (Media) arg0;
			
			Color color = null;
			if ( ! media.isAvailable() )
				color = Display.getDefault().getSystemColor( SWT.COLOR_RED );
			else
				color = Display.getDefault().getSystemColor( SWT.COLOR_BLACK );
			return color;
		}
		
		
		
	}


}
