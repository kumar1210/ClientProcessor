/**
 * 
 */
package org.com.consumer;

import static org.com.constants.GeneratorConstants.LAMBDA_JAVA_FUNCTION_REPLACE_STRING;
import static org.com.constants.GeneratorConstants.TEMPLATE_JAVA_FILE_NAME;

import org.com.helpers.AwsJavaCodeGenerator;
import org.com.helpers.CodeGenerator;
import org.com.pojo.LambdaDetails;
import org.com.util.GitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gaurkuku
 *
 */
@RestController
public class ClientCodeConsumer {

	private static final Logger logger = LoggerFactory.getLogger(ClientCodeConsumer.class);

	@PostMapping("/publishCode")
	public ResponseEntity<LambdaDetails> consumeScript(@RequestBody String userCode) {

		// logger.info("User script has been consumed, processing will be starting soon
		// with data : " + userCode);
		LambdaDetails unitLambda = new LambdaDetails();
		unitLambda.setFunctionCode(userCode);

		// generates a new class AwsFunction.java, check template java-aws-sample ->
		// App.java
		CodeGenerator generator = new AwsJavaCodeGenerator(TEMPLATE_JAVA_FILE_NAME,
				LAMBDA_JAVA_FUNCTION_REPLACE_STRING);
		String newFile = generator.generate(userCode);
		logger.info("New file has generated at : " + newFile);

		try {
			String templateProjectPath = GitUtil.getGitRepo();
			logger.info("template project has been pulled at : " + templateProjectPath);
			GitUtil.replaceFileInBaseRepo(newFile, templateProjectPath);
		} catch (Exception e) {
			logger.error("Error while cloning git repo : " + e);
		}
		// mvn build to compile the new code
		// -- also create a docker container

		// deploy generated container as a lambda function

		// get the rest api of new lambda function, set in unitLambda

		unitLambda.setLambdaApi("https://aws.dummy.url");

		logger.info("User script has been consumed, and processed successfully " + unitLambda.toString());
		return new ResponseEntity<LambdaDetails>(unitLambda, HttpStatus.OK);

	}

}
