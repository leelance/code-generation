package com.lance.code.generation.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lance.code.generation.web.SimpleApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SimpleApplication.class)
public class MainTest {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TableService tableService;

	@Test
	public void main() {
		long startTime = System.currentTimeMillis();
		logger.info("...........start application.........");
		
		tableService.run();
		
		logger.info("...end application...Time: {}",(System.currentTimeMillis()-startTime));
	}
}
