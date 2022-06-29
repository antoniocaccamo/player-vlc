package it.antanysavage.sm.player.jface.ftp;

import it.antanysavage.sm.player.util.Utils;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class RemotePathTableLabelProvider implements ITableLabelProvider{
	
	
	
	public Image getColumnImage(Object element, int columnIndex) {
		// if(columnIndex == 0)
		// return FileIconUtil.getIcon((FTPFile)element);
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		FTPFile ftpFile = (FTPFile) element;
		switch (columnIndex) {
		case 0:

			if (ftpFile.isDirectory())
				return "Dir";
			if (ftpFile.isFile())
				return "File";
			return "";

		case 1:
			if ( StringUtils.isEmpty(ftpFile.getName()))
				return "";
			return ((FTPFile) element).getName();
		case 2:
			return ((FTPFile) element).getSize() + "";
		case 3:
			Calendar cal = ((FTPFile) element).getTimestamp();
			if (cal == null)
				return "";
			return 
//			cal.get(Calendar.YEAR) + "-"
//			+ cal.get(Calendar.MONTH) + "-"
//			+ cal.get(Calendar.DAY_OF_MONTH) + " "
//			+ cal.get(Calendar.HOUR_OF_DAY) + ":"
//			+ cal.get(Calendar.MINUTE) + ":"
//			+ cal.get(Calendar.SECOND);
					Utils.debugDate(cal)
			;
		default:
			return "";
		}
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}
}
