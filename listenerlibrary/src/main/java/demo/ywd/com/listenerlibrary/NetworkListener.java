package demo.ywd.com.listenerlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import demo.ywd.com.listenerlibrary.core.NetworkCallbackImpl;
import demo.ywd.com.listenerlibrary.template.SingletonTemplate;

/**
 * 网络监听
 * Created by ywd on 2019/6/5.
 */

public class NetworkListener {
    private Context context;

    /**
     * 私有化构造方法
     */
    private NetworkListener() {
    }

    private static final SingletonTemplate<NetworkListener> INSTANCE = new SingletonTemplate<NetworkListener>() {
        @Override
        protected NetworkListener create() {
            return new NetworkListener();
        }
    };

    public static NetworkListener getInstance() {
        return INSTANCE.get();
    }

    public Context getContext() {
        return context;
    }

    /**
     * 初始化
     *
     * @param context context
     */
    @SuppressLint("MissingPermission")
    public void init(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager.NetworkCallback callback = new NetworkCallbackImpl();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            ConnectivityManager connMgr = (ConnectivityManager) NetworkListener.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr != null) {
                connMgr.registerNetworkCallback(request, callback);
            }
        } else {
            //5.0以下继续使用广播
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        }
    }
}
