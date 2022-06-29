/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.antanysavage.sm.player.swt.vlcj;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

/**
 *
 * @author antonio.caccamo
 */
public class VlcjSwt {
    
    
    private static final String MRL = "C:\\Users\\antonio.caccamo\\Videos\\rosas.mp4";
    
    public static void main (String[] args) {
        
        new NativeDiscovery().discover();
        LibXUtil.initialise();
        
        
        System.setProperty("sun.awt.xembedserver", "true");
        
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("vlcj SWT Demo");
        
        GridLayout gridLayout = new GridLayout(1, true);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        
        shell.setLayout(gridLayout);
        
        Color black = display.getSystemColor(SWT.COLOR_BLACK);
        
        Composite main = new Composite(shell, SWT.NONE);
        main.setLayout(gridLayout);
        main.setLayoutData(gridData);
        main.setBackground(black);
        
        Composite videoSurface = new Composite(main, SWT.EMBEDDED | SWT.NO_BACKGROUND);
        videoSurface.setLayout(gridLayout);
        videoSurface.setLayoutData(gridData);
        videoSurface.setBackground(black);
        
        LibVlc libvlc = LibVlc.INSTANCE;
        libvlc_instance_t instance = libvlc.libvlc_new(0, null);
        
        SwtEmbeddedMediaPlayer mediaPlayer = new SwtEmbeddedMediaPlayer(libvlc, instance);
        mediaPlayer.setVideoSurface(new CompositeVideoSurface(videoSurface));
        
        
        shell.setLocation(100, 100);
        shell.setSize(800, 450);
        
        shell.open();
        
        mediaPlayer.playMedia(MRL);
        
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        
        display.dispose();
    }
    
}
