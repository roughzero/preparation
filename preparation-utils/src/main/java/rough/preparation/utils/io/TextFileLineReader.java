/*
 * Created on 2011-12-23
 */
package rough.preparation.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 文本文件按行读取.
 * 每次读取一行, 若返回为空, 则表示结束.
 * 可设定字符集, 若文件包含 bom 头, 可自动识别.
 * 简单的说就是如果有 GBK 的文件和含有 bom 头的 UTF-8 的文件, 只要设置 GBK 就可以全部正常的读出来.
 * 
 * @author rough
 * @since 2011-12-23
 */
public class TextFileLineReader implements LineReader {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String READED_CHARSET = "ISO-8859-1";
    private static final LineDecorater DEFAULT_DECORATER = line -> line;

    private final RandomAccessFile access;
    private String charset;
    private boolean hasBom;
    private LineDecorater decorater;
    private boolean isFirstLine;

    public LineDecorater getDecorater() {
        return decorater;
    }

    public void setDecorater(LineDecorater decorater) {
        this.decorater = decorater;
    }

    /**
     * Constructor.
     * 
     * @param filename 文件名
     * @throws FileNotFoundException FileNotFoundException
     */
    public TextFileLineReader(String filename) throws FileNotFoundException {
        this(filename, DEFAULT_CHARSET);
    }

    /**
     * Constructor.
     * 
     * @param fileName 文件名
     * @param charset 字符集
     * @throws FileNotFoundException FileNotFoundException
     */
    public TextFileLineReader(String fileName, String charset) throws FileNotFoundException {
        this(fileName != null ? new File(fileName) : null, charset);
    }

    /**
     * Constructor.
     * 
     * @param file 文件
     * @throws FileNotFoundException FileNotFoundException
     */
    public TextFileLineReader(File file) throws FileNotFoundException {
        this(file, DEFAULT_CHARSET);
    }

    /**
     * Constructor.
     * 
     * @param file 文件
     * @param charset 字符集
     * @throws FileNotFoundException FileNotFoundException
     */
    public TextFileLineReader(File file, String charset) throws FileNotFoundException {
        this.charset = charset;
        this.access = new RandomAccessFile(file, "r");
        this.hasBom = false;
        this.decorater = DEFAULT_DECORATER;
        isFirstLine = true;
    }

    /**
     * @see rough.preparation.utils.io.LineReader#readLine()
     */
    public String readLine() throws IOException {
        String line = access.readLine();
        if (line != null) {
            if (isFirstLine) {
                line = checkBom(line);
                isFirstLine = false;
            }
            line = new String(line.getBytes(READED_CHARSET), charset);
            return decorater.decorate(line);
        } else
            return null;
    }

    private String checkBom(String firstLine) {
        String result = firstLine;
        if (firstLine != null && firstLine.length() >= 3 && !hasBom) {
            if (firstLine.charAt(0) == 239 && firstLine.charAt(1) == 187 && firstLine.charAt(2) == 191) {
                charset = "UTF-8";
                result = firstLine.substring(3);
                hasBom = true;
            }
        }
        return result;
    }

    /**
     * @see rough.preparation.utils.io.LineReader#close()
     */
    public void close() {
        try {
            access.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
