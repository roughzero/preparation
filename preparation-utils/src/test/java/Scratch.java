import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rough.preparation.utils.io.TextFileLineReader;
import rough.preparation.utils.io.TextFileLineWriter;

import java.io.File;
import java.io.IOException;

public class Scratch {
    protected static Log logger = LogFactory.getLog(Scratch.class);

    public static void main(String[] args) throws IOException {
        String path = "d:\\data\\rough\\tmp\\";
        File parent = new File(path);
        String[] files = {"gh-charge.log",};
        TextFileLineWriter writer = new TextFileLineWriter(new File(parent, "out.txt"));
        int i = 0;
        for (String file : files) {
            TextFileLineReader reader = new TextFileLineReader(new File(parent, file));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                int index = line.indexOf("cn.com.sinosoft.test.CostTimeLogger");
                if (index > 0) {
                    line = line.substring(index + "cn.com.sinosoft.test.CostTimeLogger === - ".length());
                    writer.writeln(line);
                }
            }
            i++;
            if (i % 1000 == 0) {
                logger.info(i + " lines finished.");
            }
        }
    }
}