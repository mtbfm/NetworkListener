package demo.ywd.com.listenerlibrary.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义网络状态注解
 * Created by ywd on 2019/6/10.
 */

@Target(ElementType.METHOD)//作用在方法之上
@Retention(RetentionPolicy.RUNTIME)//jvm运行时，通过反射获取该注解的值
public @interface Network {
    NetType netType();
}
