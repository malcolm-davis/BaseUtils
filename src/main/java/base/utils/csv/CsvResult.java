package base.utils.csv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvResult {

    public static CsvResult newResults() {
        return new CsvResult();
    }

    public CsvResult addChild(final CsvResult child) {
        child.setParent(this);
        getChildList().add(child);
        return child;
    }

    public boolean addColumnValue(final String column, final String value) {
        if ((column != null) && (value != null && value.trim().length()>0)) {
            String insertValue = value;
            String presentValue = getColumnValueMap().get(column);
            if( presentValue!=null ) {
                insertValue = presentValue += "," + value;
            }
            getColumnValueMap().put(column,insertValue);
            if(!m_headers.contains(column)) {
                m_headers.add(column);
            }
            return true;
        }
        return false;
    }

    public String getColumn(int rowIndex, final String header) {
        if(rowIndex < 0) {
            throw new IllegalArgumentException("rowIndex < 0.  rowIndex must be positive");
        }
        if(size() <= rowIndex) {
            throw new IllegalArgumentException("rowIndex exceeds row count");
        }

        String value = getDataCache().get(rowIndex).get(header);
        return ( value==null ) ? "" : value.trim();
    }


    public List<Map<String, String>> getDataCache() {
        if(m_dataCache==null) {
            m_dataCache = getData();
        }
        return m_dataCache;
    }


    public List<Map<String, String>> getData() {
        // root, pull headers from children
        List<Map<String, String>>list = new ArrayList<Map<String, String>>();
        if( getChildList().size()>0 ) {
            if(!getColumnValueMap().isEmpty()) {
                Map<String, String>map = new HashMap<String, String>();
                map.putAll(getColumnValueMap());
                for (CsvResult csvResult : getChildList()) {
                    map.putAll(csvResult.getColumnValueMap());
                    //list.addAll(csvResult.getData());
                }
                list.add(map);
            } else {
                for (CsvResult csvResult : getChildList()) {
                    list.addAll(csvResult.getData());
                }
            }
            return list;
        }

        list.add(getColumnValueMap());
        return list;
    }

    public String[] getHeaders() {
        // root, pull headers from children
        if( getChildList().size()>0 ) {
            List<String>headers = new ArrayList<String>();

            for(String header : m_headers) {
                if(!headers.contains(header)) {
                    headers.add(header);
                }
            }
            for (CsvResult csvResult : getChildList()) {
                for(String header : csvResult.getHeaders()) {
                    if(!headers.contains(header)) {
                        headers.add(header);
                    }
                }
            }
            return headers.toArray(new String[0]);
        }
        return m_headers.toArray(new String[0]);
    }

    public CsvResult getParent() {
        return m_parent;
    }

    public int size() {
        return getDataCache().size();
    }

    protected List<CsvResult> getChildList() {
        if (m_childResultList == null) {
            m_childResultList = new ArrayList<>();
        }
        return m_childResultList;
    }

    protected Map<String, String> getColumnValueMap() {
        if (m_columnValue == null) {
            m_columnValue = new HashMap<>();
        }
        return m_columnValue;
    }

    private void setParent(final CsvResult parent) {
        m_parent = parent;
    }

    CsvResult() {
    }

    private CsvResult m_parent;

    private List<CsvResult> m_childResultList;

    private Map<String, String> m_columnValue;

    List<Map<String, String>>m_dataCache;

    private final List<String>m_headers = new ArrayList<String>();

    private String m_xmlTagName;

    public void setName(String rootTagName) {
        m_xmlTagName = rootTagName;
    }

    public String getName() {
        return m_xmlTagName;
    }


    public boolean isRoot() {
        return m_parent==null;
    }

}
