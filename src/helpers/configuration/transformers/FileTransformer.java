package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:15
 */
public class FileTransformer
        implements PropertyTransformer<File> {
    public static final FileTransformer SHARED_INSTANCE = new FileTransformer();

    public File transform(String value, Field field)
            throws TransformationException {
        return new File(value);
    }
}
