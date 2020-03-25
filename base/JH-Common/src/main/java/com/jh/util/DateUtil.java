package com.jh.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *  日期工具类
 * @version <1> 2018-04-28 cxw : Created.
 */
public class DateUtil {

	/*
	 * 日期减去年数获取减去年数后的日期，如2017-10-10 年数减1后为2016-10-10
	 * @param date 字符串类型日期
	 * @param num 减去的年数
	 * @return LocalDate 返回LocalDate类型日期
	 * @version <1> 2018-05-23 cxw: Created.
	 */
	public static LocalDate plusYears(String date,int num){
		return  strToLocalDate(date).plusYears(num);
	}

  /*
   * 字符串日期转换为LocalDate类型
   * @param date 字符串类型日期
   * @return LocalDate 返回LocalDate类型日期
   * @version <1> 2018-04-27 cxw: Created.
   */
	public static LocalDate strToLocalDate(String date){
		return  LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}



	/*
   * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
   * @param nowdate 当前日期
   * @param delay 前移或后延的天数
   * @return LocalDate 返回LocalDate类型日期
   * @version <1> 2018-05-03 cxw: Created.
   */
	public static String getNextDay(String nowdate, String delay) {
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		}catch(Exception e){
			return "";
		}
	}

	/**
	 * 字符串转日期
	 * @param time
	 * @return
	 */
	public static Date StringToDateYMD(String time) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * localdate转换为年月日字符串如2018年8月1日
	 * @param time
	 * @return String
	 *  @version <1> 2018-05-25 cxw: Created.
	 */
	public static String localDateToYMD(LocalDate time) {
		return time.getYear()+"年"+time.getMonth().getValue()+"月"+time.getDayOfMonth()+"日";
	}

	/**
	 * String转换为年月日字符串如2018年8月1日
	 * @param time
	 * @return String
	 *  @version <1> 2018-05-28 cxw: Created.
	 */
	public static String strToYMD(String time) {
		LocalDate date = strToLocalDate(time);
		return date.getYear()+"年"+date.getMonth().getValue()+"月"+date.getDayOfMonth()+"日";
	}



	public static Date StringToDateYMD(String time,String pattern){
		Date returnDate = null;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat(pattern);
			returnDate = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  returnDate;
	}

	/**
	 * 数据库存的是int,先乘以1000后转为long再用timestamp转换
	 * long转日期
	 * @param time
	 * @return
	 */
	public static Date intToDate(int time){
		Timestamp ts = new Timestamp((long)time * 1000);
		Date date = new Date();
		date = ts;
		return date;
	}

	/**
	 * 将整型时间转换为日期字符串
	 * @param time   整型时间 单位秒
	 * @return
	 */
	public static String getDateString(int time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(DateUtil.intToDate(time)).split("-")[0];
	}

	/**
	 * 日期转字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date,String format){
		DateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * long转字符串
	 * @return
	 */
	public static String intToString(int time,String format){
		return dateToString(intToDate(time),format);
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static Integer getNowTime(){
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * 字符串转int
	 * @param time
	 * @return
	 */
	public static int StringToInt(String time){
		if (time.split(":").length == 1){
			time += " 00:00:00";
		}
		Timestamp ts = Timestamp.valueOf(time);
		return (int)(ts.getTime() / 1000);
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param pTime 修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static String dayToWeek(String pTime) throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if(c.get(Calendar.DAY_OF_WEEK) == 1){
			dayForWeek = 7;
		}else{
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}

		String result = "";
		switch (dayForWeek) {
			case 1:
				result = "星期一";
				break;
			case 2:
				result = "星期二";
				break;
			case 3:
				result = "星期三";
				break;
			case 4:
				result = "星期四";
				break;
			case 5:
				result = "星期五";
				break;
			case 6:
				result = "星期六";
				break;
			case 7:
				result = "星期日";
				break;
			default:
				break;
		}
		return result;
	}

	/**
	 * 秒转时分秒
	 * @param time
	 * @return
	 */
	public static String secToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	/**
	 * 调整格式
	 * @param i
	 * @return
	 */
	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	/**
	 * secToTime 方法会返回10:21差小时格式
	 * @return
	 */
	public static String getHMSfomTime(int time){
		if(secToTime(time).split(":").length == 2){
			return "00:"+secToTime(time);
		} else {
			return secToTime(time);
		}

	}

	/**
	 * 在日期上减少天数
	 */
	public static Date subDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加天数
	 *
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加数个整月
	 */
	public static Date addMonth(Date date, int n)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	public static Date subYear(Date date, int n){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR,cal.get(Calendar.YEAR) - n);
		return cal.getTime();
	}

	/**
	 *  <pre>
	 * 求两个日期之间的天数差
	 *  @param startDate
	 *  @param endDate
	 *  @return
	 *  @author <1> 2017年2月13日下午4:15:54-Hayden:
	 * </pre>
	 */
	public static int getTwoDateBetweenDay(Date startDate,Date endDate){
		long between = (endDate.getTime() - startDate.getTime())/1000; //转换成秒
		int days = (int)between / (24*3600);
		return days;
	}

	/**
	 * 求两个日期之间的天数差
	 *  @param start	开始时间	单位秒
	 *  @param end		结束时间	单位秒
	 * @return
	 * <1> 2017-06-16 cxj : Created.
	 */
	public static int getTwoDateBetweenDay(int start,int end){
		return (end - start) / (60 * 60 * 24);
	}

	/**
	 * 求两个日期之间的月数差
	 *  @param startData
	 *  @param endData
	 * @return
	 * <1> 2017-06-16 cxj : Created.
	 */
	public static int getTwoDateBetweenMonth(Date startData,Date endData){
		int startYear,endYear,startMonth,endMonth;
		int result;

		Calendar c = Calendar.getInstance();

		c.setTime(startData);
		startYear = c.get(Calendar.YEAR);   //开始年
		startMonth = c.get(Calendar.MONTH);     //开始月

		c.setTime(endData);
		endYear = c.get(Calendar.YEAR);     //结束年
		endMonth = c.get(Calendar.MONTH);       //结束月

		if(startYear == endYear){
			result = endMonth - startMonth;
		}else{
			result = 12 * (endYear - startYear) + endMonth - startMonth;
		}

		return result;
	}

	/**
	 * 求两个日期之间的月数差
	 *  @param start
	 *  @param end
	 * @return
	 * <1> 2017-06-16 cxj : Created.
	 */
	public static int getTwoDateBetweenMonth(String start,String end) {
		Date startDate = null;
		Date endDate = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			startDate = sdf.parse(start);
			endDate = sdf.parse(end);

			return getTwoDateBetweenMonth(startDate,endDate);
		}catch (Exception e){
			e.printStackTrace();
		}

		return 0;
	}

	public static int getTwoDateBetweenMonth(int start,int end) {
		Date startDate = intToDate(start);
		Date endDate = intToDate(start);

		return getTwoDateBetweenMonth(startDate,endDate);
	}

	/**
	 * 构造同比环比查询时间段条件
	 * @param beginTime
	 * @param endTime
	 * @param contrastType
	 * @return
	 */
	public static List<Map<String,Integer>> computerDateTime(Integer beginTime, Integer endTime, String contrastType){
		List<Map<String,Integer>> dataTimeList = new ArrayList<Map<String,Integer>>();
		Map<String,Integer> firstTime = new HashMap<String, Integer>();
		firstTime.put("beginDate", beginTime);
		firstTime.put("endDate",endTime);
		dataTimeList.add(firstTime);

		Date beginDate = DateUtil.intToDate(beginTime);
		Date endDate = DateUtil.intToDate(endTime);
		if("yoy".equals(contrastType)){
			//同比
			Map<String,Integer> secondTime = new HashMap<String, Integer>();
			secondTime.put("beginDate", DateUtil.StringToInt(DateUtil.dateToString(DateUtil.subYear(beginDate,1),"yyyy-MM-dd")));
			secondTime.put("endDate", DateUtil.StringToInt(DateUtil.dateToString(DateUtil.subYear(endDate,1),"yyyy-MM-dd")));
			dataTimeList.add(secondTime);
		} else if ("mom".equals(contrastType)){
			//环比
			Map<String,Integer> secondTime = new HashMap<String, Integer>();
			secondTime.put("beginDate", DateUtil.StringToInt(DateUtil.dateToString(DateUtil.addMonth(beginDate,-1),"yyyy-MM-dd")));
			secondTime.put("endDate", DateUtil.StringToInt(DateUtil.dateToString(DateUtil.addMonth(endDate,-1),"yyyy-MM-dd")));
			dataTimeList.add(secondTime);
		}
		return dataTimeList;
	}


	/**
	 * 获取系统当前时间
	 * @return
	 * @version <1> 2017-06-16 LiuChuanwei : Created.
	 */
	public static Timestamp getCurrentTime(){
		return new Timestamp(new Date(System.currentTimeMillis()).getTime());
	}

	/**
	 * 	Timestamp转化为String:
	 * @return
	 * <1> 2018年03月22日 14:57-cxw:
	 */
	public  static String getTimestamptoStr(Timestamp time)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
		return df.format(time);
	}

	/**
	 * 	String转化为localDateTime
	 * @return
	 * <1> 2018年03月22日 14:57-cxw:
	 */
	public static LocalDateTime strtoLocalDataTime(String date){
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//LocalDateTime ldt = LocalDateTime.parse(date,df);
		LocalDate ld = LocalDate.parse(date, df);
		LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
		return ldt;
	}

	/**
	 * 	String转化为Timestamp:
	 * @return
	 * <1> 2017年11月14日 14:57-cxw:
	 */
	public  static Timestamp getStrToTimestamp(String time)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  Timestamp.valueOf(time);
	}

	/**
	 *时间格式转换换为 yyyy-MM-dd
	 * @param date
	 * *<1> 2018年04月09日 14:57-cxw:
	 * @return
	 */
	public static Date strToDate(Date date) {
		String strDate = dateToString(date,"yyyy-MM-dd");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}


	/**
	 * LocalDateTime转Date
	 * @param time
	 * @return date
	 * @version <1> 2018-03-22 cxw： Created.
	 */
	public static Date  localDateTimeToDate (LocalDateTime time) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = time.atZone(zoneId);
		Date date = Date.from(zdt.toInstant());
		return date;
	}


	/**
	 * 获取当前年份
	 * @return
	 * @version<1> 2018-03-13 lcw : Created.
	 */
	public static int getYear(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 * @return
	 */
	public static int getMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}


	/**
	 * 计算当前日期是一年的第多少天
	 * @return
	 * @version<1> 2018-03-13 lcw : Created.
	 * @version<2> 2018-04-12 lcw : 第多少天采用三位数进行保存
	 */
	public static String getDayOfYear(){
		String str = "";
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		if(day < 10){
			str = "00" + day;
		}else if(day <100){
			str = "0" + day;
		}else{
			str = day + "";
		}
		return str;
	}


	/**
	 * 日期格式转换
	 * @param date
	 * @return
	 */
	public static String yyyymmddToYYYYmm(String date) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		return sdf.format(sdf.parse(date));
	}

	/**
	 * LocalDate转Date
	 * @param localDate
	 * @return Date
	 * @version <1> 2018/7/20 8:38 zhangshen: Created.
	 */
	public static Date LocalDateToUdate(LocalDate localDate) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
		return Date.from(instant);
	}

	/**
	 * Date转LocalDate
	 * @param date
	 * @return LocalDate
	 * @version <1> 2018/7/20 8:38 zhangshen: Created.
	 */
	public static LocalDate UDateToLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime.toLocalDate();
	}

    /**
     * 根据日期获取年份
     * @param date
     * @return
     */
	public static Integer getYearByDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }



	/**
	 * 日期转星期
	 *
	 * @param datetime
	 * @return
	 */
	public static String dateToWeek(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}


	/**
	 * 根据时间获取该日期是一个星期的第几天
	 * @param date
	 * @return
	 */
	public static int weekOfDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(w <= 0){
			w = 7;
		}

		return w;
	}


	/*
	 * 字符串日期转换为LocalDateTime类型
	 * @param date 字符串类型日期
	 * @return LocalDateTime 返回LocalDateTime类型日期
	 * @version <1> 2019-04-13 zhangshen: Created.
	 */
	public static LocalDateTime strToLocalDateTime(String date, String patternStr){
		return  LocalDateTime.parse(date, DateTimeFormatter.ofPattern(patternStr));
	}


}
