package cn.webank.mumble.mpc.giftcard.common.annotation;

import java.lang.annotation.*;

/**
 * Created by leaflyhuang on 2017/7/6.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface WechatEventMethod {

    String[] events();

    Class<?> requestMessageClass();

}
