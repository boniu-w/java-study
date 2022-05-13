package com.yhl.ros.common.utils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import com.yhl.ros.common.CoordinatePoint;
import com.yhl.ros.common.Point;

/**
 * @ClassName: CoordinateUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:07
 */
public class CoordinateUtils {

	private static final String LON_MATCH = "[\\-+]?(0?\\d{1,2}|0?\\d{1,2}\\.\\d{1,7}|1[0-7]?\\d|1[0-7]?\\d\\.\\d{1,7}|180|180\\.0{1,7})";
	private static final String LAT_MATCH = "[\\-+]?([0-8]?\\d|[0-8]?\\d\\.\\d{1,7}|90|90\\.0{1,7})";
    private static final double EARTH_RADIUS = 637_1393.00D;
    private static final double RADIAN = Math.PI / 180.00D;
    private static final double HALF = 0.5D;

    /**
     * @param point1 坐标点1
     * @param point2 坐标点2
     * @return 返回double 类型的距离，向上进行四舍五入，距离精确到厘米，单位为米
     */
    public static double metreDistance(Point point1, Point point2) {
        double lat1 = point1.x();
        double lon1 = point1.y();
        double lat2 = point2.x();
        double lon2 = point2.y();
        double x, y, a, b, distance;

        lat1 *= RADIAN;
        lat2 *= RADIAN;
        x = lat1 - lat2;
        y = lon1 - lon2;
        y *= RADIAN;
        a = Math.sin(x * HALF);
        b = Math.sin(y * HALF);
        distance = EARTH_RADIUS * Math.asin(Math.sqrt(a * a + Math.cos(lat1) * Math.cos(lat2) * b * b)) / HALF;

        return new BigDecimal(distance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @param point1 坐标点1
     * @param point2 坐标点2
     * @return 返回double 类型的距离，向上进行四舍五入，距离精确到米，单位为公里
     */
    public static double kilometreDistance(Point point1, Point point2) {
        double lat1 = point1.x();
        double lon1 = point1.y();
        double lat2 = point2.x();
        double lon2 = point2.y();
        double x, y, a, b, distance;

        lat1 *= RADIAN;
        lat2 *= RADIAN;
        x = lat1 - lat2;
        y = lon1 - lon2;
        y *= RADIAN;
        a = Math.sin(x * HALF);
        b = Math.sin(y * HALF);
        distance = EARTH_RADIUS * Math.asin(Math.sqrt(a * a + Math.cos(lat1) * Math.cos(lat2) * b * b)) / HALF / 1000;

        return new BigDecimal(distance).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static boolean checkCoordinate(String longitude, String latitude) {
        boolean longitudeFlag = false;
        boolean latitudeFlag = false;
        try {
            if (Pattern.matches(LAT_MATCH, latitude)) {
                latitudeFlag = true;
            }
            if (Pattern.matches(LON_MATCH, longitude)) {
                longitudeFlag = true;
            }
            Double.parseDouble(longitude);
            Double.parseDouble(latitude);
            if (longitudeFlag && latitudeFlag) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
//		Point point1 = new CoordinatePoint(39.941037, 116.434027);
//		Point point2 = new CoordinatePoint(39.941564, 116.461665);
//		System.out.println(kilometreDistance(point1, point2));

        System.out.println(checkCoordinate( "116.434027","39.941037"));
    }

}
