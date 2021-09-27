/**
 * 
 */
package org.com.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gaurkuku
 *
 */
public class AESCipher {

	private static SecretKeySpec secretKey;
	private static byte[] key;
	private static final String PADDING = "AES/ECB/PKCS5PADDING";
	private static final String ALGORITHM = "SHA-1";
	private static final Logger logger = LoggerFactory.getLogger(AESCipher.class);
	
	private static void generateSecretKey(String stringKey) {
		MessageDigest shaDigest = null;

		try {
			key = stringKey.getBytes(StandardCharsets.UTF_8);
			shaDigest = MessageDigest.getInstance(ALGORITHM);
			key = shaDigest.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error while generating the secret key "+ e.getMessage() + e);
		}
	}

	public static String encrypt(String strToEncrypt, String secret) {
		try {
			generateSecretKey(secret);
			Cipher cipher = Cipher.getInstance(PADDING);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			logger.error("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt, String secret) {
		try {
			generateSecretKey(secret);
			Cipher cipher = Cipher.getInstance(PADDING);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			logger.error("Error while decrypting: " + e.toString());
		}
		return null;
	}
}
