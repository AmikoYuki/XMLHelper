package helpers.configuration;

import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import helpers.configuration.transformers.*;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:05
 * To change this template use File | Settings | File Templates.
 */
public class PropertyTransformerFactory {
    public static PropertyTransformer newTransformer(Class clazzToTransform, Class<? extends PropertyTransformer> tc)
            throws TransformationException {
        if (tc == PropertyTransformer.class) {
            tc = null;
        }

        if (tc != null) {
            try {
                return (PropertyTransformer) tc.newInstance();
            } catch (Exception e) {
                throw new TransformationException("Can't instantiate property transfromer", e);
            }
        }
        if ((clazzToTransform == Boolean.class) || (clazzToTransform == Boolean.TYPE)) {
            return BooleanTransformer.SHARED_INSTANCE;
        }
        if ((clazzToTransform == Byte.class) || (clazzToTransform == Byte.TYPE)) {
            return ByteTransformer.SHARED_INSTANCE;
        }
        if ((clazzToTransform == Character.class) || (clazzToTransform == Character.TYPE)) {
            return CharTransformer.SHARED_INSTANCE;
        }
        if ((clazzToTransform == Double.class) || (clazzToTransform == Double.TYPE)) {
            return DoubleTransformer.SHARED_INSTANCE;
        }
        if ((clazzToTransform == Float.class) || (clazzToTransform == Float.TYPE)) {
            return FloatTransformer.SHARED_INSTANCE;
        }
        if ((clazzToTransform == Integer.class) || (clazzToTransform == Integer.TYPE)) {
            return IntegerTransformer.SHARED_INSTANCE;
        }
        if ((clazzToTransform == Long.class) || (clazzToTransform == Long.TYPE)) {
            return LongTransformer.SHARED_INSTANCE;
        }
        if ((clazzToTransform == Short.class) || (clazzToTransform == Short.TYPE)) {
            return ShortTransformer.SHARED_INSTANCE;
        }
        if (clazzToTransform == String.class) {
            return StringTransformer.SHARED_INSTANCE;
        }
        if (clazzToTransform.isEnum()) {
            return EnumTransformer.SHARED_INSTANCE;
        }

        if (clazzToTransform == File.class) {
            return FileTransformer.SHARED_INSTANCE;
        }
        if (ClassUtils.isSubclass(clazzToTransform, InetSocketAddress.class)) {
            return InetSocketAddressTransformer.SHARED_INSTANCE;
        }
        if (clazzToTransform == Pattern.class) {
            return PatternTransformer.SHARED_INSTANCE;
        }
        if (clazzToTransform == Class.class) {
            return ClassTransformer.SHARED_INSTANCE;
        }

        throw new TransformationException("Transformer not found for class " + clazzToTransform.getName());
    }
}