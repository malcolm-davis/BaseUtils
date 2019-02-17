package base.utils.test.performance;

import java.util.ArrayList;
import java.util.List;

import base.utils.test.TestExecutionListener;

/**
 * <p>Controls the creation and execution of the test.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class TestCaseExecutor {

    /**
     * Create a new test.
     * @param name name of the test
     * @param maxIterationsPerThread max number of test iterations. (optional.  0 means no limit)
     * @param maxRuntime max runtime of the individual test threads.  (optional.  0 means no limit)
     * @param delayBetweenActionsInMilliseconds between executions
     * @param random if the delay should have a random
     * @return a {@link org.elasolutions.utils.test.performance.TestCaseExecutor} object.
     */
    public static TestCaseExecutor newTest(final String name,
            final int maxIterationsPerThread,
            final long maxRuntime,
            final long delayBetweenActionsInMilliseconds,
            final boolean random) {

        if( maxRuntime<=0l && maxIterationsPerThread<=0 ) {
            throw new IllegalArgumentException(
                "Neither maxIterationsPerThread nor maxRunTime are configured. "
                        + "One or both fields must be configured.");
        }
        return new TestCaseExecutor(name, maxIterationsPerThread, maxRuntime, delayBetweenActionsInMilliseconds, random);
    }

    /**
     *
     * Multiple threads using a single action.
     *
     * @param action to execute
     * @param numberOfThreads to create against the single action.
     * @param testListener listen to test event during execution
     * void
     */
    public void run(final TestAction action, final int numberOfThreads, TestExecutionListener testListener) {
        if(action==null) {
            throw new IllegalArgumentException("Null value for action");
        }
        if(numberOfThreads<1) {
            throw new IllegalArgumentException("Invalid numberOfThreads value="+numberOfThreads);
        }
        // configure testing
        for(int count = 0; count < numberOfThreads; count++) {
            final TestThread thread = new TestThread(m_name, count, m_maxIterationsPerThread,
                m_maxRuntime, m_delayBetweenActionsInMilliseconds, m_random, action, testListener);
            getTestList().add(thread);
        }

        executeTest(0);
    }

    /**
     * A single thread per-action.
     *
     * @param actions to execute.  A thread will be created for each action in the list.
     * @param testListener listen to test event during execution
     * void
     */
    public void run(final List<TestAction>actions, TestExecutionListener testListener) {
        if(actions==null) {
            throw new IllegalArgumentException("Null value for actions");
        }
        if(actions.isEmpty()) {
            throw new IllegalArgumentException("No actions to execute");
        }

        run(actions, testListener, 0);
    }

    /**
     * A single thread per-action.
     *
     * @param actions to execute.  A thread will be created for each action in the list.
     * @param testListener listen to test event during execution
     * @param incrementalLoadTime incremental load time, rather than all at once
     * void
     */
    public void run(final List<TestAction>actions, TestExecutionListener testListener, long incrementalLoadTime) {
        if(actions==null) {
            throw new IllegalArgumentException("Null value for actions");
        }
        if(actions.isEmpty()) {
            throw new IllegalArgumentException("No actions to execute");
        }

        // configure testing
        for(int count = 0; count < actions.size(); count++) {
            TestAction actionToUse = actions.get(count);
            final TestThread thread =
                    new TestThread(m_name, count, m_maxIterationsPerThread,
                        m_maxRuntime, m_delayBetweenActionsInMilliseconds, m_random, actionToUse, testListener);
            getTestList().add(thread);
        }

        executeTest(incrementalLoadTime);
    }


    public void pauseTest() {
        for (final TestThread thread : getTestList()) {
            thread.pauseTest();
        }
    }

    public void resumeTest() {
        for (final TestThread thread : getTestList()) {
            thread.resumeTest();
        }
    }

    public void cancelTest() {
        for (final TestThread thread : getTestList()) {
            thread.cancelTest();
        }
    }

    /**
     * <b>Important</b>  This is only useful after calling cancelTest().
     * This method should not be used during normal operations.
     *
     * void
     */
    public void waitForCancel() {
        for (final TestThread thread : getTestList()) {
            try { thread.join(); } catch (InterruptedException excep) {}
        }
    }

    /**
     * Returns true if any test thread is still running.
     * @return
     * boolean
     */
    public boolean isRunning() {
        for (final TestThread thread : getTestList()) {
            if(thread.isRunning()) {
                return true;
            }
        }
        return false;
    }

    void executeTest(long incrementalLoadTime) {
        //----------------------------------------------------------------------------
        // begin test
        getResults().setStartTime(System.currentTimeMillis());
        for (final TestThread thread : getTestList()) {
            thread.start();
            if(incrementalLoadTime>0) {
                try { Thread.sleep(incrementalLoadTime); } catch (InterruptedException excep) {}
            }
        }

        //----------------------------------------------------------------------------
        // wait for test to finish
        for (final TestThread thread : getTestList()) {
            try {
                thread.join();
            } catch (final InterruptedException excep) {
                excep.printStackTrace();
            }
        }
        getResults().setEndTime(System.currentTimeMillis());

        //----------------------------------------------------------------------------
        // record results
        long totalAverageThreadTimes = 0;
        int cnt = 1;
        for (final TestThread thread : getTestList()) {
            totalAverageThreadTimes = totalAverageThreadTimes + thread.averageRuntime();
            getResults().addTestThread(thread);
            cnt++;
        }
        getResults().setAverageThreadTime((totalAverageThreadTimes / (cnt - 1)));

        //----------------------------------------------------------------------------
        // ask the tests cleanup
        for (final TestThread thread : getTestList()) {
            thread.cleanup();
        }
    }

    public TestResults getResults() {
        if( m_testResults==null ) {
            m_testResults = TestResults.newResults(m_name, getTestList().size(), m_maxIterationsPerThread, m_maxRuntime);
        }
        return m_testResults;
    }

    /**
     * <p>getTestList.</p>
     *
     * @return a {@link java.util.List} object.
     */
    List<TestThread> getTestList() {
        if (m_testsList == null) {
            m_testsList = new ArrayList<TestThread>();
        }
        return m_testsList;
    }

    private List<TestThread> m_testsList;

    private TestResults m_testResults;

    private final String m_name;

    private final int m_maxIterationsPerThread;

    private final long m_maxRuntime;

    private final long m_delayBetweenActionsInMilliseconds;

    private final boolean m_random;

    private TestCaseExecutor(final String name,
            final int maxIterationsPerThread,
            final long maxRuntime,
            final long delayBetweenActionsInMilliseconds,
            final boolean random) {
        m_name = name;
        m_maxIterationsPerThread = maxIterationsPerThread;
        m_maxRuntime = maxRuntime;
        m_delayBetweenActionsInMilliseconds = delayBetweenActionsInMilliseconds;
        m_random = random;
    }

}
