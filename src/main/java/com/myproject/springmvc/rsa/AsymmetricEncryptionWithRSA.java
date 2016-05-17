package com.myproject.springmvc.rsa;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;




/**
 * <b>
 *	没有用
 * </b>
 * 
 * @author Rogers
 * @date 2016-5-15 下午8:11:01
 *
 */
@Deprecated
public class AsymmetricEncryptionWithRSA {

	private static KeyPair keyPair;
	 
    private static KeyPair initKeyPair() {
        try {
            keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algorithm not supported! " + e.getMessage() + "!");
        }
 
        return keyPair;
    }
 
    public static void main(String[] args) {
        initKeyPair();
        try {
            final Cipher cipher = Cipher.getInstance("RSA");
            final String plaintext = "Text to be encrypted ";
 
            // ENCRYPT using the PUBLIC key
            PublicKey publicKey = keyPair.getPublic();
//            String publicKeyStr = new String(Base64.encodeBase64String(publicKey.getEncoded()));
//            System.out.println("public key str : " + publicKey);
            
            PrivateKey privateKey = keyPair.getPrivate();
//            String privateKeyStr = new String(Base64.decodeBase64(privateKey.getEncoded()));
//            System.out.println("private key str : " + privateKey);
            
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            // jdk8写法
//            String chipertext = new String(Base64.getEncoder().encode(encryptedBytes));
            String chipertext = new String(Base64.encodeBase64(encryptedBytes));
            System.out.println("encrypted (chipertext) = " + chipertext);
            System.out.println("f6irLem0biD9Uz4NuQxW9BH/UlHBJQJOE7xGFVVYbM+Qlb9LAwO4bjDPgdy4gP76yHaEis7g9rLkNR1oJg7IFRXNInnFcuv3KoKT6xCXyTal1/h6zYO2Yc6HTLHHda+sBd6g2RUTMRCS0qo+Fac01kdU49TsHoJlYaRebN67j20=".length());
 
            // DECRYPT using the PRIVATE key
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // jdk8写法
//            byte[] ciphertextBytes = Base64.getDecoder().decode(chipertext.getBytes());
            byte[] ciphertextBytes = Base64.decodeBase64(chipertext.getBytes());
            byte[] decryptedBytes = cipher.doFinal(ciphertextBytes);
            String decryptedString = new String(decryptedBytes);
            System.out.println("decrypted (plaintext) = " + decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algorithm not supported! " + e.getMessage() + "!");
        } catch (NoSuchPaddingException | InvalidKeyException e) {
            System.err.println("Cipher cannot be created!");
            e.printStackTrace();
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            System.err.println("An error occurred during the encryption!");
            e.printStackTrace();
        }
    }
    
}
