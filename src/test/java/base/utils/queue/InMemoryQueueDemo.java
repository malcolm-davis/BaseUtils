package base.utils.queue;

import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class InMemoryQueueDemo {

    public static void main(String[] args) {
        System.out.println("Start: InMemoryQueueDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        InMemoryQueueDemo test = new InMemoryQueueDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        LinkedBlockingDeque <String> queue = new LinkedBlockingDeque<String>();

        for(int i = 0; i < 100; i++) {
            queue.add(""+i);
        }

        Vector<WorkerThread>workerList = new Vector<WorkerThread>();
        for( int cnt=0; cnt<10; cnt++) {
            workerList.add(new WorkerThread(cnt, queue));
        }

        for(WorkerThread worker : workerList) {
            worker.start();
        }

        // Wait for all the workers to finish
        for(WorkerThread worker : workerList) {
            try {
                worker.join();
            } catch (InterruptedException excep) {
                excep.printStackTrace();
            }
        }
    }

    public class WorkerThread extends Thread {

        WorkerThread(int workerId, BlockingQueue<String> queue) {
            m_workerId = workerId;
            m_queue = queue;
        }

        @Override
        public void run() {
            while( m_run && !m_queue.isEmpty() ) {
                String data = m_queue.poll();
                if (data == null) {
                    break;
                }
                System.out.println("worker="+ m_workerId + ", data="+data);

                try { Thread.sleep(250); } catch (InterruptedException excep) { excep.printStackTrace(); }
            }
        }

        boolean m_run = true;

        private final BlockingQueue<String> m_queue;

        private final int m_workerId;
    }

}
