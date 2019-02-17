package base.utils.test.performance;

import java.util.ArrayList;
import java.util.List;

import base.utils.test.EventStatus;
import base.utils.test.TestEvent;
import base.utils.test.TestExecutionListener;
import base.utils.test.performance.EventDetailResult;
import base.utils.test.performance.TestAction;
import base.utils.test.performance.TestCaseExecutor;
import base.utils.test.performance.TestResults;
import base.utils.test.performance.TestThread;

public class PerformanceIncrementLoadDemo {

    public static void main(final String[] args) {
        System.out.println("Start: PerformanceIncrementLoadDemo");
        System.out.println("---------------------------------------------");

        final long start = System.currentTimeMillis();

        final PerformanceIncrementLoadDemo test = new PerformanceIncrementLoadDemo();
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

        final long maxRuntime = 5000;

        final long delayBetweenActionsInMilliseconds = 50;

        final boolean useRandomDelay = false;

        final long incrementalLoadTime = 2000;

        // 10 actions, 10 threads, 1 thread per action
        List<TestAction>actions = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            actions.add(new TestActionImpl());
        }

        final TestCaseExecutor load2 = TestCaseExecutor.newTest(testName, numberOfActionsPerThread, maxRuntime,
            delayBetweenActionsInMilliseconds, useRandomDelay);
        load2.run(actions, new TestExecutionListener() {
            @Override
            public void event(TestEvent event, EventStatus status, int threadId,
                    int executionCount, long runtime, long totalThreadRuntime) {
                if( TestEvent.End.equals(event) ) {
                    System.out.printf("Executed: id=%4d,  cnt=%4d,  %s,  runtime=%d\r\n",
                        Integer.valueOf(threadId), Integer.valueOf(executionCount), status, Long.valueOf(runtime));
                }
            }
        }, incrementalLoadTime);

        // display results
        for (TestThread result : load2.getTestList()) {
            System.out.println(result.toString());
            for( EventDetailResult details : result.getResults() ) {
                System.out.println("\t" + details.toString());
            }
        }

        TestResults results2 = load2.getResults();
        System.out.println("Total runtime = " + results2.getTestDuration());
        System.out.println("Average thread execution time= " + results2.getAverageThreadTime());
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
                Thread.sleep(500);
            } catch (InterruptedException excep) {
                excep.printStackTrace();
            }

            //-----------------------------------------------
            // return the status or message of the test.
            if( (count%3)==0 ) {
                return EventStatus.Failure;
            }
            return EventStatus.Success;
        }

        @Override
        public void cleanup() {
            // do some clean up here, such as closing database connections.
        }
    }
}
