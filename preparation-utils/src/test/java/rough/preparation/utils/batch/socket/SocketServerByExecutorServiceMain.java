/*
 * Created on 2018年1月2日
 */
package rough.preparation.utils.batch.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class SocketServerByExecutorServiceMain {
    private static final int PORT = 10086;
    private static final int THREADS = 2;

    private static boolean isStopping = false;

    private static ServerSocket serverSocket = null;
    private static ExecutorService executorService = null;

    public static void main(String[] args) throws IOException {
        init();

        while (!isStopping) {
            Socket socket = serverSocket.accept();

            executorService.execute(new SocketTaskRunnable(socket));
        }

        close();
    }

    public static ExecutorService executorService() {
        return executorService;
    }

    private static void init() throws IOException {
        serverSocket = new ServerSocket(PORT); //1024-65535的某个端口
        executorService = new ScheduledThreadPoolExecutor(THREADS);
    }

    /**
     * 关闭所有资源
     * @throws IOException 
     */
    private static void close() throws IOException {
        executorService.shutdown();
        serverSocket.close();
    }

}