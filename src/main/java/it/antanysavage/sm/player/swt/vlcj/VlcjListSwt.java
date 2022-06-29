/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.antanysavage.sm.player.swt.vlcj;


import static it.antanysavage.sm.player.wwo.LocationSearch.logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.list.DefaultMediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

/**
 *
 * @author antonio.caccamo
 */
public class VlcjListSwt {
    
    
    private static final Logger logger = LoggerFactory.getLogger(VlcjListSwt.class);
    
    
    
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
        
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
        
        
        MediaListPlayer mediaListPlayer = new DefaultMediaListPlayer(libvlc, instance);
        
        mediaListPlayer.addMediaListPlayerEventListener(new MediaListPlayerEventAdapter() {
            @Override
            public void nextItem(MediaListPlayer mediaListPlayer, libvlc_media_t item, String itemMrl) {
                logger.info("nextItem()={}" , itemMrl);
            }
        });
        
        mediaListPlayer.setMediaPlayer(mediaPlayer);
        mediaPlayer.attachVideoSurface();
        
        
        MediaList mediaList = mediaPlayerFactory.newMediaList();
        mediaList.addMedia(MRL);
        mediaList.addMedia("C:\\Users\\antonio.caccamo\\Videos\\sample-mov.mov");
        mediaList.addMedia(MRL);
        
        mediaListPlayer.setMediaList(mediaList);
        
        
        videoSurface.addListener(SWT.Resize,  new Listener() {
            public void handleEvent (Event e) {
                Rectangle rect = videoSurface.getClientArea();
                logger.info("VLC media player resized {}", rect);
                String ar = String.format("%d:%d", rect.width, rect.height);
                mediaPlayer.setAspectRatio(ar);
                logger.info("VLC media player AR {}", ar);
            }
        });
        
        videoSurface.addListener( SWT.Move , new Listener() {
            public void handleEvent (Event e) {
                Rectangle rect = videoSurface.getClientArea();
                logger.info("VLC media player resized {}", rect);
                String ar = String.format("%d:%d", rect.width, rect.height);
                mediaPlayer.setAspectRatio(ar);
                logger.info("VLC media player AR {}", ar);
            }
        });
        
        shell.setLocation(100, 100);
        shell.setSize(800, 450);
        
        shell.open();
        
        mediaListPlayer.play();
        
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        
        display.dispose();
    }
    
}
