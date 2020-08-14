/*
 * Created on 2011-12-29
 */
package rough.preparation.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class NumberUtils {
    private static final Map<String, String> toChineseMap = new HashMap<>();
    static {
        toChineseMap.put("0", "零");
        toChineseMap.put("1", "一");
        toChineseMap.put("2", "二");
        toChineseMap.put("3", "三");
        toChineseMap.put("4", "四");
        toChineseMap.put("5", "五");
        toChineseMap.put("6", "六");
        toChineseMap.put("7", "七");
        toChineseMap.put("8", "八");
        toChineseMap.put("9", "九");
    }

    public static String toChinese(int number) {
        String[] unitsHigh = { "兆", "亿", "万", "" };
        StringBuilder tmp = new StringBuilder(Integer.toString(number));
        while (tmp.length() < 16) {
            tmp.insert(0, "0");
        }
        String[] tmps = new String[4];
        String[] results = new String[4];
        for (int i = 0; i < 4; i++) {
            tmps[i] = tmp.substring(i * 4, i * 4 + 4);
            results[i] = toChinese0To4(tmps[i]);
            if (StringUtils.isNotEmpty(results[i]))
                results[i] += unitsHigh[i];
        }
        int lastlevel = -1;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (lastlevel >= 0 && i - lastlevel > 1) {
                if (StringUtils.isNotEmpty(results[i])) {
                    result.append("零");
                }
            } else if (lastlevel >= 0 && StringUtils.isNotEmpty(results[i]) && !results[i].contains("千")) {
                result.append("零");
            }
            result.append(results[i]);
            if (StringUtils.isNotEmpty(results[i]))
                lastlevel = i;
        }
        if (StringUtils.isEmpty(result.toString()))
            result = new StringBuilder("零");
        return result.toString();
    }

    private static String toChinese0To4(String s) {
        String[] units = { "千", "百", "十", "" };
        StringBuilder tmp = new StringBuilder(s);
        while (tmp.length() < 4) {
            tmp.insert(0, "0");
        }
        String[] results = new String[4];
        for (int i = 0; i < 4; i++) {
            results[i] = tmp.substring(i, i + 1);
            if ("0".equals(results[i]))
                results[i] = "";
            else
                results[i] = toChineseMap.get(results[i]) + units[i];
        }

        StringBuilder result = new StringBuilder();
        int lastlevel = -1;
        for (int i = 0; i < 4; i++) {
            if (lastlevel >= 0 && i - lastlevel > 1) {
                if (StringUtils.isNotEmpty(results[i])) {
                    result.append("零");
                }
            }
            result.append(results[i]);
            if (StringUtils.isNotEmpty(results[i]))
                lastlevel = i;
        }
        return result.toString();
    }
}
