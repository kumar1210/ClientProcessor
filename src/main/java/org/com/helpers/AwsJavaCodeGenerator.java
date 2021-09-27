/**
 * 
 */
package org.com.helpers;

import static org.com.constants.GeneratorConstants.NEW_JAVA_FILE_NAME;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author gaurkuku
 *
 */
public class AwsJavaCodeGenerator extends CodeGenerator {

	private static final Logger logger = LoggerFactory.getLogger(AwsJavaCodeGenerator.class);

	public AwsJavaCodeGenerator(String fName, String replaceString) {
		super(fName, replaceString);
	}

	@Override
	public String generate(String newCode) {

		try {
			logger.info("Code Generation for file " + getFileName() + " has been started.");
			String filePath = getClass().getResource(getFileName()).getFile();
			File templateFile = new File(filePath);
			String templateContents = new String(Files.readAllBytes(templateFile.toPath()), StandardCharsets.UTF_8);
			templateContents = templateContents.replace(getStrToReplace(), newCode);
			String targetDir = getClass().getClassLoader().getResource(".").getFile();
			String newFilePath = targetDir + NEW_JAVA_FILE_NAME;
			File newFile = new File(newFilePath);
			if (newFile.exists()) {
				logger.warn("File Already exists");
				if (newFile.delete()) {
					newFile.createNewFile();
					logger.info("A new Request handler is created!");
				}
			} else {
				newFile.createNewFile();
				logger.info("A new Request handler is created!");
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
				writer.write(templateContents);
				writer.close();
			}
			return newFilePath;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage() + " " + e);
		} catch (IOException e) {
			logger.error(e.getMessage() + " " + e);
		}
		return null;
	}

}
