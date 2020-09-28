package com.xbaimiao.plugins;

import java.lang.annotation.*;

/**
 * 监听器
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventListener {
}
