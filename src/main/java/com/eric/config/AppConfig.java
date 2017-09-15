package com.eric.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 取代以前的XML,改以JAVA的POJO來做Spring環境的設定
 * @author yung.ho
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.eric")
public class AppConfig {
	
}
