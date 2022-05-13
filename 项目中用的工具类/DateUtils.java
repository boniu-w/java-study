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
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * @ClassName: DateUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:09
 */
public class DateUtils {
	/**
	 * 时间戳处理
	 */
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

	/**
	 * @date 14:25 2018/11/16 根据时间获取序号
	 */
	public static int getIndex(Date date) {
		return Integer.parseInt(simpleDateFormat.format(date).split(":")[0]);
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

	/**
	 * yyyyMMddHHmm
	 */
	public static final String COMMON_DATE_STR5 = "yyyyMMddHHmm";

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

	public static Date getBeginYearMonthDateTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String dateTime = dateToString(c.getTime()) + " 00:00:00";
		return stringToDate(dateTime, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date getEndYearMonthDateTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.DATE, -1);
		String dateTime = dateToString(c.getTime()) + " 23:59:59";
		return stringToDate(dateTime, "yyyy-MM-dd HH:mm:ss");
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
		if (time == null || date == null) {
			return null;
		}
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
		String dateStr = dateToString(date, "yyyy-MM-dd") + " " + timeStr;
		return stringToDate(dateStr, "yyyy-MM-dd " + timeFormatStr);
	}

	public static Date getZeroHourMinute() {
		TimeZone default1 = TimeZone.getDefault();
		return new Date(0 - default1.getOffset(0l));
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
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}

	/**
	 * 根据开始时间和结束时间返回时间段内的年月集合
	 *
	 * @param beginDate
	 * @param endDate
	 * @return List
	 */
	public static List<String> getMonthBetweenTwoDate(Date beginDate, Date endDate) {
		List<String> lYearMonth = new ArrayList<String>();
		lYearMonth.add(dateToString4(beginDate));// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lYearMonth.add(dateToString4(cal.getTime()));
			} else {
				break;
			}
		}
		lYearMonth.add(dateToString4(endDate));// 把结束时间加入集合
		return lYearMonth;
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

	public static String Min2HHmmss(long mins) {
		String HHmmss = "";
		if (mins <= 0) {
			HHmmss = "00:00:00";
		}
		long hour = mins / 60;
		long min = mins - hour * 60;
		String hourStr = String.valueOf(hour);
		String minStr = String.valueOf(min);
		if (hour < 10) {
			hourStr = "0" + hourStr;
		}
		if (min < 10) {
			minStr = "0" + minStr;
		}
		HHmmss = hourStr + ":" + minStr + ":00";
		return HHmmss;
	}

	public static Long HHmmss2Min(String mins) {
		String[] minStr = mins.split(":");
		return Long.valueOf(Integer.parseInt(minStr[0]) * 60 + Integer.parseInt(minStr[1]));
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

	public static int getDayOfWeek(Date date) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		if (instance.get(Calendar.DAY_OF_WEEK) - 1 == 0) {
			return 7;
		} else {
			return instance.get(Calendar.DAY_OF_WEEK) - 1;
		}
	}

	public static int getDayOfWeek(String dateStr, String pattern) {
		Date date = stringToDate(dateStr, pattern);
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		if (instance.get(Calendar.DAY_OF_WEEK) - 1 == 0) {
			return 7;
		} else {
			return instance.get(Calendar.DAY_OF_WEEK) - 1;
		}
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
	public static int getDateInterval(java.util.Date after, java.util.Date before, int field) {
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
		long interval = Math.abs(endDate.getTime() - startDate.getTime());
		return interval / (1000 * 60);
	}

	/**
	 * 求两个日期差（分钟）
	 *
	 * * @param date 开始日期
	 * 
	 * @return 两个日期相差分钟
	 */
	public static Long dateToMinute(Date date) {
		String s = dateToString(date, "yyyy-MM-dd");
		Date date1 = stringToDate(s);
		return getMinuteInterval(date1, date);
	}

	/**
	 * 功能:获取不带时分秒的时间
	 *
	 * @param sourceDate
	 * @return
	 */
	public static Date getShortDate(Date sourceDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
			datesStr[i] = dateToString(dateList.get(i), "yyyy-MM-dd");
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
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				days = 29;
			} else {
				days = 28;
			}
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return parseString(sdf.format(new Date()));
	}

	public static Date parseString(String strDate) {
		if (StringUtils.isNotBlank(strDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		if (min == 0) {
			return "0";
		}
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
		long interval = 0L;
		if (beforeDate.before(lastDate)) {
			interval = lastDate.getTime() - beforeDate.getTime();
		} else {
			interval = beforeDate.getTime() - lastDate.getTime();
		}

		int betweenDays = (int) (interval / (1000 * 60 * 60 * 24));
		if (betweenDays > days) {
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
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	/**
	 * 功能:平移分钟
	 *
	 * @param date
	 * @param hour
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
		} else {
			return null;
		}
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
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 * @description:String转换为Date pattern
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
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 * @description:String转换为Date yyyy-MM-dd
	 */
	public static Date stringToDate(String stringDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 * @description:String转换为Date yyyy-MM-dd
	 */
	public static String dateToString(Date formatDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (null == formatDate) {
			return null;
		}
		String date = sdf.format(formatDate);
		return date;
	}

	/**
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 * @description:String转换为Date yyyy-MM
	 */
	public static String dateToString2(Date formatDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		if (null == formatDate) {
			return null;
		}

		String date = sdf.format(formatDate);
		return date;
	}

	/**
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 * @description:date转换为String yyyy年MM月dd日 hh:mm:ss
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
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date startDate, Date endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		if (mins == null || mins.longValue() == 0) {
			return "0";
		}
		return (mins / 60 < 10 ? "0" + mins / 60 : mins / 60) + ":" + StringUtils.leftPad("" + mins % 60, 2, "0");
	}

	/**
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getMillisInterval(Date beginDate, Date endDate) {
		return endDate.getTime() - beginDate.getTime();
	}

	/**
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getSecondsInterval(Date beginDate, Date endDate) {
		return (endDate.getTime() - beginDate.getTime()) / 1000;
	}

	/**
	 * 删除给定Date的时分秒毫秒
	 *
	 * @param now
	 * @return
	 */
	public static Date truncateTimeOfDate(Date now) {
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
	public static Date addDate(java.util.Date now, long addTime) {
		if (now == null) {
			return null;
		}
		return new java.util.Date(now.getTime() + addTime);
	}

	public static Date addDateYear(Date now, int yearCount) {
		return addDate(now, yearCount, 0, 0, 0, 0, 0, 0);
	}

	public static Date addDate(java.util.Date now, int year, int month, int day, int hour, int minute, int second,
			int millSecond) {
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

	public static Date addDate(java.util.Date now, int year, int month, int day, int hour, int minute, int second) {
		return addDate(now, year, month, day, hour, minute, second, 0);
	}

	public static Date addDate(java.util.Date now, int hour, int minute, int second) {
		return addDate(now, 0, 0, 0, hour, minute, second, 0);
	}

	public static Date addDateField(java.util.Date now, int field, int amount) {
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
		Long intervalDay = getDateInterval(DateUtils.truncateTimeOfDate(startDate),
				DateUtils.truncateTimeOfDate(endDate));

		if (intervalDay < 0) {
			return result;
		}

		for (Long i = 0L; i <= intervalDay; i++) {
			Date currentDate = addDateField(startDate, Calendar.DATE, i.intValue());
			result.add(currentDate);
		}
		return result;
	}

	public static int getDOW(String date) {
		int result = 0;
		try {
			if (StringUtils.isNotBlank(date)) {
				Date tmp = new SimpleDateFormat("yyyyMMddHHmm").parse(date);
				result = new DateTime(tmp).dayOfWeek().get();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getLocalTime(String localTimeStr, int timeZone) {
		String timeZoneStr = "";
		if (timeZone < 0) {
			if (Math.abs(timeZone) < 10) {
				timeZoneStr = "-0" + Math.abs(timeZone) + ":00";
			} else {
				timeZoneStr = "-" + Math.abs(timeZone) + ":00";
			}
		} else {
			if (timeZone < 10) {
				timeZoneStr = "+0" + timeZone + ":00";
			} else {
				timeZoneStr = "+" + timeZone + ":00";
			}
		}
		String result = null;
		try {
			Date tmp = new SimpleDateFormat("yyyyMMddHHmm").parse(localTimeStr);
			String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tmp).replaceAll(" ", "T");
			result = format + timeZoneStr;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getGMTTime(String gmtTimeStr) {
		String result = null;
		try {
			Date tmp = new SimpleDateFormat("yyyyMMddHHmm").parse(gmtTimeStr);
			String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tmp).replaceAll(" ", "T");
			result = format + "+00:00";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isContinuityDay(String beginDate, String endDate, String pattern) {
		Date dateBegin = stringToDate(beginDate, pattern);
		Calendar instance = Calendar.getInstance();
		instance.setTime(dateBegin);
		instance.add(Calendar.DAY_OF_MONTH, 1);
		Date dateAddOne = instance.getTime();
		return dateToString(dateAddOne, pattern).equals(endDate);
	}

	public static Integer getDowByLocalDateTime(String dateTimeStr) {
		DateTime dateTime = new DateTime(dateTimeStr);
		return dateTime.getDayOfWeek();
	}

	public static String getTimeByLocalDateTime(String dateTimeStr) {
		DateTime dateTime = new DateTime(dateTimeStr);
		return new SimpleDateFormat("HHmm").format(dateTime.toDate());
	}

	public static String formatPekDateFromGMTString(String gmtString) {
		DateTime dateTime = new DateTime(gmtString, DateTimeZone.forOffsetHours(8));
		return dateTime.toString("yyyy-MM-dd HH:mm");
	}

	public static String formatPekDateFromGMTString(String gmtString, String pattern) {
		DateTime dateTime = new DateTime(gmtString, DateTimeZone.forOffsetHours(8));
		return dateTime.toString(pattern);
	}

	public static String formatPekDateFromGMTNoTime(String gmtString, String pattern) {
		DateTime dateTime = new DateTime(gmtString, DateTimeZone.forOffsetHours(8));
		return dateTime.toString(pattern);
	}

	public static int getSlot(String dateTime, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date;
		try {
			date = sdf.parse(dateTime);
			SimpleDateFormat hh = new SimpleDateFormat("HH");
			String format = hh.format(date);
			return Integer.parseInt(format);
		} catch (ParseException e) {
			System.out.println("getSlot parser accourred an exception: " + e.getMessage());
		}
		return -1;
	}

	/**
	 * 给定时间段和星期几，计算该时间段内共有多少个给定的星期几
	 *
	 * @param start
	 *            开始时间,格式yyyy-MM-dd
	 * @param end
	 *            结束时间，格式yyyy-MM-dd
	 * @param a
	 *            星期几，从星期一到星期天，分别用数字1-7表示
	 * @return 星期几统计数
	 */
	public static long weekend(Date start, Date end, int a) {
		// 计数
		long sunDay = 0;
		try {
			// 开始时间
			Calendar startDate = Calendar.getInstance();
			startDate.setTime(start);
			// 结束时间
			Calendar endDate = Calendar.getInstance();
			endDate.setTime(end);
			// 开始日期是星期几
			int SW = startDate.get(Calendar.DAY_OF_WEEK) - 1;
			// 结束日期是星期几
			int EW = endDate.get(Calendar.DAY_OF_WEEK) - 1;

			long diff = endDate.getTimeInMillis() - startDate.getTimeInMillis();
			// 给定时间段内一共有多少天
			long days = diff / (1000 * 60 * 60 * 24);
			// 给定时间内，共有多少个星期
			long w = Math.round(Math.ceil(((days + SW + (7 - EW)) / 7.0)));
			// 总的星期几统计数
			sunDay = w;
			if (a != 7) {
				// 给定的星期几小于起始日期的星期几，需要减少一天
				if (a < SW) {
					sunDay--;
				}
				// 给定的星期几大于结束日期的星期几，需要减少一天
				if (a > EW) {
					sunDay--;
				}
			}
		} catch (Exception se) {
			se.printStackTrace();
		}
		return sunDay;
	}
	
	public static Date addDays(Date date, int days) {
		return DateUtils.addDateField(date, Calendar.DATE, days);
	}

	public static void main(String[] args) {
		Date zeroHourMinute = getZeroHourMinute();
		System.out.println(zeroHourMinute);
		System.out.println(zeroHourMinute.getTime());
	}
}
