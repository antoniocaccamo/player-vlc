package it.antanysavage.sm.player.sequences.model;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.bundle.LocaleManager;
import it.antanysavage.sm.player.exception.SequenceMoviePlayerException;
import it.antanysavage.sm.player.ftp.FTPDownloadProgramTimerTask;
import it.antanysavage.sm.player.sequences.schema.Sequence;
import it.antanysavage.sm.player.sequences.schema.Video;
import it.antanysavage.sm.player.sequences.schema.Videos;
import it.antanysavage.sm.player.util.Utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.swt.widgets.TableItem;

public class Program extends Sequence {

    private Lock MY_LOCK = new ReentrantLock();

    private static Logger logger = LoggerFactory.getLogger(Program.class);

    private int nextIndex = 0;

    private File filepath;

    private TableItem[] items;

    private boolean played;

    private long cycle;

    private ResourceManager manager = new ResourceManager();

    private FileChannel fileChannel;

    private java.util.Timer ftpDownloadProgramTimer = new java.util.Timer();

    private FTPDownloadProgramTimerTask ftpDownloadProgramTimerTask = null;

    private QueueLinkedList<Media> mediaQueue = new QueueLinkedList<Media>();
    

    public Program(Sequence sequence, String filepath) throws SequenceMoviePlayerException, Exception {
        this(sequence, filepath, null);
    }

    public Program(Sequence sequence, String filepath, String remotePath) throws SequenceMoviePlayerException, Exception {
        super();
        this.setName(sequence.getName());
        this.filepath = new File(filepath);
        setRemotePath(remotePath);

        logger.warn("**** remote path : " + getRemotePath());

        setVideos(new Videos());
        loadVideo(sequence);

        if (StringUtils.isNotEmpty(StringUtils.trim(getRemotePath()))) {
            File lock = new File(getFilepath().getAbsolutePath() + ".lock");
            fileChannel = new RandomAccessFile(lock, "rw").getChannel();
        }

        logger.info("resource manager : " + manager.toString());
        if (isRemote()) {
            ftpDownloadProgramTimerTask = new FTPDownloadProgramTimerTask(this);
            logger.info("program update every " + Utils.hours2millis(Player.APP_FTP_REFRESH) + " ms");
            ftpDownloadProgramTimer.schedule(ftpDownloadProgramTimerTask, 100, Utils.hours2millis(Player.APP_FTP_REFRESH));
            File lock = new File(getFilepath().getAbsolutePath() + ".lock");
            fileChannel = new RandomAccessFile(lock, "rw").getChannel();
        }
    }

    public QueueLinkedList<Media> getQueueLinkedList() {
        return mediaQueue;
    }

    public void loadVideo(Sequence sequence) throws SequenceMoviePlayerException {
        getVideos().clearVideo();
        if (mediaQueue.getTotal() > 0) {
            mediaQueue.empty();
        }
        
        for (int i = 0; i < sequence.getVideos().getVideoCount(); i++) {
            Video video = sequence.getVideos().getVideo(i);
            Media media = new Media(this, video);
            this.getVideos().addVideo(media);
            this.mediaQueue.enqueue(media);
//            if ( media.getResourceType() == Resource.VIDEO ) {
//            	MediaListItem mli = new MediaListItem(	media.getFile().getName(), media.getFile().getAbsolutePath(), null);
//            	mediaListItems.add(mli);
//            }
//            logger.info("mediaListItems ["+mediaListItems+"]");
        }
    }

    public ResourceManager getManager() {
        return manager;
    }

    private void checkEnd() {

        if (this.nextIndex == this.getVideos().getVideoCount()) {
            this.nextIndex = 0;
            this.cycle++;
            logger.info("# cycle : " + cycle);
        }
    }

    public long getCycle() {
        MY_LOCK.lock();
        try {
            return cycle;
        } finally {
            MY_LOCK.unlock();
        }
    }

    public synchronized String getDuration() {
        MY_LOCK.lock();
        try {
            double duration = 0;
            for (int i = 0; i < this.getVideos().getVideoCount(); i++) {
                Media v = (Media) this.getVideos().getVideo(i);
                duration += v.getDuration();
            }

            return Utils.getDurationString(duration);
        } finally {
            MY_LOCK.unlock();
        }
    }

    public File getFilepath() {
        return filepath;
    }

    public synchronized void goToInit() {
        MY_LOCK.lock();
        try {
            this.nextIndex = 0;
        } finally {
            MY_LOCK.unlock();
        }
    }

    public synchronized boolean isEmpty() {
        MY_LOCK.lock();
        try {
            return this.getVideos().getVideoCount() == 0;
        } finally {
            MY_LOCK.unlock();
        }
    }

    public synchronized boolean isPlayed() {
        MY_LOCK.lock();
        try {
            return played;
        } finally {
            MY_LOCK.unlock();
        }
    }

    public synchronized void moveDown(int startIndex) {
        MY_LOCK.lock();
        try {
            if (this.getVideos().getVideoCount() > 1 && startIndex < getVideos().getVideoCount() - 1) {
                int destIndex = startIndex + 1;
                this.swap(startIndex, destIndex);
            }
        } finally {
            MY_LOCK.unlock();
        }
    }

    public synchronized void moveUp(int startIndex) {
        MY_LOCK.lock();
        try {
            if (this.getVideos().getVideoCount() > 1 && startIndex > 0) {
                int destIndex = startIndex - 1;
                this.swap(startIndex, destIndex);
            }
        } finally {
            MY_LOCK.unlock();
        }
    }

	//	public synchronized Media next() {		
    //		MY_LOCK.lock();
    //		try {
    //			Media sq = null;
    //			if ( this.getVideos().getVideoCount() > 0 ) {
    //				sq = (Media) this.getVideos().getVideo(this.nextIndex);
    //
    //				this.nextIndex++;
    //				if ( nextIndex == this.getVideos().getVideoCount()  ) {
    //					this.nextIndex = 0;
    //				}
    //			}
    //			return sq;
    //		} finally {
    //			MY_LOCK.unlock();
    //		}
    //	}
    public Media next() {
//	    Date now = Calendar.getInstance().getTime();
        Calendar now = Calendar.getInstance(LocaleManager.getLocale());
        logger.debug("now is : " + Utils.debugDate(now));
        return next(now);
    }
    
    private Media nextMediaQueue(final Calendar now) {
        MY_LOCK.lock();
        Media next = null;
        Calendar saved = (Calendar) now.clone();
        boolean found = false;
        try {
            if ( ! mediaQueue.isEmpty() ) {
            	
            	if ( mediaQueue.getTotal() == 1 ) {
            		Media media = (Media) mediaQueue.getFirst().element;
            		if ( media.isPlayable(now)) {
            			mediaQueue.setPlaying(mediaQueue.getFirst());
            			next = media;
            		}
            		return next;
            	}
            	
            	if ( mediaQueue.getPlaying() == null  ) {   
            		mediaQueue.setPlaying(mediaQueue.getFirst());
            		Media media = (Media) mediaQueue.getPlaying().element;
            		if ( media.isPlayable(now)) {
            			mediaQueue.setPlaying(mediaQueue.getFirst());
            			return media;
            		}
            	}
            	
            	Node tmp = mediaQueue.getPlaying();
            	tmp = tmp.next;
            	while ( ! found && tmp != mediaQueue.getPlaying() ){
            		Media media = (Media) tmp.element;
            		if ( media.isPlayable(now)) {
            			mediaQueue.setPlaying(tmp);
            			return media;
            		}
            		tmp = tmp.next;
                    if (tmp == mediaQueue.getLast()) {
                        cycle++;
                    }
            	}            	
            }
            //at first
            return next;
        } finally {
            MY_LOCK.unlock();
        }
    }

    private Media loopMedia(final Calendar now) {
        boolean found = false;
        Node tmp = mediaQueue.getPlaying();
        Node first = tmp;
        tmp = tmp.next;
        while (!found && tmp != first) {
            logger.info("found [" + found + "] tmp [" + tmp + "] first [" + first + "]");
            Media media = (Media) tmp.element;
            if (media.isPlayable(now)) {
                mediaQueue.setPlaying(tmp);
                return media;
            }
            tmp = tmp.next;
            if (tmp == mediaQueue.getLast()) {
                cycle++;
            }
        }
        return null;
    }

    private Media next(final Calendar now) {
        MY_LOCK.lock(); 
        Calendar saved = (Calendar) now.clone();
        try {
            Media nextMedia2Play = null;
            Media v = (Media) getVideos().getVideo(this.nextIndex);
            // is the next playable ?
            logger.debug("now is : " + Utils.debugDate(now));
            if (v.isPlayable(now)) {
                nextMedia2Play = v;
                this.nextIndex++;
                checkEnd();
            } else {
                this.nextIndex++;
                checkEnd();
                Media vv = null;
                boolean nextFound = false;
                while (!nextFound && v != vv) {
                    vv = (Media) getVideos().getVideo(this.nextIndex);
                    this.nextIndex++;
                    checkEnd();
                    logger.debug("saved is : " + Utils.debugDate(saved));
                    if (vv.isPlayable(saved)) {
                        nextFound = true;
                    }
                }
                if (nextFound) {
                    nextMedia2Play = vv;
                }
            }
            return nextMedia2Play;
        } finally {
            MY_LOCK.unlock();
        }
    }

    public Media vecchio_next(Date now) {
        MY_LOCK.lock();
        try {
            Media sq = null;
            Media v = (Media) this.getVideos().getVideo(this.nextIndex);
            boolean canbeplayed = true;

            if (!v.isDateOrTimed() && !v.hasError()) {
                sq = v;
                this.nextIndex++;
                checkEnd();
            } else {
                if (v.isBeetwen(now) && !v.hasError()) {
                    sq = v;
                    this.nextIndex++;
                    checkEnd();
                } else {
                    boolean nextFound = false;
                    this.nextIndex++;
                    checkEnd();
                    Media vv = null;
                    while (v != vv && !nextFound) {
                        vv = (Media) this.getVideos().getVideo(this.nextIndex);
                        if (!vv.isDateOrTimed() && !v.hasError()) {
                            nextFound = true;
                        } else {
                            if (vv.isBeetwen(now) && !v.hasError()) {
                                nextFound = true;
                            }
                        }
                        this.nextIndex++;
                        checkEnd();
                    }
                    if (nextFound) {
                        sq = vv;
                    }
                }
            }

            return sq;
        } finally {
            MY_LOCK.unlock();
        }
    }

    public boolean removeVideo(Media vv) {
        MY_LOCK.lock();
        try {
            boolean removed = getVideos().removeVideo(vv);
            if (removed) {
                for (int i = vv.getId() - 1; i < getVideos().getVideoCount(); i++) {
                    Media v = (Media) getVideos().getVideo(i);
                    v.setId(v.getId() - 1);
                }
            }
            return removed;
        } finally {
            MY_LOCK.unlock();
        }
    }

    public synchronized void setPlayed(boolean played) {
        MY_LOCK.lock();
        try {
            if (played) {
                this.cycle = 0;
            }
            this.played = played;
        } finally {
            MY_LOCK.unlock();
        }
    }

    private void swap(int startIndex, int destIndex) {
        Media tmp01 = (Media) this.getVideos().getVideo(startIndex);
        Media tmp02 = (Media) this.getVideos().getVideo(destIndex);
        this.getVideos().setVideo(destIndex, tmp01);
        this.getVideos().setVideo(startIndex, tmp02);
        int tmp = tmp01.getId();
        tmp01.setId(tmp02.getId());
        tmp02.setId(tmp);
    }

    public String toString() {
        MY_LOCK.lock();
        try {
            StringBuffer sb = new StringBuffer();

            sb.append("Name [" + this.getName() + "] ");
            sb.append("Number of videos [" + this.getVideos().getVideoCount() + "] ");
            sb.append("Duration [" + this.getDuration() + "] ");
            sb.append("Path [" + this.filepath.getAbsolutePath() + "]");
            if (StringUtils.isNotEmpty(getRemotePath())) {
                sb.append(" RemotePath [" + getRemotePath() + "]");
            }

            return sb.toString();
        } finally {
            MY_LOCK.unlock();
        }
    }

    public void setFilepath(File f) {
        filepath = f;

    }

    public static Program clone(Program origin, String name, File f) throws Exception {
        Sequence sequence = Utils.getSequence(origin);
        sequence.setName(name);
        return new Program(sequence, f.getAbsolutePath());
    }

    public FileLock lock() throws Exception {
        return fileChannel.tryLock();
    }

    public void unlock(FileLock lock) throws Exception {
        lock.release();
    }

    @Override
    protected void finalize() throws Throwable {
        if (fileChannel != null) {
            fileChannel.close();
        }
    }

    public boolean isRemote() {
        return StringUtils.isNotEmpty(getRemotePath());
    }

}
