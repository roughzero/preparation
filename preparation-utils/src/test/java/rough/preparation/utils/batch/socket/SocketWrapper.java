/*
 * Created on 2018年1月2日
 */
package rough.preparation.utils.batch.socket;

import java.net.Socket;
import java.util.Date;

public class SocketWrapper {

    private final Date createTime;
    private final Socket socket;

    public SocketWrapper(Socket socket) {
        this.createTime = new Date();
        this.socket = socket;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Socket getSocket() {
        return socket;
    }

}
