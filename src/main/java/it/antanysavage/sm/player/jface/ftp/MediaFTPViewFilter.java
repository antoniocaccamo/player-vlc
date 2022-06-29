package it.antanysavage.sm.player.jface.ftp;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.sequences.schema.types.AcceptedVideoTypes;
import it.antanysavage.sm.player.swt.views.MediaNewComposite;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class MediaFTPViewFilter extends ViewerFilter {

	
	private MediaNewComposite mediaNewComposite;

	public MediaFTPViewFilter(MediaNewComposite mediaNewComposite) {
		this.mediaNewComposite = mediaNewComposite;
	}
	
	@Override
	public boolean select(Viewer arg0, Object parentElement, Object element) {
		FTPFile ftpFile = (FTPFile) element;

		if (ftpFile.isDirectory())				
			return true;

		if (mediaNewComposite.getMediaType() == AcceptedVideoTypes.FTPVIDEO_TYPE) {
			for (int i = 1; i < Player.VIDEO_FILTERS.length; i++) {
				if (ftpFile.getName().indexOf(
						Player.VIDEO_FILTERS[i].substring(2)) != -1)
					return true;
			}
		}
		if (mediaNewComposite.getMediaType() == AcceptedVideoTypes.FTPIMAGE_TYPE) {
			for (int i = 1; i < Player.PHOTO_FILTERS.length; i++) {
				if (ftpFile.getName().indexOf(
						Player.PHOTO_FILTERS[i].substring(2)) != -1)
					return true;
			}
		}
		return false;
	}

}
