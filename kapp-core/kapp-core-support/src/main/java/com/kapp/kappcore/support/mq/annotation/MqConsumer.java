package com.kapp.kappcore.support.mq.annotation;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RabbitListener
public @interface MqConsumer {
    @AliasFor(annotation = RabbitListener.class, attribute = "queues")
    String[] queue() default {};

}
