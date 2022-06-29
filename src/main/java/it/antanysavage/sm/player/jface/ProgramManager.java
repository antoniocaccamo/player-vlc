package it.antanysavage.sm.player.jface;

import java.util.HashMap;
import java.util.Iterator;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.actions.DeleteMediaAction;
import it.antanysavage.sm.player.actions.NewMediaAction;
import it.antanysavage.sm.player.actions.ProgramNewAction;
import it.antanysavage.sm.player.actions.ProgramOpenAction;
import it.antanysavage.sm.player.actions.OpenSequenceAction;
import it.antanysavage.sm.player.actions.ProgramOpenFTPAction;
import it.antanysavage.sm.player.actions.ProgramSaveAsAction;
import it.antanysavage.sm.player.actions.ProgramSaveAction;
import it.antanysavage.sm.player.actions.SaveSequenceAction;
import it.antanysavage.sm.player.actions.VideoDeleteAction;
import it.antanysavage.sm.player.actions.VideoModifyAction;
import it.antanysavage.sm.player.actions.VideoNewAction;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.swt.views.MediaModifyComposite;
import it.antanysavage.sm.player.util.Utils;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;


public class ProgramManager extends ApplicationWindow {

	private static final Logger logger = LoggerFactory.getLogger(ProgramManager.class);	

	private Player theplayer ;
	private CTabFolder programTabFolder;

	private StatusLineManager slm = new StatusLineManager();

	//	private Combo comboSequences;

	public ProgramManager(Player theplayer) {
		super( null );
		this.theplayer = theplayer;
		addMenuBar();
		addStatusLine();
	}

	public boolean close() {
		getShell().setVisible(false);
		return false;
	}

	public Player getTheplayer() {
		return theplayer;
	}	

	protected MenuManager createMenuManager() {
		MenuManager menu = new MenuManager(null);


		MenuManager programMenu = new MenuManager(LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE));
		
		logger.info("Player.FTP_MODE : " + Player.FTP_MODE);

		if ( ! Player.FTP_MODE ) {
			programMenu.add(new ProgramNewAction( this));
			programMenu.add(new Separator() );

			programMenu.add( new ProgramOpenAction(this));
			programMenu.add(new ProgramSaveAction( this ));
			programMenu.add(new ProgramSaveAsAction( this ));

		} else {
			programMenu.add( new ProgramOpenAction(this));
			programMenu.add(new Separator() );
			programMenu.add( new ProgramOpenFTPAction(this));
		}

		programMenu.add(new Separator() );

		Action quitAction = new Action(LocaleManager.getText(LocaleManager.SM_MENU_SEQUENCE_QUIT)) {
			public void run() {
				close();
			}
		};
		programMenu.add(quitAction);

		menu.add(programMenu);

		if ( ! Player.FTP_MODE ) {

			MenuManager mediaMenu = new MenuManager(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA));

			mediaMenu.add( new VideoNewAction(this) );
			mediaMenu.add(new Separator());

			mediaMenu.add( new VideoModifyAction(this) );		
			mediaMenu.add(new Separator());

			mediaMenu.add( new VideoDeleteAction(this) );



			menu.add(mediaMenu);
		}

		return menu;
	}

	protected Control createContents(Composite parent) {

		getShell().setText( 
				LocaleManager.getText( LocaleManager.APP_TITLE ) + 
				" | " + 
				LocaleManager.getText( LocaleManager.APP_MENU_SEQUENCE_MANAGER)
				);
		getShell().setImage(Player.LOGO_IMAGE );

		programTabFolder = new CTabFolder(parent, SWT.NONE);
		programTabFolder.setBorderVisible(true);
		programTabFolder.setUnselectedCloseVisible(false);
		programTabFolder.addListener( SWT.MouseDown, new Listener() {			
			public void handleEvent(Event event) {				
				CTabItem tabItem = programTabFolder.getSelection();
				ProgramTab programTab = (ProgramTab) tabItem.getControl();
				getStatusLineManager().setMessage(programTab.getProgram().toString());
				programTab.getVideosOfSequencesTableViewer().setInput(programTab.getProgram().getVideos());
				programTab.getVideosOfSequencesTableViewer().refresh();
			}
		});		

		parent.setSize(500, 350);


		return parent;
	}


	/*
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
	 */	

	//	private void adaptVideosOfSequencesTableViewer() {
	//
	//		this.videosOfSequencesTableViewer.setContentProvider( new IStructuredContentProvider() {
	//
	//			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
	//			}
	//
	//			public void dispose() {
	//			}
	//
	//			public Object[] getElements(Object arg0) {
	//				Videos videos = (Videos) arg0;				
	//				return videos.getVideo();
	//
	//				//				ArrayList list = (ArrayList) arg0;
	//				//				return list.toArray();
	//
	//			}
	//		});
	//
	//		this.videosOfSequencesTableViewer.setLabelProvider( new SequencesTableLabelProvider());
	//
	//		Table table = this.videosOfSequencesTableViewer.getTable();
	//
	//
	//		table.addMouseListener( new MouseAdapter() {
	//
	//			public void mouseDown(MouseEvent arg0) {
	//				IStructuredSelection selection = (IStructuredSelection) videosOfSequencesTableViewer.getSelection() ;
	//				Media v = (Media) selection.getFirstElement();
	//				if ( v != null ) {
	//					slm.setMessage(Utils.getSequenceMediaDescription(v));
	//				}
	//			}
	//
	//			public void mouseDoubleClick(MouseEvent arg0) {
	//				// TODO Auto-generated method stub
	//				IStructuredSelection selection = (IStructuredSelection) videosOfSequencesTableViewer.getSelection() ;
	//				if ( selection.size() != 1)
	//					return;
	//				Media media = (Media) selection.getFirstElement();
	//				if ( media != null ) {
	//					logger.info("Edit video with id : " + media.getId());
	//					modifyMedia( media );					
	//				}
	//
	//			}
	//		});
	//
	//		TableLayout tableLayout = new TableLayout();
	//
	//		for ( int i = 0 ; i <= 8 ; i++ ) {
	//
	//			TableColumn tc = new TableColumn(table, SWT.LEFT);
	//			tc.setText( LocaleManager.getText("app.video.sequence.table.column." + i) );
	//			tableLayout.addColumnData(new ColumnWeightData( 50 , true));
	//		}
	//
	//		table.setLayout(tableLayout);
	//
	//		table.setHeaderVisible(true);
	//		table.setLinesVisible(true);
	//
	//
	//	}
	/*
	private void modifyMedia( Media media ) {
		Point p  = getShell().getLocation();
		p.x += 10;
		p.y += 10;

		Display display = Display.getDefault();
		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		ModifyMediaComposite inst = new ModifyMediaComposite(shell, SWT.NONE , this,  media);
		shell.setLayout(new FillLayout());
		shell.layout();

		shell.setText( LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CHANGE));
		Point size = inst.getSize();
		Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
		shell.setSize(shellBounds.width, shellBounds.height);
		shell.setLocation(p);
		inst.pack();
		shell.pack();
		inst.forceFocus();
		shell.setImage(Player.LOGO_IMAGE);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	 */
	//	private void adaptSequecensListViewer( ) {
	//		this.sequecensListViewer.setContentProvider( 				
	//				new IStructuredContentProvider() {
	//					public Object[] getElements(Object element) {
	//						if ( element != null ) {
	//							HashMap hm = (HashMap) element;
	//							return hm.values().toArray();
	//							//							ArrayList list = (ArrayList) element;
	//							//							return list.toArray();
	//						} else {
	//							return null;
	//						}
	//					}
	//					public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
	//					}
	//					public void dispose() {	
	//					}
	//				}
	//		);
	//
	//		this.sequecensListViewer.setLabelProvider(new LabelProvider() {
	//			public Image getImage(Object element) {
	//				return null;
	//			}
	//
	//			public String getText(Object element) {
	//				return ((Program)element).getName();
	//			}
	//		});
	//
	//		this.sequecensListViewer.setSorter(new ViewerSorter(){
	//			public int compare(Viewer viewer, Object e1, Object e2) {
	//
	//				return ((Program)e1).getName().compareTo(((Program)e2).getName());
	//			}
	//
	//		});
	//
	//		this.sequecensListViewer.getList().addMouseListener( new MouseAdapter () {
	//
	//			public void mouseDown(MouseEvent arg0) {
	//				IStructuredSelection selection = (IStructuredSelection) sequecensListViewer.getSelection();
	//				Program smp = (Program) selection.getFirstElement();
	//				if ( smp != null ) {
	//					videosOfSequencesTableViewer.setInput( smp.getVideos() );
	//					logger.debug("sequence ["+smp.getName()+"] choosed");
	//					slm.setMessage( Utils.getSequenceDescription(smp));
	//				}
	//			}
	//
	//			public void mouseDoubleClick(MouseEvent arg0) {
	//
	//
	//
	////				IStructuredSelection selection = (IStructuredSelection) sequecensListViewer.getSelection();
	//				//				String sequenceSelected = (String)selection.getFirstElement();
	//				//				if ( ! SequenceMoviePlayerUtils.isAnEmptyString(sequenceSelected) ) {
	//				//					Videos videos = (Videos) sequencesHashMap.get(sequenceSelected);
	//				//					videosOfSequencesTableViewer.setInput(videos);
	//				////					videosOfSequencesTableViewer.getTable().pack();
	//				//					logger.info("sequence ["+sequenceSelected+"] choosed");
	//				//					
	//				//				}
	////				Program smp = (Program) selection.getFirstElement();
	////				if ( smp != null ) {
	////
	////					
	////					slm.setMessage( Utils.getSequenceDescription(smp));
	////				}
	//			}
	//		});
	//	}


	protected StatusLineManager createStatusLineManager() {
		return slm;
	}


	//	public void setSequences(HashMap sequencesHashMap) {
	//		this.sequencesHashMap = sequencesHashMap;
	//		this.sequecensListViewer.setInput( this.sequencesHashMap.keySet() );
	//		//		this.sequecensListViewer.update(this.sequencesHashMap.keySet(), null);
	//		logger.info("sequences list loaded");
	//	}

	//	public void updateSequences() {
	//		this.sequecensListViewer.setInput( this.smPlayer.getSequencesLoaded() );
	//	}

	//	public HashMap getSequencesHashMap() {
	//		return sequencesHashMap;
	//	}

	public StatusLineManager getStatusLineManager() {
		return this.slm;
	}


	public static void main(String[] args) {
		ProgramManager wwin = new ProgramManager(null);
		wwin.setBlockOnOpen(true);
		//		wwin.setShellStyle(SWT.SHADOW_ETCHED_IN);
		wwin.open();
		Display.getCurrent().dispose();
	}



	public CTabFolder getProgramTabFolder() {
		return programTabFolder;
	}

	public void createInitialTabs(HashMap<String, Program> sh) {		
		if ( ! sh.isEmpty() ) {
			CTabItem[] items = programTabFolder.getItems();
			for(int i = 0; items != null && i < items.length; i++ ){
				items[i].dispose();
			}
			Iterator<String> it = sh.keySet().iterator();
			while(it.hasNext()){
				Program program = sh.get(it.next());
				CTabItem tabItem = new CTabItem(  getProgramTabFolder(), SWT.NONE);
				tabItem.setText(program.getName());
				tabItem.setControl( new ProgramTab( getProgramTabFolder(), this, program) );
				getProgramTabFolder().setSelection(tabItem);
			}
		}
	}

}
