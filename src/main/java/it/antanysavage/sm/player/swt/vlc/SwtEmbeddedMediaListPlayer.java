/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.antanysavage.sm.player.swt.vlc;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.player.list.DefaultMediaListPlayer;

/**
 *
 * @author antonio.caccamo
 */
public class SwtEmbeddedMediaListPlayer extends DefaultMediaListPlayer{
    
    public SwtEmbeddedMediaListPlayer(LibVlc libvlc, libvlc_instance_t instance) {
        super(libvlc, instance);
    }
    
}
