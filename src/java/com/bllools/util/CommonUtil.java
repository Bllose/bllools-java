package com.bllools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

    public static final String FORMAT_ID = "yyyyMMddHHmmssSSS";

    public static final String FORMAT_UTC = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_DATE = "yyyy-MM-dd";

    public static final String FORMAT_UTC_PATTERN = CommonUtil.FORMAT_DATE_PATTERN
        + "\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";

    public static final String FORMAT_DATE_PATTERN =
        "((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-"
            + "(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))"
            + "|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])"
            + "|((0[48]|[2468][048]|[3579][26])00))-02-29))";

    private CommonUtil() {
    }

    public static String convertDate2Str(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis()/* + 8 * 60 * 60 * 1000*/);
    }

    /**
     * 判断一个日期是当月第几周
     *
     * @param str 日期字符串
     */
    public static int getWeek(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_UTC);
            Date date = sdf.parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
            return dayOfWeek == 1 ? weekOfMonth - 1 : weekOfMonth;
        } catch (ParseException e) {
            System.out.println(str + " get week of month failed!");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * 获取指定日期所在周的周四的日期
     *
     * @param str 指定日期
     * @return 周四日期
     */
    public static String getThursDay(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_UTC);
            Date date = sdf.parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 1) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            }
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day + 3);

            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            System.out.println(str + " get week of month failed!");
            System.out.println(e.getMessage());
        }
        return "";
    }

    public static void log(String log) {
        System.out.println(log);
    }
}
