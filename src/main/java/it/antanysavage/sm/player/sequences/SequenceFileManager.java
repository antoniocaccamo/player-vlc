package it.antanysavage.sm.player.sequences;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Sequence;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

public class SequenceFileManager {
	
	private static Logger logger = LoggerFactory.getLogger(SequenceFileManager.class);
	
	private URL sPath = null;
	
	private SequenceFileManager() {
		
	}
	
	public static Sequence read(File file ) throws Exception {
		Sequence Sequence =   null;
		
		try {
			FileReader fr = new FileReader(file);
			Sequence = (Sequence) Unmarshaller.unmarshal(Sequence.class, fr);
		} catch (Exception e) {
			logger.error("error reading sequence file ["+ file.getAbsolutePath() +"]", e);
			throw e;
		}
		
		return Sequence;
		
	}
	
	 
	public static Sequence read(String path ) throws Exception {
//		Sequence sequence =   null;
//		try {
//			FileReader fr = new FileReader(path);
//			sequence = (Sequence) Unmarshaller.unmarshal(Sequence.class, fr);
//			
//		} catch (Exception e) {
//			logger.error("error occurred ", e);
//		}
//		return sequence;
		File f = new File(path);
		return read (f);
	}
	
	
	
	public static Sequence read(URL url ) throws Exception {
		Sequence sequence =   null;
		
//		try {
//			File f = new File(url.toURI());
//			FileReader fr = new FileReader(f);
//			sequence = (Sequence) Unmarshaller.unmarshal(Sequence.class, fr);
//			fr.close();
//		} catch (Exception e) {
//			logger.error("error occurred ", e);
//		}
		File f = new File(url.toURI());
		return read (f);
		
	}
	
	public static void write ( Program smp ){
		try {
			File f = new File(smp.getFilepath().getPath());
			FileWriter fw = new FileWriter(f);
			Sequence sequence = Utils.getSequence(smp);
			Marshaller.marshal(sequence, fw)	;
			fw.close();
		} catch (Exception e) {
			MessageDialog.openError(
					Display.getDefault().getActiveShell(), 
					LocaleManager.getText(LocaleManager.APP_TITLE), 								
					"Error writing sequence : " + e.getLocalizedMessage()
			);
			logger.error("error occurred ", e);
		}
	}
	
	public static void main(String[] args) {
		URL url = ClassLoader.getSystemResource("Sequence/test.xml");
		try {
			Sequence sequence = SequenceFileManager.read(url);
			System.out.println(sequence);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
