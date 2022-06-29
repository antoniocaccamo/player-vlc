package it.antanysavage.sm.player.swt.vlc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

public class VlcJ {
    private static final Logger logger = LoggerFactory.getLogger(VlcJ.class);
    public static void main(String[] args) {
        new NativeDiscovery().discover();
        LibXUtil.initialise();
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("VlcJ Video Player");

        GridLayout gridLayout = new GridLayout(1, true);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;

        shell.setLayout(gridLayout);

        SwtEmbeddedMediaPlayerComponent player = new SwtEmbeddedMediaPlayerComponent(shell, SWT.NONE);

        shell.setLocation(100, 100);
        shell.setSize(800, 450);
        shell.open();

        player.getMediaPlayer().playMedia("https://archive.org/download/BigBuckBunny_328/BigBuckBunny_512kb.mp4");

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        player.release(true);
        display.dispose();
    }
}
