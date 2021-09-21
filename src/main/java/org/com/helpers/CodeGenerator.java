/**
 * 
 */
package org.com.helpers;

/**
 * @author gaurkuku
 *
 */
public abstract class CodeGenerator {

	private String fileName;
	private String strToReplace;

	protected CodeGenerator(String fName, String replace) {
		this.fileName = fName;
		this.strToReplace = replace;
	}

	/**
	 * 
	 * @param newCode
	 *                <p>
	 *                this needs to replace at the position of @param strToReplace
	 *                within the @param fileName
	 */
	public abstract String generate(String newCode);

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
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

}
