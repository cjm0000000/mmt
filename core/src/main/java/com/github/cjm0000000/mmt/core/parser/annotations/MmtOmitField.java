package com.github.cjm0000000.mmt.core.parser.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a field to be omitted.
 * @author lemon
 * @version 2.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MmtOmitField {

}
