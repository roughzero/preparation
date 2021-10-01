/*
 * Created on 2011-6-17
 */
package rough.preparation.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 文本文件读取.
 * 可设定字符集, 若文件包含 bom 头, 可自动识别.
 * 简单的说就是如果有 GBK 的文件和含有 bom 头的 UTF-8 的文件, 只要设置 GBK 就可以全部正常的读出来.
 *
 * TODO 只能分辨 UTF-8 的 BOM, 不能分辨其他的.
 * 
 * 2011-08-05 add LineDecorater.
 * 
 * @author rough
 */
public class TextFileReader {
    private final TextFileLineReader reader;
    private final StringLineWriter writer;
    private static final String DEFAULT_CHARSET = "UTF-8";

    private String lineSeparator;

    public void setDecorater(LineDecorator decorater) {
        reader.setDecorator(decorater);
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public TextFileReader(String name) throws FileNotFoundException {
        this(name, DEFAULT_CHARSET);
    }

    public TextFileReader(String name, String charset) throws FileNotFoundException {
        this(name != null ? new File(name) : null, charset);
    }

    public TextFileReader(File file) throws FileNotFoundException {
        this(file, DEFAULT_CHARSET);
    }

    public TextFileReader(File file, String charset) throws FileNotFoundException {
        this.reader = new TextFileLineReader(file, charset);
        this.writer = new StringLineWriter();
    }

    public String readFile() throws IOException {
        try {
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
                if (line != null)
                    writer.write(lineSeparator);
            }

            return writer.toString();
        } finally {
            reader.close();
            writer.close();
        }
    }
}
