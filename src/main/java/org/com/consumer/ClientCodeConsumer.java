/**
 * 
 */
package org.com.consumer;

import static org.com.constants.GeneratorConstants.LAMBDA_JAVA_FUNCTION_REPLACE_STRING;
import static org.com.constants.GeneratorConstants.TEMPLATE_JAVA_FILE_NAME;
import static org.com.constants.GeneratorConstants.JAVA_FILE_EXTENSION;
import static org.com.constants.GeneratorConstants.SUCCESS;

import java.util.function.BiFunction;

import org.com.helpers.CodeGenerator;
import org.com.pojo.LambdaDetails;
import org.com.util.RepoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaurkuku
 *
 */
@RestController
public class ClientCodeConsumer {

	@Autowired
	@Qualifier("resourcePath")
	String resourcePath;

	@Autowired
	@Qualifier("lambdaHomePath")
	String lambdaHomePath;

	@Autowired
	@Qualifier("generatorFactory")
	private BiFunction<String, String, CodeGenerator> javaGeneratorFactory;
	
	@Autowired
	RepoUtil repoUtil;

	private static final Logger logger = LoggerFactory.getLogger(ClientCodeConsumer.class);

	@PostMapping("/publishJavaCode")
	public ResponseEntity<LambdaDetails> consumeScript(@RequestBody String userCode, @RequestHeader String lambdaName) {

		logger.info("User script has been consumed, processing will be starting soon");
		LambdaDetails unitLambda = new LambdaDetails();
		unitLambda.setUserCode(userCode);
		unitLambda.setLambdaName(lambdaName);

		CodeGenerator generator = javaGeneratorFactory.apply(TEMPLATE_JAVA_FILE_NAME,
				LAMBDA_JAVA_FUNCTION_REPLACE_STRING);
		generator.setLambdaDetails(unitLambda);
		generator.setFileExtension(JAVA_FILE_EXTENSION);
		String newFileContent = generator.generate(unitLambda);
		String newFilePath = generator.createFile(newFileContent, lambdaHomePath);
		logger.info("New file has generated at : " + newFilePath);

		String gitCloneStatus = repoUtil.getGitRepo(generator);
		if (SUCCESS.equalsIgnoreCase(gitCloneStatus)) {
			logger.info("template project has been pulled : " + gitCloneStatus);
		} else {
			return new ResponseEntity<LambdaDetails>(HttpStatus.FAILED_DEPENDENCY);
		}
		Boolean replacedStatus = repoUtil.replaceFileInBaseRepo(generator);
		if (replacedStatus) {
			String mvnLogs = repoUtil.compileLambdaProject(generator);
			logger.info(mvnLogs);
		} else {
			logger.error("Failed while copying the generated file to base template project " + replacedStatus);
		}

		// deploy generated container as a lambda function

		// get the rest api of new lambda function, set in unitLambda

		unitLambda.setLambdaApi("https://aws.dummy.url");

		logger.info("User script has been consumed, and processed successfully " + unitLambda.toString());
		return new ResponseEntity<LambdaDetails>(unitLambda, HttpStatus.OK);

	}

}
