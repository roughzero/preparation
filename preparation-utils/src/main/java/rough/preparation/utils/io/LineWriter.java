/*
 * Created on 2011-12-23
 */
package rough.preparation.utils.io;

import java.io.IOException;

/**
 * 文本行输出器.
 * 
 * @author rough
 */
public interface LineWriter {

    /**
     * @return 行间隔符号
     */
    String getLineSeparator();

    /**
     * 设置行间隔符号.
     * @param lineSeparator 行间隔符号
     */
    void setLineSeparator(String lineSeparator);

    /**
     * 写入内容.
     * @param content 内容
     * @throws IOException IOException
     */
    void write(String content) throws IOException;

    /**
     * 写入内容并换行.
     * @param content 内容
     * @throws IOException IOException
     */
    void writeln(String content) throws IOException;

    /**
     * 换行.
     * @throws IOException IOException
     */
    void writeln() throws IOException;

    /**
     * 关闭资源.
     */
    void close();
}
