package it.antanysavage.sm.player.swt.vlc;

import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.LibVlcFactory;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.linux.LinuxVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.mac.MacVideoSurfaceAdapter;
import uk.co.caprica.vlcj.player.embedded.videosurface.windows.WindowsVideoSurfaceAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

import java.text.MessageFormat;
import java.util.Arrays;
import uk.co.caprica.vlcj.medialist.MediaList;

public class SwtMediaPlayerFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(SwtMediaPlayerFactory.class);
    
    /**
     * Workaround for running under Java7 on Linux.
     * <p>
     * Without this (unless other client configuration changes have already been made) an
     * unsatisfied link error will likely be thrown by the JVM when an attempt is made to play
     * video in an embedded media player.
     */
    static {
        // With recent VLC/JDK it seems necessary to do this (it will be silently ignored on non-
        // X platforms) - it can however cause problems if using the JVM splash-screen options
        // Ultimately this needs more investigation, it may no longer be necessary to do this with
        // VLC 3.0.0+
        //
        // Without this, it is also possible that opening a JavaFX FileChooser will cause a fatal
        // JVM crash
        String initX = System.getProperty("VLCJ_INITX");
        logger.debug("initX={}", initX);
        if(!"no".equalsIgnoreCase(initX)) {
            LibXUtil.initialise();
        }
    }
    
    /**
     * Help text if libvlc failed to load and initialise.
     */
    private static final String PLUGIN_PATH_HELP =
            "Failed to initialise libvlc.\n\n" +
            "This is most often caused either by an invalid vlc option being passed when creating a MediaPlayerFactory or by libvlc being unable to locate the required plugins.\n\n" +
            "If libvlc is unable to locate the required plugins the instructions below may help:\n\n" +
            "In the text below <libvlc-path> represents the name of the directory containing \"{0}\" and \"{1}\" and <plugins-path> represents the name of the directory containing the vlc plugins...\n\n" +
            "For libvlc to function correctly the vlc plugins must be available, there are a number of different ways to achieve this:\n" +
            " 1. Make sure the plugins are installed in the \"<libvlc-path>/{2}\" directory, this should be the case with a normal vlc installation.\n" +
            " 2. Set the VLC_PLUGIN_PATH operating system environment variable to point to \"<plugins-path>\".\n\n" +
            "More information may be available in the log.\n\n";
    
    /**
     * Native library interface.
     */
    protected final LibVlc libvlc;
    
    /**
     * Native library instance.
     */
    protected final libvlc_instance_t instance;
    
    /**
     * True when the factory has been released.
     */
    private boolean released;
    
    /**
     * Create a new media player factory.
     * <p>
     * If you want to enable logging or synchronisation of the native library interface you must use
     * {@link #SwtMediaPlayerFactory(LibVlc)} and {@link LibVlcFactory}.
     * <p>
     * This factory constructor will enforce a minimum required native library version check - if a
     * suitable native library version is not found a RuntimeException will be thrown.
     * <p>
     * If you do not want to enforce this version check, use one of the other constructors that
     * accepts a LibVlc instance that you obtain from the {@link LibVlcFactory}.
     */
    public SwtMediaPlayerFactory() {
        this(new String[] {});
    }
    
    /**
     * Create a new media player factory.
     * <p>
     * If you want to enable logging or synchronisation of the native library interface you must use
     * {@link #SwtMediaPlayerFactory(LibVlc)} and {@link LibVlcFactory}.
     * <p>
     * This factory constructor will enforce a minimum required native library version check - if a
     * suitable native library version is not found, a RuntimeException will be thrown.
     * <p>
     * If you do not want to enforce this version check, use one of the other constructors that
     * accepts a LibVlc instance that you obtain from the {@link LibVlcFactory}.
     * <p>
     * Most initialisation arguments may be gleaned by invoking <code>"vlc -H"</code>.
     *
     * @param libvlcArgs initialisation arguments to pass to libvlc
     */
    public SwtMediaPlayerFactory(String... libvlcArgs) {
        this(LibVlcFactory.factory().atLeast("2.1.0").create(), libvlcArgs);
    }
    
    /**
     * Create a new media player factory.
     * <p>
     * Use {@link LibVlcFactory} to get a reference to the native library.
     *
     * @param libvlc interface to the native library
     */
    public SwtMediaPlayerFactory(LibVlc libvlc) {
        this(libvlc, new String[] {});
    }
    
    /**
     * Create a new media player factory.
     * <p>
     * Use {@link LibVlcFactory} to get a reference to the native library.
     *
     * @param libvlc interface to the native library
     * @param libvlcArgs initialisation arguments to pass to libvlc
     */
    public SwtMediaPlayerFactory(LibVlc libvlc, String... libvlcArgs) {
        logger.debug("MediaPlayerFactory(libvlc={},libvlcArgs={})", libvlc, Arrays.toString(libvlcArgs));
        // JNA will look for the libvlc shared library here (and also libvlccore)...
        logger.debug("jna.library.path={}", System.getProperty("jna.library.path"));
        // Convenience
        if(libvlcArgs == null) {
            libvlcArgs = new String[0];
        }
        // Ordinarily libvlc will look for it's plugins in a directory named "vlc/plugins" relative
        // to the directory where libvlccore is loaded from, this can be overridden by explicitly
        // specifying the "VLC_PLUGIN_PATH" system property (although this should not be necessary)
        String vlcPluginPath = System.getProperty("VLC_PLUGIN_PATH");
        logger.debug("VLC_PLUGIN_PATH={}", vlcPluginPath);
        this.libvlc = libvlc;
        this.instance = libvlc.libvlc_new(libvlcArgs.length, libvlcArgs);
        logger.debug("instance={}", instance);
        if(instance == null) {
            logger.error("Failed to initialise libvlc");
            String msg = MessageFormat.format(PLUGIN_PATH_HELP, new Object[] {RuntimeUtil.getLibVlcName(), RuntimeUtil.getLibVlcCoreName(), RuntimeUtil.getPluginsDirectoryName()});
            throw new RuntimeException(msg);
        }
        // Cache the equalizer static data
        /*
        equalizerAvailable = LibVlcVersion.getVersion().atLeast(new Version("2.2.0"));
        logger.debug("equalizerAvailable={}", equalizerAvailable);
        if(equalizerAvailable) {
        equalizerBandFrequencies = createEqualizerBandFrequencies();
        equalizerPresetNames = createEqualizerPresetNames();
        }
        else {
        equalizerBandFrequencies = null;
        equalizerPresetNames = null;
        }
        */
    }
    
    /**
     * Create a new embedded media player.
     * <p>
     * Full-screen will not be available, to enable full-screen support see
     * {@link #newEmbeddedMediaPlayer(FullScreenStrategy)}, or use an alternate mechanism to
     * manually set full-screen if needed.
     *
     * @return media player instance
     */
    public SwtEmbeddedMediaPlayer newEmbeddedMediaPlayer() {
        logger.debug("newEmbeddedMediaPlayer()");
        return newEmbeddedMediaPlayer(null);
    }
    
    /**
     * Create a new play-list media player.
     *
     * @return media player instance
     */
    public SwtEmbeddedMediaListPlayer newEmbeddedMediaListPlayer(){
        return new SwtEmbeddedMediaListPlayer(libvlc, instance);
    }
    
    
    /**
     * Create a new media list for a play-list media player.
     *
     * @return media list instance
     */
    public MediaList newMediaList() {
        logger.debug("newMediaList()");
        return new MediaList(libvlc, instance);
    }
    
    /**
     * Create a new embedded media player.
     *
     * @param fullScreenStrategy full screen implementation, may be <code>null</code>
     * @return media player instance
     */
    public SwtEmbeddedMediaPlayer newEmbeddedMediaPlayer(FullScreenStrategy fullScreenStrategy) {
        logger.debug("newEmbeddedMediaPlayer(fullScreenStrategy={})", fullScreenStrategy);
        return new SwtEmbeddedMediaPlayer(libvlc, instance, fullScreenStrategy);
    }
    
    /**
     * Create a new video surface for a Composite.
     *
     * @param composite composite
     * @return video surface
     */
    public CompositeVideoSurface newVideoSurface(Composite composite) {
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
        CompositeVideoSurface videoSurface = new CompositeVideoSurface(composite, videoSurfaceAdapter);
        logger.debug("videoSurface={}", videoSurface);
        return videoSurface;
    }
    
    /**
     * Release the native resources associated with this factory.
     */
    public void release() {
        logger.debug("release()");
        if(!released) {
            if(instance != null) {
                libvlc.libvlc_release(instance);
            }
            released = true;
        }
    }
}
