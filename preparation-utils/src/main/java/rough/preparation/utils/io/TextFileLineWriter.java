/*
 * Created on 2011-12-23
 */
package rough.preparation.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 写入对象为文本文件的 LineWriter 实现类.
 * 默认字符集为"UTF-8", 不带 BOM 信息.
 * 注意每次写入之前会删除原有的文件.
 *
 * @author rough
 */
public class TextFileLineWriter extends AbstractLineWriter implements LineWriter {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final boolean DEFAULT_HAS_BOM = false;
    private final String charset;

    private boolean hasBom;
    private boolean isFirstLine;
    private final RandomAccessFile access;

    public boolean isHasBom() {
        return hasBom;
    }

    public void setHasBom(boolean hasBom) {
        this.hasBom = hasBom;
    }

    /**
     * Constructor.
     *
     * @param filename 文件名.
     * @throws FileNotFoundException FileNotFoundException
     */
    public TextFileLineWriter(String filename) throws FileNotFoundException {
        this(filename, DEFAULT_CHARSET);
    }

    /**
     * Constructor.
     *
     * @param filename 文件名
     * @param charset  字符集
     * @throws FileNotFoundException FileNotFoundException
     */
    public TextFileLineWriter(String filename, String charset) throws FileNotFoundException {
        this(filename != null ? new File(filename) : null, charset);
    }

    /**
     * Constructor.
     *
     * @param file 文件
     * @throws FileNotFoundException FileNotFoundException
     */
    public TextFileLineWriter(File file) throws FileNotFoundException {
        this(file, DEFAULT_CHARSET);
    }

    /**
     * Constructor.
     *
     * @param file    文件
     * @param charset 字符集
     * @throws FileNotFoundException If file does not exists.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public TextFileLineWriter(File file, String charset) throws FileNotFoundException {
        if (file == null) {
            throw new FileNotFoundException("File can not be null.");
        }
        this.charset = charset;
        if (file.exists())
            file.delete();
        this.access = new RandomAccessFile(file, "rw");
        this.hasBom = DEFAULT_HAS_BOM;
        isFirstLine = true;
    }

    /**
     * @see rough.preparation.utils.io.LineWriter#write(java.lang.String)
     */
    public void write(String line) throws IOException {
        if (isFirstLine) {
            writeBom();
            isFirstLine = false;
        }
        access.write(line.getBytes(charset));
    }

    /**
     * 写入文件的 BOM 信息.
     *
     * @throws IOException While catch IOException.
     */
    private void writeBom() throws IOException {
        if (hasBom) {
            if (this.charset.equalsIgnoreCase("UTF-8")) {
                access.write(new byte[]{(byte) 239, (byte) 187, (byte) 191});
            }
        }
    }

    /**
     * @see rough.preparation.utils.io.LineWriter#close()
     */
    public void close() {
        try {
            if (access != null)
                access.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
