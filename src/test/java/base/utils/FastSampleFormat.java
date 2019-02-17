package base.utils;

import java.text.ParseException;

import base.utils.FastDecimalFormat;



public class FastSampleFormat {

    public static void main(String[] args) {
        System.out.println("Start: FastDecimalFormatDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        FastSampleFormat test = new FastSampleFormat();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        System.out.println(formatDollar().formatNumber(12.222222));
        System.out.println(formatDollar().formatNumber(1.22));
        System.out.println(formatDollar().formatNumber(.222222));

        System.out.println("-----------------------------------------");
        FastDecimalFormat format = FastDecimalFormat.newFormat("##0.##");
        System.out.println(format.formatNumber(12.222222));
        System.out.println(format.formatNumber(1.27));
        System.out.println(format.formatNumber(.222222));
        System.out.println(format.formatNumber(1));
        System.out.println(format.formatNumber(0));


        System.out.println("-----------------------------------------");
        System.out.println(formatSimple().formatNumber(12.222222));
        System.out.println(formatSimple().formatNumber(1.27));
        System.out.println(formatSimple().formatNumber(.222222));
        System.out.println(formatSimple().formatNumber(1.2));
        System.out.println(formatSimple().formatNumber(0.25));

        try {
            System.out.println(formatSimple().convertStringToNumber("0.25"));
        } catch (ParseException excep) {
            // TODO Auto-generated catch block
            excep.printStackTrace();
        }

        System.out.println("-----------------------------------------");

        System.out.println(format(10));
        System.out.println(format(11.1));
        System.out.println(format(11.25));
        System.out.println(format(1.27));
        System.out.println(format(.222222));
        System.out.println(format(1.2));
        System.out.println(format(0.25));


    }

    String format(double value) {
        if( value>=10 ) {
            String text = Double.toString(value);
            int integerPlaces = text.indexOf('.');
            int decimalPlaces = text.length() - integerPlaces - 1;

            String signValue = text.substring(integerPlaces+1);
            if(decimalPlaces > 0 && !signValue.equals("0")) {
                return formatLarge().formatNumber(value);
            }
        }
        return formatSimple().formatNumber(value);
    }

    FastDecimalFormat formatDollar() {
        if(m_formatDollar==null) {
            m_formatDollar = FastDecimalFormat.newFormat(FastDecimalFormat.MONEY);
        }
        return m_formatDollar;
    }

    private FastDecimalFormat m_formatDollar;

    FastDecimalFormat formatSimple() {
        if(m_formatSimpleDouble==null) {
            m_formatSimpleDouble= FastDecimalFormat.newFormat("###.##");
        }
        return m_formatSimpleDouble;
    }

    FastDecimalFormat formatLarge() {
        if(m_formatLargeDouble==null) {
            m_formatLargeDouble= FastDecimalFormat.newFormat("###");
        }
        return m_formatLargeDouble;
    }

    private FastDecimalFormat m_formatSimpleDouble;

    private FastDecimalFormat m_formatLargeDouble;
}
