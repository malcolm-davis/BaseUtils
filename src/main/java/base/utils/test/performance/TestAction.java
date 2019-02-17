package base.utils.test.performance;

import base.utils.test.EventStatus;

/**
 * <p>TestAction interface.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public interface TestAction {

    /**
     * <p>action.</p>
     *
     * @param threadId a int.
     * @param count a int.
     * @return a {@link java.lang.String} object.
     */
    public EventStatus action(int threadId, int count, final StringBuffer optionalResultMessage);

    /**
     * <p>cleanup.</p>
     */
    public void cleanup();
}
