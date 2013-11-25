package helpers.configuration.transformers;

import helpers.configuration.TransformationException;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 04:17
 */
public class InetSocketAddressTransformer
        implements PropertyTransformer<InetSocketAddress> {
    public static final InetSocketAddressTransformer SHARED_INSTANCE = new InetSocketAddressTransformer();

    public InetSocketAddress transform(String value, Field field)
            throws TransformationException {
        String[] parts = value.split(":");

        if (parts.length != 2) {
            throw new TransformationException("Can't transform property, must be in format \"address:port\"");
        }
        try {
            if ("*".equals(parts[0])) {
                return new InetSocketAddress(Integer.parseInt(parts[1]));
            }
            InetAddress address = InetAddress.getByName(parts[0]);
            int port = Integer.parseInt(parts[1]);
            return new InetSocketAddress(address, port);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}
