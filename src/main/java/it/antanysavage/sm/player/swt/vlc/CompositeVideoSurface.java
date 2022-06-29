package it.antanysavage.sm.player.swt.vlc;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurface;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.lang.reflect.Field;

/**
 * Implementation of a video surface component that uses an SWT Composite.
 * <p>
 * <em>This class might get added to a future version of vlcj.</em>
 */
public class CompositeVideoSurface extends VideoSurface {

	private final Composite composite;

	public CompositeVideoSurface(Composite composite, VideoSurfaceAdapter videoSurfaceAdapter) {
		super(videoSurfaceAdapter);
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
}
