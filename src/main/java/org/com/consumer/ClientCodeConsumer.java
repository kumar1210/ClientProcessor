/**
 * 
 */
package org.com.consumer;

import static org.com.constants.GeneratorConstants.LAMBDA_JAVA_FUNCTION_REPLACE_STRING;
import org.com.helpers.AwsRequestHandlerGenerator;
import org.com.helpers.CodeGenerator;
import org.com.pojo.LambdaDetails;
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

		//logger.info("User script has been consumed, processing will be starting soon with data : " + userCode);
		LambdaDetails unitLambda = new LambdaDetails();
		unitLambda.setFunctionCode(userCode);

		// generates a new class AwsFunction.java, check template java-aws-sample -> App.java
		CodeGenerator generator = new AwsRequestHandlerGenerator("LambdaFunctionTemplate.java", LAMBDA_JAVA_FUNCTION_REPLACE_STRING);
		String newFile = generator.generate(userCode);
		logger.info("Newly generated file has content : "+ newFile);
		
		// Download zip template-project : get api to download template-project.zip :
		// https://github.com/kumar1210/java-aws-sample/archive/refs/heads/master.zip -- through postman it needs token

		// Copy AwsFunction.java in downloaded zip

		// mvn build to compile the new code
				// -- also create a docker container

		// deploy generated container as a lambda function

		// get the rest api of new lambda function, set in unitLambda

		unitLambda.setLambdaApi("https://aws.dummy.url");
		
		logger.info("User script has been consumed, and processed successfully " + unitLambda.toString());
		return new ResponseEntity<LambdaDetails>(unitLambda, HttpStatus.OK);

	}
}
