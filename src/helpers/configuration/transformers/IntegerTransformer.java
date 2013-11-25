package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:17
 */
public class IntegerTransformer
        implements PropertyTransformer<Integer> {
    public static final IntegerTransformer SHARED_INSTANCE = new IntegerTransformer();

    public Integer transform(String value, Field field)
            throws TransformationException {
        try {
            return Integer.decode(value);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}
