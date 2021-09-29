/**
 * 
 */
package org.com.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.com.pojo.LambdaDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.com.constants.GeneratorConstants.FILE_SEPARATOR;

/**
 * @author gaurkuku
 *
 */
public abstract class CodeGenerator {

	private String templateFileName;
	private String strToReplace;
	private LambdaDetails lambdaDetails;
	private String fileExtension;
	private String lambdaHomeDirectory;

	private static final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);

	protected CodeGenerator(String fName, String replace) {
		this.templateFileName = fName;
		this.strToReplace = replace;
	}

	/***
	 * 
	 * @param lambdaDetails
	 * @return
	 */
	public abstract String generate(LambdaDetails lambdaDetails);

	/**
	 * @return the templateFileName
	 */
	public String getTemplateFileName() {
		return templateFileName;
	}

	/**
	 * @param templateFileName the templateFileName to set
	 */
	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	/**
	 * @return the strToReplace
	 */
	public String getStrToReplace() {
		return strToReplace;
	}

	/**
	 * @param strToReplace the strToReplace to set
	 */
	public void setStrToReplace(String strToReplace) {
		this.strToReplace = strToReplace;
	}

	/**
	 * @return the lambdaDetails
	 */
	public LambdaDetails getLambdaDetails() {
		return lambdaDetails;
	}

	/**
	 * @param lambdaDetails the lambdaDetails to set
	 */
	public void setLambdaDetails(LambdaDetails lambdaDetails) {
		this.lambdaDetails = lambdaDetails;
	}

	/**
	 * @return the fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * @param fileExtension the fileExtension to set
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	/**
	 * @return the lambdaHomeDirectory
	 */
	public String getLambdaHomeDirectory() {
		return lambdaHomeDirectory;
	}

	/**
	 * @param lambdaHomeDirectory the lambdaHomeDirectory to set
	 */
	public void setLambdaHomeDirectory(String lambdaHomeDirectory) {
		this.lambdaHomeDirectory = lambdaHomeDirectory;
	}

	/**
	 * 
	 * @return the template file absolute path
	 */
	public String getTemplateFilePath() {

		return getClass().getResource(getTemplateFileName()).getFile();
	}

	public String createFile(String fileContents, String lambdaHomePath) {

		try {

			File lambdaDirectory = createNewCleanDirectory(lambdaHomePath);

			// save the directory path for future purpose
			setLambdaHomeDirectory(lambdaDirectory.getAbsolutePath());

			String newFilePath = lambdaDirectory + FILE_SEPARATOR + getLambdaDetails().getLambdaName()
					+ getFileExtension();
			File newFile = createNewFile(newFilePath);

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
				writer.write(fileContents);
				writer.close();
			}

			return newFilePath;
		} catch (IOException e) {
			logger.error("Error while generating Lambda File : ", e);
		}
		return null;
	}

	private File createNewCleanDirectory(String path) {

		File lambdaDirectory = new File(path + FILE_SEPARATOR + lambdaDetails.getLambdaName());
		Boolean theDir = lambdaDirectory.mkdir();
		logger.info("the new temp directory has been created : " + theDir);
		if (!theDir) {
			try {
				FileUtils.cleanDirectory(lambdaDirectory);
			} catch (IOException e) {
				logger.error("Error while cleaning directory : " + lambdaDetails.getLambdaName() + " with error ", e);
			}
		}
		return lambdaDirectory;
	}

	private File createNewFile(String newFilePath) throws IOException {

		File newFile = new File(newFilePath);
		if (newFile.exists()) {
			logger.warn("File Already exists");
			if (newFile.delete()) {
				newFile.createNewFile();
				logger.info("A new Request handler is created with name : " + lambdaDetails.getLambdaName());
			}
		} else {
			newFile.createNewFile();
			logger.info("A new Request handler is created with name : " + lambdaDetails.getLambdaName());
		}
		return newFile;

	}
}
