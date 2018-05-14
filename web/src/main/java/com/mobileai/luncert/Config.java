package com.mobileai.luncert;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@MapperScan("com.mobileai.luncert.dao.mysql")
@EnableTransactionManagement
@EnableNeo4jRepositories("com.mobileai.luncert.dao.neo4j")
@EnableWebMvc
public class Config {

}
