package base.utils.test.stress;

/**
 * <p>StressAction interface.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public interface StressAction {

    /**
     * <p>action.</p>
     *
     * @param threadId a int.
     * @param count a int.
     * @return a {@link java.lang.String} object.
     */
    public String action(int threadId, int count);

    /**
     * <p>cleanup.</p>
     */
    public void cleanup();

}
