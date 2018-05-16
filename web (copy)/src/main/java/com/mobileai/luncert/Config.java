package com.mobileai.luncert;

import com.mobileai.luncert.interceptor.AuthInterceptor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@MapperScan("com.mobileai.luncert.dao.mysql")
@EnableTransactionManagement
@EnableNeo4jRepositories("com.mobileai.luncert.dao.neo4j")
@EnableWebMvc
public class Config implements WebMvcConfigurer {

	@Bean
	public AuthInterceptor authInterceptor() {
		// note spring doesn't response to manage customized Interceptor
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(authInterceptor());
		registration.addPathPatterns("/today/task/*");
	}

}
