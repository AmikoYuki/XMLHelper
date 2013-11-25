package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:08
 */
public abstract interface PropertyTransformer<T> {
    public abstract T transform(String paramString, Field paramField)
            throws TransformationException;
}
