/**
 * 
 */
package com.myproject.springmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;

/**
 * <b>
 *
 * </b>
 * 
 * @author Rogers
 * @date 2016-5-7 下午10:48:37
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SmartRequestParam {

	boolean required() default true;
	
	Class<?> type() default HashMap.class;
	
}
