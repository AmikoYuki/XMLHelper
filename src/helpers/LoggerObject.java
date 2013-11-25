package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 03:55
 */
public abstract class LoggerObject {
    protected final Logger _log = LoggerFactory.getLogger(getClass());

    public void error(String st, Exception e) {
        _log.error(getClass().getSimpleName() + ": " + st, e);
    }

    public void error(String st) {
        _log.error(getClass().getSimpleName() + ": " + st);
    }

    public void warn(String st, Exception e) {
        _log.warn(getClass().getSimpleName() + ": " + st, e);
    }

    public void warn(String st) {
        _log.warn(getClass().getSimpleName() + ": " + st);
    }

    public void info(String st, Exception e) {
        _log.info(getClass().getSimpleName() + ": " + st, e);
    }

    public void info(String st) {
        _log.info(getClass().getSimpleName() + ": " + st);
    }
}
