package com.jh.util.cache;

import com.jh.util.CacheUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:id转name：
 *
 * @version <1> 2019-02-16 14:54 lijie: Created.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdTransform {
    public String type();
    public String propName();
    public String transType() default CacheUtil.CACHE_TRANS_NAME;//默认名称
}
