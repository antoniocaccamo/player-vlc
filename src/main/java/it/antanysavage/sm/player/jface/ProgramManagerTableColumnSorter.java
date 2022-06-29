package it.antanysavage.sm.player.jface;

import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.util.Utils;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class ProgramManagerTableColumnSorter extends ViewerSorter {
	
	private static final Logger logger = LoggerFactory.getLogger(ProgramManagerTableColumnSorter.class); 

	public static final int       ID = 0;	
	public static final int     TYPE = 1;
	public static final int DURATION = 2;
	public static final int     PATH = 3;
	
	public static final int    START = 4;
	public static final int      END = 5;
	
	public static final int     FROM = 6;
	public static final int       TO = 7;
	
	public static final int    LIMIT = 8;

	private int criteria;
	private int versus;

	public ProgramManagerTableColumnSorter(int criteria , int versus) {
		this.criteria = criteria;
		this.versus   = versus;
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		Media m1 = (Media) e1;
		Media m2 = (Media) e2;

		int cmp = 0;

		switch (criteria) {

		case ID:
			cmp = intComparation(m1.getId(), m2.getId());
			logger.debug("criteria {ID} cmp {"+cmp+"} m1.getId() {"+m1.getId()+"} m2.getId() {"+m2.getId()+"}");
			break;

		case TYPE :
			String s1 = LocaleManager.getText("model.sequece.media." + m1.getType().getType() );
			String s2 = LocaleManager.getText("model.sequece.media." + m2.getType().getType() );			
			cmp = s1.compareTo(s2);
			logger.debug("criteria {TYPE} cmp {"+cmp+"} s1() {"+s1+"} s2 {"+s2+"}");
			break;

		case DURATION:
			cmp = floatComparation(m1.getDuration(), m2.getDuration());
			logger.debug("criteria {DURATION} cmp {"+cmp+"} m1.getDuration(){"+m1.getDuration()+"} m2.getDuration() {"+m2.getDuration()+"}");
			break;

		case PATH:
			cmp = stringWithNullComparation(m1.getPath(), m2.getPath());			
			logger.debug("criteria {PATH} cmp {"+cmp+"} m1.getPath(){"+m1.getPath()+"} m2.getPath() {"+m2.getPath()+"}");
			break;

		case FROM:
			cmp = stringWithNullComparation(m1.getFrom(), m2.getFrom());
			logger.debug("criteria {FROM} cmp {"+cmp+"} m1.getFrom(){"+m1.getFrom()+"} m2.getFrom() {"+m2.getFrom()+"}");
			break;
			
		case TO:
			cmp = stringWithNullComparation(m1.getTo(), m2.getTo());
			logger.debug("criteria {TO} cmp {"+cmp+"} m1.getTo(){"+m1.getTo()+"} m2.getTo() {"+m2.getTo()+"}");
			break;
			
		case START:
			cmp = stringWithNullComparation(m1.getStart(), m2.getStart());
			logger.debug("criteria {START} cmp {"+cmp+"} m1.getStart(){"+m1.getStart()+"} m2.getStart() {"+m2.getStart()+"}");
			break;
			
		case END:
			cmp = stringWithNullComparation(m1.getEnd(), m2.getEnd());
			logger.debug("criteria {END} cmp {"+cmp+"} m1.getEnd(){"+m1.getEnd()+"} m2.getEnd() {"+m2.getEnd()+"}");
			break;
			
		case LIMIT:
			cmp = intComparation(m1.getLimited(), m2.getLimited());
			logger.debug("criteria {LIMIT} cmp {"+cmp+"} m1.getLimited(){"+m1.getLimited()+"} m2.getLimited() {"+m2.getLimited()+"}");
			break;
			
			
		default:
			cmp = 0;
			break;
		}


		return cmp * versus;
	}

	private int stringWithNullComparation(String s1, String s2) {
		int cmp = 0;
		
		if ( ! Utils.isAnEmptyString( s1 ) && ! Utils.isAnEmptyString( s2 ) ) {
			cmp = s1.compareTo( s2 );
		} 
		if ( Utils.isAnEmptyString( s1 )  && ! Utils.isAnEmptyString( s2 ) ) {
			cmp = -1;
		}
		if ( ! Utils.isAnEmptyString( s1 )  && Utils.isAnEmptyString( s2 ) ) {
			cmp = 1;
		}
		
		return cmp;
	}

	private int intComparation(int i1, int i2) {
		return Integer.valueOf(i1).compareTo( Integer.valueOf(i2));
	}
	
	private int floatComparation(float f1 , float f2) {
		return Float.valueOf(f1).compareTo( Float.valueOf(f2));
	}

}
