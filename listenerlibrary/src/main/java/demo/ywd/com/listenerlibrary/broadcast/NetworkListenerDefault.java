package demo.ywd.com.listenerlibrary.broadcast;

import android.content.Context;
import android.content.IntentFilter;

import demo.ywd.com.listenerlibrary.Constants;
import demo.ywd.com.listenerlibrary.core.NetChangeListener;
import demo.ywd.com.listenerlibrary.template.SingletonTemplate;

/**
 * 网络监听-使用接口回调形式
 * Created by ywd on 2019/6/12.
 */

public class NetworkListenerDefault {
    private Context context;
    private NetworkStateReceiver receiver;

    private NetworkListenerDefault() {
        receiver = new NetworkStateReceiver();
    }

    private static final SingletonTemplate<NetworkListenerDefault> INSTANCE = new SingletonTemplate<NetworkListenerDefault>() {
        @Override
        protected NetworkListenerDefault create() {
            return new NetworkListenerDefault();
        }
    };

    public static NetworkListenerDefault getInstance() {
        return INSTANCE.get();
    }

    public Context getContext() {
        return context;
    }

    public void setListener(NetChangeListener listener) {
        receiver.setListener(listener);
    }

    public void init(Context context) {
        this.context = context;
        IntentFilter filter = new IntentFilter(Constants.ANDROID_NET_CHANGE_ACTION);
        context.registerReceiver(receiver, filter);
    }
}
