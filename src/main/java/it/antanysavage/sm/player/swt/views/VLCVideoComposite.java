package it.antanysavage.sm.player.swt.views;

import it.antanysavage.sm.player.email.EmailSender;
import it.antanysavage.sm.player.swt.players.VLCVideoPlayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.antanysavage.sm.player.swt.vlc.SwtEmbeddedMediaPlayerComponent;
import it.antanysavage.sm.player.util.Utils;
import uk.co.caprica.vlcj.player.MediaPlayer;

public class VLCVideoComposite extends SwtEmbeddedMediaPlayerComponent {
    
    private static final Logger logger = LoggerFactory.getLogger(VLCVideoComposite.class);
    
    protected static Color black  = new Color(Display.getCurrent(), 0, 0, 0);
    
//    private VLCVideoPlayer vlcVideoPlayer;
    
    public VLCVideoComposite(Composite parent) {
        super(parent, SWT.NONE);                
        
        addListener(SWT.Resize,  new Listener() {
            public void handleEvent (Event e) {
                Rectangle rect = getClientArea();
                logger.info("VLC media player resized {}", rect);
                String ar = String.format("%d:%d", rect.width, rect.height);
                getMediaPlayer().setAspectRatio(ar);
                logger.info("VLC media player AR {}", ar);
            }
        });
        
    }

//    public void setVLCVideoPlayer(VLCVideoPlayer  vlcVideoPlayer) {
//        this.vlcVideoPlayer = vlcVideoPlayer;
//    }
//    
//    @Override
//    public void playing(MediaPlayer mediaPlayer) {
//        float duration = (float) (mediaPlayer.getLength() / 1000);
//        logger.info("duration {} ", duration);
//        this.vlcVideoPlayer.getCurrent().setDuration(duration);
//    }
//    
//    @Override
//    public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
//        float ctime = 	 (float) (newTime / 1000) ;
//        vlcVideoPlayer.getPlayerMaster().getScreenManager().updateVideoProgressBarSelection(
//                ctime,
//                vlcVideoPlayer.getCurrent().getDuration()
//        );
//        
//    }
//    
//    public void error(MediaPlayer mediaPlayer) {
//        logger.error("error occurred when playing media {}" , Utils.getMediaDescription(vlcVideoPlayer.getCurrent()));
//        EmailSender.getInstance().sendMediaError(vlcVideoPlayer.getCurrent());
//        vlcVideoPlayer.next();
//    };
//
//    @Override
//    public void finished(MediaPlayer mediaPlayer) {
//        logger.info("finished playing media {}" , Utils.getMediaDescription(vlcVideoPlayer.getCurrent()));
//        vlcVideoPlayer.next();
//    }
    
    
    
    
}
