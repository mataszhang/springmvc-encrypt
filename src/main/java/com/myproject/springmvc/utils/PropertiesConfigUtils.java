package com.myproject.springmvc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类负责加载指定个数的properties文件
 * 
 * @author Rogers
 */
public class PropertiesConfigUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertiesConfigUtils.class);

	private static Map<String, Properties> propertiesConfigMap = new HashMap<String, Properties>();

	/**
	 * 加载propertiesConfig.properties文件
	 */
	public static Properties loadConfig(String propertiesKey) {
		InputStream input = null;
		Reader reader = null;
		Properties config = null;
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			// 获得properties文件名称
			String fileName = propertiesKey;
			if (!propertiesKey.endsWith(".properties")) {
				fileName = propertiesKey + ".properties";
			}
			input = cl.getResourceAsStream(fileName);
			try {
				reader = new InputStreamReader(input, "UTF-8");
				config = new Properties();
				config.load(reader);
				propertiesConfigMap.put(propertiesKey, config);
			} catch (Exception e) {
				logger.error(propertiesKey + ".properties error:", e);
			}
		} catch (Exception e) {
			logger.error("propertiesConfig.properties load failed, reason:", e);
		} finally {
			try {
				if (null != reader)
					reader.close();
				if (null != input)
					input.close();
			} catch (IOException e) {
			}
		}
		return config;
	}

	/**
	 * 获得某一具体key所对应的值
	 * 
	 * @param fileKey
	 *            该文件的名称（不包含后缀.properties）
	 * @param key
	 *            该文件中配置的key
	 * @return
	 */
	public static String getProperty(String fileKey, String key) {
		Properties thisConfig = propertiesConfigMap.get(fileKey);
		if (null == thisConfig) {
			// fileKey对应空的properties，尝试load此名称的配置文件
			thisConfig = loadConfig(fileKey);
		}

		if (null == thisConfig) {
			return null;
		} else {
			return thisConfig.getProperty(key);
		}
	}

	/**
	 * 获得某一具体key所对应的值
	 * 
	 * @param fileKey
	 *            该文件的名称（不包含后缀.properties）
	 * @param key
	 *            该文件中配置的key
	 * @param defaultValue
	 *            当有对应配置文件，但是查询值为null，则返回defaultValue
	 * @return
	 */
	public static String getProperty(String fileKey, String key,
			String defaultValue) {
		String result = getProperty(fileKey, key);
		if (null == result) {
			return defaultValue;
		} else {
			return result;
		}
	}

	/**
	 * 通过文件名称（不包含后缀.properties）获得Properties对象
	 * 
	 * @param fileKey
	 *            文件的名称（不包含后缀.properties）
	 * @return
	 */
	public static Properties getPropertyObject(String fileKey) {
		Properties thisConfig = propertiesConfigMap.get(fileKey);
		if (null == thisConfig) {
			// fileKey对应空的properties，尝试load此名称的配置文件
			thisConfig = loadConfig(fileKey);
		}
		return thisConfig;
	}

//	public static void main(String[] args) {
//		System.out.println(PropertiesConfigUtils.getProperty("message-cn",
//				"starDesc2"));
//	}
}
