package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:19
 */
public class ShortTransformer
        implements PropertyTransformer<Short> {
    public static final ShortTransformer SHARED_INSTANCE = new ShortTransformer();

    public Short transform(String value, Field field)
            throws TransformationException {
        try {
            return Short.decode(value);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}
