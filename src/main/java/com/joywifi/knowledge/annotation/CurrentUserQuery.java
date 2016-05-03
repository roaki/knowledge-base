package com.joywifi.knowledge.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.joywifi.knowledge.entity.BaseEntity;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUserQuery {

    QueryEnum type() default QueryEnum.LIST;

    Class<? extends BaseEntity> clazz() default BaseEntity.class;

    String pkField() default "id";

    enum QueryEnum{
        LIST, EDIT, UPDATE, DELETE
    }
}
