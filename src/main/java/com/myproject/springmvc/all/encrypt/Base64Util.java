package com.myproject.springmvc.all.encrypt;

import org.apache.commons.codec.binary.Base64;

/**
 * <b>
 *	Base64编码，解码，无需密钥
 *
 *	Base64是最常用的编码之一，比如开发中用于传递参数、现代浏览器中的<img />标签直接通过Base64字符串来渲染图片以及用于邮件中等等。Base64编码在RFC2045中定义，它被定义为：Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。
 *
 *	参考：http://www.cnblogs.com/Bonker/p/3558849.html
 *
 * </b>
 * 
 * @author Rogers
 * @date 2016-5-15 下午7:44:30
 *
 */
public class Base64Util {

	/**
	 * 字符编码
	 */
	public final static String ENCODING = "UTF-8";

	/**
	 * Base64编码
	 * 
	 * @param data 待编码数据
	 * @return String 编码数据
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception {

		// 执行编码
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));

		return new String(b, ENCODING);
	}

	/**
	 * Base64安全编码<br>
	 * 遵循RFC 2045实现
	 * 
	 * @param data
	 *            待编码数据
	 * @return String 编码数据
	 * 
	 * @throws Exception
	 */
	public static String encodeSafe(String data) throws Exception {

		// 执行编码
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING), true);

		return new String(b, ENCODING);
	}

	/**
	 * Base64解码
	 * 
	 * @param data 待解码数据
	 * @return String 解码数据
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception {

		// 执行解码
		byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));

		return new String(b, ENCODING);
	}
	
	public static void main(String[] args) {
		String data = "<<<Hello, Ray!>>>";
		try {
			String encode = Base64Util.encode(data);
			System.out.println("encode=" + encode);
			System.out.println("encode.length()=" + encode.length());
			String decode = Base64Util.decode(encode);
			System.out.println("decode=" + decode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
