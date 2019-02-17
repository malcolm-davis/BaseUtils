package base.utils.test.performance;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomCheck {

    public static void main(String[] args) {
        System.out.println("Start: RandomCheck ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        RandomCheck test = new RandomCheck();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis()-start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        int min = 10;
        int max = 11;
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));
        System.out.println(ThreadLocalRandom.current().nextInt(min, max));

        System.out.println("----------------------------------------");
        final Random random = new Random();
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));
        System.out.println(random.nextInt(Long.valueOf(max-min).intValue()));

    }

}
