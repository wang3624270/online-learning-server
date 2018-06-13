package cn.edu.sdu.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class DateTimeTool {
	/**
	 * 
	 * <p>
	 * 日期时间处理
	 * </p>
	 * 
	 * <p>
	 * 该类主要将日期和时间转换成 以1970年1月1日0点0分为基准准的毫秒数的长整数来表示 然后对长整数进行处理 并提供与一般日期格式的相互转换的方法
	 * </p>
	 * 
	 */

	private static GregorianCalendar calendar = new GregorianCalendar();

	public final static long DAY = 86400000; // 一天的毫秒数

	public final static long HOUR = 3600000; // 一小时的毫秒数

	public final static long MINUTE = 60000; // 一分钟的毫秒数

	public final static long SECOND = 1000; // 一秒钟的毫秒数

	private static String[] weekString = new String[] { "空", "星期一", "星期二",
			"星期三", "星期四", "星期五", "星期六", "星期日" };

	public DateTimeTool() {

	}

	public static long getNowMillis() {
		GregorianCalendar now = new GregorianCalendar();
		return now.getTimeInMillis();
	}

	// 获得当前时间“yyyy-mm-dd hh:mm:ss.000”
	public static String getNow() {
		GregorianCalendar now = new GregorianCalendar();
		return getDateTime(now.getTimeInMillis());
	}

	// 获得当前时间的毫秒表示(长整型)
	public static long getCurDateMillis() {
		GregorianCalendar now = new GregorianCalendar();
		String sDate = getTimeStr(now.getTime(), "yyyy-MM-dd");
		return getDateMillis(sDate);
	}

	// 获得当前日期“yyyy-mm-dd”
	public static String getCurDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(Calendar.getInstance().getTime());
	}
	public static String getCurDate(String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(Calendar.getInstance().getTime());
	}

	// 提供“yyyy-mm-dd”形式的字符串到毫秒的转换
	public static long getDateMillis(String dateString) {
		String[] date = dateString.split("-");
		long dateMillis = 0;
		try {
			dateMillis = getDateMillis(Integer.parseInt(date[0]),
					Integer.parseInt(date[1]), Integer.parseInt(date[2]));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("输入的日期格式不正确！");
		}
		return dateMillis;
	}

	// 提供“hh:mm:ss.000”形式的字符串 转换成毫秒
	// 或“hh:mm:ss”形式的字符串 转换成毫秒
	public static long getTimeMillis(String timeString) {
		long lTimeMillis = 0l;
		try {
			int temp = 0;
			String tempStr = timeString.replace('.', ':');
			String[] time = tempStr.split(":");
			temp = Integer.parseInt(time[0]);
			lTimeMillis += temp * HOUR;
			temp = Integer.parseInt(time[1]);
			lTimeMillis += temp * MINUTE;
			temp = Integer.parseInt(time[2]);
			lTimeMillis += temp * SECOND;
			if (time.length > 3) { // 如果有毫秒
				temp = Integer.parseInt(time[3]);
				lTimeMillis += temp;
			}
		} catch (Exception ex) {
		}
		return lTimeMillis;
	}

	// 提供“yyyy-mm-dd hh:mm:ss.000”形式的字符串 转换成毫秒
	// 或“yyyy-mm-dd hh:mm:ss”形式的字符串 转换成毫秒
	public static long getDateTimeMillis(String dateTimeString) {
		String[] date = dateTimeString.split(" ");
		if (date.length < 2) { // 中间没空格
			return 0;
		}
		if (date.length > 2) { // 中间有多个空格
			date[1] = date[date.length - 1]; //
		}
		// System.out.println(date[0]);
		// System.out.println(date[1]);
		long dateL = getDateMillis(date[0]);
		long timeL = getTimeMillis(date[1]);
		return dateL + timeL;
	}

	// 根据输入的整型“int yyyy”，“int mm”，“int dd”，
	// 转换成毫秒表示的时间(长整型)OK
	public static long getDateMillis(int year, int month, int day) {
		// 说明：在系统中用0，1，2,...,11表示一年中的12个月份
		// 所以转化时要将实际月份 减去 1
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTimeInMillis();
	}

	// 根据输入的毫秒数，获得日期“yyyy-mm-dd”字符串
	public static String getDate(long millis) {

		String sDate = getYear(millis) + "-";
		sDate += getMonth(millis) + "-";
		sDate += getDay(millis);
		return sDate;
	}

	// 根据输入的毫秒数，获得日期时间“hh:mm:ss.000”字符串
	public static String getTime(long millis) {
		calendar.setTimeInMillis(millis);
		String sTime = "";
		sTime += getHour(millis) + ":";
		sTime += getMinute(millis) + ":";
		sTime += getSecond(millis) + ".";
		String strMillSec = "" + getMillSecond(millis);
		if (strMillSec.length() == 1) {
			strMillSec = "00" + strMillSec;
		} else if (strMillSec.length() == 2) {
			strMillSec = "0" + strMillSec;
		}
		sTime += strMillSec;
		return sTime;
	}

	// 根据输入的毫秒数，获得日期时间“yyyy-mm-dd hh:mm:ss.000”字符串
	public static String getDateTime(long millis) {
		calendar.setTimeInMillis(millis);
		String sDate = getDate(millis);
		String sTime = getTime(millis);
		return sDate + " " + sTime;
	}

	// 根据输入的毫秒数，获得 年
	public static int getYear(long millis) {
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.YEAR);
	}

	// 根据输入的毫秒数，获得 月
	public static int getMonth(long millis) {
		// 说明：在系统中用0，1，2,...,11表示一年中的12个月份
		// 所以要转化到实际月份时， 需要将系统的月份加 1
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.MONTH) + 1;// 系统月份加1
	}

	// 根据输入的毫秒数，获得 日
	public static int getDay(long millis) {
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.DATE);
	}

	// 根据输入的毫秒数，获得 小时
	public static int getHour(long millis) {
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	// 根据输入的毫秒数，获得 分钟
	public static int getMinute(long millis) {
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.MINUTE);
	}

	// 根据输入的毫秒数，获得 秒
	public static int getSecond(long millis) {
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.SECOND);
	}

	// 根据输入的毫秒数，获得 毫秒
	public static int getMillSecond(long millis) {
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.MILLISECOND);
	}

	// 根据“yyyy-mm-dd”得到 "星期几",如“星期二”OK
	public static String getWeekDay(String dateString) {
		String weekDay = "";
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date givedDate = dateFormatter.parse(dateString);
			SimpleDateFormat weekFormatter = new SimpleDateFormat("E",
					Locale.CHINA);
			weekDay = weekFormatter.format(givedDate);
		} catch (ParseException ee) {
			ee.printStackTrace();
			return weekDay;
		}
		return weekDay;
	}

	// 根据输入的毫秒数，得到 "星期几",如“星期二”OK
	public static String getWeekDay(long date) {
		String weekDay = "";
		String dateString = getDate(date);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date givedDate = dateFormatter.parse(dateString);
			SimpleDateFormat weekFormatter = new SimpleDateFormat("E",
					Locale.CHINA);
			weekDay = weekFormatter.format(givedDate);
		} catch (ParseException ee) {
			ee.printStackTrace();
			return weekDay;
		}
		return weekDay;
	}

	//
	// 根据“yyyy-mm-dd”得到 整型表示的周几，如 1：(星期一)，2：(星期二)
	public static int getIntWeekDay(String dateString) {
		String sWeekDay = getWeekDay(dateString);
		int iWeekDay = 0;
		for (int i = 1; i <= 7; i++) {
			if (weekString[i].equals(sWeekDay)) {
				iWeekDay = i;
				break;
			}
		}
		return iWeekDay;
	}

	// 根据输入的毫秒数，得到 整型表示的周几，如 1：(星期一)，2：(星期二)
	public static int getIntWeekDay(long date) {
		String sWeekDay = getWeekDay(date);
		int iWeekDay = 0;
		for (int i = 1; i <= 7; i++) {
			if (weekString[i].equals(sWeekDay)) {
				iWeekDay = i;
				break;
			}
		}
		return iWeekDay;
	}

	/**
	 * 获取 从某日期起第一个星期几的日期“yyyy-mm-dd” 如：得到2005-10-26以后的第一个星期一是什么日期 startDate 起始日
	 * 格式“yyyy-mm-dd” weekday 要查找的星期数 如“星期四” 返回 “yyyy-mm-dd”
	 */
	public static String getDateStringByDateAndWeekday(String startDate,
			String weekday) {
		long datemillis = getDateMillis(startDate);
		String sWeekday = getWeekDay(datemillis);
		while (!sWeekday.equals(weekday)) {
			datemillis += DAY;
			sWeekday = getWeekDay(datemillis);
		}
		return getDate(datemillis);
	}

	/**
	 * 获取 从某日期起第一个星期几的日期“yyyy-mm-dd” 如：得到2005-10-26以后的第一个星期一是什么日期 startDate 起始日
	 * 格式“yyyy-mm-dd” weekday 要查找的星期数 如“星期四” 返回 long
	 */
	public static long getDateMillisByDateAndWeekday(String startDate,
			String weekday) {
		long datemillis = getDateMillis(startDate);
		String sWeekday = getWeekDay(datemillis);
		while (!sWeekday.equals(weekday)) {
			datemillis += DAY;
			sWeekday = getWeekDay(datemillis);
		}
		return datemillis;
	}

	/**
	 * 获取 从某日期之前第一个星期几的日期“yyyy-mm-dd” 如：得到2005-10-26之前的第一个星期一是什么日期 startDate 起始日
	 * 格式“yyyy-mm-dd” weekday 要查找的星期数 如“星期四” 返回 “yyyy-mm-dd”
	 */
	public static String getBeforeDateStringByDateAndWeekday(String startDate,
			String weekday) {
		long datemillis = getDateMillis(startDate);
		String sWeekday = getWeekDay(datemillis);
		while (!sWeekday.equals(weekday)) {
			datemillis -= DAY;
			sWeekday = getWeekDay(datemillis);
		}
		return getDate(datemillis);
	}

	/**
	 * 获取 从某日期之前第一个星期几的日期“yyyy-mm-dd” 如：得到2005-10-26之前的第一个星期一是什么日期 startDate 起始日
	 * 格式“yyyy-mm-dd” weekday 要查找的星期数 如“星期四” 返回 long
	 */
	public static long getBeforeDateMillisByDateAndWeekday(String startDate,
			String weekday) {
		long datemillis = getDateMillis(startDate);
		String sWeekday = getWeekDay(datemillis);
		while (!sWeekday.equals(weekday)) {
			datemillis -= DAY;
			sWeekday = getWeekDay(datemillis);
		}
		return datemillis;
	}

	/**
	 * 获取当前日期前（后）几天的日期。正数表示后几天，负数表示前几天
	 * 
	 * @param date
	 * @param shift
	 * @return
	 */
	public static String getDateStringShift(String date, int shift) {
		long datemillis = getDateMillis(date);
		datemillis += shift * DAY;
		return getDate(datemillis);
	}

	/**
	 * 获取当前日期前（后）几天的日期。正数表示后几天，负数表示前几天
	 * 
	 * @param date
	 * @param shift
	 * @return
	 */
	public static long getDateMillisShift(String date, int shift) {
		long datemillis = getDateMillis(date);
		datemillis += shift * DAY;
		return datemillis;
	}

	public static void main(String[] args) {
		System.out.println(getNow());
		System.out.println(getCurDate());

		Calendar calendar = new GregorianCalendar(Locale.CHINA);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(calendar.getTime()));
		System.out.println(calendar.get(Calendar.YEAR));

		// lDate += DAY;
		// System.out.println(getDate(lDate));
		/*
		 * try{ Date date1 = df.parse(sdate1); Date date2 = df.parse(sdate2);
		 * long l1 = date1.getTime(); long l2 = date2.getTime(); int days =
		 * getDay(l1); System.out.println(days); }catch(ParseException ex){ }
		 */

	}

	// 获得当前时间--zjc
	public static Date getNowTime(String format) {
		SimpleDateFormat sdFormat = new SimpleDateFormat(format);
		GregorianCalendar gc = new GregorianCalendar();
		try {
			return sdFormat.parse(sdFormat.format(gc.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// 获得当前时间--zjc
	public static String getNowTimeStr(String format) {
		SimpleDateFormat sdFormat = new SimpleDateFormat(format);
		GregorianCalendar gc = new GregorianCalendar();

		return sdFormat.format(gc.getTime());
	}

	//
	public static Date getNowTime() {
		return getNowTime("yyyy-MM-dd HH:mm:ss");
	}

	//
	public static Date formatDateTime(String timeSrc, String f) {
		SimpleDateFormat sdFormat = new SimpleDateFormat(f);
		sdFormat.setLenient(true);
		try {
			if (timeSrc == null || timeSrc.trim().equals(""))
				return null;

			Date tmpDate = sdFormat.parse(timeSrc);
			return tmpDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return null;
		}
	}

	//
	public static String parseDateTime(Date timeSrc, String f) {
		if (timeSrc == null)
			return null;
		SimpleDateFormat sdFormat = new SimpleDateFormat(f);
		String result = sdFormat.format(timeSrc);
		if (result != null)
			return result;
		else
			return "";
	}

	/**
	 * 格式化时间到字符串
	 * 
	 * @param srcDate
	 *            源时间
	 * @param format
	 * @return
	 */
	public static String getTimeStr(Date srcDate, String format) {
		SimpleDateFormat sdFormat = new SimpleDateFormat(format);
		if (srcDate == null)
			return "";

		return sdFormat.format(srcDate);
	}

	public static String getShiftDate(int n) {

		GregorianCalendar now = new GregorianCalendar();
		now.add(Calendar.DAY_OF_MONTH, n);
		int m = now.get(Calendar.MONTH) + 1;
		String shiftdate = now.get(Calendar.YEAR) + "-" + m + "-"
				+ now.get(Calendar.DAY_OF_MONTH);
		return shiftdate;
	}

	public static int getYear() {
		GregorianCalendar now = new GregorianCalendar();
		calendar.setTimeInMillis(now.getTimeInMillis());
		return calendar.get(Calendar.YEAR);
	}
	public static int getMonth() {
		//GregorianCalendar now = new GregorianCalendar();
		//calendar.setTimeInMillis(now.getTimeInMillis());
		//return calendar.get(Calendar.MONTH);
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MONTH)+1;
	}

	public static Date getDateByString(String str) {
		if(str.equals(""))
			return null;
		if(str.equals("0000-00-00"))
			return null;
		StringTokenizer sz = new StringTokenizer(str,"-");
		String s;
		s = sz.nextToken();
		int year = Integer.parseInt(s);
		int month, day;
		s = sz.nextToken();
		if(s.length() == 1) {
			month = s.charAt(0) - '0';
		}else {
			if (s.charAt(0) == '0')
				month = s.charAt(1) - '0';
			else
				month = s.charAt(1) - '0' + 10;
		}
		s = sz.nextToken();
		if(s.length() == 1) {
			day = s.charAt(0) - '0';
		}else {
			if (s.charAt(0) == '0')
				day = s.charAt(1) - '0';
			else 
				day = s.charAt(1) - '0' + 10 * (s.charAt(0) - '0');
		}
		return new Date(getDateMillis(year, month, day));
	}

	public static Date getDateByString(String str, String ds) {
		StringTokenizer sz = new StringTokenizer(str, ds);
		String temp;
		int year = 0, month = 0, day = 0;
		if (sz.hasMoreTokens()) {
			temp = sz.nextToken();
			year = Integer.parseInt(temp);
		}
		if (sz.hasMoreTokens()) {
			temp = sz.nextToken();
			month = Integer.parseInt(temp);
		}
		if (sz.hasMoreTokens()) {
			temp = sz.nextToken();
			day = Integer.parseInt(temp);
		}
		return new Date(getDateMillis(year, month, day));
	}

	// wj add
	public static String formatTime(String timeSrc) {
		String time = "";
		if (timeSrc != null) {
			if (!timeSrc.equals("")) {
				SimpleDateFormat sdFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdFormat.parse(timeSrc);
					time = dFormat.format(date);
				} catch (ParseException e) {
					// TODO 自动生成 catch 块
					// e.printStackTrace();
					time = "ErrorTime";
				}
			} else {
				time = "";
			}
		} else {
			time = "";
		}
		return time;
	}

	public static String formatTime(Date date) {
		String time = "";
		if (date != null) {
			SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				time = dFormat.format(date);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				// e.printStackTrace();
				time = "ErrorTime";
			}

		} else {
			time = "";
		}
		return time;
	}

	public static String formatTimeTwo(String timeSrc) {
		String time = "";
		if (timeSrc != null) {
			if (!timeSrc.equals("")) {
				SimpleDateFormat sdFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat dFormat = new SimpleDateFormat(
						"yyyy  年  MM  月  dd  日");
				try {
					Date date = sdFormat.parse(timeSrc);
					time = dFormat.format(date);
				} catch (ParseException e) {
					// TODO 自动生成 catch 块
					// e.printStackTrace();
					time = "ErrorTime";
				}
			} else {
				time = "";
			}
		} else {
			time = "";
		}
		return time;
	}

	public static List<String> getDateList(Date start, Date end) {
		Calendar nstart = Calendar.getInstance();
		nstart.setTime(start);
		Calendar nend = Calendar.getInstance();
		nend.setTime(end);
		if (nstart.after(nend)) {
			return null;
		}

		List<String> retList = new ArrayList<String>();
		String startString = getTimeStr(start, "yyyy-MM-dd");

		retList.add(startString);

		for (; nstart.before(nend);) {
			nstart.add(Calendar.DATE, 1);
			Date date = nstart.getTime();
			String startString1 = getTimeStr(date, "yyyy-MM-dd");
			retList.add(startString1);
		}

		return retList;
	}

	public static String getScheduleMonth(Date date) {
		String time = "";
		if (date != null) {
			SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				time = dFormat.format(date);
				time = time.substring(0, 4) + "年" + time.substring(5, 7) + "月";
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				time = "ErrorTime";
			}

		} else {
			time = "";
		}
		return time;
	}

	public static String getScheduleDay(Date date) {
		String time = "";
		if (date != null) {
			SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				time = dFormat.format(date);
				time = time.substring(0, 4) + "年" + time.substring(5, 7) + "月"
						+ time.substring(8, 10) + "日";
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				time = "ErrorTime";
			}

		} else {
			time = "";
		}
		return time;
	}

	/**
	 * 输出所在月给定周数的所有日期
	 * 
	 * @param date
	 *            源时间
	 * @param weekNums
	 *            周数
	 * @return yyyy-MM-dd
	 */
	public static String[] getScheduleMultiWeekDaysString(Date date) {

		String[] timeString = new String[7 * 6];
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < timeString.length; i++)
			timeString[i] = "";
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			int year = Integer.parseInt(dFormat.format(date).substring(0, 4));
			int month = Integer.parseInt(dFormat.format(date).substring(5, 7));
			c1.set(year, month - 1, 1);// 获得月初日期yyyy-MM-01
			int day = c1.get(Calendar.DAY_OF_WEEK) - 1;// 获取该天是一周中的第几天，0代表周日
			Date tempdate = null;
			c1.add(Calendar.DAY_OF_MONTH, -day);// 获取本周“周日”的日期
			tempdate = c1.getTime();
			timeString[0] = dFormat.format(tempdate);
			int count = 0;
			for (int i = 1; i < timeString.length; i++) {
				c1.add(Calendar.DAY_OF_MONTH, 1);// 往后推一天
				tempdate = c1.getTime();
				timeString[i] = dFormat.format(tempdate);
				if (i % 7 == 0 && i > 21)// 至少为4行
				{
					if (Integer.parseInt(dFormat.format(tempdate).substring(5,
							7)) == month)
						count = i;
				}
			}
			count = count + 7;
			String[] resultString = new String[count];
			for (int i = 0; i < count; i++)
				resultString[i] = timeString[i];
			return resultString;
		} else
			return timeString;

	}

	
	/**
	 * 输出所在周的所有日期
	 * 
	 * @param date
	 *            源时间
	 * @return yyyy-MM-dd
	 */
	public static String[] getScheduleOneWeekDaysString(Date date) {
		String[] timeString = new String[7];
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < 7; i++)
			timeString[i] = "";
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			int day = c1.get(Calendar.DAY_OF_WEEK) - 1;// 获取该天是一周中的第几天，0代表周日
			Date tempdate = null;
			c1.add(Calendar.DAY_OF_MONTH, -day);// 获取本周“周日”的日期
			tempdate = c1.getTime();
			timeString[0] = dFormat.format(tempdate);
			for (int i = 1; i < 7; i++) {
				c1.add(Calendar.DAY_OF_MONTH, 1);// 往后推一天
				tempdate = c1.getTime();
				timeString[i] = dFormat.format(tempdate);
			}
		}
		return timeString;
	}

	public static Date nextDay(Date date) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.DAY_OF_MONTH, 1);
			return c1.getTime();
		} else
			return null;
	}
	public static Date nextDay(Date date, int num) {
		if (date != null) {
			if(num == 0)
				return date;
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.DAY_OF_MONTH, num);
			return c1.getTime();
		} else
			return null;
	}

	public static Date prevDay(Date date) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.DAY_OF_MONTH, -1);
			return c1.getTime();
		} else
			return null;

	}
	public static Date prevDay(Date date, int n) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.DAY_OF_MONTH, -n);
			return c1.getTime();
		} else
			return null;
	}

	public static Date nextWeek(Date date) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.DAY_OF_MONTH, 7);
			return c1.getTime();
		} else
			return null;
	}

	public static Date prevWeek(Date date) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.DAY_OF_MONTH, -7);
			return c1.getTime();
		} else
			return null;
	}

	public static Date nextMonth(Date date) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.MONTH, 1);
			return c1.getTime();
		} else
			return null;
	}
	public static Date nextMonth(Date date,int n) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.MONTH, n);
			return c1.getTime();
		} else
			return null;
	}

	public static Date prevMonth(Date date) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.MONTH, -1);
			return c1.getTime();
		} else
			return null;
	}
	public static Date prevMonth(Date date, int n) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.MONTH, -n);
			return c1.getTime();
		} else
			return null;
	}

	public static Date nextYear(Date date,int n) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.YEAR, n);
			return c1.getTime();
		} else
			return null;
	}

	public static Date prevYear(Date date) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			c1.add(Calendar.YEAR, -1);
			return c1.getTime();
		} else
			return null;
	}
	
	public static Date prevMonthShift(Date date) {
		if (date != null) {
			Calendar c1 = Calendar.getInstance();
			SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
			int year = Integer.parseInt(dFormat.format(date).substring(0, 4));
			int month = Integer.parseInt(dFormat.format(date).substring(5, 7));
			int day = Integer.parseInt(dFormat.format(date).substring(8, 10));
			c1.set(year, month - 2, day);// 获得上月初日期yyyy-MM-01
			return c1.getTime();
		} else
			return null;
	}
	public static String changeDateStringToChines(String date){
		return date.substring(0,4) + "年" +date.subSequence(5, 7) + "月" + date.subSequence(8, 10) + "日";
	}
	public static int getDiffDays(Date d1, Date d2){
		long t1 = d1.getTime();
		long t2 = d2.getTime();
		return (int)((t2-t1)/(60l*1000l*60l*24l));
	}
	
	/*****
	 * 获得月份初始日期
	 * 2014-08-25,xushuai
	 * *******/
	public static Date getMiniMonthDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
	
	/*****
	 * 获得月份最后日期
	 * 2014-08-25,xushuai
	 * *******/
	public static Date getMaxMonthDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
	public static String getGradeDate(){
		GregorianCalendar now = new GregorianCalendar();
		now.setTimeInMillis(now.getTimeInMillis());
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		if(month >= 3 && month <= 4) {
			return year + "-03-30";
		}
		else if(month >= 6 && month <= 7) {
			return year + "-06-30";
		}
		else if(month >= 9 && month <= 10) {
			return year + "-09-30";
		}
		else if(month == 12)
			return (year) + "-12-30";
		else  {
			return (year-1) + "-12-30";			
		}
	}	
	public static int getMonthDiff(Date startDate, Date endDate){
		int monthDay = 0;
		
		Calendar starCal = Calendar.getInstance();
        starCal.setTime(startDate);
		int sYear =starCal.get(Calendar.YEAR);
		int sMonth = starCal.get(Calendar.MONTH);
        int sDay = starCal.get(Calendar.DATE);
        
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int eYear = endCal.get(Calendar.YEAR);
        int eMonth = endCal.get(Calendar.MONTH);
        int eDay = endCal.get(Calendar.DATE);
        
        monthDay = ((eYear - sYear) * 12 + (eMonth - sMonth));
        if (sDay < eDay) {
        	monthDay = monthDay + 1;
        }
        return monthDay;
	}
	public static String getTermOrder(){
		String order = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String thisDate = format.format(new Date());
		String year = thisDate.substring(0, 4);
		String month = thisDate.substring(4, 6);
		if (new Integer(month) > 8) {
			order = "2";
		} else {
			order = "1";
		}
		return order;
	}
	public static String getYearTermOrder(){
		String order = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String thisDate = format.format(new Date());
		String year = thisDate.substring(0, 4);
		String month = thisDate.substring(4, 6);
		if (new Integer(month) > 8) {
			order = year + "2";
		} else {
			order = year + "1";
		}
		return order;
	}
	
	public static boolean isCanOperationDuring(String ds, String de){
		Date d1 = DateTimeTool.formatDateTime(ds, "yyyy-MM-dd HH:mm:ss");
		Date d2 = DateTimeTool.formatDateTime(de, "yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		if(d.before(d1) || d.after(d2))
			return false;
		else
			return true;

	}
	public static boolean testObjectIsEqualDate(Date s, Date d) {
		if (s == null && d == null)
			return true;
		else if (s != null && d == null)
			return false;
		else if (s == null && d != null)
			return false;
		else {
			String ss = parseDateTime(s, "yyyy-MM-dd HH:mm:ss");
			String ds = parseDateTime(d, "yyyy-MM-dd HH:mm:ss");
			return ss.equals(ds);
		}
	}
	
	public static Date getNextNthDate(Date date, int n){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, n);
		return c.getTime();
	}
	/**
	 * 获取某日期后几月的日期。
	 * 
	 * @param date
	 * @param shift只能用于正数
	 * @return
	 */
	public static Date getMonthShift(String date, int shift) {
		String targetDate = "";
		String months = "";
		String dstr[] = convertStringToArray(date, "-");
		int year = Integer.valueOf(dstr[0]);
		int month = Integer.valueOf(dstr[1]);
		if (month + shift > 12) {
			year += (month + shift) / 12;
		}
		month = (month + shift) % 12;
		if (month == 0) {
			months = "12";
		} else if (month < 10) {
			months = "0" + month;
		} else {
			months = String.valueOf(month);
		}
		targetDate = year + "-" + months + "-" + dstr[2];
		return formatDateTime(targetDate, "yyyy-MM-dd");
	}
	public static String[] convertStringToArray(String src, String split)
	{
		return src.split(split);
	}
	public static String getDayStartEndTimeStr(Date s, Date e){
		if(s == null)
			return "";
		String str = parseDateTime(s, "yyyy-MM-dd HH:mm:ss");
		String rs = str.substring(0,16);
		if(e == null)
			return rs;
		str = parseDateTime(e, "yyyy-MM-dd HH:mm:ss");
		rs += "-" + str.substring(11,16);
		return rs;
	}
}