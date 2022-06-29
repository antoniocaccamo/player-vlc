package it.antanysavage.sm.player.jface.ftp;

//import it.antanysavage.sm.player.screengroup.setter.ScreenGroupSetter;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class ScreenGroupConfigurationFTPViewFilter extends ViewerFilter {
	
	private static Logger logger = LoggerFactory.getLogger(ScreenGroupConfigurationFTPViewFilter.class);

	@Override
	public boolean select(Viewer arg0, Object parentElement, Object element) {
		FTPFile ftpFile = (FTPFile) element;

		if (ftpFile.isDirectory())				
			return true;

//		logger.debug("FTP File : " + ftpFile.getName() );
//		if ( ftpFile.getName().indexOf(ScreenGroupSetter.FILE_EXT) != -1 )
//			return true;
		
		return false;
	}

}
