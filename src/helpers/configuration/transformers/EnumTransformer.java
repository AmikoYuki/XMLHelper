package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:15
 */
@SuppressWarnings("unchecked")
public class EnumTransformer implements PropertyTransformer<Enum<?>> {

    public static final EnumTransformer SHARED_INSTANCE = new EnumTransformer();

    public Enum<?> transform(String value, Field field)
            throws TransformationException {
        Class clazz = field.getType();
        try {
            return Enum.valueOf(clazz, value);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}
