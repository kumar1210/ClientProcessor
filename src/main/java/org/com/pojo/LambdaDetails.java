/**
 * 
 */
package org.com.pojo;

/**
 * @author gaurkuku
 *
 */
public class LambdaDetails {

	private String functionCode;
	private String lambdaApi;

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getLambdaApi() {
		return lambdaApi;
	}

	public void setLambdaApi(String lambdaApi) {
		this.lambdaApi = lambdaApi;
	}

	@Override
	public String toString() {
		return " { \"FunctionCode\" : " + functionCode + ", \"lambdaApi\" : " + lambdaApi +"}";
	}
}
