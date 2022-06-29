package it.antanysavage.sm.player.swt.vlcj;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurface;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.player.embedded.videosurface.linux.LinuxVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.mac.MacVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.windows.WindowsVideoSurfaceAdapter;

/**
 * Implementation of a video surface component that uses an SWT Composite.
 * <p>
 * <em>This class might get added to a future version of vlcj.</em>
 */
public class CompositeVideoSurface extends VideoSurface {
    
    private static final Logger logger = LoggerFactory.getLogger(CompositeVideoSurface.class);
    
    private final Composite composite;
    
    public CompositeVideoSurface(Composite composite) {
        super(CompositeVideoSurface.newVideoSurfaceAdapter(composite));
        this.composite = composite;
    }
    
    @Override
    public void attach(LibVlc libvlc, MediaPlayer mediaPlayer) {
        long componentId = getHandle(composite);
        videoSurfaceAdapter.attach(libvlc, mediaPlayer, componentId);
    }
    
    private long getHandle(Composite c) {
        long handle = 0l;
        Field _viewField;
        Field _idField;
        try {
            if (RuntimeUtil.isMac()) {
                _viewField = Control.class.getDeclaredField("view");
                Object view = _viewField.get(c);
                Class<?> idClass = Class.forName("org.eclipse.swt.internal.cocoa.id");
                _idField = idClass.getDeclaredField("id");
                handle = _idField.getLong(view);
                
            } else if (RuntimeUtil.isNix()) {
                _idField = Composite.class.getDeclaredField("embeddedHandle");
                handle = _idField.getLong(c);
            } else {
                _idField = Control.class.getDeclaredField("handle");
                handle = _idField.getLong(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return handle;
    }
    
    
    private static VideoSurfaceAdapter newVideoSurfaceAdapter(Composite composite) {
        logger.debug("newVideoSurface(canvas={})", composite);
        VideoSurfaceAdapter videoSurfaceAdapter;
        if(RuntimeUtil.isNix()) {
            videoSurfaceAdapter = new LinuxVideoSurfaceAdapter();
        }
        else if(RuntimeUtil.isWindows()) {
            videoSurfaceAdapter = new WindowsVideoSurfaceAdapter();
        }
        else if(RuntimeUtil.isMac()) {
            videoSurfaceAdapter = new MacVideoSurfaceAdapter();
        }
        else {
            throw new RuntimeException("Unable to create a media player - failed to detect a supported operating system");
        }
        logger.debug("videoSurfaceAdapter={}", videoSurfaceAdapter);
        return videoSurfaceAdapter;
    }
}
