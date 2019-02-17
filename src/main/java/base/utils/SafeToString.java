package base.utils;


/**
 * <p>SafeToString class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class SafeToString {
    /**
     * <p>toString.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @return a {@link java.lang.String} object.
     */
    public static String toString(Object obj) {
        if( obj==null ) {
            return "";
        }
        return obj.toString();
    }
}
