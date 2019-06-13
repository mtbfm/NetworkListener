package demo.ywd.com.listenerlibrary.netcallback;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import demo.ywd.com.listenerlibrary.NetworkListener;
import demo.ywd.com.listenerlibrary.core.MethodManager;
import demo.ywd.com.listenerlibrary.core.NetType;

/**
 * NetworkCallback实现
 * Created by ywd on 2019/6/13.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    private static final String TAG = "NetworkCallbackImpl";
    private NetType netType;
    private Map<Object, List<MethodManager>> networkMap;

    public NetworkCallbackImpl() {
        //初始化
        netType = NetType.NONE;
        networkMap = new HashMap<>();
    }

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.d(TAG, "onAvailable: 网络已连接");
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.e(TAG, "onLost: 网络已断开");
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.d(TAG, "onCapabilitiesChanged: 网络类型为wifi");
                post(NetType.WIFI);
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.d(TAG, "onCapabilitiesChanged: 蜂窝网络");
                post(NetType.CMWAP);
            } else {
                Log.d(TAG, "onCapabilitiesChanged: 其他网络");
                post(NetType.AUTO);
            }
        }
    }

    private void post(NetType netType) {
        Set<Object> set = networkMap.keySet();
        for (Object observer : set) {
            List<MethodManager> methodList = networkMap.get(observer);
            if (methodList != null) {
                for (MethodManager methodManager : methodList) {
                    //两者参数比较
                    if (methodManager.getType().isAssignableFrom(netType.getClass())) {
                        switch (methodManager.getNetType()) {
                            case AUTO:
                                invoke(methodManager, observer, netType);
                                break;
                            case WIFI:
                                if (netType == NetType.WIFI || netType == NetType.NONE) {
                                    invoke(methodManager, observer, netType);
                                }
                                break;
                            case CMWAP:
                                if (netType == NetType.CMWAP || netType == NetType.NONE) {
                                    invoke(methodManager, observer, netType);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(MethodManager methodManager, Object observer, NetType netType) {
        try {
            methodManager.getMethod().invoke(observer, netType);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    public void registerObserver(Object observer) {
        List<MethodManager> methodManagers = networkMap.get(observer);
        if (methodManagers == null) {
            methodManagers = findAnnotationMethods(observer);
            networkMap.put(observer, methodManagers);
        }
    }

    private List<MethodManager> findAnnotationMethods(Object observer) {
        List<MethodManager> methodList = new ArrayList<>();
        Class<?> clazz = observer.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //获取方法注解
            demo.ywd.com.listenerlibrary.core.Network network = method.getAnnotation(demo.ywd.com.listenerlibrary.core.Network.class);
            if (network == null) {
                continue;
            }
            //方法参数校验
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException(method.getName() + "有且只有一个参数与");
            }
            String name = parameterTypes[0].getName();
            if (name.equals(NetType.class.getName())) {
                MethodManager methodManager = new MethodManager(parameterTypes[0], network.netType(), method);
                methodList.add(methodManager);
            }
        }
        return methodList;
    }

    /**
     * 解除注册
     *
     * @param observer 观察者(Activity/Fragment)
     */
    public void unRegisterObserver(Object observer) {
        if (!networkMap.isEmpty()) {
            networkMap.remove(observer);
        }
        Log.d(TAG, "unRegisterObserver: " + observer.getClass().getName() + "注销成功");
    }

    /**
     * 应用退出时调用
     */
    public void unRegisterAll() {
        if (!networkMap.isEmpty()) {
            networkMap.clear();
        }
    }
}
