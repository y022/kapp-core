package com.kapp.kappcore.web.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置自定义拦截器
 */
@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {

	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截所有请求
		registry.addInterceptor(globalInterceptor()).addPathPatterns("/**");
		log.info("系统拦截器初始化完成");
	}
	
	@Bean
	public GlobalInterceptor globalInterceptor() {
		return new GlobalInterceptor();
	}

	// 拦截器跨域配置
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 跨域路径
		CorsRegistration cors = registry.addMapping("/**");
		// 可访问的外部域
		cors.allowedOrigins("*");
		// 支持跨域用户凭证
		//cors.allowCredentials(true);
		//cors.allowedOriginPatterns("*");
		// 设置 header 能携带的信息
		cors.allowedHeaders("*");
		// 支持跨域的请求方法
		cors.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
		// 设置跨域过期时间，单位为秒
		cors.maxAge(3600);
	}

	// 简写形式
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//				.allowedOrigins("*")
//				//.allowCredentials(true)
//				//.allowedOriginPatterns("*")
//				.allowedHeaders("*")
//				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//				.maxAge(3600);
//	}

}
