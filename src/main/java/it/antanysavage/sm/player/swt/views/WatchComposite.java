package it.antanysavage.sm.player.swt.views;

import it.antanysavage.sm.player.ScreenManagerComposite;
import it.antanysavage.sm.player.util.SWTUtils;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;
import java.util.Calendar;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


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
public class WatchComposite extends Composite implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(WatchComposite.class);


	private static Color black  = new Color(Display.getCurrent(), 0, 0, 0);
	private static Color yellow = new Color(Display.getCurrent(), 255, 255, 0);


	private ScreenManagerComposite screenManagerComposite;
	private Canvas timeCanvas;
	private Canvas dateCanvas;

	private FontData timeFontData;
	private FontData dateFontData;
	
	private RGB     timeRGB;
	private RGB     dateRGB;  
	private File    backgroundImageFile = null;


	public WatchComposite(final ScreenManagerComposite screenManagerComposite, Composite parent) {
		super(parent, SWT.NONE);
		this.screenManagerComposite = screenManagerComposite;

		GridLayout thisLayout = new GridLayout(1,false);
		setLayout(thisLayout);
		getShell().setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		GridData timeCanvasLData = new GridData();
		timeCanvasLData.grabExcessHorizontalSpace = true;
		timeCanvasLData.grabExcessVerticalSpace = true;
		timeCanvasLData.horizontalAlignment = GridData.FILL;
		timeCanvasLData.verticalAlignment = GridData.FILL;
		timeCanvas = new Canvas(this, SWT.NONE );
		timeCanvas.setLayoutData(timeCanvasLData);
		
		timeCanvas.setBackgroundMode(SWT.INHERIT_DEFAULT);
		timeRGB = screenManagerComposite.getPlayerSetting().getTimeLabelFontColor();
		timeCanvas.setForeground( SWTUtils.getColor(timeRGB) );
		// time canvas paint listener
		timeCanvas.addPaintListener( 
				new PaintListener() {					
					public void paintControl(PaintEvent evt) {
						String text = Utils.geWatchTime( Calendar.getInstance());
						// font
						if ( timeFontData == null || ! timeFontData.equals( screenManagerComposite.getPlayerSetting().getTimeLabelFontData() ) ){
							timeFontData = screenManagerComposite.getPlayerSetting().getTimeLabelFontData();
							Font font = new Font(Display.getCurrent(), timeFontData );							
							timeCanvas.setFont(font);
							//	previosFont.dispose();
							logger.info("watch time font changed to : " + timeFontData );
						}
						// color
						RGB rgb = screenManagerComposite.getPlayerSetting().getTimeLabelFontColor();
						if ( ! timeRGB.equals(rgb) ) {
							timeRGB = rgb;
							timeCanvas.setForeground( SWTUtils.getColor(timeRGB) );
						}
						// draw time
						
						if ( WatchComposite.this.screenManagerComposite.isRunning() 
								|| !
								WatchComposite.this.screenManagerComposite.isImageWindowWhenNotActive() )  {
							

							Point textSize = evt.gc.textExtent(text);


							evt.gc.drawText(
									text                                      , 
									(timeCanvas.getSize().x - textSize.x) / 2 , 
									(timeCanvas.getSize().y - textSize.y) / 2 ,
									true
									);
						}
						
						
					}
				}
		);

		GridData dateCanvasLData = new GridData();
		dateCanvasLData.grabExcessHorizontalSpace = true;
		dateCanvasLData.horizontalAlignment = GridData.FILL;
		dateCanvasLData.verticalAlignment = GridData.FILL;
		dateCanvasLData.grabExcessVerticalSpace = true;
		dateCanvas = new Canvas(this, SWT.NONE);
		dateCanvas.setLayoutData(dateCanvasLData);		
		dateRGB = screenManagerComposite.getPlayerSetting().getDateLabelFontColor();
		dateCanvas.setForeground(SWTUtils.getColor( dateRGB ));		
		dateCanvas.setBackgroundMode(SWT.INHERIT_DEFAULT);
		// date canvas paint listener
		dateCanvas.addPaintListener( 
				new PaintListener() {					
					public void paintControl(PaintEvent evt) {
						String text = Utils.getWatchDate( Calendar.getInstance());
						if ( dateFontData == null || ! dateFontData.equals( screenManagerComposite.getPlayerSetting().getDateLabelFontData() ) ){
							dateFontData = screenManagerComposite.getPlayerSetting().getDateLabelFontData();
							Font font = new Font(Display.getCurrent(), dateFontData );							
							dateCanvas.setFont(font);
							logger.info("watch date font changed to : " + dateFontData );
							//							previosFont.dispose();
						}
						// color
						RGB rgb = screenManagerComposite.getPlayerSetting().getDateLabelFontColor();
						if ( ! dateRGB.equals(rgb) ) {
							dateRGB = rgb;
							dateCanvas.setForeground( SWTUtils.getColor(dateRGB) );
						}
						// draw date
						if ( WatchComposite.this.screenManagerComposite.isRunning() 
								|| !
								WatchComposite.this.screenManagerComposite.isImageWindowWhenNotActive() )  {
							Point textSize = evt.gc.textExtent(text);						
							evt.gc.drawText(
									text                                      , 
									(dateCanvas.getSize().x - textSize.x) / 2 , 
									(dateCanvas.getSize().y - textSize.y) / 2 ,
									true
									);
						}

					}
				}
		);
		// watch composite paint listener
		addPaintListener(
				new PaintListener() {				
					public void paintControl(PaintEvent arg0) {
						File f = screenManagerComposite.getPlayerSetting().getWatchImageFile();
						if ( f != null ){							
							if ( ! f.equals(backgroundImageFile) ){
								logger.info("changing watch background .. " + f.getAbsolutePath());
								Image prevImage = getBackgroundImage();
								backgroundImageFile = f;
								Image image = SWTUtils.getImage(backgroundImageFile.getAbsolutePath());
								Point clientArea = new Point (getShell().getClientArea().width,getShell().getClientArea().height);
								Point imagePoint = new Point( image.getBounds().width,  image.getBounds().height);
								if ( ! clientArea.equals(imagePoint) ) {
									Image resized = SWTUtils.resizeImage(image, clientArea.x, clientArea.y);
									setBackgroundImage( resized);
									if ( ! image.isDisposed() ) {
										image.dispose();
									}
								}
								if ( prevImage!= null) {
									prevImage.dispose();
								}
							}
						}else {
							setBackgroundImage(null);
							setBackground(black);
						}
					}
				}
		);
		
		
	}


	public void run() {

		while( true ) {
			try {
				Display.getDefault().asyncExec(
						new Runnable() {							
							public void run() {
								redraw();
								timeCanvas.redraw();
								dateCanvas.redraw();								
							}
						}
				);				
				Thread.sleep(1000);				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}    
	}

}
