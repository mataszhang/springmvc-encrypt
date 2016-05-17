package com.myproject.springmvc.rsa;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import com.myproject.springmvc.utils.StringUtils;

public class RSAHelper {

	/** 可以先注册到虚拟机中,再通过名称使用;也可以不注册,直接传入使用 */  
    public static final Provider pro = new BouncyCastleProvider();  
    
	public final static String KEY_ALGORTHM = "RSA";
	
	public final static String CHAR_SET = "UTF-8";
	
    private RSAPrivateKey privateKey = null;  
    
    private RSAPublicKey publicKey = null; 
 
    public final static class LazyHolder {
    	public static RSAHelper rsaHelper = new RSAHelper();
    }
    
    public void generateKeyPair(String seedKey) throws Exception {
    	if (StringUtils.isBlank(seedKey)) {
    		throw new IllegalArgumentException("缺少种子参数，无法生成密钥对");
    	}
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", pro);  
        kpg.initialize(1024, new SecureRandom(seedKey.getBytes()));  
        KeyPair kp = kpg.generateKeyPair();  
  
        publicKey = (RSAPublicKey) kp.getPublic();  
        privateKey = (RSAPrivateKey) kp.getPrivate();  
    }  
    
    public String getPublicKeyStr() {
    	return new String(Base64.encode(publicKey.getEncoded()));
    }
    
    public String getPrivateKeyStr() {
    	return new String(Base64.encode(privateKey.getEncoded()));
    }

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	public String encryptByPublicKey(String originStr, String publicKeyStr) throws Exception {
		if (originStr == null) {
			throw new IllegalArgumentException("originStr is null");
		}
		if (publicKeyStr == null) {
			throw new IllegalArgumentException("publicKeyStr is null");
		}
		// 对公钥解码
		byte[] keyBytes = Base64.decode(publicKeyStr); //Base64Utils.decode(pubKey);
		// 取公钥
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);

		// jdk1.7你不支持  RSA/None/PKCS1Padding
//		 Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
		
		// 支持RSA/ECB/PKCS1Padding，每次加密后的密文都是会动态改变的
		// 参考：http://stackoverflow.com/questions/22102382/cant-find-rsa-none-nopadding-provider-in-jdk-1-8
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		
		// 下面这种方式，每次加密后的密文都是一样的，不会变
//		Cipher cipher = Cipher.getInstance("RSA", pro);  
		
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] originBys = originStr.getBytes("utf-8");
		byte[] encryptBys = cipher.doFinal(originBys);

//		String encryptStr = new String(encryptBys, CHAR_SET);
//		String encodeStr = URLEncoder.encode(encryptStr, CHAR_SET);
		String encodeStr = new String(Base64.encode(encryptBys));
		return encodeStr;
		
	}

	public String decryptByPrivateKey(String encryptText, String privateKeyStr) throws Exception {
		if (encryptText == null) {
			throw new IllegalArgumentException("encryptText is null");
		}
		if (privateKeyStr == null) {
			throw new IllegalArgumentException("privateKeyStr is null");
		}
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        Cipher cipher = Cipher.getInstance("RSA", pro);  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        byte[] re = cipher.doFinal(Base64.decode(encryptText.getBytes()));  
		String decryptText = new String(re);
		return decryptText;
	}
    
	public static void main(String[] args) {
		String seedKey = "xiexugang";
		String originStr = "{\"key1\":\"qingshagn\", \"key2\":12}";
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC40GCncXT4MF+ZAEWkaJZQqjFMnHR6txpH93GJU+yc2UFN2cjlHDIqzRqJaAOQh2WgbDJV8+AfOM/GtN7Lkb2iJHN3wfTr+FgLg3j3x0PT1ieUNQPd7/NnWsC7hzDi/YHPephAuQA86m8F4eVSFpHTi1cHisKAZ4wjm1WiHd6KwIDAQAB";
		RSAHelper rsa = RSAHelper.LazyHolder.rsaHelper;
		try {
			rsa.generateKeyPair(seedKey);
			String encryptText = rsa.encryptByPublicKey(originStr, publicKey);
			System.out.println("加密 =" + encryptText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
