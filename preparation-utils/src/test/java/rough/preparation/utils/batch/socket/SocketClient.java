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
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SocketClient implements Runnable {
    protected static Log logger = LogFactory.getLog(SocketClient.class);

    private int start;
    private int times;

    private static Integer finished = 0;
    private static long finishedTime = 0;

    public SocketClient(int start, int times) {
        this.start = start;
        this.times = times;
    }

    public void run() {
        try {
            for (int i = start; i < start + times; i++) {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                //客户端
                //1、创建客户端Socket，指定服务器地址和端口
                Socket socket;
                try {
                    socket = new Socket("localhost", 10086);
                    //2、获取输出流，向服务器端发送信息
                    OutputStream os = socket.getOutputStream();//字节输出流
                    PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
                    pw.write("用户名：admin" + (i) + "；密码：123");
                    pw.flush();
                    socket.shutdownOutput();
                    //3、获取输入流，并读取服务器端的响应信息
                    InputStream is = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String info = null;
                    while ((info = br.readLine()) != null) {
                        //logger.info("我是客户端" + (i) + "，服务器说：" + info);
                    }
                    stopWatch.stop();
                    logger.info(info + "客户端" + i + ", cost: " + stopWatch.getTime());

                    //4、关闭资源
                    br.close();
                    is.close();
                    pw.close();
                    os.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    logger.error(e, e);
                } catch (IOException e) {
                    logger.error(e, e);
                }
            }
        } finally {
            synchronized (finished) {
                finishedTime = System.currentTimeMillis();
                finished++;
            }
        }
    }

    public static void main(String[] args) {

        int start = 0;
        int threads = 1;
        int times = 10;

        SocketClient[] socketClients = new SocketClient[threads];

        for (int i = 0; i < threads; i++) {
            socketClients[i] = new SocketClient(i * times + start, times);
        }

        long startTime = System.currentTimeMillis();

        for (SocketClient socketClient : socketClients) {
            Thread thread = new Thread(socketClient);
            thread.start();
        }

        while (true) {
            if (finished >= threads) {
                break;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error(e, e);
                }
            }
        }

        double cost = (finishedTime - startTime) / 1000;
        logger.info("Tasks: " + threads * times + "; cost: " + cost + "; TPS: " + threads * times / cost);
    }
}
