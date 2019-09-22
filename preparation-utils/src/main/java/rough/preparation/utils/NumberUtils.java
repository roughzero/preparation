/*
 * Created on 2011-12-29
 */
package rough.preparation.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class NumberUtils {
    private static Map<String, String> toChineseMap = new HashMap<String, String>();
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
        String tmp = Integer.toString(number);
        while (tmp.length() < 16) {
            tmp = "0" + tmp;
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
        String result = "";
        for (int i = 0; i < 4; i++) {
            if (lastlevel >= 0 && i - lastlevel > 1) {
                if (StringUtils.isNotEmpty(results[i])) {
                    result += "零";
                }
            } else if (lastlevel >= 0 && StringUtils.isNotEmpty(results[i]) && results[i].indexOf("千") < 0) {
                result += "零";
            }
            result += results[i];
            if (StringUtils.isNotEmpty(results[i]))
                lastlevel = i;
        }
        if (StringUtils.isEmpty(result))
            result = "零";
        return result;
    }

    private static String toChinese0To4(String s) {
        String[] units = { "千", "百", "十", "" };
        String tmp = s;
        while (tmp.length() < 4) {
            tmp = "0" + tmp;
        }
        String[] results = new String[4];
        for (int i = 0; i < 4; i++) {
            results[i] = tmp.substring(i, i + 1);
            if ("0".equals(results[i]))
                results[i] = "";
            else
                results[i] = toChineseMap.get(results[i]) + units[i];
        }

        String result = "";
        int lastlevel = -1;
        for (int i = 0; i < 4; i++) {
            if (lastlevel >= 0 && i - lastlevel > 1) {
                if (StringUtils.isNotEmpty(results[i])) {
                    result += "零";
                }
            }
            result += results[i];
            if (StringUtils.isNotEmpty(results[i]))
                lastlevel = i;
        }
        return result;
    }
}
