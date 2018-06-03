package com.twolz.qiyi.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期时间工具类
 */
public class DateTool {

	public static final String SIMPLE_DATE_FORMAT_STRING = "yyyy/MM/dd";
	public final static String DATE_TIME_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String DATE_TIME_Sec = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_TIME_SORT = "yyyyMMddHHmmss";
	public final static String DATE_TIME_SORT_SSS = "yyyyMMddHHmmssSSS";
	public final static String DATE_TIME_YMD = "yyyy-MM-dd";
	public final static String DATE_TIME_YMD_PIONT = "yyyy.MM.dd";
	public final static String DATE_TIME_YMD_NOSYMBOL = "yyyyMMdd";
	public final static String DATE_TIME_YM = "yyyy-MM";
	public final static String DATE_TIME_Y = "yyyy";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd");



	public static Date createDate(int year, int month, int date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, date);
		return calendar.getTime();
	}


	public static String format(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 日期比较 0 = 相等,1 = 大于， -1 = 小于
	 * @param d1
	 * @param d2
     * @return
     */
	public static int compareDate(Date d1,Date d2){
		if (d1.getTime() > d2.getTime()) {
			return 1;
		} else if (d1.getTime() < d2.getTime()) {
			return -1;
		} else {//相等
			return 0;
		}
	}

	/*****
	 * @author LIYE
	 * 字符串转时间
	 * @param date
	 * @return
	 */
	public static Date Str2Date(String date) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			if (date.length()>16) {
				ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}else if(date.length() >10&& date.length()<17 )
			{
				ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			}
			Date d = ft.parse(date);
			return new java.sql.Date(d.getTime());
		} catch (Exception ex) {
			return new Date(Calendar.getInstance().getTime().getTime());
		}
	}

	/*****
	 * @author LIYE
	 * 字符串转时间
	 * @param date
	 * @return
	 */
	public static Date Str2Date(String date,String pattern) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat(pattern);
			Date d = ft.parse(date);
			return new Date(d.getTime());
		} catch (Exception ex) {
			return new Date(Calendar.getInstance().getTime().getTime());
		}
	}

	/**
	 * 获取当前日期的后一天
	 * @author JJ
	 *
	 * @return String format is:yyyy-MM-dd
	 * */
	public static String getNextDay()
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new java.sql.Date(System.currentTimeMillis()));
		int day=calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE,day+1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取当前日期
	 * @return String format is:yyyy-MM-dd
	 * */
	public static String getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new java.sql.Date(System.currentTimeMillis()));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}
	
	public static String getToday() {
		//Calendar calendar = Calendar.getInstance();
		//calendar.setTime(new java.sql.Date(System.currentTimeMillis()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new java.sql.Date(System.currentTimeMillis()));
	}

	/**
	 * 获取当前日期
	 * @return String format is:MM-dd
	 * */
	public static String getCurrentDate()
	{
		Calendar calendar=Calendar.getInstance();
		return DATE_FORMAT.format(calendar.getTime());
	}

	/**
	 * 获取当前月
	 * @author tzs
	 * 2011.06.21 pm
	 * @return month
	 * */
	public static int getCurrentMonth()
	{
		Calendar ca = Calendar.getInstance();
		int month = ca.get(Calendar.MONTH)+1;//获取月
		return month;
	}

	/**
	 * //获取当前日期
	 * @author tzs
	 */
	public static Timestamp getCurrentTimestamp(){
		Timestamp d = new Timestamp(System.currentTimeMillis());
		return d;
	}

	/**
	 * 日期加或减day天
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date calculateDate(Date date,int day){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH,day);
		return calendar.getTime();
	}

	/**
	 * 日期加或减day天
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date calculateDate(String date,int day,String pattern){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Str2Date(date,pattern));
		calendar.add(Calendar.DAY_OF_MONTH,day);
		return calendar.getTime();
	}

	/**
	 * 日期加或减day天 返回字符串
	 * @param date
	 * @param day
	 * @return
	 */
	public static String calculateDateStr(String date,int day,String pattern){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Str2Date(date,pattern));
		calendar.add(Calendar.DAY_OF_MONTH,day);
		return format(calendar.getTime(),pattern);
	}


	/**
	 * 获取当前时间的相关时间
	 * @param minute
	 * @return
	 */
	public static Date getRelateTime(int minute) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, minute);
		Date myDate = c.getTime();
		return myDate;
	}

	/**
	 * 获取永远的时间 100年
	 * @param
	 * @return
	 */
	public static Date getForeverTime() {
		//2037-01-01 00:00:00
		Date myDate =Str2Date("2037-01-01 00:00:00");
		return myDate;
	}

	
	/**
	 * 计算两个时间相差的小时数
	 * @param startDate  开始时间
	 * @param endDate  结束时间
     * @return
     */
	public static int getDifferenceHours(Date startDate,Date endDate){
		if(startDate!=null&&endDate!=null){
			long startTime=startDate.getTime();
			long endTime=endDate.getTime();
			//取绝对值，防止开始时间结束时间传反
			return (int)Math.abs((endTime-startTime)/(1000*60*60));
		}
		return 0;
	}
	
	public static int getDifferenceSeconds(Date startDate,Date endDate){
		if(startDate!=null&&endDate!=null){
			long startTime=startDate.getTime();
			long endTime=endDate.getTime();
			//取绝对值，防止开始时间结束时间传反
			return (int)Math.abs((endTime-startTime)/(1000));
		}
		return 0;
	}
	
	/**
	 * 计算两个时间相差的分钟数
	 * @param startDate  开始时间
	 * @param endDate  结束时间
     * @return
     */
	public static int getDifferenceMinutes(Date startDate,Date endDate){
		if(startDate!=null&&endDate!=null){
			long startTime=startDate.getTime();
			long endTime=endDate.getTime();
			//取绝对值，防止开始时间结束时间传反
			//int minutes= (int)Math.abs((endTime-startTime)/(1000*60));
			long cha=Math.abs(endTime-startTime);
			long yu=cha%60000;
			int shang=(int)cha/60000;
			if(yu!=0){
				shang=shang+1;
			}
			return shang;
		}
		return 0;
	}
	
	
	
	/**
	 * 计算两个时间相差的分钟数,向上取整
	 * @param startDate  开始时间
	 * @param endDate  结束时间
     * @return
     */
	public static int getTimeMinutes(Date startDate,Date endDate){
		if(startDate!=null&&endDate!=null){
			long startTime = startDate.getTime();
			long endTime = endDate.getTime();
			long cha = endTime - startTime;
			double a = cha;
			double b = 60000;
			double c = a / b;
			double zhi = Math.ceil(c);

			return (int) zhi;
		}
		return 0;
	}
	

	/**
	 * 计算日期型查询条件
	 * @param src
	 * @param isStartTime 是否是开始时间
	 * @return
	 */
	public static Date getQueryDate(String src, boolean isStartTime) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss");
		try {
			if (isStartTime) {
				date = formatter.parse(src+"00:00:00");
			} else {
				date = formatter.parse(src + "23:59:59");
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取本周第一天
	 * @return
	 */
	public static String getNowWeekBegin() {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
		}
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);

		return preMonday;

	}
	
	/**
	 * 获取当月第一天
	 * @return
	 */
	public static String getNowMonthBegin() {
		String firstDay="";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        //获取前月的第一天
        Calendar   cal_1=Calendar.getInstance();//获取当前日期 
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        firstDay = format.format(cal_1.getTime());
        
        return firstDay;
	}
	
	/**
	 * 获取当年第一天
	 * @return
	 */
	public static String getNowYearBegin() {
	    Calendar   cal_1=Calendar.getInstance();//获取当前日期 
        int year= cal_1.get(Calendar.YEAR);
        String firstDay=year+"-01-01";
        return firstDay;
	}

	/**
	 * 获取15个月后的
	 * @return
     */
	public static String get15MouthAfter( ){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 15);
		Date date = c.getTime();
		String result = f.format(date);
		//result=result+" 00:00:00";
		return result;
	}

	public static Date strToDate(String str){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			 date=format.parse(str);
		}catch (Exception e){
		}
		return date;
	}

	public static Date strToDate(String str,String formart){
		SimpleDateFormat format =  new SimpleDateFormat(formart);
		Date date=null;
		try {
			date=format.parse(str);
		}catch (Exception e){
		}
		return date;
	}

	public static Date strToTime(String str){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date=format.parse(str);
		}catch (Exception e){
		}
		return date;
	}

	public static String dateToStr(Date date){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return  format.format(date);
	}
	public static String dateToStr(Date date,String pattern){
		SimpleDateFormat format =  new SimpleDateFormat(pattern);
		return  format.format(date);
	}

	public static String dateToStr2(Date date){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  format.format(date);
	}

	public static String dateToStr3(Date date){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
		return  format.format(date);
	}

	/**
	 * 获取两个日期之间的日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 日期集合
	 */
	public static List<Date> getBetweenDates(Date start, Date end) {
		List<Date> result = new ArrayList<Date>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);
		tempStart.add(Calendar.DAY_OF_YEAR, 1);

		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);
		if(compareDate(end,start)==1){
			result.add(start);
		}
		while (tempStart.before(tempEnd)) {
			result.add(tempStart.getTime());
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		if(compareDate(end,start)>=0){
			result.add(end);
		}
		return result;
	}

	/**
	 * date2比date1多的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDifferentDays(Date date1,Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2)   //同一年
		{
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2-day1) ;
		}
		else    //不同年
		{
			return day2-day1;
		}
	}

	/**
	 * 日期加或减day天
	 * @param date
	 * @param day
	 * @param dateType 1：月，2天，3：年
	 * @return
	 */
	public static Date calculateDate(Date date,int day,int dateType){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (dateType==1) {
			calendar.add(Calendar.MONTH,day);
		} else if(dateType==2) {
			calendar.add(Calendar.DATE,day);
		} else if(dateType==3) {
			calendar.add(Calendar.YEAR,day);
		}

		return calendar.getTime();
	}

	public static void main(String[] args) {
		System.out.println(dateToStr(DateTool.calculateDate(new Date(),1,1),DATE_TIME_Sec));
	}

}
