package demo.ywd.com.listenerlibrary.broadcast.annotation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;

import demo.ywd.com.listenerlibrary.Constants;
import demo.ywd.com.listenerlibrary.template.SingletonTemplate;

/**
 * 网络监听-使用注解
 * Created by ywd on 2019/6/5.
 */

public class NetworkListenerWithAnno {
    private Context context;
    private NetworkStateReceiverWithAnno receiver;

    /**
     * 私有化构造方法
     */
    private NetworkListenerWithAnno() {
        receiver = new NetworkStateReceiverWithAnno();
    }

    private static final SingletonTemplate<NetworkListenerWithAnno> INSTANCE = new SingletonTemplate<NetworkListenerWithAnno>() {
        @Override
        protected NetworkListenerWithAnno create() {
            return new NetworkListenerWithAnno();
        }
    };

    public static NetworkListenerWithAnno getInstance() {
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
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        context.registerReceiver(receiver, filter);
    }

    /**
     * 注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    public void registerObserver(Object observer) {
        receiver.registerObserver(observer);
    }

    /**
     * 解除注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    public void unRegisterObserver(Object observer) {
        receiver.unRegisterObserver(observer);
    }
}
