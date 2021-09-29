/**
 * 
 */
package org.com.pojo;

import java.nio.file.Paths;
import java.util.function.BiFunction;

import org.com.helpers.AwsJavaCodeGenerator;
import org.com.helpers.CodeGenerator;
import org.com.util.RepoUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author gaurkuku
 *
 */
@Configuration
public class ProcessorBeans {

	@Bean
	@Qualifier("resourcePath")
	public String getResourcePath() {
		return getClass().getClassLoader().getResource(".").getFile();
	}

	@Bean
	@Qualifier("lambdaHomePath")
	public String getHomePath() {
		return Paths.get("").toAbsolutePath().getParent().toString();
	}

	@Bean
	@Qualifier("generatorFactory")
	public BiFunction<String, String, CodeGenerator> generatorFactory() {
		return (fileName, replaceString) -> constructJavaCodeGeenrator(fileName, replaceString);
	}

	@Bean
	@Scope(value = "prototype")
	public CodeGenerator constructJavaCodeGeenrator(String fileName, String replaceString) {
		return new AwsJavaCodeGenerator(fileName, replaceString);
	}
	
	@Bean
	public RepoUtil getRepoUtil() {
		return new RepoUtil();
	}
}
