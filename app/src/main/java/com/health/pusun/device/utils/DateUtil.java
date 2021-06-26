package com.health.pusun.device.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");//设置日期格式
        String dateStr = df.format(new Date());// new Date()为获取当前系统时间
        return dateStr;
    }
    public static String getCurrentDateMinute() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        String dateStr = df.format(new Date());// new Date()为获取当前系统时间
        return dateStr;
    }

    public static String transDateMinute(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");//设置日期格式
        String dateStr = df.format(date);// new Date()为获取当前系统时间
        return dateStr;
    }

    public static String getSimpleDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getSimpleDateForHMS() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getDateTopBar() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd E");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getDateMyTopDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd E");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }
    public static String getCurrentDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getDateAddOrMinus(String date, int oneDay) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd E");//设置日期格式

        Calendar c = Calendar.getInstance();
        c.setTime(df.parse(date));   //设置当前日期
        c.add(Calendar.DATE, oneDay); //日期加/减1
        Date resultDate = c.getTime(); //结果

        return df.format(resultDate);// new Date()为获取当前系统时间
    }

    public static String getSimpleDateAddOrMinus(String date, int oneDay) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");//设置日期格式

        Calendar c = Calendar.getInstance();
        c.setTime(df.parse(date));   //设置当前日期
        c.add(Calendar.DATE, oneDay); //日期加/减1
        Date resultDate = c.getTime(); //结果

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        return simpleDateFormat.format(resultDate);// new Date()为获取当前系统时间
    }

    public static String getSimpleDateAddOneDay(String date, int oneDay) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(df.parse(date));   //设置当前日期
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, oneDay); //日期加/减1
        Date resultDate = c.getTime(); //结果

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        return simpleDateFormat.format(resultDate);// new Date()为获取当前系统时间
    }

    public static String getSimpleDateAddOrMinus(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");//设置日期格式

        Calendar c = Calendar.getInstance();
        c.setTime(df.parse(date));   //设置当前日期
        Date resultDate = c.getTime(); //结果

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        return simpleDateFormat.format(resultDate);// new Date()为获取当前系统时间
    }

    public static String getSimpleDateForMonth(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(df.parse(date));   //设置当前日期
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date resultDate = c.getTime(); //结果

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M");//设置日期格式

        return simpleDateFormat.format(resultDate);// new Date()为获取当前系统时间
    }

    public static String getDateStr(long seconds) {
        Date date = new Date(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr.split(" ")[0];
    }

    public static String getTimeStr(long seconds) {
        Date date = new Date(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String getDateStr2bit(long seconds) {
        Date date = new Date(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return dateStr.split(" ")[0];
    }

    public static long getDateZeroTime2bit(long seconds) {
        Date date = new Date(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);
        return getStringToDate(dateStr.split(" ")[0] + " 00:00:00");
    }



    public static String getDateOnlyStr2bit(long seconds) {
        Date date = new Date(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date).split(" ")[0];
        String dateOnlyStr = dateStr.split("-")[2];
        return dateOnlyStr;
    }


    public static String getSimpleCalendarDate(long seconds) {
        Date date = new Date(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String getDateTimeStr2bit(long seconds) {
        Date date = new Date(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }


    /* 将字符串转为时间戳 */
    public static long getStringToDate(String time) {

        SimpleDateFormat sf = null;
        if (time.contains("-")) {
            sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (time.contains(".")) {
            sf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        }

        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    /* 将字符串转为时间戳 */
    public static long getStringToDateMM(String time) {

        SimpleDateFormat sf = null;
        if (time.contains("-")) {
            sf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        } else if (time.contains(".")) {
            sf = new SimpleDateFormat("yyyy.M.d HH:mm:ss");
        }

        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    /**
     * 判断是否是未来的日期
     */
    public static boolean isFutureDate(String date){
        try {
            Date nowdate=new Date();
            String myDate = date;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
            Date d = sdf.parse(myDate);
            boolean flag = d.before(nowdate);
            if(flag){
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


//    得到本周周一

        public static Date getMondayOfThisWeek(Date date) {
        Calendar c = Calendar.getInstance();
         c.setTime(date);
         int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
         if (day_of_week == 0)
         day_of_week = 7;
         c.add(Calendar.DATE, -day_of_week + 1);
         return c.getTime();
         }

    //得到本周周日

    public static Date getSundayOfThisWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return c.getTime();
    }

    //得到本月第一天

    public static Date getFirstOfThisMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        return c.getTime();
    }

    //得到本月最后一天

    public static Date getLastOfThisMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        return c.getTime();
    }


    public static boolean isToday(long timeStamp){
        String dateString = getDateStr2bit(new Date().getTime() / 1000);
        long timeDayStart = DateUtil.getStringToDate(dateString + " 00:00:00");
        long timeDayEnd = DateUtil.getStringToDate(dateString + " 23:59:59");
        if(timeStamp >= timeDayStart && timeStamp <= timeDayEnd)
        return  true;
        else return false;
    }


    /**
     * 获取2个日期之间相隔几天
     * @param fDate
     * @param oDate
     * @return
     */
    public static int getDayInterval(Date fDate, Date oDate) 
    {
        long dayInterval= 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            fDate = sdf.parse(sdf.format(fDate));
            oDate = sdf.parse(sdf.format(oDate));

            Calendar cal = Calendar.getInstance();
            
            cal.setTime(fDate);
            long fTime = cal.getTimeInMillis();
            
            cal.setTime(oDate);
            long oTime = cal.getTimeInMillis();
            
            dayInterval = (oTime - fTime) / (1000 * 3600 * 24);
        }catch (ParseException e){
            
        }

        return Integer.parseInt(String.valueOf(dayInterval));
    }
}
