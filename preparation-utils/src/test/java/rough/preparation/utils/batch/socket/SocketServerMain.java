/*
 * Created on 2018年1月2日
 */
package rough.preparation.utils.batch.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import rough.preparation.batch.MultiThreadBatchRunner;

public class SocketServerMain {
    private static final int PORT = 10086;
    private static final int THREADS = 2;

    private static boolean isStopping = false;

    private static Queue<SocketWrapper> taskQueue = new ConcurrentLinkedQueue<SocketWrapper>();

    private static ServerSocket serverSocket = null;

    public static void main(String[] args) throws IOException {
        init();

        SocketTaskRunner socketTaskRunner = new SocketTaskRunner(taskQueue);
        socketTaskRunner.setDebugCount(0);
        Map<String, String> params = new HashMap<String, String>();
        params.put(MultiThreadBatchRunner.KEY_COUNT_OF_THREADS, Integer.toString(THREADS));
        socketTaskRunner.run(params);

        while (!isStopping) {
            Socket socket = serverSocket.accept();

            taskQueue.add(new SocketWrapper(socket));
        }

        serverSocket.close();
        close();
    }

    public static Queue<SocketWrapper> taskQueue() {
        return taskQueue;
    }

    private static void init() throws IOException {
        serverSocket = new ServerSocket(PORT); //1024-65535的某个端口
    }

    /**
     * 关闭所有资源
     * @throws IOException 
     */
    private static void close() throws IOException {
        serverSocket.close();
    }

}