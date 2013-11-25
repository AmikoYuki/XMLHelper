package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:13
 */
public class CharTransformer
        implements PropertyTransformer<Character> {
    public static final CharTransformer SHARED_INSTANCE = new CharTransformer();

    public Character transform(String value, Field field)
            throws TransformationException {
        try {
            char[] chars = value.toCharArray();
            if (chars.length > 1) {
                throw new TransformationException("To many characters in the value");
            }

            return Character.valueOf(chars[0]);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}
