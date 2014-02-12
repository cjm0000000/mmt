package com.github.cjm0000000.mmt.shared.toolkit.secure;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.cjm0000000.mmt.core.config.MmtCharset;

/**
 * Secure Util for MMT
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public final class SecureUtil {
	private static Log logger = LogFactory.getLog(SecureUtil.class);
	private static final String S = "abcdefghijklmnopqrstuvwxyz1234567890,.`-=/ABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*()_+";
	
	/**
	 * AES加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return
	 */
	public static String aesEncrypt(String content, String encryptKey) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(encryptKey.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes(MmtCharset.LOCAL_CHARSET);
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return parseByte2HexStr(result); // 加密
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | UnsupportedEncodingException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * AES解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param encryptKey
	 *            解密密钥
	 * @return
	 */
	public static String aesDecrypt(String hexString, String encryptKey) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(encryptKey.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(toByteArray(hexString));
			return new String(result); // 加密
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**将二进制转换成16进制
     * @param buf
     * @return
     */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1)
				hex = '0' + hex;
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
    
	/**
	 * SHA1
	 * 
	 * @param str
	 * @return
	 */
	public static String sha1(String str) {
		try {
			byte[] digest = MessageDigest.getInstance("SHA1").digest( str.getBytes());
			return toHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			logger.error("ERROR:SHA1 faild: " + e.getMessage());
		}
		return null;
	}

	/**
	 * MD5
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		try {
			byte[] digest = MessageDigest.getInstance("MD5").digest( str.getBytes());
			return toHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			logger.error("ERROR:MD5 faild: " + e.getMessage());
		}
		return null;
	}
	
	/**
     * 生成密钥
     * @param length
     * @return
     */
	public static String generateSecretKey(int length) {
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder();
		final int keyLen = S.length();
		for (int i = 0; i < length; i++)
			sb.append(S.charAt(rnd.nextInt(keyLen)));
		return sb.toString();
	}

	/**
	 * byte to hex
	 * @param digest
	 * @return
	 */
	private static String toHexString(byte[] digest) {
		String str = "";
		String tempStr = "";
		for (int i = 0; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1)
				str = str + "0" + tempStr;
			else
				str = str + tempStr;
		}
		return str.toLowerCase();
	}
	
	/**将16进制转换为二进制
     * @param hexStr
     * @return
     */
	private static byte[] toByteArray(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	public static void main(String[] args){
        String content = "pass";
        String secureKey = "uziV2+-1FkVt)gkfuBsFsR3UgWvagl$yLRFfPbdw_)-x%9-F-W3qLg5umr/DNZh)oWfnIwl18t/BsJ#%PEFJ(GGyOW/atOWcSWlNym6yiomyd^4YQ!HjP=15.)IpYr(0UoTu_Y`UxoGHf)spiDEiCkQG6NW=P20nj/~ZyFPU6W8.vcY=7qeNuYB^G!H$aIzlQaZgrCSI8~)MbNqPP@@Yi1UI4eh7sRn`^5e=aqqiHnXVr%Ur=5@3&Z_.DmfH#J&u";
        System.out.println(secureKey);
        //加密
        System.out.println("加密前：" + content);
        String secCode = aesEncrypt(content, secureKey);
        System.out.println(secCode);
        System.out.println(secCode.length());
        System.out.println("解密后：" + aesDecrypt(secCode, secureKey));
	}

}
