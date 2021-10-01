/*
 * Created on 2011-10-20
 */
package rough.preparation.utils.batch;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import rough.preparation.batch.MultiThreadBatchRunner;
import rough.preparation.utils.io.LineReader;
import rough.preparation.utils.io.TextFileLineReader;

public class SleepBatchRunnerTest {

    protected static Log logger = LogFactory.getLog(SleepBatchRunnerTest.class);

    private static final String COMMAND_FILE = "batch.command";

    public static void main(String[] args) throws InterruptedException {
        SleepBatchRunner batchRunner = new SleepBatchRunner();
        batchRunner.setDebugCount(10);
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put(MultiThreadBatchRunner.KEY_COUNT_OF_THREADS, "2");
        batchRunner.run(parameter);

        while (true) {
            // 检查命令
            String cmd = readCommand();

            if ("stop".equalsIgnoreCase(cmd)) {
                logger.info("Accept command to stop!");
                batchRunner.stop();
                break;
            }
            if (batchRunner.getFinished() >= 1000)
                break;
            Thread.sleep(1000);
        }
    }

    private static String readCommand() {
        String cmd = null;
        File file = new File(COMMAND_FILE);
        if (file.exists() && file.isFile()) {
            try {
                LineReader reader = new TextFileLineReader(file);
                cmd = reader.readLine();
                reader.close();
                file.delete();
            } catch (IOException e) {
                logger.error("Catch Exception when read command file.", e);
            }
        }
        logger.debug("Read Command: " + cmd);
        return cmd;
    }
}
