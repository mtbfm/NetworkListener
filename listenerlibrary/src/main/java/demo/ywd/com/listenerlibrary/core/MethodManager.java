package demo.ywd.com.listenerlibrary.core;

import java.lang.reflect.Method;

/**
 * 保存符合要求的网络监听注解方法
 * Created by ywd on 2019/6/12.
 */

public class MethodManager {
    //方法的类型(NetType)
    private Class<?> type;
    //网络(注解)类型(netType = NetType.WIFI)
    private NetType netType;
    //需要执行的方法(自动执行)
    private Method method;

    public MethodManager(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
