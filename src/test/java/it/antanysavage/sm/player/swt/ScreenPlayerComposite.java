package it.antanysavage.sm.player.swt;
import it.antanysavage.sm.player.util.SWTUtils;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
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
public class ScreenPlayerComposite extends org.eclipse.swt.widgets.Composite {



	private SashForm sashForm;
	private Canvas topNewsCanvas;
	private Composite topNewsComposite;
	private Canvas bottomNewsCanvas;
	private Composite bottomNewsComposite;
	private Composite playerComposite;
	private Timer timer;

	private StringBuffer sb = new StringBuffer();
	
	private static final String MSG = "da pauraasifhoasnfosanfoasjnfoasjfosanflskan4ln";
	
	private boolean first = true;

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
		Shell shell = new Shell(display, SWT.CLOSE);
		ScreenPlayerComposite inst = new ScreenPlayerComposite(shell, SWT.NONE);
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
		if (inst.timer != null ) {
			inst.timer.purge();
			inst.timer.cancel();
		}
	}

	public ScreenPlayerComposite(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.makeColumnsEqualWidth = true;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			thisLayout.horizontalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(443, 375);

			sashForm = new SashForm(this, SWT.EMBEDDED);
			FillLayout sashFormLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
			sashForm.setLayout(sashFormLayout);
			sashForm.setOrientation(SWT.VERTICAL);
			sashForm.SASH_WIDTH = 3;
			GridData sashFormLData = new GridData();
			sashFormLData.horizontalAlignment = GridData.FILL;
			sashFormLData.verticalAlignment = GridData.FILL;
			sashFormLData.grabExcessVerticalSpace = true;
			sashFormLData.grabExcessHorizontalSpace = true;
			sashForm.setLayoutData(sashFormLData);

			topNewsComposite = new Composite(sashForm, SWT.NONE);
			GridLayout topNewsCompositeLayout = new GridLayout();
			topNewsCompositeLayout.makeColumnsEqualWidth = true;
			topNewsCompositeLayout.marginHeight = 0;
			topNewsCompositeLayout.horizontalSpacing = 0;
			topNewsCompositeLayout.marginWidth = 0;
			topNewsComposite.setLayout(topNewsCompositeLayout);

			GridData topNewsCanvasLData = new GridData();
			topNewsCanvasLData.grabExcessVerticalSpace = true;
			topNewsCanvasLData.grabExcessHorizontalSpace = true;
			topNewsCanvasLData.horizontalAlignment = GridData.FILL;
			topNewsCanvasLData.verticalAlignment = GridData.FILL;
			topNewsCanvas = new Canvas(topNewsComposite, SWT.NONE);
			topNewsCanvas.setLayoutData(topNewsCanvasLData);
			topNewsCanvas.setBackground(SWTUtils.getColor(0, 255, 128));

			playerComposite = new Composite(sashForm, SWT.NONE);
			GridLayout playerCompositeLayout = new GridLayout();
			playerCompositeLayout.makeColumnsEqualWidth = true;
			playerCompositeLayout.horizontalSpacing = 0;
			playerCompositeLayout.marginHeight = 0;
			playerCompositeLayout.marginWidth = 0;
			playerCompositeLayout.verticalSpacing = 0;
			playerComposite.setLayout(playerCompositeLayout);

			bottomNewsComposite = new Composite(sashForm, SWT.NONE);
			GridLayout bottomNewsCompositeLayout = new GridLayout();
			bottomNewsCompositeLayout.makeColumnsEqualWidth = true;
			bottomNewsCompositeLayout.verticalSpacing = 0;
			bottomNewsCompositeLayout.marginWidth = 0;
			bottomNewsCompositeLayout.marginHeight = 0;
			bottomNewsCompositeLayout.horizontalSpacing = 0;
			bottomNewsComposite.setLayout(bottomNewsCompositeLayout);

			GridData bottomNewsCanvasLData = new GridData();
			bottomNewsCanvasLData.grabExcessHorizontalSpace = true;
			bottomNewsCanvasLData.grabExcessVerticalSpace = true;
			bottomNewsCanvasLData.verticalAlignment = GridData.FILL;
			bottomNewsCanvasLData.horizontalAlignment = GridData.FILL;
			bottomNewsCanvas = new Canvas(bottomNewsComposite, SWT.NONE);
			bottomNewsCanvas.setLayoutData(bottomNewsCanvasLData);
			bottomNewsCanvas.setBackground(SWTUtils.getColor(0, 128, 255));

			sashForm.setBounds(0,0, getClientArea().x,  getClientArea().y);
			sashForm.setWeights(new int[] {0, (int) (getSize().y * 0.9) , (int) (getSize().y * 0.1)});

			layout();
			timer = new Timer();
			timer.schedule( new ScrollingCompositeTask(), 1000, 100);
			bottomNewsCanvas.addListener(SWT.Paint, new ScrollingListener() );
			//			timer.schedule( new ScreenPlayerCompositeTask(), 1000, 500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private class ScreenPlayerCompositeTask extends TimerTask {
		@Override
		public void run() {
			final int [] weights = new int[3];
			Random random = new Random(System.currentTimeMillis());
			weights[0] = random.nextInt(400) + 10;
			weights[1] = random.nextInt(400) + 10;
			weights[2] = random.nextInt(400) + 10;
			System.err.println("weights[] = {"+weights[0]+", "+weights[1]+", "+weights[2]+"}");
			Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							sashForm.setWeights(weights);
						}
					}

			);
		}
	}

	private class ScrollingCompositeTask extends TimerTask {
		@Override
		public void run() {
			Display.getDefault().asyncExec(
					new Runnable() {						
						public void run() {
							bottomNewsCanvas.redraw();
						}
					}
				);
		}
	}
	
	private class ScrollingListener implements Listener {
		public void handleEvent(Event evt) {
			if ( first ){				
				int averageCharWidth = evt.gc.getFontMetrics().getAverageCharWidth();
				int spaces = bottomNewsCanvas.getSize().x / averageCharWidth;
				System.err.println("averageCharWidth " + averageCharWidth +
						", bottomNewsCanvas.getSize().x "+ bottomNewsCanvas.getSize().x  +
						", spaces : " + spaces 
					);
				for( int i = 0; i < spaces; i++ ){
					sb.append("  ");
				}
				sb.append(MSG);
				System.err.println("sb [" + sb.toString() + "]");
			} 
			else {
				String ff = sb.substring(0, 1);
				sb = sb.deleteCharAt(0).append(ff);
			} 
			first = false;			
			Point textSize = evt.gc.textExtent(sb.toString());
			evt.gc.drawText(
					sb.toString()                                      , 
					0 , 
					(bottomNewsCanvas.getSize().y - textSize.y) / 2 ,
					true
			);
		}
	}

}
