package it.antanysavage.sm.player.jface.ftp;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.actions.ProgramSaveAsAction;
import it.antanysavage.sm.player.sequences.schema.types.AcceptedVideoTypes;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class ProgramFTPViewFilter extends ViewerFilter {
	
	private static Logger logger = LoggerFactory.getLogger(ProgramFTPViewFilter.class);

	@Override
	public boolean select(Viewer arg0, Object parentElement, Object element) {
		FTPFile ftpFile = (FTPFile) element;

		if (ftpFile.isDirectory())				
			return true;

		logger.debug("FTP File : " + ftpFile.getName() );
		if ( ftpFile.getName().indexOf(Player.SEQUENCE_FILE_EXT) != -1 )
			return true;
		
		return false;
	}

}
