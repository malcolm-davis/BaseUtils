package base.utils;

import base.utils.StringDoubleUtil;
import junit.framework.TestCase;


public class StringDoubleUtilTest extends TestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public final void testCopy() {

        assertTrue( StringDoubleUtil.isDouble("1.0"));
        assertFalse(StringDoubleUtil.isDouble("a"));
        assertTrue( StringDoubleUtil.getDouble(null,0)==0);
        assertTrue( StringDoubleUtil.getDouble("4.4",0)==4.4);
    }

}
