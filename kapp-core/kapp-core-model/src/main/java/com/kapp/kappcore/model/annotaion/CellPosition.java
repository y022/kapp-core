package com.kapp.kappcore.model.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CellPosition {
    int row() default 0;
    int column() default 0;
    String cellRef() default "A1";
}
