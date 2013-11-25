package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:18
 */
public class PatternTransformer
        implements PropertyTransformer<Pattern> {
    public static final PatternTransformer SHARED_INSTANCE = new PatternTransformer();

    public Pattern transform(String value, Field field)
            throws TransformationException {
        try {
            return Pattern.compile(value);
        } catch (Exception e) {
            throw new TransformationException("Not valid RegExp: " + value, e);
        }
    }
}
