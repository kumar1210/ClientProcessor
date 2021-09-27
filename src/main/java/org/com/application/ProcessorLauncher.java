package org.com.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "org.com.consumer" })
public class ProcessorLauncher {

	private static final Logger logger = LoggerFactory.getLogger(ProcessorLauncher.class);

	public static void main(String[] args) {
		logger.info(
				"------------------------- Client Processor is getting instantiated -------------------------------");
		SpringApplication.run(ProcessorLauncher.class, args);
		logger.info(
				"------------------------- Client Processor is successfully instantiated -------------------------------");
	}
}
