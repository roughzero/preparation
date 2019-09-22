/*
 * Created on 2011-12-23
 */
package rough.preparation.utils.io;

import java.io.IOException;

/**
 * 文本行输出器实现基础类.
 * 
 * @author rough
 */
public abstract class AbstractLineWriter implements LineWriter {
    private String lineSeparator;

    public String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public AbstractLineWriter() {
        lineSeparator = System.getProperty("line.separator");
    }

    public void writeln(String line) throws IOException {
        write(line);
        writeln();
    }

    public void writeln() throws IOException {
        write(lineSeparator);
    }
}
