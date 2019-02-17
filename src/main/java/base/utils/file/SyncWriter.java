package base.utils.file;

import java.io.Closeable;
import java.io.IOException;

/**
 * <p>SyncWriter interface.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public interface SyncWriter extends Closeable {

    /** {@inheritDoc} */
    @Override
    public abstract void close() throws IOException;

    /**
     * <p>write.</p>
     *
     * @param s a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public abstract void write(String s) throws IOException;

    /**
     * <p>flush.</p>
     *
     * @throws java.io.IOException if any.
     */
    public abstract void flush() throws IOException;

    /**
     * <p>getByteArray.</p>
     *
     * @return an array of byte.
     */
    public abstract byte[] getByteArray();
}
