/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package it.antanysavage.sm.player.swt.vlc;

import it.antanysavage.sm.player.email.EmailSender;
import it.antanysavage.sm.player.swt.players.VLCVideoPlayer;
import it.antanysavage.sm.player.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 *
 * @author antonio.caccamo
 */
public class PlayerVLCEventHandler extends MediaPlayerEventAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(PlayerVLCEventHandler.class);
    
    private final VLCVideoPlayer vlcvp;
    
    public PlayerVLCEventHandler( VLCVideoPlayer vlcvp ) {
        this.vlcvp = vlcvp;
    }
    
//    @Override
//    public void playing(MediaPlayer mediaPlayer) {
//        float duration = ( (float) mediaPlayer.getLength() ) / 1000 ;
//        logger.info("duration {} ", duration);
//        this.vlcvp.getCurrent().setDuration(duration);
//    }
    
    @Override
    public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
        float ctime = 	 ((float) newTime ) / 1000 ;
        vlcvp.getPlayerMaster().getScreenManager().updateVideoProgressBarSelection(
                ctime,
                vlcvp.getCurrent().getDuration()
        );
        
    }
    
//    public void error(MediaPlayer mediaPlayer) {
//        logger.error("error occurred when playing media {}" , Utils.getMediaDescription(vlcvp.getCurrent()));
//        EmailSender.getInstance().sendMediaError(vlcvp.getCurrent());
//        vlcvp.next();
//    };
//    
//    public void finished(MediaPlayer mediaPlayer) {
//        logger.info("finished playing media {}" , Utils.getMediaDescription(vlcvp.getCurrent()));
//        vlcvp.next();
//    };
    
}
