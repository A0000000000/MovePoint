package xyz.a00000.movepointserverdaemon;

import android.os.SystemClock;
import android.view.InputDevice;
import android.view.MotionEvent;

import java.nio.charset.StandardCharsets;

import xyz.a00000.movepointserverdaemon.bean.Point;
import xyz.a00000.movepointserverdaemon.connection.ServerSocketController;
import xyz.a00000.movepointserverdaemon.connection.SocketClient;
import xyz.a00000.movepointserverdaemon.input.InputManagerInject;
import xyz.a00000.movepointserverdaemon.utils.JsonUtils;

public class DaemonMain {

    public static int sPort = 33271;

    public static void main(String[] args) {
        System.out.println("Daemon process is running ...");
        try {
            System.out.println("Create InputManager.");
            InputManagerInject inputManager = new InputManagerInject();
            System.out.println("Create InputManager end.");
            System.out.println("Create ServerSocket, port is " + sPort);
            ServerSocketController controller = new ServerSocketController(sPort, socket -> new SocketClient(socket, data -> {
                try {
                    Point point = JsonUtils.fromJson(new String(data, StandardCharsets.UTF_8), Point.class);
                    System.out.println("Get input data: " + point);
                    if (point != null) {
                        MotionEvent ev = getMotionEvent(point);
                        inputManager.injectInputEvent(ev, 2);
                        ev.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start());
            System.out.println("Create ServerSocket end.");
            System.out.println("Run Socket Listener.");
            controller.task();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Daemon process is exit.");
        System.exit(0);
    }

    public static MotionEvent getMotionEvent(Point point) {
        MotionEvent ev = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), point.getType(), point.getX(), point.getY(), 0);
        ev.setSource(InputDevice.SOURCE_TOUCHSCREEN);
        return ev;
    }

}
