package com.myproject.springmvc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 参考org.apache.commons.lang.StringUtils
 * 将isEmpty isNotEmpty isBlank isNotBlank 参数改为Object
 * @author inigo.liu
 *
 */
public class StringUtils {

	private static Logger logger = LoggerFactory.getLogger(StringUtils.class);
	
	private static String[] illegalChars = new String[0];
	
	/**
	 * 去除特殊符号，包括：\n \r \t 
	 * @return
	 */
	public static String removeSpecificString(final String targetStr) {
		String str = targetStr.replaceAll("\n", "");
		str = str.replaceAll("\r", "");
		str = str.replaceAll("\t", "");
		return str;
	}
	
	/**
	 * 校验param是否合法（检测非法字符）
	 * @param param
	 * @return
	 */
	public static boolean checkIllegalParam(String param) {
		//当第一次调用时加载illegal_param.properties配置文件
		if (null == illegalChars || illegalChars.length < 1) {
			InputStream input = null;
			Reader reader = null;
			try {
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				input = cl.getResourceAsStream("illegal_param.properties");
				reader = new InputStreamReader(input, "UTF-8");
				BufferedReader bufferR = new BufferedReader(reader);
				List<String> illegalCharList = new ArrayList<String>();
				String line = null;
				while (null != (line = bufferR.readLine())) {
					illegalCharList.add(line);
				}
				illegalChars = illegalCharList.toArray(new String[0]);
			} catch (Exception e) {
				logger.error("illegal_param.properties load failed", e);
			} finally {
				try {
					if (null != reader)
						reader.close();
					if (null != input)
						input.close();
				} catch (IOException e) {
				}
			}
		}
		//开始检测
		if (isBlank(param)) {
			return false;
		}
		for (String str : illegalChars) {
			if (param.contains(str))
				return false;
		}
		return true;
	}
	
	// Empty checks
    //-----------------------------------------------------------------------
    /**
     * <p>Checks if a String is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the String.
     * That functionality is available in isBlank().</p>
     *
     * @param obj  the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(Object obj) {
    	if (null == obj) {
    		return true;
    	} else {
    		return obj.toString().length() == 0;
    	}
        
    }

    /**
     * <p>Checks if a String is not empty ("") and not null.</p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param obj  the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null
     */
    public static boolean isNotEmpty(Object obj) {
        return !StringUtils.isEmpty(obj);
    }

    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param obj  the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(Object obj) {
    	if (null == obj) {
    		return true;
    	}
    	String str = obj.toString();
        int strLen;
        if ((strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param obj  the String to check, may be null
     * @return <code>true</code> if the String is
     *  not empty and not null and not whitespace
     * @since 2.0
     */
    public static boolean isNotBlank(Object obj) {
        return !StringUtils.isBlank(obj);
    }
    
    /**
     * 判断是否为空，并返回对象字符型
     * @param obj
     * @return
     */
    public static String getString(Object obj){
    	
    	if(isNotBlank(obj)){
    		return obj.toString();
    	}
    	return "";
    	
    }
    
    
    
    /**
     * 判断是否为空，并返回Date
     * obj yyyy-MM-dd
     * @param obj
     * @return
     * @throws java.text.ParseException 
     */
    public static Date getDate(Object obj) throws java.text.ParseException{ 
    	DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		date = sdf.parse((String) obj);
		return date;
    }
    
    
    

    /**
     * 判断是否为空，并返回对象整形
     * @param obj
     * @return
     */
    public static Integer getInteger(Object obj){
    	if(isBlank(obj)){
    		return null;
    	} else if (obj instanceof Integer) {
    		return (Integer)obj;
    	}
    	return Integer.valueOf(obj.toString());
    	
    }
    
    /**
     * 判断是否为空，并返回对象整形
     * @param obj
     * @return
     */
    public static Long getLong(Object obj){
    	if(isBlank(obj)){
    		return null;
    	} else if (obj instanceof Long) {
    		return (Long)obj;
    	}
    	return Long.valueOf(obj.toString());
    	
    }
    
    /**
     * 判断是否为空，并返回整形
     * @param obj
     * @return
     */
    public static Integer getInt(Object obj){
    	if(isBlank(obj)){
    		return 0;
    	} else if (obj instanceof Integer) {
    		return (Integer)obj;
    	}
    	return Integer.valueOf(obj.toString());
    }
    
    /**
     * 判断是否为空，并返回对象整形
     * @param obj
     * @return
     */
    public static Double getDouble(Object obj){
    	
    	if(isNotBlank(obj)){
    		return Double.valueOf(obj.toString());
    	}
    	return null;
    	
    }
	
    /**
     * 判断是否为空，并返回对象Byte
     * @param obj
     * @return
     */
    public static Byte getByte(Object obj){
    	if(isBlank(obj)){
    		return null;
    	}
    	if (obj instanceof Integer) {
    		return ((Integer) obj).byteValue();
    	} else if (obj instanceof Byte) {
    		return (Byte)obj;
    	} else {
    		String objStr = obj.toString();
    		return Byte.valueOf(objStr);
    	}
    }
    
    /**
	 * 检测字符串是不带符号的数字
	 * <pre>
	 * 110     =true
	 * 110.0   =false
	 * -110    =false
	 * </pre>
	 * @param obj
	 */
	public static boolean checkIsNumber(Object obj) {
		if (null == obj) {
			return false;
		}
		String str = String.valueOf(obj);
		String regxStr = "^[0-9]+";
		Pattern pattern = Pattern.compile(regxStr);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	
	/**
	 * Html 转码.
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}

	/**
	 * Html 解码.
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml(htmlEscaped);
	}

	public static boolean isContentZhongwen(String str) {
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}
	
	public static void main(String[] args) {
//		System.out.println(isContentZhongwen("sdf中国"));
		
		removeSpecificString("");
		
	}
	
	public static String[] convertStrToArray(String str){   
        String[] strArray = null;   
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
        return strArray;
    } 
	
	/**
	 * 把elong数据库字段名，改成我们符合我们数据库规则的字段名，比如HotelId --> hotel_id
	 */
	public static String convertTofield(String str){
		String resultString = "";
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			boolean isUpper=Character.isUpperCase(c);
		    if(isUpper){
		    	String endString =  String.valueOf(Character.toLowerCase(c)); 
		    	if (i>0) {
		    		endString = "_"+ endString;
				}
		    	resultString += endString;
		    }else {
		    	resultString += String.valueOf(c);
			}
		    
		}
		return resultString;
	}
	
	/**
	 * 检查必传参数是否为空
	 * 如果参数xxx为空，则返回 :xxx is blank!
	 * @return
	 */
	public static String checkBlankParams(Map<String, Object> reqParams, String ... paramNames) {
		String result = null;
		if (reqParams == null || reqParams.isEmpty()) {
			return "request params is blank!";
		}
		for (String paramsName : paramNames) {
			if (StringUtils.isBlank(reqParams.get(paramsName))) {
				result = paramsName + " is blank!";
				break;
			}
		}
		return result;
	}
	
	/**
	 * 检查参数是否为不带符号的正整数
	 * 如果参数xxx为非数字或者带符号数字，则返回 :xxx is illegal number!
	 * @return
	 */
	public static String checkIllegalNumberParams(Map<String, Object> reqParams, String ... paramNames) {
		String result = null;
		for (String paramsName : paramNames) {
			if (!StringUtils.checkIsNumber(reqParams.get(paramsName))) {
				result = paramsName + " is illegal number!";
				break;
			}
		}
		return result;
	}
	
	public static boolean isInteger(String str) {
		try {
			Integer.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
     * <p>Gets a substring from the specified String avoiding exceptions.</p>
     *
     * <p>A negative start position can be used to start/end <code>n</code>
     * characters from the end of the String.</p>
     *
     * <p>The returned substring starts with the character in the <code>start</code>
     * position and ends before the <code>end</code> position. All position counting is
     * zero-based -- i.e., to start at the beginning of the string use
     * <code>start = 0</code>. Negative start and end positions can be used to
     * specify offsets relative to the end of the String.</p>
     *
     * <p>If <code>start</code> is not strictly to the left of <code>end</code>, ""
     * is returned.</p>
     *
     * <pre>
     * StringUtils.substring(null, *, *)    = null
     * StringUtils.substring("", * ,  *)    = "";
     * StringUtils.substring("abc", 0, 2)   = "ab"
     * StringUtils.substring("abc", 2, 0)   = ""
     * StringUtils.substring("abc", 2, 4)   = "c"
     * StringUtils.substring("abc", 4, 6)   = ""
     * StringUtils.substring("abc", 2, 2)   = ""
     * StringUtils.substring("abc", -2, -1) = "b"
     * StringUtils.substring("abc", -4, 2)  = "ab"
     * </pre>
     *
     * @param str  the String to get the substring from, may be null
     * @param start  the position to start from, negative means
     *  count back from the end of the String by this many characters
     * @param end  the position to end at (exclusive), negative means
     *  count back from the end of the String by this many characters
     * @return substring from start position to end positon,
     *  <code>null</code> if null String input
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        // handle negatives
        if (end < 0) {
            end = str.length() + end; // remember end is negative
        }
        if (start < 0) {
            start = str.length() + start; // remember start is negative
        }

        // check length next
        if (end > str.length()) {
            end = str.length();
        }

        // if start is greater than end, return ""
        if (start > end) {
            return "";
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }
}
