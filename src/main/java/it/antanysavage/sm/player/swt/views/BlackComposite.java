package it.antanysavage.sm.player.swt.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public class BlackComposite extends Composite {

	

	protected static Color black  = new Color(Display.getCurrent(), 0, 0, 0);
	


	public BlackComposite(Composite parent) {
		super(parent, SWT.NONE);



		GridLayout thisLayout = new GridLayout(1,false);
		this.setLayout(thisLayout);
		GridData gridData = new GridData();
		gridData.horizontalSpan = GridData.FILL;
		gridData.verticalSpan = GridData.FILL;		
		thisLayout.marginHeight = 0;
		thisLayout.marginWidth = 0;

		setBackground( black );
	}

	


}
