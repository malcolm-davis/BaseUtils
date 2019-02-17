package base.utils;

import java.io.Serializable;


public class Car implements Serializable {
    public Car(final String name) {
        m_name = name;
    }

    public String getName() {
        return m_name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Car other = (Car) obj;
        if (m_name == null) {
            if (other.m_name != null) {
                return false;
            }
        } else if (!m_name.equals(other.m_name)) {
            return false;
        }
        return true;
    }

    private final String m_name;

}
