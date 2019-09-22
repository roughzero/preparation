/*
 * Created on 2011-12-29
 */
package rough.preparation.utils;

import junit.framework.TestCase;

public class NumberUtilsTest extends TestCase {

    public void testToChinese() {
        assertEquals("零", NumberUtils.toChinese(0));
        assertEquals("一", NumberUtils.toChinese(1));
        assertEquals("一十一", NumberUtils.toChinese(11));
        assertEquals("二十一", NumberUtils.toChinese(21));
        assertEquals("一百", NumberUtils.toChinese(100));
        assertEquals("一百零二", NumberUtils.toChinese(102));
        assertEquals("一百二十", NumberUtils.toChinese(120));
        assertEquals("一千", NumberUtils.toChinese(1000));
        assertEquals("一千零一", NumberUtils.toChinese(1001));
        assertEquals("一千零二十", NumberUtils.toChinese(1020));
        assertEquals("一千一百一十", NumberUtils.toChinese(1110));
        assertEquals("一千一百一十一", NumberUtils.toChinese(1111));
        assertEquals("一万", NumberUtils.toChinese(10000));
        assertEquals("一万零一", NumberUtils.toChinese(10001));
        assertEquals("一万零一十一", NumberUtils.toChinese(10011));
        assertEquals("一千零一万零一十一", NumberUtils.toChinese(10010011));
        assertEquals("九千九百九十九万九千九百九十九", NumberUtils.toChinese(99999999));
        assertEquals("一亿", NumberUtils.toChinese(100000000));
        assertEquals("一亿零一", NumberUtils.toChinese(100000001));
        assertEquals("一亿零一百零一", NumberUtils.toChinese(100000101));
    }

}
