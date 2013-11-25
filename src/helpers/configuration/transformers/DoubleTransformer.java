package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:14
 */
public class DoubleTransformer
        implements PropertyTransformer<Double> {
    public static final DoubleTransformer SHARED_INSTANCE = new DoubleTransformer();

    public Double transform(String value, Field field)
            throws TransformationException {
        try {
            return Double.valueOf(Double.parseDouble(value));
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}