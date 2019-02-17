package base.utils.cache;

import base.utils.cache.Element;
import base.utils.cache.SimpleCache;
import base.utils.cache.SimpleCacheCallback;

public class CacheTemplateDemo implements SimpleCacheCallback<Integer, Car> {

    public static void main(final String[] args) {
        System.out.println("Start: CacheTemplateDemo");
        System.out.println("---------------------------------------------");

        final long start = System.currentTimeMillis();

        final CacheTemplateDemo test = new CacheTemplateDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    SimpleCache<Integer, Car> m_store;

    private void run() {
        //final SimpleCache<Integer, Car> store = new SimpleCache<Integer, Car>(CacheTemplateDemo.class.getName(), 4, this);
        // final SimpleCache<Integer, Car> store = new SimpleCache<Integer, Car>(CacheTemplateDemo.class.getName());
        m_store = new SimpleCache<Integer, Car>(CacheTemplateDemo.class.getName(), 4000, this);
        m_store.setExpireCacheCheck(1000);
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
            final Integer keyOne = Integer.valueOf(1);
            m_store.put(keyOne, new Car(keyOne, "my car"));

            final Integer key2 = Integer.valueOf(2);
            m_store.put(key2, new Car(key2, "van"));

            Car car = m_store.get(keyOne);
            System.out.println(car.m_name);

            m_store.put(keyOne, new Car(keyOne, "my car update"));
            car = m_store.get(keyOne);
            System.out.println(car.m_name);

            try { Thread.sleep(3000); } catch (final InterruptedException excep) { }

            m_store.put(keyOne, new Car(keyOne, "my car update again"));
            car = m_store.get(keyOne);
            System.out.println(car.m_name);

            try { Thread.sleep(3000); } catch (final InterruptedException excep) { }

            m_store.put(keyOne, new Car(keyOne, "my car update again"));
            car = m_store.get(keyOne);
            System.out.println(car.m_name);

            try { Thread.sleep(10000); } catch (final InterruptedException excep) { }

            // careful, due to timeout, data
            car = m_store.get(key2);
            if( car!=null) {
                System.out.println(car.m_name);
            } else {
                System.out.println("No car corresponding to " + key2.toString());
            }
        }
    }


    @Override
    public void removedElement(Element element) {
        System.out.println("element removed: " + element.getKey().toString() + ": " + element.getObjectValue().toString());
    }
}
