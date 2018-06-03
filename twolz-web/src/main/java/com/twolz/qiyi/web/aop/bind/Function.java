package com.twolz.qiyi.web.aop.bind;

import java.lang.annotation.*;

/**
 * 每个类的功能描述
 * Created by liuwei
 * date: 2017-10-25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Function {
    String value() default "";
    String moduleName() default "";
    String subModuleName() default "";
}
