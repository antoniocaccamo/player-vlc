package it.antanysavage.sm.player.jface;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.util.Utils;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class ProgramTableLabelProvider implements ITableLabelProvider , IColorProvider {

	public Image getColumnImage(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColumnText(Object arg0, int arg1) {
		Media  video = (Media) arg0;
		String s = "";
		switch (arg1) {

		case 0:
			s = "" +video.getId();
			break;

		case 1:
			s = LocaleManager.getText("model.sequece.media." + video.getType().getType() );
			break;

		case 2:
			s =  Utils.getDurationString( video.getDuration() );
			break;

		case 3 :
			s = ( Utils.isAnEmptyString(video.getPath()) ? "" : video.getPath() );
			break;

		case 4 :
			s = ( ! video.isDated()  ? "" : Utils.getWatchDate(video.getStartDate()) );
			break;
		
		case 5 :
			s = ( ! video.isDated() ? "" : Utils.getWatchDate(video.getEndDate()) );					
			break;
			
		case 6 :
			s = ( ! video.isTimed() ? "" : video.getFrom() );
			break;

		case 7 :
			s = ( ! video.isTimed() ? "" : video.getTo() );
			break;
			
		case 8 :
			s = Utils.getWeekShort( (StringUtils.isEmpty(video.getDaysOfWeek() ) ? "1111111" : video.getDaysOfWeek() ));
			break;
			
		case 9 :
			s = ( video.isLimited() ? ""+video.getLimited() : "-");
			break;
		
		default :
			s = "";

		}
		return s;
	}

	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public Color getBackground(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Color getForeground(Object arg0) {
		Media  media = (Media) arg0;
		
		Color color = null;
		if ( ! media.isAvailable() )
			color = Display.getDefault().getSystemColor( SWT.COLOR_RED );
		else
			color = Display.getDefault().getSystemColor( SWT.COLOR_BLACK );
		return color;
	}
					
}

