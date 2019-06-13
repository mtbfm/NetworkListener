package demo.ywd.com.test;

import android.app.Application;

import demo.ywd.com.listenerlibrary.broadcast.NetworkListenerDefault;
import demo.ywd.com.listenerlibrary.broadcast.annotation.NetworkListenerWithAnno;

/**
 * Application
 * Created by ywd on 2019/6/10.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        NetworkListenerDefault.getInstance().init(this);
        NetworkListenerWithAnno.getInstance().init(this);
//        NetworkListenerWithAnno.getInstance().init(this);
//        NetworkListener.getInstance().init(this);
    }
}
