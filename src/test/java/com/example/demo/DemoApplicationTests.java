package com.example.demo;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void hasSingleRequestMappingHandlerMappingBean() {
		assertThat(applicationContext.getBeansOfType(RequestMappingHandlerMapping.class)).hasSize(1);
		assertThat(applicationContext.getBean(RequestMappingHandlerMapping.class)).isExactlyInstanceOf(ApiVersionedRequestMapping.class);
	}

}
