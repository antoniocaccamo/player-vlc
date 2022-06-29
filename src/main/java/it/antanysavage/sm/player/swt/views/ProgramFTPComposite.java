package it.antanysavage.sm.player.swt.views;

import java.io.File;
import java.io.FileOutputStream;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.ftp.FTPDownloader;
import it.antanysavage.sm.player.jface.ProgramManager;
import it.antanysavage.sm.player.jface.ProgramTab;
import it.antanysavage.sm.player.jface.ftp.ProgramFTPViewFilter;
import it.antanysavage.sm.player.jface.ftp.RemotePathTableLabelProvider;
import it.antanysavage.sm.player.jface.ftp.RemotePathTableSorter;
import it.antanysavage.sm.player.jface.ftp.RemotePathTableStructuredContentProvider;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.util.Utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ProgramFTPComposite extends Composite {

	public static final String FTP_TEXT = " @ FTP : " ;

	private static final Logger logger = LoggerFactory.getLogger(ProgramFTPComposite.class);
	private Table table;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */

	private FTPDownloader client = new FTPDownloader();

	private ProgramManager programManager;

	private TableViewer ftpTableViewer;

	private Label ftpPahtLabel;

	public ProgramFTPComposite(Composite parent, ProgramManager programManager) {
		super(parent, SWT.NONE);
		setLayout(new GridLayout(2, false));

		ftpTableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		table = ftpTableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		this.programManager = programManager;

		remotePathAdapter();



		setSize(420,380);

		ftpPahtLabel = new Label(this, SWT.WRAP);
		ftpPahtLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		ftpPahtLabel.setText("/");

		Button closeButton = new Button(this, SWT.NONE);

		closeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (client != null) {
					try {
						client.disconnect();
					} catch (Exception e) {
						logger.equals(e);
					}
				}
				getShell().dispose();
			}
		});
		closeButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		closeButton.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CLOSE));
		
		Display.getDefault().asyncExec( new Runnable() {
			
			public void run() {
				reconnect(null);
			}
		});

		
	}

	private void remotePathAdapter() {
		Table remotePathTable = ftpTableViewer.getTable();

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

		remotePathTable.setLayout(tableLayout);
		remotePathTable.setHeaderVisible(true);

		ftpTableViewer.setContentProvider(new RemotePathTableStructuredContentProvider());
		ftpTableViewer.setLabelProvider(new RemotePathTableLabelProvider());
		ftpTableViewer.setSorter(new RemotePathTableSorter());
		ftpTableViewer.addFilter(new ProgramFTPViewFilter());

		Table table = ftpTableViewer.getTable();

		table.addMouseListener(new MouseAdapter() {

			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if ( ! client.isConnected() ) {
					reconnect(ftpPahtLabel.getText());
				}
				IStructuredSelection selection = (IStructuredSelection) ftpTableViewer.getSelection();
				if (selection.size() != 1)
					return;
				final FTPFile ftpFile = (FTPFile) selection.getFirstElement();
				if (ftpFile.isDirectory()) {
					try {
						client.changeWorkingDirectory(ftpFile.getName());
						ftpTableViewer.setInput(client.listFiles());
						ftpTableViewer.refresh();						
												
						ftpPahtLabel.setText(client.printWorkingDirectory());
						

					} catch (Exception e) {
						logger.error("error occurred ", e);
						org.eclipse.jface.dialogs.MessageDialog.openError(
								getShell(), getShell().getText(),
								e.getLocalizedMessage());
					}
				}

				if (ftpFile.isFile()) {
					try {

						File file = new File( Player.getProgramLocalPath() , ftpFile.getName() );

						if ( file.exists() ) {

							MessageDialog.openError(getShell(), getShell().getText(),
									"File already exists : " + file.getAbsoluteFile());
							return;
						}

						if ( ! file.createNewFile() ) {

							MessageDialog.openError(getShell(), getShell().getText(),
									"Can't create file : " + file.getAbsoluteFile());
							return;

						}

						String remoteFile=  client.printWorkingDirectory() + "/" + ftpFile.getName();
						logger.info("init downloading file [" + remoteFile + "] into file [" + file.getAbsoluteFile() + "]");
						FileOutputStream fos = new FileOutputStream(file);
						client.download(fos, remoteFile);						

						Program program = Utils.getProgram( SequenceFileManager.read(file) , file.getAbsolutePath()  , remoteFile); 							

						logger.info("acquired program : " + program);

						CTabItem tabItem = new CTabItem( programManager.getProgramTabFolder(), SWT.NONE);
						tabItem.setText(program.getName());
						tabItem.setControl( new ProgramTab(programManager.getProgramTabFolder(), programManager, program) );

						programManager.getProgramTabFolder().setSelection(tabItem);

						programManager.getTheplayer().addSequence(program);
						programManager.getStatusLineManager().setMessage(program.toString());
						
						SequenceFileManager.write(program);


					} catch (Exception e) {
						logger.error("error occurred ", e);
						org.eclipse.jface.dialogs.MessageDialog.openError(
								getShell(), getShell().getText(),
								e.getLocalizedMessage());
					}
				}

			}
		});

	}

	private void reconnect(String remoteDir) {
		try {
			if (!client.isConnected()) {
				client.connect();
			}
			if ( StringUtils.isNotEmpty(remoteDir) ){
				client.changeWorkingDirectory(remoteDir);
			}
			ftpTableViewer.setInput(client.listFiles());
		} catch (Exception e) {
			logger.error("error occurred ", e);
			MessageDialog.openError(getShell(), getShell().getText(),
					e.getLocalizedMessage());
			return;
		}
	}

}
