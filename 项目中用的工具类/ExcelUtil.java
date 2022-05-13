package com.yhl.ros.common.utils;

import java.text.DecimalFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @ClassName: ExcelUtil
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:16
 */
public class ExcelUtil {

	// @描述：是否是2003的excel，返回true是2003
	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	// @描述：是否是2007的excel，返回true是2007
	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	public static String getCellString(Cell cell) {
		String cellValue = "";
		DecimalFormat df = new DecimalFormat("#.##");
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		case NUMERIC:

			/*
			 * short format = cell.getCellStyle().getDataFormat();
			 * 
			 * 14 yyyy-MM-dd / 2017/01/01 31 yyyy年m月d日
			 * 
			 * Date date = null; if(format == 14 || format == 31){ date =
			 * HSSFDateUtil.getJavaDate(cell.getNumericCellValue()); } if(date == null) {
			 * cellValue = df.format(cell.getNumericCellValue()).toString(); }else {
			 * cellValue = CommonDateUtils.dateToString(date); }
			 */

			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 用于转化为日期格式
				Date date = cell.getDateCellValue();
				cellValue = CommonDateUtils.dateToString(date);
			} else {
				cellValue = df.format(cell.getNumericCellValue()).toString();
			}

			break;
		case BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case FORMULA:
			cellValue = cell.getCellFormula();
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}

}