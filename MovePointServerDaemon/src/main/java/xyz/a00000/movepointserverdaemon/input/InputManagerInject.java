package xyz.a00000.movepointserverdaemon.input;

import android.hardware.input.InputManager;
import android.view.InputEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InputManagerInject {

    private InputManager mInputManager;
    private Method mInjectInputEvent;

    public InputManagerInject() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getInstance = InputManager.class.getMethod("getInstance");
        getInstance.setAccessible(true);
        mInputManager = (InputManager) getInstance.invoke(null);
        mInjectInputEvent = InputManager.class.getMethod("injectInputEvent", InputEvent.class, Integer.TYPE);
        mInjectInputEvent.setAccessible(true);
    }

    public boolean injectInputEvent(InputEvent event, int mode) throws InvocationTargetException, IllegalAccessException {
        if (event != null) {
            return (boolean) mInjectInputEvent.invoke(mInjectInputEvent, event, mode);
        }
        return false;
    }


}
