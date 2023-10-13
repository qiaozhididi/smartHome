package iot.app.smarthome;

import android.app.Application;

import org.litepal.LitePal;

public class SmarthomeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
