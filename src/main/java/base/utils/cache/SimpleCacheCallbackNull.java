package base.utils.cache;


/**
 * <p>SimpleCacheCallbackNull class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class SimpleCacheCallbackNull implements SimpleCacheCallback<Object, Object> {

    /** {@inheritDoc} */
    @Override
    public void removedElement(Element<Object, Object> element) {
        // do nothing
    }

}
