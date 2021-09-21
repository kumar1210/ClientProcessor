/**
 * 
 */
package org.com.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gaurkuku
 *
 */
public class AwsRequestHandlerGenerator extends CodeGenerator {

	private static final Logger logger = LoggerFactory.getLogger(AwsRequestHandlerGenerator.class);

	public AwsRequestHandlerGenerator(String fName, String replaceString) {
		super(fName, replaceString);
	}

	@Override
	public String generate(String newCode) {

		try {
			logger.info("Code Generation for file "+ getFileName() +" has been generated." );
			String filePath = getClass().getResource("/LambdaFunctionTemplate.java").getFile();
			File file = new File(filePath);
			String templateContents = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
			templateContents = templateContents.replace(getStrToReplace(), newCode);
			String newFilePath = getClass().getClassLoader().getResource(".").getFile() + "/LambdaFunction.java";
			File newFile = new File(newFilePath);
			if (newFile.createNewFile()) {
			    logger.info("A new Request handler is created!");
			} else {
			    logger.warn("File Already exists");
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
				writer.write(templateContents);
				writer.close();
			}
			logger.info("Request handler has been successfully created with the code : "+ newCode);
			return templateContents;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage() + " " + e);
		} catch (IOException e) {
			logger.error(e.getMessage() + " " + e);
		}
		return null;
	}

}
