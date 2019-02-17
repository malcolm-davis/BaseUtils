package base.utils;

import java.text.DecimalFormat;


public class StringDoubleExample {
    public static void main(String[] args) {
        System.out.println("Start: StringDoubleExample ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        StringDoubleExample test = new StringDoubleExample();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        // numFormat = new DecimalFormat("'$'#,###,###");
        DecimalFormat numFormat = new DecimalFormat("'$'#0.00");

        System.out.println("1. DecimalFormat with: " + numFormat.format(1215.5674));
        System.out.println("1. DecimalFormat with: " + numFormat.format(5.5674));
        System.out.println("1. DecimalFormat with: " + numFormat.format(0.19));
        System.out.println("1. DecimalFormat with: " + numFormat.format(1.10003));
        System.out.println("1. DecimalFormat with: " + numFormat.format(.200000));

    }

}
