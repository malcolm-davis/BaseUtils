package base.utils.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import base.utils.CloseUtil;
import base.utils.InternalString;

/**
 * <p>SyncCompressFileWrite class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class SyncCompressFileWrite implements SyncWriter {

    /**
     * <p>newCompression.</p>
     *
     * @param file a {@link java.io.File} object.
     * @return a {@link org.elasolutions.utils.file.SyncCompressFileWrite} object.
     * @throws org.apache.commons.compress.compressors.CompressorException if any.
     * @throws java.io.FileNotFoundException if any.
     */
    public static SyncCompressFileWrite newCompression(final File file) throws CompressorException, FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException("Null value for file");
        }
        FileOutputStream fileStream = new FileOutputStream(file);
        BufferedOutputStream bufferStream = new BufferedOutputStream(fileStream);
        CompressorOutputStream compress =
                (new CompressorStreamFactory()).createCompressorOutputStream(
                    CompressorStreamFactory.BZIP2, bufferStream);
        return new SyncCompressFileWrite(fileStream,bufferStream,compress);
    }


    /** {@inheritDoc} */
    @Override
    public void close() throws IOException {
        flush();
        CloseUtil.close(m_compressOut);
        CloseUtil.close(m_bufferStream);
        CloseUtil.close(m_outputStream);
    }

    /** {@inheritDoc} */
    @Override
    public void flush() throws IOException {
        if( m_compressOut!=null ) {
            m_compressOut.flush();
        }
    }

    /** {@inheritDoc} */
    @Override
    public byte[] getByteArray() {
        return m_outputStream.toString().getBytes();
    }

    /** {@inheritDoc} */
    @Override
    public void write(final String text) throws IOException {
        if (InternalString.isBlank(text)) {
            return;
        }
        lock.lock();
        try {
            m_compressOut.write(text.getBytes());
        } catch (IOException excep) {
            throw excep;
        } finally {
            lock.unlock();
        }
    }

    private SyncCompressFileWrite(FileOutputStream fileStream, BufferedOutputStream bufferStream, CompressorOutputStream compress) {
        m_outputStream = fileStream;
        m_bufferStream = bufferStream;
        m_compressOut = compress;
    }

    private final ReentrantLock lock = new ReentrantLock(true);

    BufferedOutputStream m_bufferStream;

    FileOutputStream m_outputStream;

    CompressorOutputStream m_compressOut;
}
