package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:06
 */
public class BooleanTransformer
        implements PropertyTransformer<Boolean> {
    public static final BooleanTransformer SHARED_INSTANCE = new BooleanTransformer();

    public Boolean transform(String value, Field field)
            throws TransformationException {
        if (("true".equalsIgnoreCase(value)) || ("1".equals(value))) {
            return Boolean.valueOf(true);
        }
        if (("false".equalsIgnoreCase(value)) || ("0".equals(value))) {
            return Boolean.valueOf(false);
        }

        throw new TransformationException("Invalid boolean string: " + value);
    }
}