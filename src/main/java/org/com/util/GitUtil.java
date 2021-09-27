/**
 * 
 */
package org.com.util;

import static org.com.constants.GeneratorConstants.TEMPLATE_PROJECT_SRC_PATH;
import static org.com.constants.GeneratorConstants.TEMPLATE_PROJECT_URI;
import static org.com.constants.GeneratorConstants.TEMP_LOCATION;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gaurkuku
 *
 */
public class GitUtil {

	private static final Logger logger = LoggerFactory.getLogger(GitUtil.class);

	public static String getGitRepo() throws InvalidRemoteException, TransportException, GitAPIException {

		Path currentPath = Paths.get("");
		String projectHome = currentPath.toAbsolutePath().getParent().getParent().toString();
		logger.info("Project Home path is: " + projectHome);
		File gitDirectory = new File(projectHome + TEMP_LOCATION);
		Boolean theDir = gitDirectory.mkdir();
		logger.info("the new temp directory has been created : " + theDir);
		if (!theDir) {
			try {
				FileUtils.cleanDirectory(gitDirectory);
			} catch (IOException e) {
				logger.error("Error while cleaning directory : " + TEMP_LOCATION + " with error " + e);
			}
		}

		Git.cloneRepository().setURI(TEMPLATE_PROJECT_URI).setDirectory(gitDirectory).call().close();

		return gitDirectory.getAbsolutePath();

	}

	public static void replaceFileInBaseRepo(String srcFile, String destPath) {

		try {
			File sourceFile = new File(srcFile);
			destPath = destPath + TEMPLATE_PROJECT_SRC_PATH;
			File destDir = new File(destPath);
			FileUtils.copyFileToDirectory(sourceFile, destDir);
			logger.info("Generated source file : " + srcFile + " has been successfully copied at path : " + destPath);
		} catch (IOException e) {
			logger.error("Error while copy replacing file in base directory " + e);
		}
	}
}
