package base.utils;

import java.io.IOException;

import base.utils.Copy;
import junit.framework.TestCase;

public class OjectCopyTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public final void testCopy() throws IOException, ClassNotFoundException {
        final Car firstCar = new Car("Ford");
        final Car copyCar = Copy.copy(firstCar);
        assertEquals(firstCar, copyCar);
        assertEquals(firstCar.getName(), copyCar.getName());
    }


    public final void testNonSerializable() {
        try {
            Copy.copy(new Truck("Ford"));
            assertTrue(false); // should never get here.  Truck is not Serializable
        } catch (final IOException excep) {
            assertTrue(true);
        } catch (final ClassNotFoundException excep) {
            assertTrue(true); // should never get hear
        }
    }

    public final void testNullCheck() {
        try {
            Copy.copy(null);
            assertTrue(false); // should never get here.  Param is null.
        } catch (final Exception excep) {
            assertTrue(true);
        }
    }
}
