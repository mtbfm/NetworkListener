package demo.ywd.com.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import demo.ywd.com.listenerlibrary.broadcast.annotation.NetworkListenerWithAnno;
import demo.ywd.com.listenerlibrary.core.NetType;
import demo.ywd.com.listenerlibrary.core.Network;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkListenerWithAnno.getInstance().registerObserver(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkListenerWithAnno.getInstance().unRegisterObserver(this);
    }

    @Network(netType = NetType.WIFI)
    public void onNetChanged(NetType netType) {
        Log.d(TAG, "onNetChanged: 网络发生改变" + netType.name());
    }
}
