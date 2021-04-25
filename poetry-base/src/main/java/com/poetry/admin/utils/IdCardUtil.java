package com.poetry.admin.utils;


import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCardUtil {
    // 18位身份证号码正则
    private static String IDCard18_REGEX = "^[1-9](\\d{5})(19|20)(\\d{2})((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)(\\d{3})(\\d|X|x)$";
    // 15位身份证号码正则
    private static String IDCard15_REGEX = "^[1-9](\\d{5})(\\d{2})((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)(\\d{3})$";
    // 平年日期正则
    private static String OrdinaryYear_REGEX = "(((0[13578])|10|12)(([0-2][1-9])|10|20|30|31)|(((0[469])|11)(([0-2][1-9])|10|20|30))|(02(([0-2][1-8])|09|19|10|20)))";
    // 闰年日期正则
    private static String LeapYear_REGEX = "(((0[13578])|10|12)(([0-2][1-9])|10|20|30|31)|(((0[469])|11)(([0-2][1-9])|10|20|30))|(02(([0-2][1-9])|10|20)))";
    // 1-17位相乘因子数组
    private final static int[] FACTOR = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    // 18位随机码数组
    private final static char[] RANDOM = "10X98765432".toCharArray();
    //出生证明校验规则
    private static String BIRTH_REGEX = "[A-Z]{1}\\d{9}";

    static int nowYear = Calendar.getInstance().get(Calendar.YEAR);
    static int nowMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    static int nowDay = Calendar.getInstance().get(Calendar.DATE);

    public static int getAgeByIdCard(String idCard) {
        int age = 0;
        try {
            if (StringUtils.isEmpty(idCard)) {
                return age;
            }
            if (idCard.length() == 15) {
                age = nowYear - (getSubStringInt(idCard, 6, 8) + 1900) - 1;
                if (nowMonth > getSubStringInt(idCard, 8, 10)) {
                    age++;
                }
                if (nowMonth == getSubStringInt(idCard, 8, 10) && nowDay >= getSubStringInt(idCard, 10, 12)) {
                    age++;
                }
            }
            if (idCard.length() == 18) {
                age = nowYear - getSubStringInt(idCard, 6, 10) - 1;
                if (nowMonth > getSubStringInt(idCard, 10, 12)) {
                    age++;
                }
                if (nowMonth == getSubStringInt(idCard, 10, 12) && nowDay >= getSubStringInt(idCard, 12, 14)) {
                    age++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return age;
    }


    private static int getSubStringInt(String source, int start, int end) {
        return Integer.parseInt(source.substring(start, end));
    }


    /**
     * 用户身份证号码的打码隐藏加星号加*
     * <p>18位和非18位身份证处理均可成功处理</p>
     * <p>参数异常直接返回null</p>
     *
     * @param idCardNum 身份证号码
     * @return 处理完成的身份证
     */
    public static String idMask(String idCardNum) {
        if (StringUtils.isEmpty(idCardNum)) {
            return null;
        }
        int end = 1;
        int length = 18;
        int middle = 5;
        if (length != 0) {
            int front = length - middle - end;
            //计算*的数量
            int asteriskCount = idCardNum.length() - (front + end);
            StringBuffer asteriskStr = new StringBuffer();
            for (int i = 0; i < asteriskCount; i++) {
                asteriskStr.append("*");
            }
            String regex = "(\\w{" + String.valueOf(front) + "})(\\w+)(\\w{" + String.valueOf(end) + "})";
            idCardNum = idCardNum.replaceAll(regex, "$1" + asteriskStr + "$3");
        }
        return idCardNum;
    }


    private static boolean regexValidate(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    /**
     * 根据身份证号得到出生日期
     * @param idCard String
     * @return String
     */
    public static String getStringBirthdayFromIdCard(String idCard) {
        if (idCard.length() < 15) {
            return "";
        }
        String birthStr;
        if (idCard.length() == 15) {
            birthStr = "19" + idCard.substring(6, 12);
        } else {
            birthStr = idCard.substring(6, 14);
        }
        StringBuilder sb = new StringBuilder(birthStr).insert(4, "-").insert(7, "-");
        return sb.toString();
    }

    /**
     * 根据身份证号得到出生日期
     *
     * @param idCard String
     * @return Date
     */
    public static Date getBirthdayFromIdCard(String idCard) {
        if (idCard.length() < 15) {
            return null;
        }
        String birthStr;
        if (idCard.length() == 15) {
            birthStr = "19" + idCard.substring(6, 12);
        } else {
            birthStr = idCard.substring(6, 14);
        }
        StringBuilder sb = new StringBuilder(birthStr).insert(4, "-").insert(7, "-");
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(sb.toString());
        } catch (Exception ignored) {
        }
        return null;
    }
}
