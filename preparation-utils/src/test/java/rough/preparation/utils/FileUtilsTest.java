package rough.preparation.utils;

import lombok.extern.apachecommons.CommonsLog;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rough.preparation.utils.io.TextFileLineWriter;

import java.io.File;
import java.io.IOException;

@CommonsLog
public class FileUtilsTest {
    @Before
    public void before() throws IOException {
        TextFileLineWriter writer = new TextFileLineWriter("test.txt");
        writer.write("测试数据");
        writer.close();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testRead() throws IOException {
        byte[] result = FileUtils.read(new File("test.txt"));
        Assert.assertArrayEquals(result, "测试数据".getBytes());
        result = FileUtils.read("test.txt");
        Assert.assertArrayEquals(result, "测试数据".getBytes());
        File file = new File("test.txt");
        file.delete();
        FileUtils.write("测试数据".getBytes(), file);
        result = FileUtils.read("test.txt");
        Assert.assertArrayEquals(result, "测试数据".getBytes());
    }

    @After
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void after() {
        File file = new File("test.txt");
        if (file.exists()) {
            file.delete();
            log.info("test.txt deleted.");
        }
    }
}
