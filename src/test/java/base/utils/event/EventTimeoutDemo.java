package base.utils.event;

import java.util.concurrent.TimeUnit;

import base.utils.cache.SimpleCache;
import base.utils.event.EventTimeoutListener;
import base.utils.event.EventTimeoutTracker;

public class EventTimeoutDemo {

    public static void main(final String[] args) {
        System.out.println("Start: EventTimeoutDemo");
        System.out.println("---------------------------------------------");

        final long start = System.currentTimeMillis();

        final EventTimeoutDemo test = new EventTimeoutDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    SimpleCache<String, String> m_batchProcessing;

    long m_start;

    private void run() {
        // m_tracker = EventTimeoutTracker.newTracker(1, 1, new EventTimeoutListener() {
        m_tracker = EventTimeoutTracker.newTracker(1000, TimeUnit.MILLISECONDS,
            500, TimeUnit.MILLISECONDS, new EventTimeoutListener() {
            @Override
            public void expired() {
                System.out.println("EXPIRED");
            }
        });

        TestThread thread = new TestThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException excep) {
            excep.printStackTrace();
        }
        System.out.println("Finished");
    }

    class TestThread extends Thread {
        @Override
        public void run() {
            m_start = System.currentTimeMillis();
            System.out.println("Processing 1 - timeout after processing");
            for(int i = 0; i<10; i++) {
                System.out.print(".");
                m_tracker.update();
                try { Thread.sleep(200); } catch (final InterruptedException excep) { }
            }
            System.out.println("\r\nProcessing 1 - finished");

            try { Thread.sleep(2000); } catch (final InterruptedException excep) { }

            System.out.println("\r\nProcessing 2 = Should never expire");
            for(int i = 0; i<10; i++) {
                System.out.print(".");
                m_tracker.update();
                try { Thread.sleep(400); } catch (final InterruptedException excep) { }
            }
            System.out.println("\r\nProcessing 2 - finished");

            System.out.println("\r\nProcessing 3 = continuos timeout");
            for(int i = 0; i<5; i++) {
                System.out.print(".");
                m_tracker.update();
                try { Thread.sleep(2000); } catch (final InterruptedException excep) { }
            }

            System.out.println("\rLast element called at: " + (System.currentTimeMillis() - m_start));
            m_tracker.update();
            try { Thread.sleep(10000); } catch (final InterruptedException excep) { }

            m_tracker.close();
        }
    }


    EventTimeoutTracker m_tracker;
}
