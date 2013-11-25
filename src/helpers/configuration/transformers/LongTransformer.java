package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:18
 */
public class LongTransformer
        implements PropertyTransformer<Long> {
    public static final LongTransformer SHARED_INSTANCE = new LongTransformer();

    public Long transform(String value, Field field)
            throws TransformationException {
        try {
            return Long.decode(value);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}
