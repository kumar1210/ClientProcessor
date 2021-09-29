/**
 * 
 */
package org.com.pojo;

/**
 * @author gaurkuku
 *
 */
public class LambdaDetails {

	private String userCode;
	private String lambdaApi;
	private String lambdaName;

	/**
	 * @return the lambdaName
	 */
	public String getLambdaName() {
		return lambdaName;
	}

	/**
	 * @param lambdaName the lambdaName to set
	 */
	public void setLambdaName(String lambdaName) {
		this.lambdaName = lambdaName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getLambdaApi() {
		return lambdaApi;
	}

	public void setLambdaApi(String lambdaApi) {
		this.lambdaApi = lambdaApi;
	}

	@Override
	public String toString() {
		return " { \"FunctionCode\" : " + userCode + ", \"lambdaApi\" : " + lambdaApi +", \"lambdaName\" : "+ lambdaName + " }";
	}
}
