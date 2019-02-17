package base.utils.encrypt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * <p>Crypto interface.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public interface Crypto {

    /**
     * <p>decrypt.</p>
     *
     * @param str a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     * @throws javax.crypto.IllegalBlockSizeException if any.
     * @throws javax.crypto.BadPaddingException if any.
     */
    public abstract String decrypt(String str) throws IOException,
            IllegalBlockSizeException, BadPaddingException;

    /**
     * <p>encrypt.</p>
     *
     * @param str a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws java.io.UnsupportedEncodingException if any.
     * @throws javax.crypto.IllegalBlockSizeException if any.
     * @throws javax.crypto.BadPaddingException if any.
     */
    public abstract String encrypt(String str)
            throws UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException;

}
