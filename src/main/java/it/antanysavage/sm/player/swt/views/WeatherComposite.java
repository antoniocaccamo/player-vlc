package it.antanysavage.sm.player.swt.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class WeatherComposite extends Composite {
	private Composite cityComposite;
	private Composite maxComposite;
	private Canvas maxCanvas;
	private Canvas minCanvas;
	private Canvas cityCanvas;
	private Canvas conditionCanvas;
	private Composite minComposite;
	private Composite conditionComposite;
	
//	private ScreenManagerComposite screenManagerComposite;
//	private String weatherLatLng = null;

	public WeatherComposite( Composite programComposite) {
		super(programComposite, SWT.NONE);
		
//		setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));

		//getShell().setBackgroundMode(SWT.INHERIT_DEFAULT);
			GridLayout thisLayout = new GridLayout();
			thisLayout.numColumns = 3;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.horizontalSpacing = 0;
			thisLayout.verticalSpacing = 0;
			setLayout(thisLayout);


			cityComposite = new Composite(this, SWT.NONE);
			GridLayout cityCompositeLayout = new GridLayout();
			cityCompositeLayout.makeColumnsEqualWidth = true;
			GridData cityCompositeLData = new GridData();
			cityCompositeLData.horizontalAlignment = GridData.FILL;
			cityCompositeLData.grabExcessHorizontalSpace = true;
			cityCompositeLData.verticalAlignment = GridData.FILL;
			cityCompositeLData.horizontalSpan = 3;
			cityCompositeLData.grabExcessVerticalSpace = true;
			cityComposite.setLayoutData(cityCompositeLData);
			cityComposite.setLayout(cityCompositeLayout);

			GridData cityCanvasLData = new GridData();
			cityCanvasLData.grabExcessVerticalSpace = true;
			cityCanvasLData.verticalAlignment = GridData.FILL;
			cityCanvasLData.horizontalAlignment = GridData.FILL;
			cityCanvasLData.grabExcessHorizontalSpace = true;
			cityCanvas = new Canvas(cityComposite, SWT.NONE);
			cityCanvas.setLayoutData(cityCanvasLData);

			conditionComposite = new Composite(this, SWT.NONE);
			GridLayout conditionCompositeLayout = new GridLayout();
			conditionCompositeLayout.makeColumnsEqualWidth = true;
			GridData conditionCompositeLData = new GridData();
			conditionCompositeLData.grabExcessVerticalSpace = true;
			conditionCompositeLData.verticalAlignment = GridData.FILL;
			conditionCompositeLData.horizontalAlignment = GridData.FILL;
			conditionCompositeLData.grabExcessHorizontalSpace = true;
			conditionCompositeLData.horizontalSpan = 2;
			conditionCompositeLData.verticalSpan = 2;
			conditionComposite.setLayoutData(conditionCompositeLData);
			conditionComposite.setLayout(conditionCompositeLayout);

			GridData conditionCanvasLData = new GridData();
			conditionCanvasLData.grabExcessHorizontalSpace = true;
			conditionCanvasLData.grabExcessVerticalSpace = true;
			conditionCanvasLData.verticalAlignment = GridData.FILL;
			conditionCanvasLData.horizontalAlignment = GridData.FILL;
			conditionCanvas = new Canvas(conditionComposite, SWT.NONE);
			conditionCanvas.setLayoutData(conditionCanvasLData);

			minComposite = new Composite(this, SWT.NONE);
			GridLayout minCompositeLayout = new GridLayout();
			minCompositeLayout.makeColumnsEqualWidth = true;
			GridData minCompositeLData = new GridData();
			minCompositeLData.verticalAlignment = GridData.FILL;
			minCompositeLData.grabExcessVerticalSpace = true;
			minCompositeLData.horizontalAlignment = GridData.FILL;
			minCompositeLData.grabExcessHorizontalSpace = true;
			minComposite.setLayoutData(minCompositeLData);
			minComposite.setLayout(minCompositeLayout);

			GridData minCanvasLData = new GridData();
			minCanvasLData.grabExcessVerticalSpace = true;
			minCanvasLData.grabExcessHorizontalSpace = true;
			minCanvasLData.horizontalAlignment = GridData.FILL;
			minCanvasLData.verticalAlignment = GridData.FILL;
			minCanvas = new Canvas(minComposite, SWT.NONE);
			minCanvas.setLayoutData(minCanvasLData);

			maxComposite = new Composite(this, SWT.NONE);
			GridLayout maxCompositeLayout = new GridLayout();
			maxCompositeLayout.makeColumnsEqualWidth = true;
			GridData maxCompositeLData = new GridData();
			maxCompositeLData.grabExcessVerticalSpace = true;
			maxCompositeLData.grabExcessHorizontalSpace = true;
			maxCompositeLData.horizontalAlignment = GridData.FILL;
			maxCompositeLData.verticalAlignment = GridData.FILL;
			maxComposite.setLayoutData(maxCompositeLData);
			maxComposite.setLayout(maxCompositeLayout);

			GridData maxCanvasLData = new GridData();
			maxCanvasLData.verticalAlignment = GridData.FILL;
			maxCanvasLData.grabExcessVerticalSpace = true;
			maxCanvasLData.horizontalAlignment = GridData.FILL;
			maxCanvasLData.grabExcessHorizontalSpace = true;
			maxCanvas = new Canvas(maxComposite, SWT.NONE);
			maxCanvas.setLayoutData(maxCanvasLData);	
			
		}

		public Canvas getConditionCanvas() {
			return conditionCanvas;
		}

		public Canvas getCityCanvas() {
			return cityCanvas;
		}

		public Canvas getMaxCanvas() {
			return maxCanvas;
		}


		// **********************************************************

		/**
		 * Auto-generated main method to display this 
		 * org.eclipse.swt.widgets.Composite inside a new Shell.
		 */
		public static void main(String[] args) {
			showGUI();
		}

		/**
		 * Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
		 */	
		protected void checkSubclass() {
		}

		/**
		 * Auto-generated method to display this 
		 * org.eclipse.swt.widgets.Composite inside a new Shell.
		 */
		public static void showGUI() {
			Display display = Display.getDefault();
			Shell shell = new Shell(display);
			WeatherComposite inst = new WeatherComposite(shell);
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
