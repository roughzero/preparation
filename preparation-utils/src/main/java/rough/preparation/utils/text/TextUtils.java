/*
 * Created on 2013-4-8
 */
package rough.preparation.utils.text;

import org.apache.commons.lang.StringUtils;

public final class TextUtils {

    public static String toCamel(String s, boolean isFirstUpCase, String delimiter) {
        if (StringUtils.isEmpty(s))
            return StringUtils.EMPTY;
        String[] subs = StringUtils.split(s, delimiter);
        StringBuilder result = new StringBuilder();
        if (isFirstUpCase)
            result.append(StringUtils.capitalize(subs[0].toLowerCase()));
        else
            result.append(subs[0].toLowerCase());

        for (int i = 1; i < subs.length; i++) {
            result.append(StringUtils.capitalize(subs[i].toLowerCase()));
        }
        return result.toString();
    }

    public static String getSuffix(String filename) {
        if (filename == null)
            return "";
        else {
            int index = filename.length() - 1;
            for (int i = filename.length() - 1; i >= 0; i--) {
                if (filename.charAt(i) == '.') {
                    index = i;
                    break;
                }
            }

            return index == filename.length() - 1 ? "" : filename.substring(index + 1);
        }
    }
}
