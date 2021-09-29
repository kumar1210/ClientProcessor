/**
 * 
 */
package org.com.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.com.pojo.LambdaDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public String generate(LambdaDetails lambdaDetails) {

		try {
			logger.info("Code Generation for lambda " + getLambdaDetails().getLambdaName() + " has been started.");
			String filePath = getTemplateFilePath();
			File templateFile = new File(filePath);
			String templateContents = new String(Files.readAllBytes(templateFile.toPath()), StandardCharsets.UTF_8);
			String updatedFileContents = templateContents.replace(getStrToReplace(), lambdaDetails.getUserCode());
			updatedFileContents = updatedFileContents.replace("LambdaFunction", lambdaDetails.getLambdaName());
			return updatedFileContents;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage() + " " + e);
		} catch (IOException e) {
			logger.error(e.getMessage() + " " + e);
		}
		return null;
	}

}
