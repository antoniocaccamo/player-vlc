package it.antanysavage.sm.player;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;



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
public class ScreenManagerExpandBar extends Composite {

	private Player player;
	private int index;
	private ExpandItem expandItem1;

	public ScreenManagerExpandBar(Composite parent,  Player smPlayer, int i) {
		super(parent, SWT.EMBEDDED);
		this.player = smPlayer;
		this.index = i;
		GridLayout thisLayout = new GridLayout();
		this.setLayout(thisLayout);
		GridData gridData = new GridData();
		gridData.horizontalSpan = GridData.FILL;
		gridData.verticalSpan = GridData.FILL;		
		this.setLayoutData(gridData);
		createExpandBarControls();
		createControls();
	}

	private void createControls() {

	}

	private void createExpandBarControls() {
		ExpandBar bar = new ExpandBar (this, SWT.V_SCROLL);
		
		Composite composite = new Composite (bar, SWT.NONE);
		GridLayout layout = new GridLayout ();
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 8;
		layout.verticalSpacing = 10;
		composite.setLayout(layout);
		Label label = new Label (composite, SWT.NONE);
		label.setText("This is Bar 1");
		ExpandItem screenExpandItem = new ExpandItem (bar, SWT.NONE, 0);
		screenExpandItem.setText("screenExpandItem");
		screenExpandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		screenExpandItem.setControl(composite);
		
		composite = new Composite (bar, SWT.NONE);
		layout = new GridLayout (2, false);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 8;
		layout.verticalSpacing = 10;
		composite.setLayout(layout);	
		label = new Label (composite, SWT.NONE);
		label.setText("This is Bar2");
		ExpandItem timingExpandItem = new ExpandItem (bar, SWT.NONE, 1);
		timingExpandItem.setText("timingExpandItem");
		timingExpandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		timingExpandItem.setControl(composite);
		
		composite = new Composite (bar, SWT.NONE);
		layout = new GridLayout (2, true);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 8;
		layout.verticalSpacing = 10;
		composite.setLayout(layout);
		label = new Label (composite, SWT.NONE);
		label.setText("This is Bar3");	
		
		ExpandItem programExpandItem = new ExpandItem (bar, SWT.NONE, 2);
		programExpandItem.setText("programExpandItem");
		programExpandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		programExpandItem.setControl(composite);
		
		
		composite = new Composite (bar, SWT.NONE);
		layout = new GridLayout (2, true);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 8;
		layout.verticalSpacing = 10;
		composite.setLayout(layout);
		label = new Label (composite, SWT.NONE);
		label.setText("This is Bar4");	
		
		ExpandItem playerExpandItem = new ExpandItem (bar, SWT.NONE, 3);
		playerExpandItem.setText("playerExpandItem");
		{
			expandItem1 = new ExpandItem(bar, SWT.NONE);
			expandItem1.setText("expandItem1");
			{
				CTabFolder tabFolder = new CTabFolder(bar, SWT.NONE);
				expandItem1.setControl(tabFolder);
				tabFolder.setBorderVisible(true);
			}
		}
		playerExpandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		playerExpandItem.setControl(composite);
	}
	
	
public static void main (String [] args) {
		
		Display display = new Display();
	    Shell shell = new Shell(display);
	    GridLayout shellLayout = new GridLayout();
	    shellLayout.makeColumnsEqualWidth = true;
	    shell.setLayout(shellLayout);
	    shell.setText("My First SWT GUI");

	    GridData rcLData = new GridData();
	    ScreenManagerExpandBar rc = new ScreenManagerExpandBar(shell, null, 0);
		rc.setLayoutData(rcLData);

		shell.open();
		shell.pack();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();


	}

}
