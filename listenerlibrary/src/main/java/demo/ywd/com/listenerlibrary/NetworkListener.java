package demo.ywd.com.listenerlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import demo.ywd.com.listenerlibrary.netcallback.NetworkCallbackImpl;
import demo.ywd.com.listenerlibrary.receiver.NetworkStateReceiverWithAnno;
import demo.ywd.com.listenerlibrary.template.SingletonTemplate;

/**
 * 网络监听
 * Created by ywd on 2019/6/5.
 */

public class NetworkListener {
    private Context context;
    private NetworkCallbackImpl networkCallback;
    private NetworkStateReceiverWithAnno receiver;

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
        if (isHigherThenLollipop()) {
            networkCallback = new NetworkCallbackImpl();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            ConnectivityManager connMgr = (ConnectivityManager) NetworkListener.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr != null) {
                connMgr.registerNetworkCallback(request, networkCallback);
            }
        } else {
            //5.0以下继续使用广播
            receiver = new NetworkStateReceiverWithAnno();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        }
    }

    /**
     * 注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    public void registerObserver(Object observer) {
        if (isHigherThenLollipop()) {
            networkCallback.registerObserver(observer);
        } else {
            receiver.registerObserver(observer);
        }
    }

    /**
     * 解除注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    public void unRegisterObserver(Object observer) {
        if (isHigherThenLollipop()) {
            networkCallback.unRegisterObserver(observer);
        } else {
            receiver.unRegisterObserver(observer);
        }
    }

    /**
     * 版本是否是5.0及以上
     *
     * @return true/false
     */
    private boolean isHigherThenLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
