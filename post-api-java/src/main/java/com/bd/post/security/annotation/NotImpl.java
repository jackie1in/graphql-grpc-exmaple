package com.bd.post.security.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 未实现rpc用此a
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface NotImpl {
}
