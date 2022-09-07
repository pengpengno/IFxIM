package com.ifx.fxstarter.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FxStage {

    String scope() default "single";

    String path();

    boolean startShow() default false; // 启动时是否展示

    boolean lazy() default false;


}
