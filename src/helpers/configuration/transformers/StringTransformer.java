package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:20
 */
public class StringTransformer
        implements PropertyTransformer<String> {
    public static final StringTransformer SHARED_INSTANCE = new StringTransformer();

    public String transform(String value, Field field)
            throws TransformationException {
        return value;
    }
}
