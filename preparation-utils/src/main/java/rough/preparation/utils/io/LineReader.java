/*
 * Created on 2011-12-23
 */
package rough.preparation.utils.io;

import java.io.IOException;

/**
 * 文本行阅读器.
 * 
 * @author rough
 */
public interface LineReader {
    /**
     * 读取一行文本内容.
     * @return 一行内容.
     * @throws IOException IOException
     */
    String readLine() throws IOException;

    /**
     * 关闭资源.
     */
    void close();
}
