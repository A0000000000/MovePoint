package xyz.a00000.movepointserverdaemon.connection;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import xyz.a00000.movepointserverdaemon.utils.ByteLengthUtils;

public class SocketClient extends Thread {

    private Socket mSocket;
    private OnDataReadyRead mOnDataReadyRead;

    public SocketClient(Socket socket, OnDataReadyRead onDataReadyRead) {
        mSocket = socket;
        mOnDataReadyRead = onDataReadyRead;
    }

    @Override
    public void run() {
        try {
            InputStream is = mSocket.getInputStream();
            while (true) {
                byte[] sizeArr = new byte[] {(byte) is.read(), (byte) is.read(), (byte) is.read(), (byte) is.read()};
                int size = ByteLengthUtils.getLengthInteger(sizeArr);
                byte[] data = new byte[size];
                for (int i = 0; i < size; i++) {
                    data[i] = (byte) is.read();
                }
                if (mOnDataReadyRead != null) {
                    mOnDataReadyRead.onDataReadyRead(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean write(byte[] data) {
        try {
            OutputStream os = mSocket.getOutputStream();
            os.write(ByteLengthUtils.getLengthArray(data.length));
            os.write(data);
            os.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
