package base.utils.cache;


public class Car {
    final Integer m_id;
    final String m_name;
    Car(final Integer id, final String name) {
        m_id = id;
        m_name = name;
    }
    @Override
    public String toString() {
        return "id="+m_id.toString() + ", name=" +m_name;
    }
}
