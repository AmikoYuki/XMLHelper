package data.xml;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 03:41
 */
public abstract class AbstractFileParser<H extends AbstractHolder> extends AbstractParser<H> {
    protected AbstractFileParser(H holder) {
        super(holder);
    }

    public abstract File getXMLFile();

    public abstract String getDTDFileName();

    @Override
    protected final void parse() {
        File file = getXMLFile();

        if (!file.exists()) {
            warn("file " + file.getAbsolutePath() + " not exists");
            return;
        }

        File dtd = new File(file.getParent(), getDTDFileName());
        if (!dtd.exists()) {
            info("DTD file: " + dtd.getName() + " not exists.");
            return;
        }

        initDTD(dtd);
        FileInputStream io = null;
        try {
            io = new FileInputStream(file);
            parseDocument(io, file.getName());
        } catch (Exception e) {
            warn("Exception: " + e, e);
        } finally {
            try {
                if (io != null)
                    io.close();
            } catch (Exception e) {
                warn("Exception: " + e, e);
            }
            clearReader();
        }
    }
}
