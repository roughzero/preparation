/*
 * Created on 2011-12-23
 */
package rough.preparation.utils.io;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TextFileConverter {
    private final TextFileLineReader reader;
    private final TextFileLineWriter writer;

    public TextFileConverter(String source, String sourceCharset, String target, String targetCharset)
            throws FileNotFoundException {
        this.reader = new TextFileLineReader(source, sourceCharset);
        this.writer = new TextFileLineWriter(target, targetCharset);
    }

    public void setDecorator(LineDecorator decorator) {
        reader.setDecorator(decorator);
    }

    public void setLineSeparator(String lineSeparator) {
        writer.setLineSeparator(lineSeparator);
    }

    public void setHasBom(boolean hasBom) {
        writer.setHasBom(hasBom);
    }

    public void convert() throws IOException {
        try {
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
                if (line != null)
                    writer.writeln();
            }
        } finally {
            reader.close();
            writer.close();
        }
    }
}
