package base.utils.types;


/**
 * <p>NameValuePairType class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class NameValuePairType {

    /**
     * <p>newPair.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.Object} object.
     * @param type a {@link org.elasolutions.utils.types.FieldTypes} object.
     * @return a {@link org.elasolutions.utils.types.NameValuePairType} object.
     */
    public static NameValuePairType newPair(String name, Object value, FieldTypes type) {
        return new NameValuePairType(name,value, type);
    }

    /**
     * <p>newPair.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.Object} object.
     * @return a {@link org.elasolutions.utils.types.NameValuePairType} object.
     */
    public static NameValuePairType newPair(String name, Object value) {
        return new NameValuePairType(name,value, FieldTypes.getTypeFromClass(value.getClass()));
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

    /**
     * <p>getType.</p>
     *
     * @return a {@link org.elasolutions.utils.types.FieldTypes} object.
     */
    public FieldTypes getType() {
        return m_type;
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
        NameValuePairType other = (NameValuePairType) obj;
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


    NameValuePairType(String name, Object value, FieldTypes type) {
        m_name = name;
        m_value = value;
        m_type = type;

    }

    private final FieldTypes m_type;

    private final String m_name;

    private final Object m_value;

}
