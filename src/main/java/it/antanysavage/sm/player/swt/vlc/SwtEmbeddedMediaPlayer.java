package it.antanysavage.sm.player.swt.vlc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.player.DefaultMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;

/**
 * Implementation of a media player for SWT.
 * <p>
 * <em>This class might get added to a future version of vlcj.</em>
 * <p>
 *
 */
public class SwtEmbeddedMediaPlayer extends DefaultMediaPlayer {

	private final Logger logger = LoggerFactory.getLogger(SwtEmbeddedMediaPlayer.class);

	private CompositeVideoSurface videoSurface;
	/**
	 * Full-screen strategy implementation, may be <code>null</code>.
	 */
	private FullScreenStrategy fullScreenStrategy;

	public SwtEmbeddedMediaPlayer(LibVlc libvlc, libvlc_instance_t instance) {
		this(libvlc, instance, null);
	}

	/**
	 * Create a new media player.
	 *
	 * @param libvlc native interface
	 * @param instance libvlc instance
	 * @param fullScreenStrategy full-screen strategy implementation
	 */
	public SwtEmbeddedMediaPlayer(LibVlc libvlc, libvlc_instance_t instance, FullScreenStrategy fullScreenStrategy) {
		super(libvlc, instance);
		this.fullScreenStrategy = fullScreenStrategy;
	}

	public void setVideoSurface(CompositeVideoSurface videoSurface) {
		this.videoSurface = videoSurface;
	}

	public void attachVideoSurface() {
		videoSurface.attach(libvlc, this);
	}

	@Override
	protected final void onBeforePlay() {
		logger.debug("onBeforePlay(): videoSurface -> {}", videoSurface);
		attachVideoSurface();
	}

	public void setEnableMouseInputHandling(boolean enable) {
		logger.debug("setEnableMouseInputHandling(enable={})", enable);
		libvlc.libvlc_video_set_mouse_input(mediaPlayerInstance(), enable ? 1 : 0);
	}

	public void setEnableKeyInputHandling(boolean enable) {
		logger.debug("setEnableKeyInputHandling(enable={})", enable);
		libvlc.libvlc_video_set_key_input(mediaPlayerInstance(), enable ? 1 : 0);
	}

}
