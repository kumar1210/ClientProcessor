/**
 * 
 */
package org.com.util;

import static org.com.constants.GeneratorConstants.FAILURE;
import static org.com.constants.GeneratorConstants.FILE_SEPARATOR;
import static org.com.constants.GeneratorConstants.GIT;
import static org.com.constants.GeneratorConstants.MAVEN_CLEAN_INSTALL;
import static org.com.constants.GeneratorConstants.SUCCESS;
import static org.com.constants.GeneratorConstants.TEMPLATE_PROJECT_SRC_PATH;
import static org.com.constants.GeneratorConstants.TEMPLATE_PROJECT_URI;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.com.helpers.CodeGenerator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gaurkuku
 *
 */
public class RepoUtil {

	private static final Logger logger = LoggerFactory.getLogger(RepoUtil.class);

	public String getGitRepo(CodeGenerator generator) {

		try {
			File gitDirectory = new File(generator.getLambdaHomeDirectory() + FILE_SEPARATOR + GIT);
			Git.cloneRepository().setURI(TEMPLATE_PROJECT_URI).setDirectory(gitDirectory).call().close();
			return SUCCESS;
		} catch (GitAPIException e) {
			logger.error("error while cloning the base project from git : ", e);
			return FAILURE;
		}
	}

	public Boolean replaceFileInBaseRepo(CodeGenerator generator) {

		Boolean copyReplace = true;
		try {
			String srcFile = generator.getLambdaHomeDirectory() + FILE_SEPARATOR
					+ generator.getLambdaDetails().getLambdaName() + generator.getFileExtension();
			File sourceFile = new File(srcFile);
			String destPath = generator.getLambdaHomeDirectory() + FILE_SEPARATOR + GIT + TEMPLATE_PROJECT_SRC_PATH;
			File destDir = new File(destPath);
			FileUtils.copyFileToDirectory(sourceFile, destDir);
			logger.info("Generated source file : " + srcFile + " has been successfully copied at path : " + destPath);
			return copyReplace;
		} catch (IOException e) {
			logger.error("Error while copy replacing file in base directory " + e);
			copyReplace = false;
		}
		return copyReplace;
	}

	public String compileLambdaProject(CodeGenerator generator) {

		List<String> commands = new ArrayList<>();
		commands.add("bash");
		commands.add("-l");
		commands.add("-c");
		commands.add(MAVEN_CLEAN_INSTALL);
		ProcessBuilder pb = new ProcessBuilder(commands);
		String mavenHome = generator.getLambdaHomeDirectory() + FILE_SEPARATOR + GIT;
		File mavenDir = new File(mavenHome);
		System.out.println(mavenDir.exists());
		pb.directory(mavenDir);
		Process pr = null;
		try {
			pr = pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Runtime.getRuntime().exec(new String[]{"bash", "-l", "-c", "mvn", "clean",
		// "install"}, null, dir);

		BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String str = null, finalString = "";

		try {
			while ((str = br.readLine()) != null) {
				finalString = finalString + "\n" + str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return finalString;
	}
}
