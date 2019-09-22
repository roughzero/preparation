/*
 * Created on 2014年2月1日
 */
package rough.preparation.utils.io;

import java.io.IOException;

import junit.framework.Assert;
import junit.framework.TestCase;

public class StringLineWriterTest extends TestCase {
    private StringLineWriter writer;

    public void setUp() throws Exception {
        super.setUp();
        writer = new StringLineWriter();
    }

    public void testWrite() throws IOException {
        writer.write("test");
        Assert.assertEquals("test", writer.toString());
        writer.writeln("test");
        Assert.assertEquals("testtest" + writer.getLineSeparator(), writer.toString());
    }
}
