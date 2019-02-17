package base.utils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;


public class FastDecimalFormat implements Serializable {

    public static final String MONEY = "'$'#,###,###.00";

    public static final String MONEY_LEADING_ZERO = "'$'#,###,##0.00";

    public static final String MONEY_NO_CENTS = "'$'#,###,###";

    public static FastDecimalFormat newFormat() {
        return newFormat(MONEY);
    }

    public static FastDecimalFormat newFormat(String format) {
        return new FastDecimalFormat(format);
    }

    public Number convertStringToNumber(String numberString) throws ParseException {
        return decimalFormat.get().parse(numberString);
    }

    public String formatNumber(double number) {
        return decimalFormat.get().format(number);
    }

    public FastDecimalFormat(String format) {
        decimalFormat.set(new DecimalFormat(format));
    }

    private final ThreadLocal<DecimalFormat> decimalFormat = new ThreadLocal<DecimalFormat> () {
        @Override
        public DecimalFormat get() {
            return super.get();
        }

        @Override
        protected DecimalFormat initialValue() {
            return new DecimalFormat("'$'#,###,###.00");
        }

        @Override
        public void remove() {
            super.remove();
        }

        @Override
        public void set(DecimalFormat value) {
            super.set(value);
        }
    };

}
