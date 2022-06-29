/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.antanysavage.sm.player.swt.vlcj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.player.DefaultMediaPlayer;

/**
 *
 * @author antonio.caccamo
 */
public class SwtEmbeddedMediaPlayer extends DefaultMediaPlayer {
    
    private static final Logger logger = LoggerFactory.getLogger(SwtEmbeddedMediaPlayer.class);
    

    private CompositeVideoSurface videoSurface;

    public SwtEmbeddedMediaPlayer(LibVlc libvlc, libvlc_instance_t instance) {
        super(libvlc, instance);
    }

    public void setVideoSurface(CompositeVideoSurface videoSurface) {
        this.videoSurface = videoSurface;
    }

    public void attachVideoSurface() {
        videoSurface.attach(libvlc, this);
        logger.warn("attachVideoSurface() ");
    }

    @Override
    protected final void onBeforePlay() {
        attachVideoSurface();
    }
}
