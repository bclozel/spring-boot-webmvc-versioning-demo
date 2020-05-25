package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RequestMapping
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersionedResource {

    /**
     * Version of the API defined as npm range.
     *
     * @return
     */
    String version() default VersionFilter.DEFAULT_VERSION;
}