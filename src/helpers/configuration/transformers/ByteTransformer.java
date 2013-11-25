package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:12
 */
public class ByteTransformer
        implements PropertyTransformer<Byte> {
    public static final ByteTransformer SHARED_INSTANCE = new ByteTransformer();

    public Byte transform(String value, Field field)
            throws TransformationException {
        try {
            return Byte.decode(value);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}