package base.utils;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Properties;

/**
 * SystemDump dumps information about the operating system in a consistent and holistic manner.
 *
 * @author Malcolm G. Davis
 * @version 1.0
 */
public class SystemDump {

    /**
     * <p>dump.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String dump() {
        StringBuilder out = new StringBuilder();

        out.append("Environment variables:\r\n");
        java.util.Map<String,String>map = System.getenv();
        for( Object key : map.keySet() ) {
            out.append(key +"="+ map.get(key)+"\r\n");
        }

        out.append("\r\nSystem variables:\r\n");
        Properties properties = System.getProperties();
        for( Object key : properties.keySet() ) {
            out.append(key +"="+ properties.get(key)+"\r\n");
        }

        out.append("\r\nRuntime:\r\n");
        double divider = 1000*1000;
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();

        NumberFormat formatter = new DecimalFormat("#0");

        out.append("maxMemory="+ formatter.format(runtime.maxMemory()/divider) +" MB\r\n");
        out.append("totalMemory="+ formatter.format(runtime.totalMemory()/divider) +" MB\r\n");
        out.append("freeMemory="+ formatter.format(runtime.freeMemory()/divider) +" MB\r\n");
        out.append("usedMemory="+ formatter.format(usedMemory/divider) +" MB\r\n");
        out.append("availableProcessors="+ runtime.availableProcessors() +"\r\n");

        out.append("\r\nGraphics environment:\r\n");

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        out.append("maximumWindowBounds="+ ge.getMaximumWindowBounds().toString() +"\r\n");

        GraphicsDevice[] gs = ge.getScreenDevices();
        out.append("screenCount="+ gs.length +"\r\n");

        for (int j = 0; j < gs.length; j++) {
            GraphicsDevice gd = gs[j];

            out.append("screen="+ gd.getIDstring()+"\r\n");
            out.append("size="+ gd.getDefaultConfiguration().getBounds().toString()+"\r\n");
        }

        return out.toString();
    }
}
