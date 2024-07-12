package com.kapp.kappcore.support.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Data;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.persistence.EntityManager;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * set up beans
 */
@Configuration
public class ConfigurationSupport {
    @Bean
    public MapperFacade mapperFacade() {
        return new DefaultMapperFactory.Builder().build().getMapperFacade();
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    @Configuration
    public static class MqConfiguration {
        @Bean
        public MessageConverter jsonMessageConverter() {
            return new Jackson2JsonMessageConverter();
        }

        @Bean
        public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
            return new RabbitAdmin(connectionFactory);
        }

        @Bean
        public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
            final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
            rabbitTemplate.setMessageConverter(messageConverter);
            return rabbitTemplate;
        }

        @Bean
        public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
            SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
            factory.setConnectionFactory(connectionFactory);
            factory.setMessageConverter(messageConverter);
            factory.setPrefetchCount(600);
            return factory;
        }
    }


    @EnableAsync
    @Configuration
    public static class ThreadConfiguration implements AsyncConfigurer {
        @Data
        @Configuration
        @ConfigurationProperties("spring.task.execution.pool")
        public static class CustomizePoolProperties {
            private int queueCapacity;
            private int coreSize;
            private int maxSize;
            private String threadNamePrefix = "Async-Task-";
        }

        @Bean
        public ThreadPoolTaskScheduler taskScheduler(CustomizePoolProperties properties) {
            ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
            scheduler.setPoolSize(properties.getCoreSize());
            scheduler.setThreadNamePrefix(properties.getThreadNamePrefix());
            scheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            scheduler.setWaitForTasksToCompleteOnShutdown(true);
            scheduler.setAwaitTerminationSeconds(60);
            scheduler.initialize();
            return scheduler;
        }

        @Bean
        public Executor asyncExecutor(ThreadPoolTaskScheduler scheduler) {
            return scheduler;
        }


    }

}
