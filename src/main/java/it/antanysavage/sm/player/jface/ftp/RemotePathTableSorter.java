package it.antanysavage.sm.player.jface.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class RemotePathTableSorter extends ViewerSorter {
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		FTPFile ftpFile1 = (FTPFile) e1;
		FTPFile ftpFile2 = (FTPFile) e2;
		if (ftpFile1.equals(ftpFile2)) {
			return 0;
		}
		if (ftpFile1.isDirectory() && ftpFile2.isDirectory()) {
			return ftpFile1.getName().compareTo(ftpFile2.getName());
		}
		if (ftpFile1.isDirectory() && ftpFile2.isFile()) {
			return -1;
		}

		if (ftpFile1.isFile() && ftpFile2.isDirectory()) {
			return 1;
		}

		if (ftpFile1.isFile() && ftpFile2.isFile()) {
			return ftpFile1.getName().compareTo(ftpFile2.getName());
		}

		return 0;
	}

}
