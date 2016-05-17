package com.myproject.springmvc.rsa;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Crypt {

	   
    static String DES = "DES";  
    static String RSA = "RSA";  
      
    static String charset = "UTF-8";//保持平台兼容统一使用utf-8  
      
    //私钥文件路径  
    static String privateFile = "/XXX/private_pkcs8_der.key";  
    //公钥文件路径  
    static String publicFile = "/XXX/public_key.der";  
      
    //des 加密  
    private static byte [] encryptByteDES(byte[] byteD,String strKey) throws Exception {    
        return doEncrypt(byteD, getKey(strKey), DES);  
    }  
    //des 解密  
    private static byte [] decryptByteDES(byte[] byteD,String strKey) throws Exception {    
        return doDecrypt(byteD, getKey(strKey), DES);  
    }  
  
    public static SecretKey getKey(String strKey)  throws Exception {  
        DESKeySpec desKeySpec = new DESKeySpec(strKey.getBytes());  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);  
        SecretKey sk = keyFactory.generateSecret(desKeySpec);  
        return sk;  
    }  
      
    //pkcs8_der.key文件为私钥 只能保存在服务端   
    //public_key.der为公钥文件，保存在客户端  
    public static void main(String[] args) throws Exception {  
  
        String text = "ceshishuuju测试数据ceshishuuju测试数据";  
        String pwd="12345678";  
        //客户端加密  
        HashMap<String, String> data = DESAndRSAEncrypt(text.getBytes(charset),pwd);  
        System.out.println("pwd RSA加密后base64："+data.get("key"));  
        System.out.println("text DES加密后base64："+data.get("data"));  
  
        //服务端解密  
        String textDecrypt = DESAndRSADecrypt(data);  
        System.out.println("未处理原文："+text);  
        System.out.println("解密后数据："+textDecrypt);  
//      generateKeyPair();  
    }  
      
    //客户端加密  
    static HashMap<String, String> DESAndRSAEncrypt(byte[] dataToEncypt,String pwd) throws Exception{  
          
        byte[] encryptData = encryptByteDES(dataToEncypt, pwd);  
        String dataBase64 = Base64.encodeBase64String(encryptData); // encoder.encode(encryptData);  
          
        byte[] encryptKey = RSAEncrypt(pwd.getBytes(charset));  
        String keyBase64 = Base64.encodeBase64String(encryptKey); //encoder.encode(encryptKey);  
  
        HashMap<String, String> data = new HashMap<String, String>();  
        data.put("data", dataBase64);  
        data.put("key", keyBase64);  
        return data;  
    }  
    //服务端解密  
    static String DESAndRSADecrypt(HashMap<String, String> data) throws Exception {  
        String dataBase64 = data.get("data");  
        String keyBase64 = data.get("key");  
          
        byte[] encryptedData = Base64.decodeBase64(dataBase64); // decoder.decodeBuffer(dataBase64);  
        byte[] encryptedKey = Base64.decodeBase64(keyBase64);   // decoder.decodeBuffer(keyBase64);  
          
        byte[] decryptedKey= RSADecrypt(encryptedKey);  
        String pwd = new String(decryptedKey,charset);  
        System.out.println("DES密码："+pwd);  
          
        byte[] decryptedData  = decryptByteDES(encryptedData, pwd);  
        String textDecrypt = new String(decryptedData,charset);  
        return textDecrypt;  
    }  
      
    //公钥加密   
    public static byte[] RSAEncrypt(byte[] plainText) throws Exception{  
        //读取公钥  
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");  
        FileInputStream bais = new FileInputStream(publicFile);  
        Certificate cert = certificatefactory.generateCertificate(bais);  
        bais.close();  
        PublicKey puk = cert.getPublicKey();  
//      System.out.println("公钥base64："+encoder.encode(puk.getEncoded()));  
        return doEncrypt(plainText, puk, RSA);  
    }  
    //私钥解密  
    public static byte[] RSADecrypt(byte[] encryptData) throws Exception{  
        FileInputStream in = new FileInputStream(privateFile);  
        ByteArrayOutputStream bout = new ByteArrayOutputStream();  
        byte[] tmpbuf = new byte[1024];  
        int count = 0;  
        while ((count = in.read(tmpbuf)) != -1) {  
            bout.write(tmpbuf, 0, count);  
        }  
        in.close();  
        //读取私钥  
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bout.toByteArray());  
        PrivateKey prk = keyFactory.generatePrivate(privateKeySpec);  
//      System.out.println("私钥base64："+encoder.encode(prk.getPrivateExponent().toByteArray()));  
        return doDecrypt(encryptData, prk, RSA);  
    }  
      
    /** 
     * 执行加密操作 
     * @param data 待操作数据 
     * @param key Key 
     * @param type 算法 RSA or DES 
     * @return 
     * @throws Exception 
     */  
    public static byte[] doEncrypt(byte[] data,Key key,String type) throws Exception{  
        Cipher cipher = Cipher.getInstance(type);  
        cipher.init(Cipher.ENCRYPT_MODE, key);  
        return cipher.doFinal(data);  
    }  
      
    /** 
     * 执行解密操作 
     * @param data 待操作数据 
     * @param key Key 
     * @param type 算法 RSA or DES 
     * @return 
     * @throws Exception 
     */  
    public static byte[] doDecrypt(byte[] data,Key key,String type) throws Exception{  
        Cipher cipher = Cipher.getInstance(type);  
        cipher.init(Cipher.DECRYPT_MODE, key);  
        return cipher.doFinal(data);  
    }  
      
    public static void generateKeyPair() throws Exception{
    	String seed = "xiexugang";
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);  
        kpg.initialize(1024, new SecureRandom(seed.getBytes())); // 指定密钥的长度，初始化密钥对生成器  
        KeyPair kp = kpg.generateKeyPair(); // 生成密钥对  
        RSAPublicKey puk = (RSAPublicKey) kp.getPublic();  
        RSAPrivateKey prk = (RSAPrivateKey) kp.getPrivate();  
        BigInteger e = puk.getPublicExponent();  
        BigInteger n = puk.getModulus();  
        BigInteger d = prk.getPrivateExponent();  
          
        System.out.println("public key:\n"+ Base64.encodeBase64String(n.toByteArray()));  
        System.out.println("private key:\n"+ Base64.encodeBase64String(d.toByteArray()));  
    } 
    
}
