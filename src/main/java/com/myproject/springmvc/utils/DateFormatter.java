package com.myproject.springmvc.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

/**
 * 日期格式化
 * User: wuqingming
 * Date: 14-1-14
 * Time: 下午12:58
 * To change this template use File | Settings | File Templates.
 */
public class DateFormatter implements Formatter<Date> {
    private static final String DEFAULT_FORMAT = DateFormatUtils.DEFAULT_TIME_FORMAT;
    private String format = DEFAULT_FORMAT;
    public Date parse(String text, Locale locale) throws ParseException {
        if (text == null || text.trim().length() == 0) {
            return null;
        }
        try {
            return DateFormatUtils.parse(text);
        } catch (ParseException e) {
            return DateFormatUtils.parse(text, format);
        }
    }

    public String print(Date date, Locale locale) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, format);
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
