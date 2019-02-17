package base.utils.test.performance;

import java.util.ArrayList;
import java.util.List;

public class TestResults {

    public static TestResults newResults(final String name,
            final int numberOfThreads, final int maxIterationsPerThread,
            final long maxRunTime) {
        return new TestResults(name, numberOfThreads, maxIterationsPerThread, maxRunTime);
    }

    public void addTestThread(final TestThread thread) {
        getTestList().add(thread);
    }

    /**
     * <p>
     * getAverageThreadTime.
     * </p>
     *
     * @return a long.
     */
    public long getAverageThreadTime() {
        return m_averageThreadTimes;
    }

    public long getEndTime() {
        return m_endTime;
    }

    public int getMaxIterationsPerThread() {
        return m_maxIterationsPerThread;
    }

    public long getMaxRunTime() {
        return m_maxRunTime;
    }

    public String getName() {
        return m_name;
    }

    public int getNumberOfThreads() {
        return m_numberOfThreads;
    }

    public long getStartTime() {
        return m_startTime;
    }

    public long getTestDuration() {
        return m_endTime - m_startTime;
    }

    /**
     * <p>
     * getTestList.
     * </p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<TestThread> getTestList() {
        if (m_testsList == null) {
            m_testsList = new ArrayList<>();
        }
        return m_testsList;
    }

    public void setAverageThreadTime(final long averageThreadTimes) {
        m_averageThreadTimes = averageThreadTimes;

    }

    public void setEndTime(final long end) {
        m_endTime = end;
    }

    public void setStartTime(final long start) {
        m_startTime = start;
    }

    private TestResults(final String name, final int numberOfThreads,
            final int maxIterationsPerThread, final long maxRunTime) {
        m_name = name;
        m_numberOfThreads = numberOfThreads;
        m_maxIterationsPerThread = maxIterationsPerThread;
        m_maxRunTime = maxRunTime;
    }

    private final String m_name;

    private final int m_numberOfThreads;

    private final int m_maxIterationsPerThread;

    private final long m_maxRunTime;

    private long m_startTime;

    private long m_endTime;

    long m_averageThreadTimes;

    private List<TestThread> m_testsList;
}
