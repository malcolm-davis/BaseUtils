package base.utils.event;

import java.io.Closeable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventTimeoutTracker implements Closeable {

    public static EventTimeoutTracker newTracker(final long timeout,
            TimeUnit timeoutUnits, final long delayBetweenChecks, TimeUnit checkUnits,
            EventTimeoutListener listener) {
        return new EventTimeoutTracker(timeout, timeoutUnits, delayBetweenChecks, checkUnits, listener);
    }

    public static EventTimeoutTracker newTracker(final long timeoutSeconds,
            final long delayBetweenChecksSeconds, EventTimeoutListener listener) {
        return newTracker(timeoutSeconds, TimeUnit.SECONDS,
            delayBetweenChecksSeconds, TimeUnit.SECONDS, listener);
    }

    @Override
    public void close() {
        if(scheduler!=null) {
            scheduler.shutdown();
            scheduler = null;
        }
    }

    /**
     * Updates the expiration timeout
     */
    public void update() {
        m_lastAccessed = System.currentTimeMillis();
    }

    public long elapsedInMilliseconds() {
        return (System.currentTimeMillis()-m_lastAccessed);
    }

    /**
     * Checks for an expired timeout
     */
    void checkExpired() {
        if(m_lastAccessed>0) {
            if( elapsedInMilliseconds() > m_timeoutMilliseconds ) {
                m_listener.expired();
                m_lastAccessed = 0;
            }
        }
    }

    private EventTimeoutTracker(long timeout, TimeUnit timeoutUnits,
            long delayBetweenChecks, TimeUnit checkUnits, EventTimeoutListener listener) {
        m_listener = listener;
        m_timeoutMilliseconds = timeoutUnits.toMillis(timeout);
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new EventTimeoutTask(this), delayBetweenChecks, delayBetweenChecks, checkUnits);
    }


    /**
     * EventTimeoutTask TimerTask to check for expired event
     *
     * @author Malcolm G. Davis
     * @version 1.0
     */
    class EventTimeoutTask implements Runnable {

        private final EventTimeoutTracker m_callBack;

        public EventTimeoutTask(EventTimeoutTracker listener) {
            m_callBack = listener;
        }

        @Override
        public void run() {
            m_callBack.checkExpired();
        }
    }

    private long m_lastAccessed=0;

    private final long m_timeoutMilliseconds;

    private ScheduledExecutorService scheduler;

    private final EventTimeoutListener m_listener;
}
