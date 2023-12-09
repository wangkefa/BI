package com.yupi.springbootinit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验
 *就是用注解的特性来进行校验
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
//都是元注解
@Target(ElementType.METHOD)//表示这个用于方法上面
@Retention(RetentionPolicy.RUNTIME)//生命周期
public @interface AuthCheck {
    /**
     * 必须有某个角色
     *
     * @return
     */
    String mustRole() default "";//就是一个字符变量
}

