package com.yhl.ros.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: CommonDateUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:06
 */
public class CommonDateUtils {

	private CommonDateUtils() {

	}

	private static Map<String, String> weekDays = new HashMap<String, String>();

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String COMMON_DATE_STR1 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyy/MM/dd HH:mm:ss
	 */
	public static final String COMMON_DATE_STR2 = "yyyy/MM/dd HH:mm:ss";

	/**
	 * yyyy-MM-dd
	 */
	public static final String COMMON_DATE_STR3 = "yyyy-MM-dd";

	/**
	 * HH:mm
	 */
	public static final String COMMON_DATE_STR4 = "HH:mm";

	static {
		weekDays.put("0", "星期日");
		weekDays.put("1", "星期一");
		weekDays.put("2", "星期二");
		weekDays.put("3", "星期三");
		weekDays.put("4", "星期四");
		weekDays.put("5", "星期五");
		weekDays.put("6", "星期六");
	}

	public static int getYearByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取年的最大周次
	 *
	 * @param year
	 * @return 201709
	 */
	public static int getMaxWeeksOfYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		return calendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 根据时间(HH:mm)和日期 返回完整所需的日期时间
	 * 
	 * @param date
	 * @param time
	 *            格式: HH:mm
	 * @return
	 */
	public static Date getDateByTime(Date date, String time) {
		if (time == null || date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		int departHour = Integer.parseInt(time.substring(0, time.length() - 3));
		int departMin = Integer.parseInt(time.substring(time.length() - 2, time.length()));
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				departHour, departMin, 00);
		return calendar.getTime();
	}

	/**
	 * 功能:通过日期和时间生成新的日期
	 * 
	 * @param date
	 *            日期
	 * @param time
	 *            时间
	 * @param timeFormatStr
	 *            时间的格式字符串
	 * @return
	 */
	public static Date addDateTimeWithTimeStr(Date date, Date time, String timeFormatStr) {
		String timeStr = dateToString(time, timeFormatStr);
		return addDateTimeWithTimeStr(date, timeStr, timeFormatStr);
	}

	/**
	 * 功能:通过日期和时间生成新的日期
	 * 
	 * @param date
	 *            日期
	 * @param time
	 *            时间
	 * @param timeFormatStr
	 *            时间的格式字符串
	 * @return
	 */
	public static Date addDateTimeWithTimeStr(Date date, String timeStr, String timeFormatStr) {
		// yyyy-MM-dd + 空格 +时间字符串
		String dateStr = dateToString(date, COMMON_DATE_STR3) + " " + timeStr;
		return stringToDate(dateStr, COMMON_DATE_STR3 + timeFormatStr);
	}

	/**
	 * 根据开始时间和结束时间返回时间段内的时间集合
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return List
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		while (endDate.after(cal.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			lDate.add(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		if (null != dateToString(beginDate) && !dateToString(beginDate).equals(dateToString(endDate))) {
			lDate.add(endDate);// 把结束时间加入集合
		}
		return lDate;
	}

	/**
	 * 根据数字转换成对应的星期
	 */
	public static String getWeekDaysCn(String str) {
		String week = "";
		char[] chars = str.toCharArray();
		for (char c : chars) {
			if ("0".equals(String.valueOf(c))) {
				week += "日";
			}
			if ("1".equals(String.valueOf(c))) {
				week += "一";
			}
			if ("2".equals(String.valueOf(c))) {
				week += "二";
			}
			if ("3".equals(String.valueOf(c))) {
				week += "三";
			}
			if ("4".equals(String.valueOf(c))) {
				week += "四";
			}
			if ("5".equals(String.valueOf(c))) {
				week += "五";
			}
			if ("6".equals(String.valueOf(c))) {
				week += "六";
			}
		}
		return week;
	}

	/**
	 * 根据当前日期获取N天后的日期
	 * 
	 * @return
	 */
	public static Date getDateByTaDay(Date date, int number) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, number);
		Date nextDay = calendar.getTime();
		return nextDay;
	}

	public static String min2HHmmss(long mins) {
		String hhmmss = "";
		if (mins <= 0) {
			hhmmss = "00:00:00";
			return hhmmss;
		}
		long hour = mins / 60;
		long min = mins - hour * 60;
		String hourStr = String.valueOf(hour);
		String minStr = String.valueOf(min);
		if (hour < 10)
			hourStr = "0" + hourStr;
		if (min < 10)
			minStr = "0" + minStr;
		hhmmss = hourStr + ":" + minStr + ":00";
		return hhmmss;
	}

	/**
	 * 参数格式 HH:mm
	 * 
	 * @param mins
	 * @return
	 */
	public static Integer hhmm2Min(String mins) {
		String[] minStr = mins.split(":");
		return Integer.parseInt(minStr[0]) * 60 + Integer.parseInt(minStr[1]);
	}

	/**
	 * 获取日期所在周次 201709/201749
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeeksWithYearByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int weeks = getWeeksByDate(date);
		String weekStr = "";
		if (weeks < 10) {
			weekStr = "0" + weeks;
		} else {
			weekStr = String.valueOf(weeks);
		}
		return Integer.valueOf(year + weekStr);
	}

	/**
	 * 获取日期所在周次 9、32
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeeksByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setTime(date);
		int weeks = calendar.get(Calendar.WEEK_OF_YEAR);
		return weeks;
	}

	/**
	 * 功能:获取日期对应的星期中文名
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekDayCnOfDate(Date date) {
		return weekDays.get(getWeekDayOfDate(date));
	}

	/**
	 * 取得给定时间对应的星期
	 * 
	 * @return
	 */
	public static String getWeekDayOfDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		return "" + (c.get(Calendar.DAY_OF_WEEK) - 1);
	}

	/**
	 * 获取某周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某周的最后一天
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * 获取星期号
	 *
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		int result = instance.get(Calendar.DAY_OF_WEEK) - 1;
		return result == 0 ? 7 : result;
	}

	/**
	 * 获取月号
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		return instance.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 求两个日期差
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 两个日期相差天数
	 */
	public static Long getDateInterval(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		if (startDate.after(endDate)) {
			return -1L;
		}
		long interval = endDate.getTime() - startDate.getTime();
		return interval / (1000 * 60 * 60 * 24);
	}

	/**
	 * 计算两个日期之间相隔的数量，如天数，月数。。。具体由field决定
	 * 
	 * @param after
	 * @param before
	 * @param field
	 *            参考Calendar.YEAR...
	 * @return
	 */
	public static int getDateInterval(Date after, Date before, int field) {
		if (after == null || before == null) {
			return 0;
		}
		Calendar calAfter = Calendar.getInstance();
		calAfter.setTime(after);
		Calendar calBefore = Calendar.getInstance();
		calBefore.setTime(before);
		return calAfter.get(field) - calBefore.get(field);
	}

	/**
	 * 功能:求两个日期相差小时数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Long getHourInterval(Date startDate, Date endDate) {
		if (startDate.after(endDate)) {
			return -1L;
		}
		long interval = endDate.getTime() - startDate.getTime();
		return interval / (1000 * 60 * 60);
	}

	/**
	 * 求两个日期差（分钟）
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 两个日期相差分钟
	 */
	public static Long getMinuteInterval(Date startDate, Date endDate) {
		long interval = endDate.getTime() - startDate.getTime();
		return interval / (1000 * 60);
	}

	/**
	 * 功能:获取不带时分秒的时间
	 * 
	 * @param sourceDate
	 * @return
	 */
	public static Date getShortDate(Date sourceDate) {
		if (sourceDate == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(COMMON_DATE_STR3);
		String strDate = dateFormat.format(sourceDate);
		try {
			return dateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能：返回当前月份第一天
	 * 
	 * @return
	 */
	public static Date getCurrentMonthFristDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 功能：返回当前月份最后一天
	 * 
	 * @return
	 */
	public static Date getCurrentMonthLastDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}

	/**
	 * 功能：返回当前月份最后一天
	 * 
	 * @return
	 */
	public static Date getNextMonthLastDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 2);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}

	/**
	 * 获取日期所在周的七天（周一开始）
	 * 
	 * @return
	 */
	public static String[] getWeekDatesStr(Date date) {
		HashMap<String, Date> dateMap = getWeekDates(date);
		List<Date> dateList = new ArrayList<Date>(dateMap.values());
		String[] datesStr = new String[dateList.size()];
		for (int i = 0; i < dateList.size(); i++) {
			datesStr[i] = dateToString(dateList.get(i), COMMON_DATE_STR3);
		}
		return datesStr;
	}

	/**
	 * 功能:返回参数所在星期的7天, 如，传入日期'2011-06-02'(星期四), 则返回20011-06-02所在星期的周日至周一的日期Map:
	 * {(SUN:'2011-05-29'),(MON:'2011-05-30'),(TUE:'2011-05-31'),(WED:'2011-06-01'),(THU:'2011-06-02'),(FRI:'2011-06-03',(SAT:'2011-06-04')}
	 * 
	 * @param date
	 * @return
	 */
	public static HashMap<String, Date> getWeekDates(Date date) {
		HashMap<String, Date> dates = new HashMap<String, Date>();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		int index = c.get(Calendar.DAY_OF_WEEK);
		c.add(Calendar.DAY_OF_WEEK, (-1) * (index - 1));
		c.add(Calendar.DAY_OF_WEEK, 1);
		dates.put("MON", c.getTime());
		c.add(Calendar.DAY_OF_WEEK, 1);
		dates.put("TUE", c.getTime());
		c.add(Calendar.DAY_OF_WEEK, 1);
		dates.put("WED", c.getTime());
		c.add(Calendar.DAY_OF_WEEK, 1);
		dates.put("THU", c.getTime());
		c.add(Calendar.DAY_OF_WEEK, 1);
		dates.put("FRI", c.getTime());
		c.add(Calendar.DAY_OF_WEEK, 1);
		dates.put("SAT", c.getTime());
		c.add(Calendar.DAY_OF_WEEK, 1);
		dates.put("SUN", c.getTime());
		return dates;
	}

	// 计算当月有多少天
	public static int days(int year, int month) {
		int days = 0;
		if (month != 2) {
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				days = 30;
			}
		} else {
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				days = 29;
			else
				days = 28;
		}
		return days;
	}

	/**
	 * 功能: 取传入参数日期的当月第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthFirstDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 功能: 取传入参数日期的当月最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthLastDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return cal.getTime();
	}

	/**
	 * 功能: 平移月份
	 * 
	 * @param date
	 * @param monthNum
	 * @return
	 */
	public static Date moveDate(Date date, int monthNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, monthNum);
		return cal.getTime();
	}

	/**
	 * 功能: 根据days参数移动日期，days可为正 可为负 日期去除时分秒
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date moveDay(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}

	/**
	 * 获取当前日期，不带时分秒
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_STR3);
		return parseString(sdf.format(new Date()));
	}

	public static Date parseString(String strDate) {
		if (StringUtils.isNotBlank(strDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_STR3);
			try {
				return sdf.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Date parseString(String strDate, String format) {
		if (StringUtils.isNotBlank(strDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				return sdf.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 分钟转小时
	 * 
	 * @param min
	 * @return
	 */
	public static String minToHour(int min) {
		if (min == 0)
			return "0";
		int hour = min / 60;
		int leftMin = min % 60;
		String minStr = leftMin < 10 ? "0" + leftMin : leftMin + "";
		return hour + ":" + minStr;
	}

	/**
	 * 功能: 判断两个日期的间隔天数是否超过参数值 超过返回true 未超过返回false
	 * 
	 * @param beforeDate
	 *            前一日期
	 * @param lastDate
	 *            后一日期
	 * @param days
	 *            间隔天数
	 * @return
	 */
	public static Boolean checkDistanceDay(Date beforeDate, Date lastDate, int days) {
		long interval = 0l;
		if (beforeDate.before(lastDate)) {
			interval = lastDate.getTime() - beforeDate.getTime();
		} else {
			interval = beforeDate.getTime() - lastDate.getTime();
		}

		int betweenDays = (int) (interval / (1000 * 60 * 60 * 24));
		if (betweenDays > days) {
			return true;
		} else if (betweenDays == 0 && days == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能:平移小时
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date moveHour(Date date, int hour) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	/**
	 * 功能:平移分钟
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date moveMinute(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * 功能:获取Date中某个属性的值
	 * 
	 * @param date
	 * @param field
	 *            java.util.Calendar类中定义
	 * @return
	 */
	public static int getDateField(Date date, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}

	/**
	 * 功能: 根据参数years平移年份
	 * 
	 * @param date
	 * @param years
	 * @return
	 */
	public static Date moveYear(Date date, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}

	/**
	 * 功能:根据年份获得这年的最后一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getLastDayOfYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, 11, 31);
		return cal.getTime();
	}

	/**
	 * 功能:根据年份获得这年的第一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, 0, 1);
		return cal.getTime();
	}

	/**
	 * 功能:返回指定年度指定季度的第一天
	 * 
	 * @param quarter,{1,2,3,4}
	 * @param year
	 * @return
	 */
	public static Date qetQuarterFirstDay(int quarter, int year) {
		String firstDateStr = null;
		Date firstDate = null;
		if (year > 0) {
			if (quarter >= 1 && quarter <= 4) {
				switch (quarter) {
				case 1: {
					firstDateStr = year + "-01-01";
					break;
				}
				case 2: {
					firstDateStr = year + "-04-01";
					break;
				}
				case 3: {
					firstDateStr = year + "-07-01";
					break;
				}
				case 4: {
					firstDateStr = year + "-10-01";
					break;
				}
				default:
					;
				}
			}
		}
		if (firstDateStr != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_STR3);

			try {
				firstDate = sdf.parse(firstDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return firstDate;
	}

	/**
	 * 功能:返回指定年度指定季度的最后一天
	 * 
	 * @param quarter
	 * @param year
	 * @return
	 */
	public static Date getQuarterLastDay(int quarter, int year) {
		Date quarterFirstDay = qetQuarterFirstDay(quarter, year);
		if (quarterFirstDay != null) {
			return getQuarterLastDay(quarterFirstDay);
		} else
			return null;
	}

	/**
	 * 功能:返回参数日期所在季度第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getQuarterFirstDay(Date date) {
		int year = getDateField(date, Calendar.YEAR);
		int quarter = getQuarter(date);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		switch (quarter) {
		case 1: {
			c.set(Calendar.MONTH, 0);
			break;
		}
		case 2: {
			c.set(Calendar.MONTH, 3);
			break;
		}
		case 3: {
			c.set(Calendar.MONTH, 6);
			break;
		}
		case 4: {
			c.set(Calendar.MONTH, 9);
			break;
		}
		default:
			;
		}
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 功能:返回参数日期所在季度最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getQuarterLastDay(Date date) {
		int year = getDateField(date, Calendar.YEAR);
		int quarter = getQuarter(date);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		switch (quarter) {
		case 1: {
			c.set(Calendar.MONTH, 3);
			break;
		}
		case 2: {
			c.set(Calendar.MONTH, 6);
			break;
		}
		case 3: {
			c.set(Calendar.MONTH, 9);
			break;
		}
		case 4: {
			c.set(Calendar.YEAR, year + 1);
			c.set(Calendar.MONTH, 0);
			break;
		}
		default:
			;
		}
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_YEAR, -1);
		return c.getTime();
	}

	/**
	 * 功能:返回参数日期所在季度, 1：第一季度 2：第二季度 3：第三季度 4：第四季度
	 * 
	 * @param date
	 * @return
	 */
	public static int getQuarter(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		switch (month) {
		case 0:
			return 1;
		case 1:
			return 1;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 2;
		case 5:
			return 2;
		case 6:
			return 3;
		case 7:
			return 3;
		case 8:
			return 3;
		case 9:
			return 4;
		case 10:
			return 4;
		case 11:
			return 4;
		default:
			;
		}
		return -1;
	}

	/**
	 * @description:String转换为Date pattern
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String stringDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @description:String转换为Date yyyy-MM-dd
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String stringDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_STR3);
		Date date = null;
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @description:String转换为Date yyyy-MM-dd
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 */
	public static String dateToString(Date formatDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_STR3);
		if (null == formatDate)
			return null;

		String date = sdf.format(formatDate);
		return date;
	}

	/**
	 * @description:String转换为Date yyyy-MM
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 */
	public static String dateToString2(Date formatDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		if (null == formatDate)
			return null;

		String date = sdf.format(formatDate);
		return date;
	}

	/**
	 * @description:date转换为String yyyy年MM月dd日 hh:mm:ss
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 */
	public static String dateToString3(Date Date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		String dateString = null;
		try {
			dateString = sdf.format(Date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}

	/**
	 * @description:date转换为String yyyy/MM/dd/ hh:mm:ss
	 * @param Date
	 * @return
	 * @throws ParseException
	 */
	public static String dateToString5(Date Date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateString = null;
		try {
			dateString = sdf.format(Date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}

	public static String dateToString4(Date Date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String dateString = null;
		try {
			dateString = sdf.format(Date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}

	/**
	 * 格式化日期
	 * 
	 * @param Date
	 * @param pattern
	 * @return
	 */
	public static Date formatDate(Date Date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateString = null;
		Date date = null;
		try {
			dateString = sdf.format(Date);
			date = stringToDate(dateString, pattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取一天的起始时间
	 * 
	 * @param date
	 */
	public static Date getDayFirstTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取一天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayLastTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 获取一天的起始时间
	 * 
	 * @param date
	 */
	public static Date getDayFirstTimeForTimingSheet(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 1);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取一天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayLastTimeForTimingSheet(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date stringToDates(String str) throws Exception {
		DateFormat df = new SimpleDateFormat("MMM dd HH:mm:ss 'UTC'Z yyyy", Locale.ENGLISH);
		Date date = df.parse(str);
		return date;
	}

	/**
	 * 获取当前时间戳
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sdf.format(date);
		return str;
	}

	public static String dateToString(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String result = sdf.format(date);
			return result;
		}
		return "";
	}

	public static Date getDate(Date date, int intervalDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, intervalDay);
		return c.getTime();
	}

	public static Date getIntervalMonthLastDay(Date date, int intervalMonth) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, intervalMonth + 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}

	public static Date getIntervalMonthMinDay(Date date, int intervalMonth) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, intervalMonth + 1);
		c.set(Calendar.DAY_OF_MONTH, 15);
		return c.getTime();
	}

	/**
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date startDate, Date endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_STR3);
		startDate = sdf.parse(sdf.format(startDate));
		endDate = sdf.parse(sdf.format(endDate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(endDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		System.out.println(between_days);
		return Integer.parseInt(String.valueOf(between_days)) + 1;
	}

	/**
	 * 判断是否为同一天
	 * 
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static boolean isSameDay(Date day1, Date day2) {
		SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_STR3);
		String ds1 = sdf.format(day1);
		String ds2 = sdf.format(day2);
		if (ds1.equals(ds2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_STR3);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days)) + 1;
	}

	/**
	 * 把分钟转换为小时显示，格式如：2:50表示2小时50分钟
	 * 
	 * @param mins
	 * @return
	 */
	public static String formatMinutes2Hours(Long mins) {
		if (mins == null || mins.longValue() == 0)
			return "0";
		return (mins / 60 < 10 ? "0" + mins / 60 : mins / 60) + ":" + StringUtils.leftPad("" + mins % 60, 2, "0");
	}

	/**
	 * 把分钟转换为小时显示，格式如：2小时50分钟
	 * 
	 * @param mins
	 * @return
	 */
	public static String formatMinutes2HoursCN(Long mins) {
		if (mins == null || mins.longValue() == 0)
			return "0";
		return (mins / 60 < 10 ? "0" + mins / 60 : mins / 60) + "小时" + StringUtils.leftPad("" + mins % 60, 2, "0")
				+ "分钟";
	}

	/**
	 * 删除给定Date的时分秒毫秒
	 * 
	 * @param now
	 * @return
	 */
	public static Date truncateTimeOfDate(java.util.Date now) {
		if (now == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 给定的日期加上addTime的时间
	 * 
	 * @param now
	 * @param addTime
	 *            毫秒
	 * @return
	 */
	public static java.util.Date addDate(java.util.Date now, long addTime) {
		if (now == null) {
			return null;
		}
		return new java.util.Date(now.getTime() + addTime);
	}

	public static java.util.Date addDate(java.util.Date now, int year, int month, int day, int hour, int minute,
			int second, int millSecond) {
		if (now == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.YEAR, year);
		c.add(Calendar.MONTH, month);
		c.add(Calendar.DATE, day);
		c.add(Calendar.HOUR_OF_DAY, hour);
		c.add(Calendar.MINUTE, minute);
		c.add(Calendar.SECOND, second);
		c.add(Calendar.MILLISECOND, millSecond);
		return c.getTime();
	}

	public static java.util.Date addDate(java.util.Date now, int year, int month, int day, int hour, int minute,
			int second) {
		return addDate(now, year, month, day, hour, minute, second, 0);
	}

	public static java.util.Date addDate(java.util.Date now, int hour, int minute, int second) {
		return addDate(now, 0, 0, 0, hour, minute, second, 0);
	}

	public static java.util.Date addDateField(java.util.Date now, int field, int amount) {
		if (now == null) {
			return null;
		}
		if (amount == 0) {
			return now;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(field, amount);
		return c.getTime();
	}

	/**
	 * 功能:获取两个日期之间的所有日期
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static List<Date> getIntervalDates(Date startDate, Date endDate) {
		List<Date> result = new ArrayList<Date>();
		Long intervalDay = getDateInterval(CommonDateUtils.truncateTimeOfDate(startDate),
				CommonDateUtils.truncateTimeOfDate(endDate));

		if (intervalDay < 0)
			return result;

		for (Long i = 0L; i <= intervalDay; i++) {
			Date currentDate = addDateField(startDate, Calendar.DATE, i.intValue());
			result.add(currentDate);
		}
		return result;
	}

	/**
	 * 判断一个字符串是不是一个合法的日期格式
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @param pattern
	 *            合法日期格式
	 * @return
	 */
	public static boolean isValidDate(String str, String pattern) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 
	 * @param nowDate
	 *            要比较的时间
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return true在时间段内，false不在时间段内
	 * @throws Exception
	 */
	public static boolean hourMinuteBetween(String nowDate, String startDate, String endDate) throws Exception {

		if (StringUtils.isBlank(nowDate) || StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
			return false;
		}

		SimpleDateFormat format = new SimpleDateFormat("HH:mm");

		Date now = format.parse(nowDate);
		Date start = format.parse(startDate);
		Date end = format.parse(endDate);

		long nowTime = now.getTime();
		long startTime = start.getTime();
		long endTime = end.getTime();

		return nowTime >= startTime && nowTime <= endTime;

	}

	/**
	 * 判断时间段2是否与时间段1有重叠
	 * 
	 * @param startDate1
	 * @param endDate1
	 * @param startDate2
	 * @param endDate2
	 * @return
	 */
	public static boolean isContain(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {

		if (startDate2.compareTo(startDate1) >= 0 && startDate2.compareTo(endDate1) <= 0)
			return true;

		if (endDate2.compareTo(endDate1) <= 0 && endDate2.compareTo(startDate1) >= 0)
			return true;

		if (startDate2.compareTo(startDate1) <= 0 && endDate2.compareTo(endDate1) >= 0)
			return true;

		return false;
	}

	/**
	 * 分钟转小时分钟 (用做展示使用) 若分钟为0 则不显示
	 * 
	 * @param min
	 * @return 10h55m 或 10h
	 */
	public static String minToHourMin(int min) {
		if (min == 0)
			return "0";
		int hour = min / 60;
		int leftMin = min % 60;
		if (leftMin == 0) {
			return hour + "h";
		} else {
			String minStr = leftMin < 10 ? "0" + leftMin : leftMin + "";
			if (hour == 0) {
				return minStr + "m";
			} else {
				return hour + "h" + minStr + "m";
			}
		}
	}
}
