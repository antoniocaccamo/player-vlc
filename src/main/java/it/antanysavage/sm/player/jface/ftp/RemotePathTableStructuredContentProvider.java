package it.antanysavage.sm.player.jface.ftp;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class RemotePathTableStructuredContentProvider implements IStructuredContentProvider {
	
	private static Logger logger = LoggerFactory.getLogger(RemotePathTableStructuredContentProvider.class);

	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public Object[] getElements(Object arg0) {
		FTPFile[] filesFTP = (FTPFile[]) arg0;
		FTPFile parentDir = new FTPFile();
		parentDir.setName("..");
		parentDir.setType(FTPFile.DIRECTORY_TYPE);
		ArrayList<FTPFile> files = new ArrayList<FTPFile>();
		files.add(parentDir);
		files.addAll(Arrays.asList(filesFTP));
		
		return files.toArray();
	}

}
