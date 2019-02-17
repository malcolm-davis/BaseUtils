package base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * <p>ReadResource class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public enum ReadResource {
    INSTANCES;

    /**
     * <p>getText.</p>
     *
     * @param resourcePath a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public String getText(final String resourcePath) throws IOException {
        if (resourcePath == null) {
            throw new IllegalArgumentException("Null value for pathToResource");
        }
        final InputStream stream = getClass().getResourceAsStream(resourcePath);

        if (stream == null) {
            throw new IOException( "Error trying to load resource.  Please verify resource path.  "
                    + "resourcePath='" + resourcePath + "'");
        }

        StringBuilder sb = new StringBuilder();
        InputStreamReader ioStream = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(ioStream);
        try {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(LINE_SEPARATOR);
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        return sb.toString();
    }

    final static String LINE_SEPARATOR = System.getProperty("line.separator");
}
