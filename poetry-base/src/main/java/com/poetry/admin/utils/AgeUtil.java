package com.poetry.admin.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 年龄计算
 */
public class AgeUtil {

    /**
     * 根据出生日期计算月龄
     *
     * @param birthDate 出生日期 格式为 yyyy-MM-dd
     * @return 月龄
     */
    public static int getResidentAge(Date birthDate) {
        if (null == birthDate) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateBirth = sdf.format(birthDate);
        LocalDate now = LocalDate.now();
        LocalDate birth = null;
        try {
            birth = LocalDate.parse(dateBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new RuntimeException("日期格式应该为:yyyy-MM-dd，例如:20xx-01-01");
        }
        int year = now.getYear() - birth.getYear();
        int month = now.getMonthValue() - birth.getMonthValue();
        if (month<0){
            year=year-1;
        }
        return year;
    }

    /**
     * 时间加减年份
     * @param time
     * @param num
     * @return
     */
    public static Date yearAddNum(Date time, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, num);
        Date newTime = calendar.getTime();
        return newTime;
    }


    /**
     * 时间加减天数
     * @param time
     * @param days
     * @return
     */
    public static Date dateAddDays(Date time,Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date newTime = calendar.getTime();
        return newTime;
    }
}
