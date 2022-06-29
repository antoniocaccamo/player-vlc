package it.antanysavage.sm.player;

import it.antanysavage.sm.player.bundle.LocaleManager;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class ExpandBarExample {
public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	shell.setText("Expand Bar");
	ExpandBar bar = new ExpandBar (shell, SWT.V_SCROLL);

	Composite composite = new Composite (bar, SWT.NONE);
	GridLayout layout = new GridLayout ();
	layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 8;
	layout.verticalSpacing = 10;
	composite.setLayout(layout);
	Label label = new Label (composite, SWT.NONE);
	label.setText("This is Bar 1");
	ExpandItem screenExpandItem = new ExpandItem (bar, SWT.NONE, 0);
	screenExpandItem.setText(LocaleManager.getText(LocaleManager.APP_GROUP_SCREEN_SIZE));
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
	playerExpandItem.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	playerExpandItem.setControl(composite);
	
	bar.setSpacing(6);
	shell.setSize(300, 200);
	shell.open();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) {
			display.sleep ();
		}
	}
	display.dispose();
}

}

