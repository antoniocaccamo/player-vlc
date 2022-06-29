/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.antanysavage.sm.player.util;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;

/**
 *
 * @author antonio.caccamo
 */
public class Init {
    
     private static Logger logger = LoggerFactory.getLogger(Init.class);
    
    public static void init() {
        File logs = new File(Player.SETTINGS_PATH, "logs");

        if (!logs.exists() && !logs.mkdirs()) {
            MessageDialog.openError(Display.getDefault().getActiveShell(),
                    LocaleManager.getText(LocaleManager.APP_TITLE),
                    "Can't create logs dir" + logs.getAbsolutePath()
            );
            System.exit(1);
        }

//        try {
//
//            InputStream is = Player.class.getClassLoader().getResourceAsStream(Player.LOG4J);
//            Properties p = new Properties();
//            p.load(is);
//            PropertyConfigurator.configure(p);
//
//        } catch (Exception e) {
//            MessageDialog.openError(Display.getDefault().getActiveShell(),
//                    LocaleManager.getText(LocaleManager.APP_TITLE),
//                    "Can't find log4j configuration"
//            );
//            System.exit(1);
//        }

        Player.REMOTE_VIDEO_PATH = new File(Player.SETTINGS_PATH, "remote_media");

        if (!Player.REMOTE_VIDEO_PATH.exists() && !Player.REMOTE_VIDEO_PATH.mkdirs()) {
            logger.error("can't create REMOTE_VIDEO_PATH " + Player.REMOTE_VIDEO_PATH.getAbsolutePath());
            MessageDialog.openError(Display.getDefault().getActiveShell(),
                    LocaleManager.getText(LocaleManager.APP_TITLE),
                    "Can't find create application dir : " + Player.REMOTE_VIDEO_PATH.getAbsolutePath()
            );
            System.exit(1);
        }

        if (StringUtils.isNotEmpty(StringUtils.trim(System.getProperty(Player.FTP_MODE_CHECK)))) {
            Player.FTP_MODE = true;
            if (!Player.isFTPEnabled()) {
                MessageDialog.openError(Display.getDefault().getActiveShell(),
                        LocaleManager.getText(LocaleManager.APP_TITLE),
                        "FTP not setted !"
                );
                System.exit(1);
            }

            Player.REMOTE_PROGRAM_PATH = new File(Player.SETTINGS_PATH, "remote_sequence");

            if (!Player.REMOTE_PROGRAM_PATH.exists() && !Player.REMOTE_PROGRAM_PATH.mkdirs()) {
                logger.error("can't create REMOTE_PROGRAM_PATH " + Player.REMOTE_PROGRAM_PATH.getAbsolutePath());
                MessageDialog.openError(Display.getDefault().getActiveShell(),
                        LocaleManager.getText(LocaleManager.APP_TITLE),
                        "Can't find create application dir :" + Player.REMOTE_PROGRAM_PATH.getAbsolutePath()
                );
                System.exit(1);
            }
        }
    }
}
