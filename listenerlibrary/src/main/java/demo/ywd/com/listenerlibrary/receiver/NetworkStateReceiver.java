package demo.ywd.com.listenerlibrary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import demo.ywd.com.listenerlibrary.Constants;
import demo.ywd.com.listenerlibrary.core.NetChangeListener;
import demo.ywd.com.listenerlibrary.core.NetType;
import demo.ywd.com.listenerlibrary.utils.NetworkUtils;

/**
 * 广播接收-使用接口回调形式
 * Created by ywd on 2019/6/12.
 */

public class NetworkStateReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkStateReceiver";
    private NetType netType;
    private NetChangeListener listener;

    public NetworkStateReceiver() {
        //初始化网络连接状态
        netType = NetType.NONE;
    }

    public void setListener(NetChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Log.e(TAG, "onReceive: 异常");
            return;
        }
        if (intent.getAction().equals(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.d(TAG, "onReceive: 网络发生变化");
            netType = NetworkUtils.getNetType();
            if (NetworkUtils.isNetworkAvailable()) {
                Log.d(TAG, "onReceive: 网络连接成功");
                if (listener != null) {
                    listener.onConnect(netType);
                }
            } else {
                Log.e(TAG, "onReceive: 网络连接失败");
                if (listener != null) {
                    listener.onDisConnect();
                }
            }
        }
    }
}
