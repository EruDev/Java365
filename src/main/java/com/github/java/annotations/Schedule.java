package com.github.java.annotations;

import java.lang.annotation.Repeatable;

/**
 * @author pengfei.zhao
 * @date 2020/10/13 20:05
 */
@Repeatable(Schedules.class)
public @interface Schedule {
    String dayOfMonth() default "first";
    String dayOfWeek() default "Mon";
    int hour() default 12;
}
