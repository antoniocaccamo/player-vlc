package it.antanysavage.sm.player.dialogs;

import java.util.Date;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.util.Utils;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class AboutDialog extends TitleAreaDialog  {

	public AboutDialog() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);

		// Set the title
		setTitle(LocaleManager.getText(LocaleManager.APP_TITLE));

		// Set the message
		//	    setMessage("This is a JFace dialog", IMessageProvider.INFORMATION);

		// Set the image
		getShell().setImage(Player.LOGO_IMAGE );
		setTitleImage(Player.BCKG_IMAGE);


		return contents;
	}


	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		if ( Player.hasVersionInfo() ) {
			Table table = new Table(composite, SWT.FULL_SELECTION | SWT.BORDER);
			table.setLayoutData(new GridData(GridData.FILL_BOTH));
			
			 TableColumn one = new TableColumn(table, SWT.LEFT);
			 TableColumn two = new TableColumn(table, SWT.LEFT | SWT.MULTI);
			
			if ( ! Utils.isAnEmptyString(Player.VERSION) ) {			
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, "Version");
				item.setText(1, Player.VERSION);
			}
			
			Date d = null;
			if ( ! Utils.isAnEmptyString(Player.VERSION_DATE) ) {

				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, "Version date");
				item.setText(1, Player.VERSION_DATE);
				
			}
			

//			if ( ! Utils.isAnEmptyString(Player.VERSION_INFO) ) {
//				
//				TableItem item = new TableItem(table, SWT.NONE);
//				item.setText(0, "Version info");
//				item.setText(1, Player.VERSION_INFO);
//			}
			
			
		    one.pack();
		    two.pack();

		}

		//	    // Create a table
		//	    Table table = new Table(composite, SWT.FULL_SELECTION | SWT.BORDER);
		//	    table.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		//	    // Create two columns and show
		//	    TableColumn one = new TableColumn(table, SWT.LEFT);
		//	    one.setText("Real Name");
		//
		//	    TableColumn two = new TableColumn(table, SWT.LEFT);
		//	    two.setText("Preferred Name");
		//
		//	    table.setHeaderVisible(true);
		//
		//	    // Add some data
		//	    TableItem item = new TableItem(table, SWT.NONE);
		//	    item.setText(0, "Robert Harris");
		//	    item.setText(1, "Bobby");
		//
		//	    item = new TableItem(table, SWT.NONE);
		//	    item.setText(0, "Robert Warner");
		//	    item.setText(1, "Rob");
		//
		//	    item = new TableItem(table, SWT.NONE);
		//	    item.setText(0, "Gabor Liptak");
		//	    item.setText(1, "Gabor");
		//
		//	    one.pack();
		//	    two.pack();
		//	    
		//	    setDefaultImage(Player.BCKG_IMAGE);

		return composite;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		parent.pack();
	}


	public boolean close() {
		return super.close();
	}

}
