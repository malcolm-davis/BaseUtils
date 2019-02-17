package base.utils.jee;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;


/**
 * <p>InitialContextLookup class.</p>
 *
 * @author malcolm g. davis
 * @version $Id: $Id
 */
public enum InitialContextLookup {
    INSTANCE;


    /**
     *
     * @param name the key value to search
     * @param defaultValue the default value if the key value is not found
     * @return
     * String
     */
    public String lookup(String name, String defaultValue)  {
        Object o = null;
        try {
            o = getInitialContext().lookup(name);
        } catch (NamingException excep) {
            LOG.log(Level.SEVERE, "Error looking up JNDI value for name="+name +" using default="+defaultValue, excep);
        }
        return ( o!=null ) ? o.toString() : defaultValue;
    }


    /**
     * <p>lookup.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws javax.naming.NamingException if any.
     */
    public String lookup(String name) throws NamingException {
        Object o = getInitialContext().lookup(name);
        return ( o!=null ) ? o.toString() : "";
    }

    /**
     * <p>lookupInteger.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @return a {@link java.lang.Integer} object.
     * @throws javax.naming.NamingException if any.
     */
    public Integer lookupInteger(String name) throws NamingException {
        Object o = getInitialContext().lookup(name);
        return ( o!=null ) ? (Integer)o : Integer.valueOf(0);
    }

    /**
     * <p>lookupList.</p>
     *
     * @param path a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws javax.naming.NamingException if any.
     */
    public String lookupList(String path) throws NamingException {
        StringBuffer values  = new StringBuffer();
        NamingEnumeration<NameClassPair> list = getInitialContext().list(path);
        while (list.hasMore()) {
            String name = list.next().getName();
            String value = lookup(path+ "/" + name);
            values.append(name).append("=").append(value).append("\r\n");
        }
        return values.toString();
    }

    /**
     * <p>lookupMap.</p>
     *
     * @param path a {@link java.lang.String} object.
     * @return a {@link java.util.Map} object.
     * @throws javax.naming.NamingException if any.
     */
    public Map<String,String> lookupMap(String path) throws NamingException {
        Map<String,String>map = new HashMap<String,String>();
        NamingEnumeration<NameClassPair> list = getInitialContext().list(path);
        while (list.hasMore()) {
            String name = list.next().getName();
            String value = lookup(path+ "/" + name);
            map.put(name,value);
        }
        return map;
    }

    InitialContext getInitialContext() throws NamingException {
        if( context==null ) {
            context = new InitialContext();
        }
        return context;
    }

    InitialContext context = null;


    private static final Logger LOG =
            Logger.getLogger(InitialContextLookup.class.getName());

}
