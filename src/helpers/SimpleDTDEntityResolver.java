package helpers;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 03:43
 */
public class SimpleDTDEntityResolver implements EntityResolver {
    private String _fileName;

    public SimpleDTDEntityResolver(File f) {
        _fileName = f.getAbsolutePath();
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        return new InputSource(_fileName);
    }
}
