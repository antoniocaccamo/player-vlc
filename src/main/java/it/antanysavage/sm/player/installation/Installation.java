package it.antanysavage.sm.player.installation;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

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
public class Installation extends ApplicationWindow {

	private Button validityCheckbox;
	private Label labelValidity;
	
	private boolean visiblity = false;

	private Action validateAction;
	private Action invalidateAction;
	
	public Installation( ) {
		super( null );
		setShellStyle( SWT.CLOSE | SWT.TITLE  ); 
		addMenuBar();


	}


	@Override
	protected Control createContents(Composite parent) {
		getShell().setText(LocaleManager.getText(LocaleManager.APP_TITLE) + " | Installation Manager");
		getShell().setImage(new Image( getShell().getDisplay(), getClass().getClassLoader().getResourceAsStream(Player.LOGO)));
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout parentLayout = new GridLayout(2, false );
		parentLayout.makeColumnsEqualWidth = true;
		composite.setLayout(parentLayout);
		labelValidity = new Label(composite, SWT.VERTICAL);
		GridData labelValidityLData = new GridData();
		labelValidityLData.grabExcessVerticalSpace = true;
		labelValidity.setLayoutData(labelValidityLData);
		labelValidity.setText("AT ADV player is valid ?");

		GridData validityCheckboxLData = new GridData();
		validityCheckboxLData.grabExcessVerticalSpace = true;
		validityCheckboxLData.grabExcessHorizontalSpace = true;
		validityCheckboxLData.horizontalAlignment = GridData.FILL;
		validityCheckbox = new Button(composite, SWT.CHECK | SWT.CENTER);
		validityCheckbox.setLayoutData(validityCheckboxLData);
		validityCheckbox.setEnabled(false);
		validityCheckbox.setVisible(visiblity);


		parent.setSize(300, 100);
		
		return parent;
	}


	protected MenuManager createMenuManager() {
		MenuManager main_menu = new MenuManager(null);

		MenuManager menu_file = new MenuManager("File");
		menu_file.add(
				new Action("Exit") {			
					public void run() {
						System.exit(0);
					};			
				}
		);

		MenuManager menu_install = new MenuManager("Installation");
		menu_install.add(
				new Action("Check Validity") {			
					public void run() {
						check();							
					};			
				}
		);
		menu_install.add( new Separator() );
		validateAction = new Action("Validate") {			
			public void run() {
				Validate.validate();
				check();
			}			
		};
		menu_install.add(validateAction);
		
		menu_install.add( new Separator() );
		invalidateAction = new Action("Invalidate") {			
			public void run() {
				Invalidate.invalidate();
				check();
			}			
		};
		menu_install.add(invalidateAction);
		main_menu.add(menu_file);
		main_menu.add(menu_install);
		
		validateAction.setEnabled(visiblity);
		invalidateAction.setEnabled(visiblity);
		return main_menu;
	}

	public static void main(String[] args) {
		Installation wwin = new Installation();
		wwin.setBlockOnOpen(true);
		wwin.open();
		Display.getCurrent().dispose();
	}
	
	private void check() {
		boolean check = Check.checkValidation();
		validityCheckbox.setVisible(true);
		validityCheckbox.setSelection(check);
//		if ( check ) {
			validateAction.setEnabled(! check);
			invalidateAction.setEnabled(check);
//		} else {
//			validateAction.setEnabled( check);
//			invalidateAction.setEnabled(! check);
//		}
	}

}
