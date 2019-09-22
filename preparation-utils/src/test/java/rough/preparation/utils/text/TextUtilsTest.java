/*
 * Created on 2013-4-8
 */
package rough.preparation.utils.text;

import junit.framework.TestCase;

public class TextUtilsTest extends TestCase {

    public void testGetSuffix() {
        assertEquals("sql", TextUtils.getSuffix("abcd.efgh.hijk.sql"));
        assertEquals("sql", TextUtils.getSuffix(".sql"));
        assertEquals("", TextUtils.getSuffix("abcd."));
        assertEquals("", TextUtils.getSuffix("abcd"));
        assertEquals("", TextUtils.getSuffix(null));
    }

    public void testToCamel() {
        assertEquals("abcDef", TextUtils.toCamel("abc_def", false, "_"));
        assertEquals("abcDef", TextUtils.toCamel("ABC_DEF", false, "_"));
        assertEquals("AbcDef", TextUtils.toCamel("ABC_DEF", true, "_"));
    }
}
