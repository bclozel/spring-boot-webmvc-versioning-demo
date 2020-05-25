package com.example.demo;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class ApiVersionedRequestMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersionedResource typeAnnotation = AnnotationUtils.findAnnotation(handlerType, ApiVersionedResource.class);
        return createCondition(typeAnnotation);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        ApiVersionedResource methodAnnotation = AnnotationUtils.findAnnotation(method.getClass(), ApiVersionedResource.class);
        return createCondition(methodAnnotation);
    }

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return super.isHandler(beanType) && (AnnotationUtils.findAnnotation(beanType, ApiVersionedResource.class) != null);
    }

    private RequestCondition<?> createCondition(ApiVersionedResource versionMapping) {
        if (versionMapping != null) {
            return new ApiVersionedResourceRequestCondition(versionMapping.version());
        }

        return null;
    }
}