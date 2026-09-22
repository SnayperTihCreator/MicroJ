package org.tihrc.microj.core.transforms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PyExport {
    String name() default "";
    boolean args() default false;
    boolean kwargs() default false;
}
