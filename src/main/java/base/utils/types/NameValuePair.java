package base.utils.types;


/**
 * <p>NameValuePair class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class NameValuePair {

    /**
     * <p>newPair.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.Object} object.
     * @return a {@link org.elasolutions.utils.types.NameValuePair} object.
     */
    public static NameValuePair newPair(String name, Object value) {
        return new NameValuePair(name,value);
    }

    /**
     * <p>getName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return m_name;
    }

    /**
     * <p>getValue.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     */
    public <T> T  getValue() {
        return (T)m_value;
    }

    /**
     * <p>getObject.</p>
     *
     * @return a {@link java.lang.Object} object.
     */
    public Object getObject() {
        return m_value;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "NameValuePair [name=" + m_name + ", value=" + m_value + "]";
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((m_name == null) ? 0 : m_name.hashCode());
        result = prime * result + ((m_value == null) ? 0 : m_value.hashCode());
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NameValuePair other = (NameValuePair) obj;
        if (m_name == null) {
            if (other.m_name != null) {
                return false;
            }
        } else if (!m_name.equals(other.m_name)) {
            return false;
        }
        if (m_value == null) {
            if (other.m_value != null) {
                return false;
            }
        } else if (!m_value.equals(other.m_value)) {
            return false;
        }
        return true;
    }


    NameValuePair(String name, Object value) {
        m_name = name;
        m_value = value;

    }

    private final String m_name;

    private final Object m_value;

}
