package com.github.cjm0000000.mmt.core.parser.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define an class or field value.(merge from XStreamAlias)
 * @author lemon
 * @version 2.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface MmtAlias {
	/**
     * The value of the class or field value
     */
    public String value();
    public Class<?> impl() default Void.class; //Use Void to denote as Null
}
