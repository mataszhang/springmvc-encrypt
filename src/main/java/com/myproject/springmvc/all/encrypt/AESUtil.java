package com.myproject.springmvc.all.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * <b>
 *	AES算法，只有一把对称的公钥，加密解密都用一把公钥	
 * </b>
 * 
 * @author Rogers
 * @date 2016-5-15 下午7:42:39
 *
 */
public class AESUtil {

	private static final String KEY_AES = "AES";

	public static String encrypt(String src, String key) throws Exception {
		if (key == null || key.length() != 16) {
			throw new Exception("key不满足条件");
		} 
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
		Cipher cipher = Cipher.getInstance(KEY_AES);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(src.getBytes());
		return byte2hex(encrypted);
	}

	public static String decrypt(String src, String key) throws Exception {
		if (key == null || key.length() != 16) {
			throw new Exception("key不满足条件");
		}
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
		Cipher cipher = Cipher.getInstance(KEY_AES);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] encrypted1 = hex2byte(src);
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString;
	}

	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);
		}
		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) {
		// 密钥，长度必须是16位
		String key = "2key9=+-1!!@#$-)";
		// 传输的原文
		String src = "Hello Tom!";
		try {
			String encryptText = AESUtil.encrypt(src, key);
			System.out.println("encryptText=" + encryptText);
			String decryptText = AESUtil.decrypt(encryptText, key);
			System.out.println("decryptText=" + decryptText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
