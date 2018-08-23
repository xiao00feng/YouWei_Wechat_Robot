package cn.webank.mumble.mpc.giftcard.common.annotation;

import java.lang.annotation.*;

/**
 * @author leaflyhuang on 2015/11/17.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface ParameterStringLength {
    public int minLength();

    public int maxLength();

}
