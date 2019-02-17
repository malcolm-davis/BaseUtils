package base.utils;

import base.utils.SystemDump;

public class SystemDumpTest {

    public static void main(String[] args) {
        System.out.println("Start: SystemDumpTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        SystemDumpTest test = new SystemDumpTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        System.out.println(SystemDump.dump());
    }
}
