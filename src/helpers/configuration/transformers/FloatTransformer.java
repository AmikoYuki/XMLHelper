package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:16
 */
public class FloatTransformer
        implements PropertyTransformer<Float> {
    public static final FloatTransformer SHARED_INSTANCE = new FloatTransformer();

    public Float transform(String value, Field field)
            throws TransformationException {
        try {
            return Float.valueOf(Float.parseFloat(value));
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}