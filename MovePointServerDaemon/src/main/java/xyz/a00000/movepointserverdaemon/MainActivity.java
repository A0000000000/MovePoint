package xyz.a00000.movepointserverdaemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // run process cmd:
        // app_process -Djava.class.path=/sdcard/Download/classes.dex /system/bin xyz.a00000.movepointserverdaemon.DaemonMain
        new DaemonMain();
    }
}