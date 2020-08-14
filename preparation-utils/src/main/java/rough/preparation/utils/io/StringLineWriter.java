/*
 * Created on 2011-12-23
 */
package rough.preparation.utils.io;

import java.io.IOException;

/**
 * 写入对象为字符串的 LineWriter 实现类.
 * 写入完成后使用 toString() 方法得到结果.
 * 
 * @author rough
 */
public class StringLineWriter extends AbstractLineWriter implements LineWriter {
    private final StringBuffer result;

    /**
     * Constructor.
     */
    public StringLineWriter() {
        this.result = new StringBuffer();
    }

    /**
     * @see rough.preparation.utils.io.LineWriter#write(java.lang.String)
     */
    @SuppressWarnings("RedundantThrows")
    public void write(String line) throws IOException {
        result.append(line);
    }

    /**
     * 输出结果.
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return result.toString();
    }

    /**
     * @see rough.preparation.utils.io.LineWriter#close()
     */
    public void close() {
    }
}
