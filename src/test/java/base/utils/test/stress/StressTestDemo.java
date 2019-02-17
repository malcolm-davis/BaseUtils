package base.utils.test.stress;

import base.utils.test.stress.StressAction;
import base.utils.test.stress.StressResultsSummary;
import base.utils.test.stress.StressTest;
import base.utils.test.stress.StressThead;

public class StressTestDemo {

    public static void main(final String[] args) {
        System.out.println("Start: StressTestDemo");
        System.out.println("---------------------------------------------");

        final long start = System.currentTimeMillis();

        final StressTestDemo test = new StressTestDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        ///////////////////////////////////////////////////////////////////////////////////////////
        // Test: This is the test that will be executed.   The action() contains the code to be executed
        final  StressAction action = new StressAction() {
            @Override
            public String action(int threadId, int count) {
                //-----------------------------------------------
                // The count corresponds to the number of times this method has been called.
                // count is useful for things like logging or pull records from a list or file.

                //-----------------------------------------------
                // do something here.
                final String statusFlag = "complete";

                // The thread is to simulate activity
                try {
                    Thread.sleep(10);
                } catch (InterruptedException excep) {
                    excep.printStackTrace();
                }

                //-----------------------------------------------
                // return the status or message of the test.
                return statusFlag;
            }

            @Override
            public void cleanup() {
                // do some clean up here, such as closing database connections.
            }
        };

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Determine how you want the class to be executed.
        final String testName = "service call test";

        final int numberOfThreads = 150;

        final int numberOfActionsPerThread = 50;

        final long minDelayBetweenAction = 50;

        final long maxDelayBetweenAction = 50;

        final StressTest load = StressTest.newTest();
        load.setDisplayDuringExecution(true);
        load.run(testName, numberOfThreads, numberOfActionsPerThread,
            minDelayBetweenAction, maxDelayBetweenAction, action);

        ///////////////////////////////////////////////////////////////////////////////////////////
        // display results
        for (StressThead result : load.getStressThreads()) {
            System.out.println(result.toString());
            for( StressResultsSummary details : result.getResults() ) {
                System.out.println("\t" + details.toString());
            }
        }
        System.out.println("Total runtime = " + load.getTotalRuntime());
        System.out.println("Average thread execution time= " + load.getAverageThreadTime());
    }
}
