package base.utils.types;

import base.utils.Car;
import base.utils.types.NameValuePair;


public class NameValuePairTest {

    public static void main(String[] args) {
        System.out.println("Start: NameValuePairTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        NameValuePairTest test = new NameValuePairTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        final Car firstCar = new Car("Ford");
        NameValuePair pair = NameValuePair.newPair("Ford", firstCar);

        System.out.println(pair.toString());


        final Car getCar = pair.getValue();
        System.out.println(getCar.toString());

    }
}
