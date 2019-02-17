package base.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


/**
 * StringDoubleUtil provides number conversion.
 *
 * @author Malcolm G. Davis
 * @version 1.0
 */
public class StringDoubleUtil {

    /**
     * Checks to see of a value is numeric or double.
     * StringUtils.isNumericSpace() does not allow '.', this method does.
     *
     * @param text a {@link java.lang.String} object.
     * @return boolean
     */
    public static boolean isDouble(final String text) {
        if (InternalString.isBlank(text)) {
            return false;
        }

        String trimmed = text.trim();

        boolean doubleDigit = false;
        int sz = trimmed.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isDigit(trimmed.charAt(i)) == false)
                    && (trimmed.charAt(i) != '.')) {
                if( i==0 && (trimmed.charAt(i) == '-')) {
                    continue ;
                }
                return false;
            }

            // don't allow 2 '.'
            if ((trimmed.charAt(i) == '.')) {
                if(doubleDigit) {
                    return false;
                }
                doubleDigit = true;
            }
        }
        return true;
    }

    /**
     * <p>unescapeCsvDouble.</p>
     *
     * @param text a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String unescapeCsvDouble(final String text) {
        if (InternalString.isBlank(text)) {
            return "";
        }

        StringBuilder newString = new StringBuilder();
        int sz = text.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isDigit(text.charAt(i)) == false)
                    && (text.charAt(i) != '.')) {
                if( i==0 && (text.charAt(i) == '-')) {
                    newString.append(text.charAt(i));
                }
                continue ;
            }
            newString.append(text.charAt(i));
        }
        return newString.toString();
    }

    /**
     * <p>getDouble.</p>
     *
     * @param text a {@link java.lang.String} object.
     * @param errorDouble a double.
     * @return a double.
     */
    public static double getDouble(final String text, final double errorDouble) {
        if (text == null) {
            return errorDouble;
        }
        if( !isDouble(text) ) {
            return errorDouble;
        }

        double result = errorDouble;
        try {
            result = Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * <p>getDoubleObj.</p>
     *
     * @param text a {@link java.lang.String} object.
     * @param errorDouble a double.
     * @return a {@link java.lang.Double} object.
     */
    public static Double getDoubleObj(final String text, final double errorDouble) {
        if( !isDouble(text) ) {
            return Double.valueOf(errorDouble);
        }
        Double result = null;
        try {
            result = Double.valueOf(text.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if( result==null ) {
            return Double.valueOf(errorDouble);
        }
        return result;
    }

    /**
     * <p>doubleRounded.</p>
     *
     * @param value a {@link java.lang.String} object.
     * @param roundingPastDecimal a int.
     * @param mode a {@link java.math.RoundingMode} object.
     * @return a double.
     */
    public static double doubleRounded(String value, int roundingPastDecimal, RoundingMode mode) {
        double valueD = StringDoubleUtil.getDouble(value, 0.0);

        // account for leading 0 as in 0.1213
        int adjustment = 0;
        int setPrecision = value.indexOf(".");
        if( setPrecision==1 && value.charAt(0)=='0' && roundingPastDecimal>0 ) {
            adjustment--;
        } else if( setPrecision< 0 ) {
            return valueD;
        }

        setPrecision = setPrecision + roundingPastDecimal + adjustment;
        MathContext context = new MathContext(setPrecision,mode);
        return new BigDecimal(valueD, context).doubleValue();
    }

}
