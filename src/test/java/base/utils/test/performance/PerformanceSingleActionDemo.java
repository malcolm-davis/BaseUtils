package base.utils.test.performance;

import base.utils.test.EventStatus;
import base.utils.test.PrintTestExecutionListener;
import base.utils.test.performance.EventDetailResult;
import base.utils.test.performance.TestAction;
import base.utils.test.performance.TestCaseExecutor;
import base.utils.test.performance.TestResults;
import base.utils.test.performance.TestThread;

public class PerformanceSingleActionDemo {

    public static void main(final String[] args) {
        System.out.println("Start: PerformanceSingleActionDemo");
        System.out.println("---------------------------------------------");

        final long start = System.currentTimeMillis();

        final PerformanceSingleActionDemo test = new PerformanceSingleActionDemo();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        ///////////////////////////////////////////////////////////////////////////////////////////
        // Determine how you want the class to be executed.
        final String testName = "service call test";

        final int numberOfThreads = 10;

        final int numberOfActionsPerThread = 50;

        final long maxRuntime = -1; // no limit on the runtime

        final long delayBetweenActionsInMilliseconds = 50;

        final boolean useRandomDelay = true;


        ///////////////////////////////////////////////////////////////////////////////////////////
        // 1 action, multiple threads against the action
        final TestCaseExecutor load = TestCaseExecutor.newTest(testName, numberOfActionsPerThread, maxRuntime,
            delayBetweenActionsInMilliseconds, useRandomDelay);

        load.run(new TestActionImpl(), numberOfThreads, new PrintTestExecutionListener());

        // display results
        for (TestThread result : load.getTestList()) {
            System.out.println(result.toString());
            for( EventDetailResult details : result.getResults() ) {
                System.out.println("\t" + details.toString());
            }
        }

        TestResults results = load.getResults();
        System.out.println("Total runtime = " + results.getTestDuration());
        System.out.println("Average thread execution time= " + results.getAverageThreadTime());

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // Test: This is the test that will be executed.   The action() contains the code to be executed
    class  TestActionImpl implements TestAction {
        @Override
        public EventStatus action(int threadId, int count, final StringBuffer optionalResultMessage) {
            //-----------------------------------------------
            // The count corresponds to the number of times this method has been called.
            // count is useful for things like logging or pull records from a list or file.

            //-----------------------------------------------
            // do something here.
            optionalResultMessage.append("complete");

            // The thread is to simulate activity
            try {
                Thread.sleep(20);
            } catch (InterruptedException excep) {
                excep.printStackTrace();
            }

            //-----------------------------------------------
            // return the status or message of the test.
            return EventStatus.Success;
        }

        @Override
        public void cleanup() {
            // do some clean up here, such as closing database connections.
        }
    }
}
