package base.utils;



/**
 * StringIntegerUtil provides number conversion.
 *
 * @author Malcolm G. Davis
 * @version 1.0
 */
public class StringIntegerUtil {

    /**
     * Checks to see of a value is numeric or double.
     * StringUtils.isNumericSpace() does not allow '.', this method does.
     *
     * @param text a {@link java.lang.String} object.
     * @return boolean
     */
    public static boolean isInteger(final String text) {
        if (InternalString.isBlank(text)) {
            return false;
        }

        int sz = text.length();
        for (int i = 0; i < sz; i++) {
            if ( !Character.isDigit(text.charAt(i)) ){
                return false;
            }
        }
        return true;
    }

    /**
     * <p>getInteger.</p>
     *
     * @param text a {@link java.lang.String} object.
     * @param errorValue a int.
     * @return a int.
     */
    public static int getInteger(final String text, final int errorValue) {
        if (text == null) {
            return errorValue;
        }
        if( !isInteger(text) ) {
            return errorValue;
        }

        int result = errorValue;
        try {
            result = Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * <p>getIntegerObject.</p>
     *
     * @param text a {@link java.lang.String} object.
     * @param errorValue a int.
     * @return a {@link java.lang.Integer} object.
     */
    public static Integer getIntegerObject(final String text, final int errorValue) {
        if( !isInteger(text) ) {
            return Integer.valueOf(errorValue);
        }
        Integer result = null;
        try {
            result = Integer.valueOf(text.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if( result==null ) {
            return Integer.valueOf(errorValue);
        }
        return result;
    }

}
