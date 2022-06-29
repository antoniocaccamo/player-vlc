package it.antanysavage.sm.player.swt.players;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.antanysavage.sm.player.IMaster;
import it.antanysavage.sm.player.email.EmailSender;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.swt.views.VLCVideoComposite;
import it.antanysavage.sm.player.swt.vlc.PlayerVLCEventHandler;
import it.antanysavage.sm.player.swt.vlc.SwtEmbeddedMediaListPlayer;
import it.antanysavage.sm.player.swt.vlc.SwtMediaPlayerFactory;
import uk.co.caprica.vlcj.binding.internal.libvlc_state_t;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

public class VLCVideoPlayer extends IPlayer{
    
    private static final Logger logger = LoggerFactory.getLogger(VLCVideoPlayer.class);
    
    private VLCVideoComposite vlcVideoComposite;
    
    private       PlayerVLCEventHandler playerVLCEventHandler;
    private final SwtEmbeddedMediaListPlayer mediaListPlayer;
    private final MediaList mediaList;
    
    public VLCVideoPlayer(IMaster playerManager, VLCVideoComposite composite) {
        super();
        SwtMediaPlayerFactory smpf = new SwtMediaPlayerFactory();
        this.playerMaster      = playerManager;
        this.vlcVideoComposite = composite;
        
        this.mediaListPlayer   = smpf.newEmbeddedMediaListPlayer();
        this.mediaList         = smpf.newMediaList();
        this.mediaListPlayer.setMediaPlayer( vlcVideoComposite.getMediaPlayer() );
        this.mediaListPlayer.setMediaList(this.mediaList);
        this.vlcVideoComposite.getMediaPlayer().attachVideoSurface();
        
        this.mediaListPlayer.addMediaListPlayerEventListener( new MediaListPlayerEventAdapter() {
            
            @Override
            public void mediaDurationChanged(MediaListPlayer mediaListPlayer, long newDuration) {
                logger.debug("mediaListPlayer.currentMrl()={}, newDuration={}", mediaListPlayer.currentMrl(), newDuration);
                float duration = ( (float) newDuration ) / 1000 ;        
                getCurrent().setDuration(duration);
            }
            
            @Override
            public void mediaStateChanged(MediaListPlayer mediaListPlayer, int newState) {
                final libvlc_state_t state = libvlc_state_t.state(newState);
                logger.warn(
                        "mediaListPlayer.currentMrl()={}, libvlc_state_t.state(newState)={}",
                        mediaListPlayer.currentMrl(),  state
                );
                switch(state) {
                    
                    case libvlc_Playing:
                        
                        logger.info(
                                "mediaListPlayer.currentMrl()={},  libvlc_state_t.state(newState)={}",
                                mediaListPlayer.currentMrl(),  state
                        );
                        break;
                        
                    case libvlc_Error:
                        logger.error(
                                "mediaListPlayer.currentMrl()={},  libvlc_state_t.state(newState)={}",
                                mediaListPlayer.currentMrl(),  state
                        );
                        stop();
                        
                        EmailSender.getInstance().sendMediaError( getCurrent() );
                        
                        next();
                        
                    case libvlc_Ended :
                        logger.info(
                                "mediaListPlayer.currentMrl()={},  libvlc_state_t.state(newState)={}",
                                mediaListPlayer.currentMrl(),  state
                        );
                        stop();
                        next();
                }
            }
            
        });
        
        this.vlcVideoComposite.getMediaPlayer().addMediaPlayerEventListener( new PlayerVLCEventHandler(this) );
        
        this.mediaListPlayer.setMode(MediaListPlayerMode.LOOP);
        
        
    }
    
    @Override
    public void setMedia(Media movie) {
        super.setMedia(movie); //To change body of generated methods, choose Tools | Templates.
        this.mediaList.clear();
        this.mediaList.addMedia(current.getFile().getAbsolutePath());
    }
    
    @Override
    public void play() {
        try {
            this.mediaListPlayer.play();
            
        } catch (Exception e ){
            logger.error("error occurred", e);
            next();
        }
    }
    
    @Override
    public void pause() {
        this.mediaListPlayer.pause();
    }
    
    @Override
    public void resume() {
        this.mediaListPlayer.play();
    }
    
    @Override
    public void stop() {
        this.mediaListPlayer.stop();
    }
    
    
}
