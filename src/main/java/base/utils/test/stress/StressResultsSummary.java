package base.utils.test.stress;

/**
 * <p>StressResults class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class StressResultsSummary {

    /**
     * <p>newResult.</p>
     *
     * @param threadId a int.
     * @param count a int.
     * @param timeIn a long.
     * @param status a {@link java.lang.String} object.
     * @return a {@link org.elasolutions.utils.test.stress.StressResultsSummary} object.
     */
    public static StressResultsSummary newResult(final int threadId, final int count, final long timeIn,
            final String status) {
        return new StressResultsSummary(threadId, count, timeIn, status);
    }

    /**
     * <p>getCount.</p>
     *
     * @return a int.
     */
    public int getCount() {
        return m_count;
    }

    /**
     * <p>getExcutetime.</p>
     *
     * @return a long.
     */
    public long getExcutetime() {
        return m_excutetime;
    }

    /**
     * <p>getStatus.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStatus() {
        return m_status;
    }

    /**
     * <p>getThreadId.</p>
     *
     * @return a int.
     */
    public int getThreadId() {
        return m_threadId;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "status=" +m_status + ",  count=" + m_count + ",  runtime=" + m_excutetime;
    }

    StressResultsSummary(final int threadId, final int count, final long executeTime,
        final String status) {
        m_threadId = threadId;
        m_count = count;
        m_excutetime = executeTime;
        m_status = status;
    }

    private final int m_count;

    private final long m_excutetime;

    private final String m_status;

    private final int m_threadId;
}
