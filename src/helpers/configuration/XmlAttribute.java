package helpers.configuration;

import helpers.configuration.transformers.PropertyTransformer;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:10
 */
@Documented
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlAttribute {
    public abstract String name();

    public abstract Class<? extends PropertyTransformer> propertyTransformer();
}