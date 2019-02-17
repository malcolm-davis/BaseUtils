package base.utils.types;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import base.utils.StringDoubleUtil;


/**
 * Reusable type information in the form of a enumeration with methods
 *
 * @author malcolm
 * @version $Id: $Id
 */
public enum FieldTypes {

    ANYURI("URL", "http://www.example.com", "URL", URL.class, null, "URL"), // TODO: Need URL converter

    DATETIME("DATETIME", "07/29/1999", "Date", Date.class, null, "Date"),

    TIMESTAMP("TIMESTAMP", "07/29/1999 12:24:00", "Time stamp", Date.class, null, "Date"),

    // "PTH2M30" = 2 Hours 30 Minutes
    DURATION("DURATION", "PTH2M30", "Duration", Double.class, Double.valueOf(0), "Duration"),

    DURATION_DECIMAL("DURATION_DECIMAL", "1.25", "Duration", Double.class, Double.valueOf(0), "Duration"),

    DOUBLE("DOUBLE", "30.50", "Double", double.class, Double.valueOf(0.0d), "Number"),

    INT("INT", "100", "Int", int.class,  Integer.valueOf(0), "Number"),

    TEXT("STRING", "John Doe", "String", String.class, "", "Text"),

    // ISSUE Not sure if ENUM or ANY types should exist.
    // Need ANY for unknown data types.
    ENUM("ENUM", "", "String", String.class, "", "Text"),

    BOOLEAN("BOOLEAN", "false", "String", boolean.class, Boolean.FALSE, "True/False"), // BooleanConverter.TRUE_FALSE

    PERCENT("PERCENT", ".095", "Double", double.class, Double.valueOf(0.0d), "Percentage"),

    MONEY("MONEY", "4.125", "Double", double.class, Double.valueOf(0.0d), "Money"),

    QUANTITY("QUANTITY", "4.150", "Double", double.class, Double.valueOf(0.0d), "Quantity"),

    LIST("LIST", "[]", "LIST", List.class, new ArrayList<String>(), "List"),

    ANY("ANY", "", "Any", String.class, "", "Text"),

    COMPLEX("COMPLEX", "w", "Complex", Object.class, "", "Complex");


    /**
     * <p>Constructor for FieldTypes.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @param sample a {@link java.lang.String} object.
     * @param xmlSource a {@link java.lang.String} object.
     * @param classType a {@link java.lang.Class} object.
     * @param defaultNullType a {@link java.lang.Object} object.
     * @param uiLabel a {@link java.lang.String} object.
     */
    FieldTypes(final String name, final String sample,
        final String xmlSource, final Class<?> classType,
        final Object defaultNullType, final String uiLabel) {

        m_name = name;
        m_sample = sample;
        m_xmlSource = xmlSource;
        m_classType = classType;
        m_defaultNullType = defaultNullType;
        m_uiLabel = uiLabel;
    }


    /**
     * <p>getType.</p>
     *
     * @param type a {@link java.lang.String} object.
     * @return a {@link org.elasolutions.utils.types.FieldTypes} object.
     */
    public static FieldTypes getType(final String type) {
        if(type==null) {
            return null;
        }
        final FieldTypes[] types  = values();
        for(final FieldTypes fieldTypes : types) {
            if( fieldTypes.m_name.equalsIgnoreCase(type) ) {
                return fieldTypes;
            }
        }
        return ANY;
    }


    /**
     * <p>converStringToClassType.</p>
     *
     * @param type a {@link org.elasolutions.utils.types.FieldTypes} object.
     * @param value a {@link java.lang.String} object.
     * @return a {@link java.lang.Object} object.
     * @throws java.lang.Exception if any.
     */
    public Object converStringToClassType(FieldTypes type, final String value) throws Exception {
        switch(type) {
            case DATETIME:
                // TODO
                break;
            case DURATION:
                // TODO
                break;
            case ENUM:
                // TODO
                break;
            case TIMESTAMP:
                // TODO
                break;

            case TEXT:
            case ANY:
                return value;
            case ANYURI:
                return new URL(value);
            case BOOLEAN:
                return Boolean.valueOf(value);
            case INT:
                return Integer.valueOf(value);
            case DURATION_DECIMAL:
            case DOUBLE:
            case MONEY:
            case PERCENT:
            case QUANTITY:
                return StringDoubleUtil.getDoubleObj(value, 0.0);
            default:
                break;

        }
        return value;
    }


    /**
     * <p>getTypeFromClass.</p>
     *
     * @param classType a {@link java.lang.Class} object.
     * @return a {@link org.elasolutions.utils.types.FieldTypes} object.
     */
    public static FieldTypes getTypeFromClass(final Class<?> classType) {
        if(classType==null) {
            return null;
        }
        final FieldTypes[] types  = values();
        for(final FieldTypes fieldTypes : types) {
            if( fieldTypes.m_classType.equals(classType) ) {
                return fieldTypes;
            }
        }
        if( java.lang.Integer.class.equals(classType) ) {
            return FieldTypes.INT;
        } else if( java.lang.Boolean.class.equals(classType) ) {
            return FieldTypes.BOOLEAN;
        }
        return ANY;
    }


    /**
     * <p>getTypeFromExpressionString.</p>
     *
     * @param type a {@link java.lang.String} object.
     * @return a {@link org.elasolutions.utils.types.FieldTypes} object.
     */
    public static FieldTypes getTypeFromExpressionString(final String type) {
        if( "Currency".equals(type) ) {
            return MONEY;
        } else if( "Quantity".equals(type) ) {
            return QUANTITY;
        } else if( "Number".equals(type) ) {
            return INT;
        } else if( "Text".equals(type) ) {
            return TEXT;
        } else if( "Percentage".equals(type) ) {
            return PERCENT;
        } else if( "Date".equals(type) ) {
            return DATETIME;
        }
        return TEXT;
    }


    /**
     * <p>isNumeric.</p>
     *
     * @return a boolean.
     */
    @SuppressWarnings("incomplete-switch")
    public boolean isNumeric() {
        switch(this) {
            case DURATION:
            case DURATION_DECIMAL:
            case DOUBLE:
            case INT:
            case PERCENT:
            case MONEY:
            case QUANTITY:
                return true;
        }
        return false;
    }

    /**
     * <p>isDate.</p>
     *
     * @return a boolean.
     */
    public boolean isDate() {
        if( DATETIME.equals(this) || TIMESTAMP.equals(this) ) {
            return true;
        }
        return false;
    }

    /**
     * <p>isNumeric.</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @return a boolean.
     */
    public static boolean isNumeric(final Class<?> type) {
        if( type.equals(java.lang.Number.class)
                || type.equals(java.lang.Double.class)
                || type.equals(java.lang.Integer.class)
                || type.equals(double.class)
                || type.equals(int.class) ) {
            return true;
        }
        return false;
    }

    /**
     * <p>isNumeric.</p>
     *
     * @param type a {@link org.elasolutions.utils.types.FieldTypes} object.
     * @return a boolean.
     */
    public static boolean isNumeric(final FieldTypes type) {
        if(  DOUBLE.equals(type)
                || DURATION.equals(type)
                || DURATION_DECIMAL.equals(type)
                || INT.equals(type)
                || PERCENT.equals(type)
                || MONEY.equals(type)
                || QUANTITY.equals(type) ) {
            return true;
        }
        return false;
    }

    /**
     * <p>getSample.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSample() {
        return m_sample;
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
     * <p>getClassType.</p>
     *
     * @return a {@link java.lang.Class} object.
     */
    public Class<?> getClassType() {
        return m_classType;
    }

    /**
     * <p>getDataSourceType.</p>
     *
     * @return String representing the XML datatype
     */
    public String getDataSourceType() {
        return m_xmlSource;
    }

    /**
     * <p>getDefaultNullData.</p>
     *
     * @return The default value for a null object.
     */
    public Object getDefaultNullData() {
        return m_defaultNullType;
    }

    /**
     * <p>getUiLabel.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUiLabel() {
        return m_uiLabel;
    }

    /**
     * {@inheritDoc}
     *
     * (non-Javadoc) @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return m_name;
    }

    private String m_name;
    private String m_sample;
    private String m_xmlSource;
    private Class<?> m_classType;
    private Object m_defaultNullType;
    private String m_uiLabel;

}
