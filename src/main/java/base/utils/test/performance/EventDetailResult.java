package base.utils.test.performance;

import base.utils.test.EventStatus;

/**
 * <p>EventStepResult class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class EventDetailResult {

    /**
     *
     * @param threadId the unique id for the thread
     * @param count the unique sequence number for the test in the execution count of the thread
     * @param duration the duration of the test
     * @param eventStatus the status of the test
     * @param description optional description.
     * @return a {@link org.elasolutions.utils.test.performance.EventDetailResult} object.
     */
    public static EventDetailResult newResult(final int threadId, final int count, final long duration,
            EventStatus eventStatus, final String description) {
        return new EventDetailResult(threadId, count, duration, eventStatus, description);
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
    public long getDuration() {
        return m_duration;
    }

    /**
     * <p>getStatus.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDescription() {
        return m_description;
    }

    /**
     *
     * @return
     * EventResult
     */
    public EventStatus getStatus() {
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
        return "[status=" +m_status + ",  count=" + m_count + ",  runtime=" + m_duration + ",  description="+m_description + "]";
    }

    EventDetailResult(final int threadId, final int count, final long duration,
        final EventStatus result, final String status) {
        m_threadId = threadId;
        m_count = count;
        m_duration = duration;
        m_status = result;
        m_description = status;
    }

    private final int m_count;

    private final long m_duration;

    private final String m_description;

    private final EventStatus m_status;

    private final int m_threadId;
}
