package base.utils;

import java.util.Date;

import base.utils.DateTimeUtil;

public class DateTimeUtilDemo {

    public static void main(String[] args) {
        System.out.println("Start: DateTimeUtilDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        DateTimeUtilDemo test = new DateTimeUtilDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        Date now = new Date();
        System.out.println(now.toGMTString());

        Date yesterday = DateTimeUtil.addDay(now, -1);
        System.out.println(yesterday.toGMTString());
    }

}
