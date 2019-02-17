package base.utils.cache;

import base.utils.cache.Element;
import base.utils.cache.SimpleCache;
import base.utils.cache.SimpleCacheCallback;

public class CacheBatchDemo implements SimpleCacheCallback<String, String> {

    public static void main(final String[] args) {
        System.out.println("Start: CacheBatchDemo");
        System.out.println("---------------------------------------------");

        final long start = System.currentTimeMillis();

        final CacheBatchDemo test = new CacheBatchDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    SimpleCache<String, String> m_batchProcessing;

    long m_start;

    private void run() {
        //final SimpleCache<Integer, Car> store = new SimpleCache<Integer, Car>(CacheTemplateDemo.class.getName(), 4, this);
        // final SimpleCache<Integer, Car> store = new SimpleCache<Integer, Car>(CacheTemplateDemo.class.getName());
        m_batchProcessing = new SimpleCache<String, String>("CategoryName", 10000, this, 5000);
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
            System.out.println("Processing batch 1");
            for(int i = 0; i<10; i++) {
                System.out.print(".");
                m_batchProcessing.put("1", "11111");
                m_batchProcessing.put("2", "22222");
                m_batchProcessing.put("3", "33333");
                m_batchProcessing.put("4", "44444");
                m_batchProcessing.put("5", "55555");
                m_batchProcessing.put("6", "66666");
                try { Thread.sleep(2000); } catch (final InterruptedException excep) { }
            }

            System.out.println("\r\nProcessing batch 2");
            for(int i = 0; i<10; i++) {
                System.out.print(".");
                m_batchProcessing.put("1", "11111");
                m_batchProcessing.put("3", "33333");
                m_batchProcessing.put("5", "55555");
                try { Thread.sleep(2000); } catch (final InterruptedException excep) { }
            }

            System.out.println("\r\nProcessing batch 3");
            for(int i = 0; i<10; i++) {
                System.out.print(".");
                m_batchProcessing.put("3", "33333");
                try { Thread.sleep(2000); } catch (final InterruptedException excep) { }
            }

            System.out.println("\rLast element called at: " + (System.currentTimeMillis() - m_start));
            try { Thread.sleep(10000); } catch (final InterruptedException excep) { }
        }
    }


    @Override
    public void removedElement(Element<String,String> element) {
        System.out.println("Removed at time=" + (System.currentTimeMillis() - m_start) + " ms");
        System.out.println("\tRemoved: key=" + element.getKey().toString() + ",  object=" + element.getObjectValue().toString());
    }
}
