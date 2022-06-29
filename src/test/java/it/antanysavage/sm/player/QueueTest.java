/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.antanysavage.sm.player;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.SequenceFileManager;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Sequence;
import it.antanysavage.sm.player.util.Init;
import java.util.Locale;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * @author antonio.caccamo
 */
public class QueueTest {

    private static final Logger logger = LoggerFactory.getLogger(QueueTest.class);

    public static void main(String[] args) {

        Init.init();
        
        LocaleManager.changeLocale(Locale.ITALIAN.getCountry());
                

        FileDialog fileDialog = new FileDialog( new Shell(Display.getDefault()) , SWT.NONE);
        fileDialog.setFilterPath(System.getProperty("user.dir"));
        fileDialog.setFilterExtensions(new String[]{"*.xseq"});
        String sequenceFile = fileDialog.open();
        if (sequenceFile != null && !"".equals(sequenceFile)) {

            try {
                Sequence sequence = SequenceFileManager.read(sequenceFile);
                Program pgm = new Program(sequence, sequenceFile);
                
                logger.info("pgm.getQueueLinkedList() : " + pgm.getQueueLinkedList());

            } catch (Exception e) {

                logger.error("error occurred ", e);
            }

        }

    }

}
