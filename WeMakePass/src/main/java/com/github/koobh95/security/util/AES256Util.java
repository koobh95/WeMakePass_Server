package com.github.koobh95.security.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.github.koobh95.data.model.enums.ErrorCode;
import com.github.koobh95.exception.AesDecryptException;
import com.github.koobh95.exception.AesEncryptException;

import lombok.extern.log4j.Log4j2;

/**
 * - 양방향 암호화를 위한 암호화/복호화 유틸리티 클래스.
 * - SecurityConfig 클래스에서 bean으로 등록된다.
 * 
 * @author BH-Ku
 * @since 2023-12-20
 */
@Log4j2
public class AES256Util {
	private final SecretKeySpec SECRET_KEY_SPEC; // 32bytes
	private final IvParameterSpec IV_PRAM_SPEC; // 16bytes(fixed)
	private final String ALGORITHM = "AES/CBC/PKCS5Padding";
	
	private final String NO_SUCH_ALGORITHM_ERR_MSG = 
			"지원하지 않는 암호화 알고리즘이거나 현재 환경에서 사용할 수 없는 알고리즘입니다.";
	private final String ILLEGAL_BLOCK_SIZE_ERR_MSG = 
			"암/복호화 중 문제가 발생했습니다. 비밀 키 혹은 IV 값이 일치하지 않을 수 있습니다.";
	private final String INVALID_KEY_ERR_MSG = 
			"비밀 키 혹은 알고리즘이 유효하지 않습니다.";
	private final String UNSUPPORTED_ENCODING_ERR_MSG = 
			"지원하지 않는 인코딩 방식입니다.";
	
	// 이 클래스가 SecurityConfig에 의해 bean으로 초기화될 때 키값을 전달받아 초기화한다. 
	public AES256Util(String secretKey, String iv) {
		SECRET_KEY_SPEC = new SecretKeySpec(secretKey.getBytes(), "AES");
		IV_PRAM_SPEC = new IvParameterSpec(iv.getBytes());
	}
	
	/**
	 * - 문자열을 암호화하는 메서드로, 주로 응답할 데이터를 암호화하는데 사용된다.
	 * - 클라이언트로 전송이 가능하도록 URL Encoder를 사용한다.
	 * 
	 * @param plainText 암호화되지 않은 평문
	 * @return 암호화된 암호문
	 */
	public String encrypt(String plainText) {
		String encryptedText = null;
		
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY_SPEC, IV_PRAM_SPEC);
			byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));	
			encryptedText = Base64.getUrlEncoder().encodeToString(encryptedBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			log.error(NO_SUCH_ALGORITHM_ERR_MSG);
			throw new AesEncryptException(ErrorCode.AES_ENCRYPTION_ERROR);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			log.error(ILLEGAL_BLOCK_SIZE_ERR_MSG);
			throw new AesEncryptException(ErrorCode.AES_ENCRYPTION_ERROR);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			log.error(INVALID_KEY_ERR_MSG);
			throw new AesEncryptException(ErrorCode.AES_ENCRYPTION_ERROR);
		} catch (UnsupportedEncodingException e) {
			log.error(UNSUPPORTED_ENCODING_ERR_MSG);
			throw new AesEncryptException(ErrorCode.AES_ENCRYPTION_ERROR);
		}
		
		return encryptedText;
	}
	
	/**
	 * - 문자열을 복호화하는 메서드로, 주로 클라이언트로부터 받은 Request의 parameter 혹은 body에
	 *  담긴 암호화된 데이터를 복호화하는데 사용된다.
	 * - 클라이언트 측에서 암호화를 수행할 때 URL Encoder를 사용하므로 복호화할 때에도 똑같이 URL 
	 *  Encoder를 사용한다.
	 * 
	 * @param encryptedText 암호화된 암호문
	 * @return 복호화된 평문
	 */
	public String decrypt(String encryptedText) {
		String plainText = null;
		
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY_SPEC, IV_PRAM_SPEC);
			String base64Decoded = URLDecoder.decode(encryptedText, "UTF-8");
			byte[] decodeBytes = Base64.getDecoder().decode(base64Decoded);
			byte[] decryptedBytes = cipher.doFinal(decodeBytes);
			plainText = new String(decryptedBytes, "UTF-8");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			log.error(NO_SUCH_ALGORITHM_ERR_MSG);
			throw new AesDecryptException(ErrorCode.AES_DECRYPTION_ERROR);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			log.error(ILLEGAL_BLOCK_SIZE_ERR_MSG);
			throw new AesDecryptException(ErrorCode.AES_DECRYPTION_ERROR);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			log.error(INVALID_KEY_ERR_MSG);
			throw new AesDecryptException(ErrorCode.AES_DECRYPTION_ERROR);
		} catch (UnsupportedEncodingException e) {
			log.error(UNSUPPORTED_ENCODING_ERR_MSG);
			throw new AesDecryptException(ErrorCode.AES_DECRYPTION_ERROR);
		}
		
		return plainText;
	}
}
