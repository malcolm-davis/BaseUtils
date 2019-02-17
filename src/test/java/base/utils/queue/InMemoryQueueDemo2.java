package base.utils.queue;

import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class InMemoryQueueDemo2 {

    public static void main(String[] args) {
        System.out.println("Start: InMemoryQueueDemo ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        InMemoryQueueDemo2 test = new InMemoryQueueDemo2();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        // see https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/BlockingQueue.html
        LinkedBlockingDeque <String> queue = new LinkedBlockingDeque<String>();

        Vector<WorkerThread>workerList = new Vector<WorkerThread>();
        for( int cnt=0; cnt<10; cnt++) {
            workerList.add(new WorkerThread(cnt, queue));
        }

        for(WorkerThread worker : workerList) {
            worker.start();
        }

        for(int i = 0; i < 50; i++) {
            queue.offer(""+i);
        }

        while(true) {
            if( queue.isEmpty() ) {
                break;
            }
            // try { Thread.sleep(1000); } catch (InterruptedException excep) { }
        }


        for(int i = 0; i < 50; i++) {
            queue.offer(""+i);
        }

        while(true) {
            if( queue.isEmpty() ) {
                break;
            }
            //try { Thread.sleep(1000); } catch (InterruptedException excep) { }
        }


        // kill threads
        for(WorkerThread worker : workerList) {
            worker.end();
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
            while( m_run ) {
                try {
                    String data = m_queue.take();
                    if ( !m_run ) {
                        break;
                    }
                    System.out.println("worker="+ m_workerId + ", data="+data);

                    try { Thread.sleep(250); } catch (InterruptedException excep) { excep.printStackTrace(); }
                } catch (InterruptedException excep1) {
                    excep1.printStackTrace();
                }
            }
        }

        public void end() {
            m_run = false;
            try {
                m_queue.put("");
            } catch (InterruptedException excep) {
                excep.printStackTrace();
            }
        }

        boolean m_run = true;

        private final BlockingQueue<String> m_queue;

        private final int m_workerId;
    }

}
