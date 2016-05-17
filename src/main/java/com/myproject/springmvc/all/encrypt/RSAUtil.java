package com.myproject.springmvc.all.encrypt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;


public class RSAUtil {
	
	/** 可以先注册到虚拟机中,再通过名称使用;也可以不注册,直接传入使用 */  
    public static final Provider pro = new BouncyCastleProvider();  
    /** 种子,改变后,生成的密钥对会发生变化 */  
    private static final String seedKey = "xiexugang";  
    
	public final static String KEY_ALGORTHM = "RSA";
	
	public final static String CHAR_SET = "UTF-8";
	
    private static PrivateKey privateKey = null;  
    
    private static PublicKey publicKey = null;  
    
    static{  
        try {  
            generateKeyPair();  
        } catch (Exception e) {  
            throw new RuntimeException("生成密钥对失败");  
        }  
    } 
    
    private static void generateKeyPair() throws Exception {  
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", pro);  
        kpg.initialize(1024, new SecureRandom(seedKey.getBytes()));  
        KeyPair kp = kpg.generateKeyPair();  
  
        publicKey = kp.getPublic();  
        privateKey = kp.getPrivate();  
    }  
    
    public static String getPublicKeyStr() {
    	return new String(Base64.encode(publicKey.getEncoded()));
    }
    
    public static String getPrivateKeyStr() {
    	return new String(Base64.encode(privateKey.getEncoded()));
    }
    
	public static String encryptByPublicKey(String originStr, String publicKeyStr) throws Exception {
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

	public static String decryptByPrivateKey(String encryptText, String privateKeyStr) throws Exception {
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
		try {
			String publicKeyStr = getPublicKeyStr();
			String privateKeyStr = getPrivateKeyStr();
			System.out.println("公钥=" + publicKeyStr);
			System.out.println("私钥=" + privateKeyStr);
			
			String originStr = "{\"key1\":\"qingshagn\", \"key2\":12}";
			
			String encryptText = RSAUtil.encryptByPublicKey(originStr, publicKeyStr);
			System.out.println("加密后=" + encryptText);
			
			String decryptText = RSAUtil.decryptByPrivateKey(encryptText, privateKeyStr);
			System.out.println("解密后=" + decryptText);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
