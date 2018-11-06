
package com.hh.improve.interceptor;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Refresh token annotation
 * <p>
 * 用法：在需要生成token的controller上增加@Token(generate = true)， 而在需要检查重复提交的controller上添加@Token(remove = true)
 * </p>
 * 
 * @author Summer
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RefreshToken {

	/**
	 * generate a new token for refresh
	 * 
	 * @return
	 */
	boolean generate() default false;

	/**
	 * remove token, without refresh
	 * 
	 * @return
	 */
	boolean remove() default false;
}
