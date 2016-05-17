package com.myproject.springmvc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>文件名称: DateFormatUtils.java</p>
 * <p>文件描述: 日期格式化工具</p>
 * @author  Wuqingming
 */
public class DateFormatUtils {

    /**
     * 格式为：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String DATE_MODEL_1 = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 格式为：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_MODEL_2 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 格式为：yyyy-MM-dd HH:mm
     */
    public static final String DATE_MODEL_3 = "yyyy-MM-dd HH:mm";
    /**
     * 格式为：yyyy-MM-dd HH
     */
    public static final String DATE_MODEL_4 = "yyyy-MM-dd HH";
    /**
     * 格式为：yyyy-MM-dd
     */
    public static final String DATE_MODEL_5 = "yyyy-MM-dd";
    /**
     * 格式为：yyyy-MM
     */
    public static final String DATE_MODEL_6 = "yyyy-MM";

    /**
     * 格式为：yyyyMMddHHmmss.SSS
     */
    public static final String DATE_MODEL_7 = "yyyyMMddHHmmss.SSS";
    /**
     * 格式为：yyyyMMddHHmmss
     */
    public static final String DATE_MODEL_8 = "yyyyMMddHHmmss";
    /**
     * 格式为：yyyyMMddHHmm
     */
    public static final String DATE_MODEL_9 = "yyyyMMddHHmm";
    /**
     * 格式为：yyyyMMddHH
     */
    public static final String DATE_MODEL_10 = "yyyyMMddHH";
    /**
     * 格式为：yyyyMMdd
     */
    public static final String DATE_MODEL_11 = "yyyyMMdd";
    /**
     * 格式为：yyyyMM
     */
    public static final String DATE_MODEL_12 = "yyyyMM";

    /**
     * 格式为：yyyy/MM/dd HH:mm:ss.SSS
     */
    public static final String DATE_MODEL_13 = "yyyy/MM/dd HH:mm:ss.SSS";
    /**
     * 格式为：yyyy/MM/dd HH:mm:ss
     */
    public static final String DATE_MODEL_14 = "yyyy/MM/dd HH:mm:ss";
    /**
     * 格式为：yyyy/MM/dd HH:mm
     */
    public static final String DATE_MODEL_15 = "yyyy/MM/dd HH:mm";
    /**
     * 格式为：yyyy/MM/dd HH
     */
    public static final String DATE_MODEL_16 = "yyyy/MM/dd HH";
    /**
     * 格式为：yyyy/MM/dd
     */
    public static final String DATE_MODEL_17 = "yyyy/MM/dd";
    /**
     * 格式为：yyyy/MM
     */
    public static final String DATE_MODEL_18 = "yyyy/MM";

    /**
     * 格式为：yyyy
     */
    public static final String DATE_MODEL_19 = "yyyy";

    /**
     * 格式为：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_TIME_FORMAT = DATE_MODEL_2;
    /**
     * 格式为：yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = DATE_MODEL_5;

    /**
     * 获取日期格式
     * @param dateStr   日期字符串
     * @return  日期格式
     */
    public static String getFormat(String dateStr) {
        String result = null;
        if (dateStr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d+")) {
            result = DATE_MODEL_1;
        } else if (dateStr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            result = DATE_MODEL_2;
        } else if (dateStr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
            result = DATE_MODEL_3;
        } else if (dateStr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}")) {
            result = DATE_MODEL_4;
        } else if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
            result = DATE_MODEL_5;
        } else if (dateStr.matches("\\d{4}-\\d{2}")) {
            result = DATE_MODEL_6;
        } else if (dateStr.matches("\\d{14}.\\d+")) {
            result = DATE_MODEL_7;
        } else if (dateStr.matches("\\d{14}")) {
            result = DATE_MODEL_8;
        } else if (dateStr.matches("\\d{12}")) {
            result = DATE_MODEL_9;
        } else if (dateStr.matches("\\d{10}")) {
            result = DATE_MODEL_10;
        } else if (dateStr.matches("\\d{8}")) {
            result = DATE_MODEL_11;
        } else if (dateStr.matches("\\d{6}")) {
            result = DATE_MODEL_12;
        } else if (dateStr.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d+")) {
            result = DATE_MODEL_13;
        } else if (dateStr.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            result = DATE_MODEL_14;
        } else if (dateStr.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}")) {
            result = DATE_MODEL_15;
        } else if (dateStr.matches("\\d{4}/\\d{2}/\\d{2} \\d{2}")) {
            result = DATE_MODEL_16;
        } else if (dateStr.matches("\\d{4}/\\d{2}/\\d{2}")) {
            result = DATE_MODEL_17;
        } else if (dateStr.matches("\\d{4}/\\d{2}")) {
            result = DATE_MODEL_18;
        }
        return result;
    }
	
	/**
	 * 日期转字符串
	 * @param date  日期时间
	 * @param format    格式化字符串
	 * @return  日期字符串
	 */
	public static String format(Date date, String format) {
		if(date == null){
    		return null;
    	}
        String resultStr = "";
        String formatTemp = format;
        if(formatTemp == null || "".equals(formatTemp)){
        	formatTemp = DEFAULT_DATE_FORMAT;
        }
    	SimpleDateFormat dateFormat = new SimpleDateFormat(formatTemp); 
    	
    	resultStr = dateFormat.format(date).trim();
               
        return resultStr;
	}
	
	/**
	 * 字符串转日期
	 * @param dateStr   日期字符串
	 * @param format    转换格式
	 * @return  日期时间
	 * @throws java.text.ParseException
	 */
	public static Date parse(String dateStr, String format) throws ParseException {
        String formatTemp = format;
        if(formatTemp == null || "".equals(formatTemp)){
        	formatTemp = DEFAULT_DATE_FORMAT;
        }
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatTemp);
		return dateFormat.parse(dateStr);
	}

    /**
     * 字符串转日期，自动适配字符串格式
     * @param dateStr 日期字符串
     * @return
     * @throws java.text.ParseException
     */
    public static Date parse (String dateStr) throws ParseException {
        String format = getFormat(dateStr);
        return parse(dateStr, format);
    }

   /* public static void main(String[] args) throws ParseException {
        String date1 = "2014-12-12";
        String date2 = "2014-12-12 11:57:00";
        String date3 = "2014/12/12";
        String date4 = "2014/12/12 11:57:00";
        String date5 = "2014/12/12 11:57:00.30";
        String date6 = "2014-12-12 11:57:00.1";
        String date7 = "2014-12-12T11:57:00.10";
        System.out.println(parse(date1));
        System.out.println(parse(date2));
        System.out.println(parse(date3));
        System.out.println(parse(date4));
        System.out.println(parse(date5));
        System.out.println(parse(date6));
        System.out.println(parse(date7));
    }*/

    /*private static Date parseDateWithLeniency(
            String str, String[] parsePatterns, boolean lenient) throws ParseException {
        if (str == null || parsePatterns == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }

        SimpleDateFormat parser = new SimpleDateFormat();
        parser.setLenient(lenient);
        ParsePosition pos = new ParsePosition(0);
        for (String parsePattern : parsePatterns) {

            String pattern = parsePattern;

            // LANG-530 - need to make sure 'ZZ' output doesn't get passed to SimpleDateFormat
            if (parsePattern.endsWith("ZZ")) {
                pattern = pattern.substring(0, pattern.length() - 1);
            }

            parser.applyPattern(pattern);
            pos.setIndex(0);

            String str2 = str;
            // LANG-530 - need to make sure 'ZZ' output doesn't hit SimpleDateFormat as it will ParseException
            if (parsePattern.endsWith("ZZ")) {
                str2 = str.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");
            }

            Date date = parser.parse(str2, pos);
            if (date != null && pos.getIndex() == str2.length()) {
                return date;
            }
        }
        throw new ParseException("Unable to parse the date: " + str, -1);
    }*/

}

