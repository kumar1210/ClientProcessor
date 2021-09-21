/**
 * 
 */
package org.com.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gaurkuku
 *
 */
public class UserDetails {

	private String userId;
	private String mailId;
	List<LambdaDetails> lambdaList = new ArrayList<LambdaDetails>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public List<LambdaDetails> getLambdaList() {
		return lambdaList;
	}

	public void setLambdaList(LambdaDetails unitLambda) {
		this.lambdaList.add(unitLambda);
	}

}
