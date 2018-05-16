package com.mobileai.luncert;

import com.mobileai.luncert.interceptor.AdminAuthInterceptor;
import com.mobileai.luncert.interceptor.AuthInterceptor;

import org.mybatis.spring.annotation.MapperScan;
import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@MapperScan("com.mobileai.luncert.model.mysql")
@EnableTransactionManagement
@EnableNeo4jRepositories("com.mobileai.luncert.model.neo4j")
@EntityScan(basePackages = "com.mobileai.luncert.model.neo4j")
@EnableWebMvc
@Configuration
public class Config implements WebMvcConfigurer {

	@Bean
	public AdminAuthInterceptor adminAuthInterceptor() {
		// note spring doesn't response to manage customized Interceptor
		return new AdminAuthInterceptor();
	}

	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(authInterceptor());
		registration.addPathPatterns("/event/**");

		registration = registry.addInterceptor(adminAuthInterceptor());
		registration.addPathPatterns("/event/**");
	}

	// neo4j

    @Bean
    public SessionFactory sessionFactory() {
        // with domain entity base package(s)
        return new SessionFactory(getConfiguration(), "com.mobileai.luncert.model.neo4j");
    }

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
		return new org.neo4j.ogm.config.Configuration.Builder()
			.uri("bolt://neo4j:Luncert428@luncert.cn")
			.build();
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }

}
