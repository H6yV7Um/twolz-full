package com.twolz.qiyi.web.aop.bind;

import java.lang.annotation.*;

/**
 * 方法描述
 * Created by liuwei
 * date: 2017-10-25
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Operate {
    String value() default "";
    boolean isLogParams() default true;
}