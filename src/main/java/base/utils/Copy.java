package base.utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utility for making object copies without the need for adding cloning to an object.<br><br>
 *
 * <b>Notes:</b>
 * <ul>
 * <li>The object must be marked as serializable.</li>
 * <li>An error is thrown if an object cannot be serialized, or any objects referenced in the parent.</li>
 * <li>A useful tool to verify if an object is truly serializable.</li>
 * <li>See cloning library <a href='https://code.google.com/p/cloning/'>https://code.google.com/p/cloning/</a>
 *  to clone ANY Java object.</li>
 * </ul>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class Copy {

    /**
     * Returns a copy of the object.
     *
     * @throws java.io.IOException if the object, or some child object, is not marked Serializable
     * @throws java.lang.ClassNotFoundException if the object is not visible for some reason.
     * @param toCopy a {@link java.lang.Object} object.
     * @param <T> a T object.
     * @return a T object.
     */
    @SuppressWarnings("unchecked")
    public static <T> T copy(final Object toCopy)throws IOException, ClassNotFoundException {
        if (toCopy == null) {
            throw new IllegalArgumentException("Null value for the object to copy");
        }
        // rather than pay the performance price of an instanceof check, throw an exception instead.
        //	if (! (orig instanceof Serializable) ) {
        //	    throw new IllegalArgumentException("object to copy is not Serializable");
        //	}
        T obj = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            out = new ObjectOutputStream(byteStream);
            out.writeObject(toCopy);

            // close the stream prior to trying to read back out
            CloseUtil.close(out);

            in = new ObjectInputStream(new ByteArrayInputStream(byteStream.toByteArray()));
            obj = (T)in.readObject();
            CloseUtil.close(in);
        } catch (final IOException e) {
            throw e;
        } catch (final ClassNotFoundException cnfe) {
            throw cnfe;
        }
        return obj;
    }
}
