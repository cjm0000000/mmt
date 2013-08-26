package lemon.shared.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {
	private static final String S = "abcdefghijklmnopqrstuvwxyz1234567890,.`-=/ABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*()_+";
	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return
	 */
	public static String encrypt(String content, String encryptKey) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(encryptKey.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return parseByte2HexStr(result); // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param encryptKey
	 *            解密密钥
	 * @return
	 */
	public static String decrypt(String hexString, String encryptKey) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(encryptKey.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(parseHexStr2Byte(hexString));
			return new String(result); // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < buf.length; i++) {
                    String hex = Integer.toHexString(buf[i] & 0xFF);
                    if (hex.length() == 1) {
                            hex = '0' + hex;
                    }
                    sb.append(hex.toUpperCase());
            }
            return sb.toString();
    }
    
    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
            if (hexStr.length() < 1)
                    return null;
            byte[] result = new byte[hexStr.length()/2];
            for (int i = 0;i< hexStr.length()/2; i++) {
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
                    result[i] = (byte) (high * 16 + low);
            }
            return result;
    }
    
    /**
     * 生成密钥
     * @param length
     * @return
     */
    public static String generateSecretKey(int length){
    	SecureRandom rnd = new SecureRandom();
    	StringBuilder sb = new StringBuilder();
    	final int keyLen = S.length();
    	for (int i = 0; i < length; i++) {
			sb.append(S.charAt(rnd.nextInt(keyLen)));
		}
    	return sb.toString();
    }
	
	public static void main(String[] args){
        String content = "pass";
        String secureKey = "uziV2+-1FkVt)gkfuBsFsR3UgWvagl$yLRFfPbdw_)-x%9-F-W3qLg5umr/DNZh)oWfnIwl18t/BsJ#%PEFJ(GGyOW/atOWcSWlNym6yiomyd^4YQ!HjP=15.)IpYr(0UoTu_Y`UxoGHf)spiDEiCkQG6NW=P20nj/~ZyFPU6W8.vcY=7qeNuYB^G!H$aIzlQaZgrCSI8~)MbNqPP@@Yi1UI4eh7sRn`^5e=aqqiHnXVr%Ur=5@3&Z_.DmfH#J&u";
        System.out.println(secureKey);
        //加密
        System.out.println("加密前：" + content);
        String secCode = encrypt(content, secureKey);
        System.out.println(secCode);
        System.out.println(secCode.length());
        System.out.println("解密后：" + decrypt(secCode, secureKey));
	}
}
