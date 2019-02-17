package base.utils;

import java.io.Closeable;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides a central place for close behavior to reduce copy &amp; pasting.
 *
 * @author Malcolm G. Davis
 * @version 1.0
 */
public class CloseUtil {

    /**
     * <p>close.</p>
     *
     * @param close a {@link java.io.Closeable} object.
     */
    public static void close(final Closeable close) {
        if (close != null) {
            try {
                close.close();
            } catch (final IOException excep) {
                LOGGER.log(Level.SEVERE,
                    "Error closing resource: " + close.getClass().getName(), excep);
            }
        }
    }

    /**
     * <p>close.</p>
     *
     * @param rs a {@link java.sql.ResultSet} object.
     */
    public static void close(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (final Throwable excep) {
                LOGGER.log(Level.SEVERE, "Error closing PreparedStatement: "
                        + rs.getClass().getName(), excep);
            }
        }
    }

    /**
     * <p>close.</p>
     *
     * @param statement a {@link java.sql.Statement} object.
     */
    public static void close(final Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (final Throwable excep) {
                LOGGER.log(Level.SEVERE, "Error closing PreparedStatement: "
                        + statement.getClass().getName(), excep);
            }
        }
    }

    /**
     * <p>close.</p>
     *
     * @param close a {@link java.sql.PreparedStatement} object.
     */
    public static void close(final PreparedStatement close) {
        if (close != null) {
            try {
                close.close();
            } catch (final Throwable excep) {
                LOGGER.log(Level.SEVERE, "Error closing PreparedStatement: "
                        + close.getClass().getName(), excep);
            }
        }
    }

    private static final Logger LOGGER =
            Logger.getLogger(CloseUtil.class.getName());
}
