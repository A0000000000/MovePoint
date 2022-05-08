package xyz.a00000.movepointserverdaemon.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketController {

    private int mPort;
    private ServerSocket mServerSocket;
    private OnSocketConnection mOnSocketConnection;

    public ServerSocketController(int port, OnSocketConnection onSocketConnection) throws IOException {
        mPort = port;
        mServerSocket = new ServerSocket(port);
        mOnSocketConnection = onSocketConnection;
    }

    public void task() throws IOException {
        while (true) {
            Socket socket = mServerSocket.accept();
            if (mOnSocketConnection != null) {
                mOnSocketConnection.onSocketConnection(socket);
            }
        }
    }

}
