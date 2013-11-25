package data.xml;

import helpers.LoggerObject;

/**
 * Created with IntelliJ IDEA.
 * User: yuki
 * Date: 24/11/13
 * Time: 03:42
 */
public abstract class AbstractHolder extends LoggerObject {
    public void log() {
        info(String.format("loaded %d%s(s) count.", size(), formatOut(getClass().getSimpleName().replace("Holder", "")).toLowerCase()));
    }

    protected void process() {

    }

    public abstract int size();

    public abstract void clear();

    private static String formatOut(String st) {
        char[] chars = st.toCharArray();
        StringBuffer buf = new StringBuffer(chars.length);

        for (char ch : chars) {
            if (Character.isUpperCase(ch))
                buf.append(" ");

            buf.append(Character.toLowerCase(ch));
        }

        return buf.toString();
    }
}
