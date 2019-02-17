package base.utils.mod;

import java.io.IOException;

import base.utils.ReadResource;


public class ReadResourceTest {

    public static void main(String[] args) {
        System.out.println("Start: ReadResourceTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        ReadResourceTest test = new ReadResourceTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        try {
            System.out.println(ReadResource.INSTANCES.getText("/template/test.template"));
        } catch (IOException excep) {
            excep.printStackTrace();
        }
    }
}
