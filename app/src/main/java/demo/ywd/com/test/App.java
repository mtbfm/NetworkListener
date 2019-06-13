package demo.ywd.com.test;

import android.app.Application;

import demo.ywd.com.listenerlibrary.NetworkListener;

/**
 * Application
 * Created by ywd on 2019/6/10.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkListener.getInstance().init(this);
    }
}
