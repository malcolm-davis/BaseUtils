package base.utils.cache;


/**
 * <p>SimpleCacheCallback interface.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public interface SimpleCacheCallback<I, T> {
    /**
     * <p>removedElement.</p>
     *
     * @param element a {@link org.elasolutions.utils.cache.Element} object.
     */
    void removedElement(Element<I, T>element);
}
