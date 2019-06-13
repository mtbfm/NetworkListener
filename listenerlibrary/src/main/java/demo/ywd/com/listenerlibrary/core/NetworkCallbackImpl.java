package demo.ywd.com.listenerlibrary.core;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * NetworkCallbackImpl
 * Created by ywd on 2019/6/6.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    private static final String TAG = "NetworkCallbackImpl";
    //防止onCapabilitiesChanged方法中的逻辑执行多次
    private boolean needHandle = true;

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.d(TAG, "onAvailable: 网络已连接");
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.e(TAG, "onLost: 网络已中断");
        needHandle = true;
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        Log.d(TAG, "onCapabilitiesChanged: currentValue:" + needHandle);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) && needHandle) {
            Log.d(TAG, "onCapabilitiesChanged: 网络类型发生改变，NET_CAPABILITY_VALIDATED");
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.d(TAG, "onCapabilitiesChanged: 网络类型为WIFI");
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.d(TAG, "onCapabilitiesChanged: 网络类型为蜂窝");
            } else {
                Log.d(TAG, "onCapabilitiesChanged: 网络类型为其他");
            }
            needHandle = false;
        }
    }
}
