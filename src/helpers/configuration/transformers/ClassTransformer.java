package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:13
 * To change this template use File | Settings | File Templates.
 */
public class ClassTransformer
        implements PropertyTransformer<Class<?>> {
    public static final ClassTransformer SHARED_INSTANCE = new ClassTransformer();

    public Class<?> transform(String value, Field field) throws TransformationException {
        try {
            return Class.forName(value, false, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
        }
        throw new TransformationException("Cannot find class with name '" + value + "'");
    }
}
