package base.utils.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;


/**
 * An alternative to net.sf.ehcache.  I personally have had operational issues with net.sf.ehcache in some environments.<br><br>
 *
 * There are optional constructor parameters for listening to changes or adjusting time.
 * The pooling timer for the cache can also be adjusted.
 *
 *<pre>
 * int optionalElementTimeout = 1000; // when the item should timeout and be pulled from the cache.
 * SimpleCache&lt;Integer, Car&gt; store = new SimpleCache&lt;Integer, Car&gt;(&quot;Unique name for the cache&quot;, optionalElementTimeout);
 * ...
 * Integer key = Integer.valueOf(1);
 * Car mustang = new Car(&quot;Mustang&quot;, Color.RED);
 * store.put(key, mustang);
 * ....
 * // no need for casting
 * Car car = store.get(key);
 * </pre>
 *
 * @author Malcolm G. Davis
 * @version 1.0
 * @param <I> Index type (String, Integer, etc..)
 * @param <T> Type of the object to store
 */
public class SimpleCache<I, T> {

    /**
     * <p>Constructor for SimpleCache.</p>
     *
     * @param uniqueCacheName a {@link java.lang.String} object.
     */
    public SimpleCache(final String uniqueCacheName) {
        this(uniqueCacheName, DEFAULT_TIMEOUT, null);
    }

    /**
     * <p>Constructor for SimpleCache.</p>
     *
     * @param uniqueCacheName must be a unique name for the cache
     * @param expire the minimum amount of time in milliseconds that the object should stay in cache
     */
    public SimpleCache(final String uniqueCacheName, final long expire) {
        this(uniqueCacheName, expire, null);
    }

    /**
     * <p>Constructor for SimpleCache.</p>
     *
     * @param uniqueCacheName  must be a unique name for the cache
     * @param expire the minimum amount of time in milliseconds that the object should stay in cache if not updated
     * @param callback provides messages when an object has been removed from cache
     */
    public SimpleCache(final String uniqueCacheName, final long expire, SimpleCacheCallback<I, T> callback) {
        this(uniqueCacheName, expire, callback, (expire * 2));
    }

    /**
     * <p>Constructor for SimpleCache.</p>
     *
     * @param uniqueCacheName  must be a unique name for the cache
     * @param expire the minimum amount of time in milliseconds that the object should stay in cache if not updated
     * @param period time in milliseconds between successive expire checks.
     */
    public SimpleCache(final String uniqueCacheName, final long expire, final long period) {
        this(uniqueCacheName, expire, null, period);
    }


    /**
     * <p>Constructor for SimpleCache.</p>
     *
     * @param uniqueCacheName  must be a unique name for the cache
     * @param expire the minimum amount of time in milliseconds that the object should stay in cache
     * @param callback a {@link org.elasolutions.utils.cache.SimpleCacheCallback} object.
     * @param period time in milliseconds between successive expire checks.
     */
    public SimpleCache(final String uniqueCacheName, final long expire, SimpleCacheCallback<I, T> callback, final long period) {
        m_uniqueCacheName = uniqueCacheName;
        if(callback==null ) {
            m_callback = ((SimpleCacheCallback<I, T>) new SimpleCacheCallbackNull());
        } else {
            m_callback = callback;
        }
        m_expireTimeout = expire;
        m_delay = period;

        m_cleanUpTimer = new Timer();
        m_cleanUpTimer.scheduleAtFixedRate(new EntryCleanupTask(this), m_delay, m_delay);
    }

    public void close() {
        if(m_cleanUpTimer!=null) {
            m_cleanUpTimer.cancel();
        }
    }

    /**
     * Expires all elements in the cache, and remove all the elements.
     * Will notify the listeners of element removed from the cache.
     * void
     */
    public void expireAll() {
        final Collection<Element<I, T>> elements = getCache().values();
        for(final Element<I, T>element : elements ) {
            m_callback.removedElement(element);
            remove(element.getKey());
        }
    }


    /**
     * <p>get.</p>
     *
     * @param key a I object.
     * @return a T object.
     */
    public T get(final I key) {
        T item = null;
        if (key != null) {
            final Element<I, T> element = getCache().get(key);
            if (element != null) {
                item = element.getObjectValue();
            }
        }
        return item;
    }


    /**
     * <p>put.</p>
     *
     * @param id a I object.
     * @param item a T object.
     * @return a T object.
     */
    public T put(final I id, final T item) {
        if( id==null || item==null) {
            return item;
        }
        Element<I, T> element = getCache().get(id);
        if (element == null) {
            element = new Element<I, T>(id, item);
            getCache().put(id, element);
        } else {
            element.setObjectValue(item);
        }
        return item;
    }

    /**
     * <p>remove.</p>
     *
     * @param key a I object.
     */
    public void remove(final I key) {
        Element<I, T>element = getCache().remove(key);
        if(element!=null) {
            element.setObjectValue(null);
            element = null;
        }
    }

    /**
     * <p>update.</p>
     *
     * @param id a I object.
     * @param item a T object.
     */
    public void update(final I id, final T item) {
        put(id, item);
    }

    private Map<I,Element<I, T>> getCache() {
        if(m_map==null) {
            m_map = new ConcurrentHashMap<I,Element<I, T>>();
        }
        return m_map;
    }

    void removeExpired() {
        final Collection<Element<I, T>> elements = getCache().values();
        for(final Element<I, T>element : elements ) {
            if( element.elapsedInMilliseconds() > getTimeout() ) {
                m_callback.removedElement(element);
                remove(element.getKey());
            }
        }
    }

    /**
     * <p>getCacheName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCacheName() {
        return m_uniqueCacheName;
    }

    /**
     * <p>getTimeout.</p>
     *
     * @return a long.
     */
    public long getTimeout() {
        return m_expireTimeout;
    }

    /**
     * <p>setTimeout.</p>
     *
     * @param expire the minimum amount of time in milliseconds that the object should stay in cache
     * void
     */
    public void setTimeout(final long expire) {
        m_expireTimeout = expire;
    }

    /**
     * <p>setExpireCacheCheck.</p>
     *
     * @param delayBetweenChecks a long.
     */
    public void setExpireCacheCheck(final long delayBetweenChecks) {
        m_delay = delayBetweenChecks;
        m_cleanUpTimer.scheduleAtFixedRate(new EntryCleanupTask(this), m_delay, m_delay);
    }

    /**
     * EntryCleanupTask is a simple TimerTask to clean expired entries
     *
     * @author Malcolm G. Davis
     * @version 1.0
     */
    class EntryCleanupTask extends TimerTask {

        public EntryCleanupTask(final SimpleCache<?, ?>  cache) {
            m_cache = cache;
        }

        @Override
        public void run() {
            getCache().removeExpired();
        }

        public SimpleCache<?, ?> getCache() {
            return m_cache;
        }

        private final SimpleCache<?, ?>  m_cache;
    }


    private SimpleCacheCallback<I, T> m_callback;

    private final Timer m_cleanUpTimer;

    private long m_delay;

    private static final long DEFAULT_TIMEOUT = 60 * 1000;

    private long m_expireTimeout = DEFAULT_TIMEOUT;

    private final String m_uniqueCacheName;

    Map<I,Element<I, T>> m_map;
}
