package it.antanysavage.sm.player.swt.views;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;

public class WebUrlComposite extends Composite {
	
	private static final Logger logger = LoggerFactory.getLogger(WebUrlComposite.class);	

	private Browser browser;
	
	public WebUrlComposite(Composite parent) {
		this(parent, SWT.NONE);
	}
	
	public Browser getBrowser() {
		return browser;
	}
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public WebUrlComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		browser = new Browser(this, SWT.NONE);
		browser.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		browser.addProgressListener( new ProgressAdapter() {
			
			@Override
			public void changed(ProgressEvent arg0) {
				new  Runnable() {					
					@Override
					public void run() {
						browser.setVisible(false);
						logger.info("browser.getVisible() : " + browser.getVisible());
					}
				};
			}
			
			@Override
			public void completed(ProgressEvent arg0) {
				browser.setVisible(true);
				logger.info("browser.getVisible() : " + browser.getVisible());				
			}
			
		}); 
		
		Player.moveMouse(getShell());
		Player.moveMouse(browser);
		Player.moveMouse(browser.getShell());
	}

	public void navigateTo(final String url) {	
		Display.getDefault().asyncExec(
				new Runnable() {							
					public void run() {
						browser.setVisible(false);						
						browser.setUrl(url);
						logger.info("setUrl("+url+")");
						logger.info("browser.getVisible() : " + browser.getVisible());
					}
				}
				);	
	}
	
	public void navigateTo(final String url,final String weatherLatlng) {	
		setLang( LocaleManager.getLocale().getLanguage() );
		Display.getDefault().asyncExec(
				new Runnable() {							
					public void run() {
						String surl = new StringBuffer(url).append("?latlng=").append(weatherLatlng).
								append("&lang=").append(LocaleManager.getLocale().getLanguage()).toString();
						browser.setUrl(surl);
						logger.info("browser.setUrl("+surl+")");						
					}
				}
				);	
	}

	public void setLang(final String lang) {	
		Display.getDefault().asyncExec(
				new Runnable() {							
					public void run() {
						browser.execute("setLang('"+lang+"')");
						logger.info("setLang('"+lang+"')");	
					}
				}
				);	
	}
	
	private void setCoords(final String slat, final String slng) {	
		Display.getDefault().asyncExec(
				new Runnable() {							
					public void run() {
						browser.execute("setCoords('" + slat + "','" + slng + "')");
						logger.info("setCoords('" + slat + "','" + slng + "')");	
					}
				}
				);	
	}

	public void setCoords(String weatherLatlng) {
		String ss[] = StringUtils.split(weatherLatlng, ",");
		setCoords(ss[0], ss[1]);
	}
	
}
