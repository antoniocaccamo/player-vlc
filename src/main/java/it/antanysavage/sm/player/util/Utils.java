package it.antanysavage.sm.player.util;

import java.text.Format;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.jface.ProgramManager;
import it.antanysavage.sm.player.jface.ProgramTab;
import it.antanysavage.sm.player.sequences.model.Media;
import it.antanysavage.sm.player.sequences.model.Program;
import it.antanysavage.sm.player.sequences.schema.Sequence;
import it.antanysavage.sm.player.sequences.schema.Video;
import it.antanysavage.sm.player.sequences.schema.Videos;
import it.antanysavage.sm.player.swt.views.MediaModifyComposite;
import it.antanysavage.sm.player.swt.views.MediaNewComposite;
import it.antanysavage.sm.player.swt.views.ProgramFTPComposite;

public class Utils {

    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    private static long MILLISECONDS_IN_A_DAY = 86400000;

    private static Format watchTimeFormatter = new SimpleDateFormat("HH:mm:ss", LocaleManager.getLocale());

    private static Format watchDateFormatter = new SimpleDateFormat("dd MMM yyyy", LocaleManager.getLocale());

    private static SimpleDateFormat timeMediumFormatter = new SimpleDateFormat("HH:mm:ss");

    private static SimpleDateFormat timeShortFormatter = new SimpleDateFormat("HH:mm");

    private static SimpleDateFormat dayFormatter = new SimpleDateFormat("yyyyMMdd");

    private static SimpleDateFormat dayOfWeekFormatter = new SimpleDateFormat("EEE", LocaleManager.getLocale());
    private static SimpleDateFormat dayOfWeekShortFormatter = new SimpleDateFormat("E", LocaleManager.getLocale());

    private static SimpleDateFormat debugFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private static MessageFormat durationFormatter = new MessageFormat("{0,number,##}:{1,number,##}:{2,number,##}");

    private static MessageFormat hhmmFormatter = new MessageFormat("{0,number,##}:{1,number,##}");

    private static MessageFormat sequenceMediaMessageFormat = new MessageFormat(LocaleManager.getText(LocaleManager.MODEL_SEQUENCE_MEDIA_DESCRIPTION));

    private static MessageFormat sequenceMessageFormat = new MessageFormat(LocaleManager.getText(LocaleManager.MODEL_SEQUENCE_DESCRIPTION));

    public static boolean isAnEmptyString(String s) {
//        if (s != null && !"".equals(s.trim())) {
//            return false;
//        }
//        return true;
    	return StringUtils.isEmpty(s);
    }

    public static String getWatchDate(Calendar c) {
        return Utils.watchDateFormatter.format(c.getTime());
    }

    public static String getWatchDate(Date d) {
        return Utils.watchDateFormatter.format(d);
    }

    public static String geWatchTime(Calendar c) {
        return geWatchTime(c.getTime());
    }

    public static String geWatchTime(Date d) {
        return Utils.timeMediumFormatter.format(d);
    }

	//	public static SequenceModel getSequenceModel(Sequence sequence) throws SequenceMoviePlayerException {
    //		SequenceModel model = new SequenceModel();		
    //		ArrayList list = new ArrayList();
    //
    //		model.setName(sequence.getName());
    //		Videos videos = sequence.getVideos();
    //		for ( int i = 0 ; i < videos.getVideoCount(); i++ ){
    //			SequenceVideo movie  = SequenceMovieFactory.getSequenceMovie(videos.getVideo(i));
    //			list.add(movie);
    //		}
    //		model.setVideos(list);
    //		return model;
    //	}

    /*public static String getDurationString(long duration) {

     long[] args = new long[3];

     args[0] = duration / ( 60*60);
     duration -= (args[0] * 60 * 60);
     args[1] = duration / 60;
     args[2] = duration % 60;

     StringBuffer sb = new StringBuffer();

     sb.append("" + ( args[0] < 10 ? "0" : "" ) + args[0] + ":" );
     sb.append("" + ( args[1] < 10 ? "0" : "" ) + args[1] + ":" );
     sb.append("" + ( args[2] < 10 ? "0" : "" ) + args[2]);


     return sb.toString();
     }*/
    public static String getDurationString(double duration) {

        long[] args = new long[4];

        long dd = Double.valueOf(duration).longValue();

        double dec = duration - dd;

        args[3] = (long) (1000 * dec);
        args[0] = dd / (60 * 60);
        dd -= (args[0] * 60 * 60);
        args[1] = dd / 60;
        args[2] = dd % 60;

        StringBuffer sb = new StringBuffer();

        sb.append("" + (args[0] < 10 ? "0" : "") + args[0] + ":");
        sb.append("" + (args[1] < 10 ? "0" : "") + args[1] + ":");
        sb.append("" + (args[2] < 10 ? "0" : "") + args[2] + "," + args[3]);

        return sb.toString();
    }

    public static Program getProgram(Sequence sequence, String filepath) throws Exception {
        return getProgram(sequence, filepath, sequence.getRemotePath());
    }

    public static Program getProgram(Sequence sequence, String filepath, String remotePath) throws Exception {
        Program smp = new Program(sequence, filepath, remotePath);
        return smp;
    }

    public static Sequence getSequence(Program smp) throws Exception {
        Sequence sequence = new Sequence();

        sequence.setName(smp.getName());
        sequence.setVideos(new Videos());
        sequence.setRemotePath(smp.getRemotePath());

        for (int i = 0; i < smp.getVideos().getVideoCount(); i++) {
            Media vSmp = (Media) smp.getVideos().getVideo(i);
            Video v = new Video();
            Media.copy(v, vSmp);
            //FIXME patch ...
            if (vSmp.hasLimited()) {
                v.setLimited(vSmp.getLimited());
            }
            sequence.getVideos().addVideo(v);
        }
        return sequence;
    }

    public static Date getTimeMedium(String s) {
        Date d = null;
        if (!Utils.isAnEmptyString(s)) {
            try {
                d = Utils.timeMediumFormatter.parse(s);
            } catch (ParseException e) {
                logger.error("error occurred ", e);
            }
        }
        return d;
    }

    public static Date getTimeShort(String s) {
        Date d = null;
        if (!Utils.isAnEmptyString(s)) {
            try {
                d = Utils.timeShortFormatter.parse(s);
            } catch (ParseException e) {
                logger.error("error occurred ", e);
            }
        }
        return d;
    }

    public static String getTimeAsString(Calendar c) {
        return getTimeAsString(c.getTime());
    }

    public static String getTimeAsString(Date d) {
        return Utils.watchTimeFormatter.format(d);
    }

    public static Date getDate(String s) {
        Date d = null;
        if (!Utils.isAnEmptyString(s)) {
            try {
                d = Utils.dayFormatter.parse(s);
            } catch (ParseException e) {
                logger.error("error occurred ", e);
            }
        }
        return d;
    }

    public static String getDateAsString(Calendar c) {
        return Utils.getDateAsString(c.getTime());
    }

    public static String getDateAsString(Date d) {
        return Utils.dayFormatter.format(d);
    }

    public static String getTimeAsString(int hh, int mm) {
//		Integer[] args = new Integer[2];
//		args[0] = new Integer(hh);
//		args[1] = new Integer(mm);
//		return Utils.hhmmFormatter.format(args);

        return getTimeAsString(hh, mm, 0);

    }

    public static String getTimeAsString(int hh, int mm, int ss) {
//		Integer[] args = new Integer[2];
//		args[0] = new Integer(hh);
//		args[1] = new Integer(mm);
//		return Utils.hhmmFormatter.format(args);

        StringBuffer sb = new StringBuffer();

        sb.append("" + (hh < 10 ? "0" : "") + hh + ":");
        sb.append("" + (mm < 10 ? "0" : "") + mm + ":");
        sb.append("" + (ss < 10 ? "0" : "") + ss);

        return sb.toString();

    }

    public static String debugDate(Calendar c) {
        return debugDate(c.getTime());
    }

    public static String debugDate(Date d) {
        return debugFormatter.format(d);
    }

    public static String getMediaDescription(Media vv) {

//        String[] args = {
//            "" + vv.getId(),
//            LocaleManager.getText("model.sequece.media." + vv.getType().getType()),
//            "" + Utils.getDurationString(vv.getDuration()),
//            (Utils.isAnEmptyString(vv.getPath()) ? "" : vv.getPath())
//        };
//        return sequenceMediaMessageFormat.format(args);
    	return vv.toString();
    }

    public static String getProgramDescription(Program smp) {

        String[] args = {
            smp.getName(),
            "" + smp.getVideos().getVideoCount(),
            "" + smp.getDuration(),
            smp.getFilepath().getName()
        };

        return sequenceMessageFormat.format(args);
    }

    public static String getNextActivationTimerScheduleDelay(long delay) {
        logger.info("delay : " + delay);
        Calendar c = Calendar.getInstance(TimeZone.getDefault(), LocaleManager.getLocale());
        c.setTimeInMillis(delay);
        return Utils.geWatchTime(c);
    }

    public static long hours2millis(double hours) {
        double millis = 1000 * 3600 * hours;
        return (long) millis;
    }

    public static void setStartOfDay(Calendar start) {
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
    }

    public static void setStartOfDay(Date start) {
        start.setHours(0);
        start.setMinutes(0);
        start.setSeconds(0);
    }

    public static void setEndOfDay(Date start) {
        start.setHours(23);
        start.setMinutes(59);
        start.setSeconds(59);
    }

    public static void setEndOfDay(Calendar end) {
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
    }

    public static boolean isLimitationElapsed(final Date lastTimePlay, Date now) {
        Date last = (Date) lastTimePlay.clone();
        Utils.setStartOfDay(now);
        Utils.setStartOfDay(last);
        long diff = now.getTime() - last.getTime();
        if (diff < Utils.MILLISECONDS_IN_A_DAY) {
            return false;
        }
        return true;
    }

    public static void insertMedia(Shell shellParent, ProgramTab programTab, String text) {
        Display display = Display.getDefault();
        Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
        MediaNewComposite inst = new MediaNewComposite(shell, programTab);
        shell.setLayout(new FillLayout());
        shell.layout();

        shell.setText(text);
        Point size = inst.getSize();
        Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
        shell.setSize(shellBounds.width, shellBounds.height);
        Point p = shellParent.getLocation();
        p.x += 10;
        p.y += 10;
        shell.setLocation(p);
        inst.pack();
        shell.pack();
        inst.forceFocus();
        shell.setImage(Player.LOGO_IMAGE);
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    public static void modifyMedia(Shell shellParent, ProgramTab programTab, Media media) {
        Point p = shellParent.getLocation();
        p.x += 10;
        p.y += 10;

        Display display = Display.getDefault();
        Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
//		NewModifyMediaComposite inst = new NewModifyMediaComposite(shell,  media);
        MediaModifyComposite inst = new MediaModifyComposite(shell, programTab, media);
        shell.setLayout(new FillLayout());
        shell.layout();

        shell.setText(LocaleManager.getText(LocaleManager.SM_MENU_MEDIA_CHANGE));
        Point size = inst.getSize();
        Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
        shell.setSize(shellBounds.width, shellBounds.height);
        shell.setLocation(p);
        inst.pack();
        shell.pack();
        inst.forceFocus();
        shell.setImage(Player.LOGO_IMAGE);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    public static void deleteMedia(IStructuredSelection selection, ProgramTab programTab) {
        Iterator it = selection.iterator();
        while (it.hasNext()) {
            Media media = (Media) it.next();
            if (media != null) {
                Program program = media.getMyProgram();
                if (program.removeVideo(media)) {
                    logger.info("program [" + program.getName() + "] | removed  " + Utils.getMediaDescription(media));
                    programTab.getVideosOfSequencesTableViewer().remove(media);
                    programTab.getVideosOfSequencesTableViewer().refresh();
                } else {
                    MessageDialog.openError(programTab.getShell(),
                            LocaleManager.getText(LocaleManager.APP_MENU_SEQUENCE_MANAGER) + " | ",
                            "The movie with id [" + media.getId() + "] is not in the sequence [" + program.getName() + "]"
                    );
                }
            }
        }
    }

    public static String getDayOfWeek(int day) {
        Calendar calendar = Calendar.getInstance(LocaleManager.getLocale());
        calendar.set(Calendar.DAY_OF_WEEK, day);
        return dayOfWeekFormatter.format(calendar.getTime());
    }

    public static String getWeekShort(String daysOfWeek) {
        StringBuffer sb = new StringBuffer("-------");

        Calendar c = Calendar.getInstance(LocaleManager.getLocale());

        if (daysOfWeek.charAt(0) == '1') {
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            sb.setCharAt(0, dayOfWeekShortFormatter.format(c.getTime()).charAt(0));
        } else {
            sb.setCharAt(0, '-');
        }

        if (daysOfWeek.charAt(1) == '1') {
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            sb.setCharAt(1, dayOfWeekShortFormatter.format(c.getTime()).charAt(0));
        } else {
            sb.setCharAt(1, '-');
        }

        if (daysOfWeek.charAt(2) == '1') {
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            sb.setCharAt(2, dayOfWeekShortFormatter.format(c.getTime()).charAt(0));
        } else {
            sb.setCharAt(2, '-');
        }

        if (daysOfWeek.charAt(3) == '1') {
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            sb.setCharAt(3, dayOfWeekShortFormatter.format(c.getTime()).charAt(0));
        } else {
            sb.setCharAt(3, '-');
        }

        if (daysOfWeek.charAt(4) == '1') {
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            sb.setCharAt(4, dayOfWeekShortFormatter.format(c.getTime()).charAt(0));
        } else {
            sb.setCharAt(4, '-');
        }

        if (daysOfWeek.charAt(5) == '1') {
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            sb.setCharAt(5, dayOfWeekShortFormatter.format(c.getTime()).charAt(0));
        } else {
            sb.setCharAt(5, '-');
        }

        if (daysOfWeek.charAt(6) == '1') {
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            sb.setCharAt(6, dayOfWeekShortFormatter.format(c.getTime()).charAt(0));
        } else {
            sb.setCharAt(6, '-');
        }

        return sb.toString();
    }

    public static void programFTP(ProgramManager programManager, String text) {
        Display display = Display.getDefault();
        Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
        Composite inst = new ProgramFTPComposite(shell, programManager);
        shell.setLayout(new FillLayout());
        shell.layout();

        shell.setText(text + ProgramFTPComposite.FTP_TEXT);
        Point size = inst.getSize();
        Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
        shell.setSize(shellBounds.width, shellBounds.height);
        Point p = programManager.getShell().getLocation();
        p.x += 10;
        p.y += 10;
        shell.setLocation(p);
//		inst.pack();
        shell.pack();
        inst.forceFocus();
        shell.setImage(Player.LOGO_IMAGE);
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    

}
