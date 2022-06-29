package it.antanysavage.sm.player.news;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.news.model.Diary;
import it.antanysavage.sm.player.news.schema.Infos;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

public class NewsFileManager {
	
	private static Logger logger = LoggerFactory.getLogger(NewsFileManager.class);
	
	private URL sPath = null;
	
	
	public static Infos read(File file ) throws Exception {
		Infos Sequence =   null;
		
		try {
			FileReader fr = new FileReader(file);
			Sequence = (Infos) Unmarshaller.unmarshal(Infos.class, fr);
		} catch (Exception e) {
			logger.error("error reading diary file ["+ file.getAbsolutePath() +"]", e);
			throw e;
		}
		
		return Sequence;
		
	}
	
	 
	public static Infos read(String path ) throws Exception {
		File f = new File(path);
		return read (f);
	}
	
	
	
	public static Infos read(URL url ) throws Exception {
		File f = new File(url.toURI());
		return read (f);
		
	}
	
	public static void write ( Diary smp ){
		try {
			File f = new File(smp.getFilepath().getPath());
			FileWriter fw = new FileWriter(f);			
			Marshaller.marshal( (Infos) smp , fw)	;
			fw.close();
		} catch (Exception e) {
			MessageDialog.openError(
					Display.getDefault().getActiveShell(), 
					LocaleManager.getText(LocaleManager.APP_TITLE), 								
					"Error writing diary: " + e.getLocalizedMessage()
			);
			logger.error("error occurred ", e);
		}
	}
	
	public static void main(String[] args) {
		URL url = ClassLoader.getSystemResource("Sequence/info.test.xml");
		NewsFileManager sr = new NewsFileManager();
		try {
			Infos sequence = NewsFileManager.read(url);
			System.out.println(sequence);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
