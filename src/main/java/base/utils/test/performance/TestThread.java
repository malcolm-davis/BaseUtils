package base.utils.test.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import base.utils.test.EventStatus;
import base.utils.test.NullTestExecutionListener;
import base.utils.test.TestEvent;
import base.utils.test.TestExecutionListener;

public class TestThread extends Thread {

    public TestThread(final String name, final int threadId, final int maxIterations, long maxRunTime,
            final long millisDelay, final boolean randomDelay, final TestAction runAction, TestExecutionListener testListener) {
        m_name = name;
        m_threadId = threadId;
        m_maxIterations = maxIterations;
        m_maxRunTime = maxRunTime;
        m_delay = millisDelay;
        m_randomDelay = randomDelay;
        m_runAction = runAction;
        m_testListener = ( testListener==null ) ? new NullTestExecutionListener() : testListener ;
    }

    public void pauseTest() {
        pause = true;
    }

    public void resumeTest() {
        pause = false;
    }

    public void cancelTest() {
        execute = false;
    }

    public boolean isRunning() {
        return m_isRunning;
    }

    /** {@inheritDoc} */
    @Override
    public void run() {
        m_startTime = System.currentTimeMillis();

        final StringBuffer optionalDescription = new StringBuffer();

        m_isRunning = true;

        while(execute) {

            while(pause) {
                try { Thread.sleep(100); } catch (InterruptedException excep) {}
            }

            // increment count and check count if using counter for testing
            m_executionCount++;
            if( m_maxIterations>0  ) {
                if( m_executionCount > m_maxIterations ) {
                    execute = false;
                    break;
                }
            }

            // check run time if max run time is configured
            if( m_maxRunTime>0  ) {
                long totalRunTime = System.currentTimeMillis() - m_startTime;
                if( totalRunTime>=m_maxRunTime  ) {
                    System.out.println("totalRunTime>=m_maxRunTime : totalRunTime=" + totalRunTime + ".  m_maxRunTime="+m_maxRunTime );
                    execute = false;
                    break;
                }
            }

            final long start = System.currentTimeMillis();

            m_testListener.event(TestEvent.Start, EventStatus.Success, m_threadId, m_executionCount, 0, 0);

            final EventStatus status = m_runAction.action(m_threadId, m_executionCount, optionalDescription);

            final long runtime = (System.currentTimeMillis() - start);

            m_testExecutionTime += runtime;

            final long actionTimein = (System.currentTimeMillis() - m_startTime);

            m_testListener.event(TestEvent.End, status, m_threadId,  m_executionCount,  runtime, actionTimein);

            EventDetailResult results = EventDetailResult.newResult(m_threadId, m_executionCount, runtime, status,
                optionalDescription.toString());

            getResults().add(results);

            optionalDescription.setLength(0);

            if (m_delay > 0) {
                try {
                    if (m_randomDelay) {
                        // should the delay be a random value?
                        final Random random = new Random();
                        final long pause = random.nextInt(Long.valueOf(m_delay).intValue());
                        Thread.sleep(pause);
                    } else {
                        Thread.sleep(m_delay);
                    }
                } catch (final InterruptedException excep) {
                }
            }
        }

        m_isRunning = false;
        m_endTime = System.currentTimeMillis();
    }

    /**
     * <p>getResults.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<EventDetailResult> getResults() {
        if (m_results == null) {
            m_results = new ArrayList<EventDetailResult>();
        }
        return m_results;
    }

    /**
     * <p>cleanup.</p>
     */
    public void cleanup() {
        m_runAction.cleanup();
    }

    /**
     * <p>The number of times that the thread executed.</p>
     * <p>This could be less than the total count if a timeframe limit (m_totalRuntime) has been used.</p>
     *
     * @return a int.
     */
    public int executionCount() {
        return m_executionCount;
    }

    /**
     * <p>totalTestExecutionTime is the total of all test, and does not include pauses or thread sleeps.
     * TestExecutionTime and thread time should be different.
     * </p>
     *
     * @return a long.
     */
    public long totalTestExecutionTime() {
        return m_testExecutionTime;
    }

    /**
     * <p>averageRuntime is the totalTestExecutionTime divided by the total execution count.</p>
     *
     * @return a long.
     */
    public long averageRuntime() {
        return m_testExecutionTime / m_executionCount;
    }

    /**
     * <p>getTestThreadTime.</p>
     *
     * @return a long.
     */
    public long getThreadRuntime() {
        return m_endTime - m_startTime;
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "TestThread[id=" + m_threadId
                + ",  startTime=" + m_startTime
                + ",  endTime=" + m_endTime
                + ",  threadRuntime =" + getThreadRuntime()
                + ",  testExecutionTime=" + m_testExecutionTime
                + ",  executionCount=" + m_executionCount
                + ",  averageRuntime=" + averageRuntime()
                + "]";
    }

    private List<EventDetailResult> m_results;

    private final boolean m_randomDelay;

    private final int m_threadId;

    private final String m_name;

    private final TestAction m_runAction;

    private final int m_maxIterations;

    private final long m_delay;

    private long m_testExecutionTime;

    private long m_startTime;

    private long m_endTime;

    private boolean m_isRunning;

    private final long m_maxRunTime;

    private int m_executionCount;

    private final TestExecutionListener m_testListener;

    private volatile boolean pause;

    private volatile boolean execute = true;
}
