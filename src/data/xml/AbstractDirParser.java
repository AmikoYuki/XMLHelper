package data.xml;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;


/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 03:41
 */
public abstract class AbstractDirParser<H extends AbstractHolder> extends AbstractParser<H> {
    protected AbstractDirParser(H holder) {
        super(holder);
    }

    public abstract File getXMLDir();

    public abstract boolean isIgnored(File f);

    public abstract String getDTDFileName();

    @Override
    protected final void parse() {
        File dir = getXMLDir();

        if (!dir.exists()) {
            warn("Dir " + dir.getAbsolutePath() + " not exists");
            return;
        }

        File dtd = new File(dir, getDTDFileName());
        if (!dtd.exists()) {
            info("DTD file: " + dtd.getName() + " not exists.");
            return;
        }

        initDTD(dtd);

        try {
            Collection<File> files = FileUtils.listFiles(dir, FileFilterUtils.suffixFileFilter(".xml"), FileFilterUtils.directoryFileFilter());

            for (File f : files)
                if (!f.isHidden())
                    if (!isIgnored(f)) {
                        FileInputStream io = null;
                        try {
                            io = new FileInputStream(f);
                            parseDocument(io, f.getName());
                        } catch (Exception e) {
                            info("Exception: " + e + " in file: " + f.getName(), e);
                        } finally {
                            try {
                                if (io != null)
                                    io.close();
                            } catch (Exception e) {
                                warn("Exception: " + e, e);
                            }
                        }
                    }
        } catch (Exception e) {
            warn("Exception: " + e, e);
        } finally {
            clearReader();
        }
    }
}
