package base.utils.file;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SyncFileWrite controls the writing to a file from multiple threads.
 *
 * @author Malcolm G. Davis
 * @version 1.0
 */
public class SyncFileWrite implements SyncWriter {

    /**
     * <p>Constructor for SyncFileWrite.</p>
     *
     * @param file a {@link java.io.File} object.
     * @throws java.lang.Exception if any.
     */
    public SyncFileWrite(final File file) throws Exception {
        try {
            out = new BufferedWriter(new FileWriter(file, true));
        } catch (final IOException excep) {
            LOGGER.log(Level.SEVERE,"Unable to create/open file: " + file.getAbsolutePath(), excep);
            throw new Exception("Unable access file");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        try {
            out.flush();
            out.close();
        } catch (final IOException excep) {
            excep.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public byte[] getByteArray() {
        return new byte[0];
    }

    /**
     * {@inheritDoc}
     *
     * Optional flush command.  Flush will occur on close.
     */
    @Override
    public void flush() {
        try {
            out.flush();
        } catch (final IOException excep) {
            excep.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void write(final String text) {
        if (text==null || text.trim().length()==0 ) {
            return;
        }
        lock.lock();
        try {
            out.write(text);
            out.write(LINE_SEPARATOR);
        } catch (final Exception excep) {
            LOGGER.log(Level.SEVERE,"Unable to write text line to file.", excep);
        } finally {
            lock.unlock();
        }
    }

    private final ReentrantLock lock = new ReentrantLock(true);

    private BufferedWriter out;

    final static String LINE_SEPARATOR = System.getProperty("line.separator");

    private static final Logger LOGGER =
            Logger.getLogger(SyncFileWrite.class.getName());


}
