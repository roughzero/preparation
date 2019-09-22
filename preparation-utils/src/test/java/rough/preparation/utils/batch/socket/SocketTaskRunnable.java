/*
 * Created on 2018年1月10日
 */
package rough.preparation.utils.batch.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SocketTaskRunnable implements Runnable {
    protected Log logger = LogFactory.getLog(getClass());

    private Date createTime;
    private Socket socket;

    public SocketTaskRunnable(Socket socket) {
        this.createTime = new Date(System.currentTimeMillis());
        this.socket = socket;
    }

    public void run() {
        try {
            Thread.sleep(100);

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                logger.info("我是服务器，客户端说：" + info + ";");
            }
            socket.shutdownInput();//关闭输入流

            long wait = 1000 - (System.currentTimeMillis() - createTime.getTime());
            if (wait > 0) {
                Thread.sleep(wait);
            }

            //4、获取输出流，响应客户端的请求
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("欢迎您！");
            pw.flush();

            //5、关闭资源
            pw.close();
            os.close();
            br.close();
            isr.close();
            is.close();
            socket.close();

        } catch (IOException e) {
            logger.error(e, e);
        } catch (InterruptedException e) {
            logger.error(e, e);
        } finally {
        }
    }

}
