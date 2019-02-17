package base.utils.cache;

/**
 * <p>Element class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class Element<I, T>   {

    /**
     * <p>Constructor for Element.</p>
     *
     * @param id a I object.
     * @param item a T object.
     */
    public Element(final I id, final T item) {
        m_id = id;
        m_item = item;
        m_lastAccessed = System.currentTimeMillis();
    }

    /**
     * <p>getKey.</p>
     *
     * @return a I object.
     */
    public I getKey() {
        return m_id;
    }

    /**
     * <p>getObjectValue.</p>
     *
     * @return a T object.
     */
    public T getObjectValue() {
        m_lastAccessed = System.currentTimeMillis();
        return m_item;
    }

    /**
     * <p>setObjectValue.</p>
     *
     * @param item a T object.
     */
    public void setObjectValue(final T item) {
        m_lastAccessed = System.currentTimeMillis();
        m_item = null; // force a clear of the previous reference.
        m_item = item;
    }

    /**
     * <p>elapsedInMilliseconds.</p>
     *
     * @return a long.
     */
    public long elapsedInMilliseconds() {
        return (System.currentTimeMillis()-m_lastAccessed);
    }

    /**
     * <p>elapsedInSeconds.</p>
     *
     * @return a long.
     */
    public long elapsedInSeconds() {
        return (System.currentTimeMillis()-m_lastAccessed)/1000;
    }

    private long m_lastAccessed;

    private T m_item;

    private final I m_id;
}
