/*
 * Created on 2018年1月2日
 */
package rough.preparation.utils.batch.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import rough.preparation.batch.MultiThreadTaskHandler;

public class SocketTaskHandler extends MultiThreadTaskHandler<SocketWrapper, Boolean> {

    @Override
    protected Boolean doProcee(SocketWrapper task) {
        boolean result = false;
        if (task.getSocket() == null) {
            return false;
        }

        try {
            Thread.sleep(100);

            InputStream is = task.getSocket().getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                logger.info("我是服务器，客户端说：" + info + "; queueSize: " + SocketServerMain.taskQueue().size());
            }
            task.getSocket().shutdownInput();//关闭输入流

            long wait = 1000 - (System.currentTimeMillis() - task.getCreateTime().getTime());
            if (wait > 0) {
                Thread.sleep(wait);
            }

            //4、获取输出流，响应客户端的请求
            OutputStream os = task.getSocket().getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("欢迎您！");
            pw.flush();

            //5、关闭资源
            pw.close();
            os.close();
            br.close();
            isr.close();
            is.close();
            task.getSocket().close();

            result = true;
        } catch (IOException e) {
            logger.error(e, e);
        } catch (InterruptedException e) {
            logger.error(e, e);
        } finally {
        }
        return result;
    }

}
