package com.yhl.ros.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils; 
import org.apache.commons.lang3.Validate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;

/**
 * @ClassName: EasyExcelUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:09
 */
public class EasyExcelUtils {

	/**
	 * 下载EXCEL文件2007版本
	 *
	 * @throws IOException
	 *             IO异常
	 */
	public static void exportExcel2007Format(EasyExcelParams excelParams) throws IOException {
		exportExcel(excelParams, ExcelTypeEnum.XLSX);
	}

	/**
	 * 下载EXCEL文件2003版本
	 *
	 * @throws IOException
	 *             IO异常
	 */
	public static void exportExcel2003Format(EasyExcelParams excelParams) throws IOException {
		exportExcel(excelParams, ExcelTypeEnum.XLS);
	}

	/**
	 * 根据参数和版本枚举导出excel文件
	 *
	 * @param excelParams
	 *            参数实体
	 * @param typeEnum
	 *            excel类型枚举
	 * @throws IOException
	 */
	private static void exportExcel(EasyExcelParams excelParams, ExcelTypeEnum typeEnum) throws IOException {
		Validate.isTrue(excelParams.isValid(), "easyExcel params is not valid");

		HttpServletResponse response = excelParams.getResponse();
		ServletOutputStream out = response.getOutputStream();
		ExcelWriter writer = new ExcelWriter(out, typeEnum, excelParams.isNeedHead());
		prepareResponds(response, excelParams.getExcelNameWithoutExt(), typeEnum);
		Sheet sheet1 = new Sheet(1, 0, excelParams.getDataModelClazz());
		if (StringUtils.isNotBlank(excelParams.getSheetName())) {
			sheet1.setSheetName(excelParams.getSheetName());
		}
		writer.write(excelParams.getData(), sheet1);
		writer.finish();
		out.flush();
	}

	/**
	 * 将文件输出到浏览器（导出文件）
	 * 
	 * @param response
	 *            响应
	 * @param fileName
	 *            文件名（不含拓展名）
	 * @param typeEnum
	 *            excel类型
	 */
	private static void prepareResponds(HttpServletResponse response, String fileName, ExcelTypeEnum typeEnum) {
		String fileName2Export = new String((fileName).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName2Export + typeEnum.getValue());
	}

	public static <E> List<E> readExcel1(Workbook workbook, Class<E> clazz)
			throws IllegalAccessException, InstantiationException {
		List<E> datas = new ArrayList<E>();
		// 得到工作簿开始解析数据
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
		if (sheet != null) {
			List<String> headers = new ArrayList<>();
			Row tableHead = sheet.getRow(0);
			int index = 0;
			while (true) {
				Cell cell = tableHead.getCell(index);
				if (cell == null) {
					break;
				}
				String value = cell.getStringCellValue();
				headers.add(value);
				index++;
			}
			String[] list = { "目的地", "订单号", "目的地址", "发货人", "收货人" };
			List<String> mustHeaders = Arrays.asList(list);
			if (!headers.containsAll(mustHeaders)) {
				return null;
			}
			int lastRowNum = sheet.getLastRowNum();
			for (int i = 1; i <= lastRowNum; i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;
				E data = clazz.newInstance();
				Field[] declaredFields = clazz.getDeclaredFields();
				for (Field fied : declaredFields) {
					ExcelProperty annotation = fied.getAnnotation(ExcelProperty.class);
					if (annotation == null) {
						continue;
					}
					String value = annotation.value()[0];
					for (int j = 0; j < headers.size(); j++) {
						String header = headers.get(j);
						if (header.equals(value)) {
							try {
								Class<?> type = fied.getType();
								fied.setAccessible(true);
								if (row.getCell(j) != null) {
									if (type.equals(String.class)) {
										if (row.getCell(j).getCellTypeEnum() == CellType.STRING) {
											fied.set(data, row.getCell(j).getStringCellValue());
										} else if (row.getCell(j).getCellTypeEnum() == CellType.NUMERIC) {
											fied.set(data, row.getCell(j).getNumericCellValue() + "");
										}
									} else if (type.equals(Double.class)) {
										fied.set(data, row.getCell(j).getNumericCellValue());
									} else if (type.equals(Date.class)) {
										try {
											fied.set(data, row.getCell(j).getDateCellValue());
										} catch (IllegalStateException ex) {
											fied.set(data, DateUtils.parseString(row.getCell(j).getStringCellValue(),
													"yyyy/MM/dd HH:mm"));
										}
									}
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
				datas.add(data);
			}
		}

		return datas;
	}
	
	public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }
	
	public static <E> List<E> readExcel(Workbook workbook, Class<E> clazz, Integer dataBeginRow)
			throws IllegalAccessException, InstantiationException {
		List<E> datas = new ArrayList<E>();
		// 得到工作簿开始解析数据
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
		if (sheet != null) {
			List<String> headers = new ArrayList<>();
			Row tableHead = sheet.getRow(0);
			int index = 0;
			while (true) {
				Cell cell = tableHead.getCell(index);
				if (cell == null) {
					break;
				}
				String value = cell.getStringCellValue();
				headers.add(value);
				index++;
			}
			int lastRowNum = sheet.getLastRowNum();
			for (int i = dataBeginRow; i <= lastRowNum; i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				Cell cell = row.getCell(0);
				if (cell == null) {
					continue;
				}
				cell.setCellType(CellType.STRING);
				if (StringUtils.isBlank(cell.getStringCellValue())) {
					continue;
				}
				E data = clazz.newInstance();
				List<Field> declaredFields = Lists.newArrayList(clazz.getDeclaredFields()).stream().sorted((t1,t2) -> {
					return t1.getName().compareTo(t2.getName());
				}).collect(Collectors.toList());
				Map<String, Double> loadCosts = new HashMap<>();
				for (Field field : declaredFields) {
					field.setAccessible(true);
					ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
					if (annotation == null) {
						field.set(data, loadCosts);
					} else {
						String value = annotation.value()[0];
						for (int j = 0; j < headers.size(); j++) {
							String header = headers.get(j);
							Cell _cell = row.getCell(j);
							if (null != _cell) {
								_cell.setCellType(CellType.STRING);
							}
							if (header.equals(value)) {
								try {
									Class<?> type = field.getType();
									if (_cell != null) {
										if (type.equals(String.class)) {
											field.set(data, _cell.getStringCellValue());
										} else if (type.equals(Double.class)) {
											field.set(data, Double.parseDouble(_cell.getStringCellValue()));
										} else if (type.equals(Long.class)) {
											field.set(data, Long.parseLong(_cell.getStringCellValue()));
										} else if (type.equals(Date.class)) {
											field.set(data, DateUtils.parseString(_cell.getStringCellValue(), "yyyyMMdd"));
										}
									}
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							} else {
								if (header.startsWith("KBETR")) {
									loadCosts.put(header, Double.parseDouble(_cell.getStringCellValue()));
								}
							}
						}
					}
				}
				datas.add(data);
			}
		}
		return datas;
	}
	
	public static void main(String[] args) {
		double xx = 22.0;
		long yy = 22;
		if (xx == yy) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
	}

	public static class EasyExcelParams {

		/**
		 * excel文件名（不带拓展名)
		 */
		private String excelNameWithoutExt;
		/**
		 * sheet名称
		 */
		private String sheetName;
		/**
		 * 是否需要表头
		 */
		private boolean needHead = true;
		/**
		 * 数据
		 */
		private List<? extends BaseRowModel> data;

		/**
		 * 数据模型类型
		 */
		private Class<? extends BaseRowModel> dataModelClazz;

		/**
		 * 响应
		 */
		private HttpServletResponse response;

		public EasyExcelParams() {
		}

		/**
		 * 检查不允许为空的属性
		 */
		public boolean isValid() {
			return ObjectUtils.allNotNull(excelNameWithoutExt, data, dataModelClazz, response);
		}

		public String getExcelNameWithoutExt() {
			return excelNameWithoutExt;
		}

		public void setExcelNameWithoutExt(String excelNameWithoutExt) {
			this.excelNameWithoutExt = excelNameWithoutExt;
		}

		public String getSheetName() {
			return sheetName;
		}

		public void setSheetName(String sheetName) {
			this.sheetName = sheetName;
		}

		public boolean isNeedHead() {
			return needHead;
		}

		public void setNeedHead(boolean needHead) {
			this.needHead = needHead;
		}

		public List<? extends BaseRowModel> getData() {
			return data;
		}

		public void setData(List<? extends BaseRowModel> data) {
			this.data = data;
		}

		public Class<? extends BaseRowModel> getDataModelClazz() {
			return dataModelClazz;
		}

		public void setDataModelClazz(Class<? extends BaseRowModel> class1) {
			this.dataModelClazz = class1;
		}

		public HttpServletResponse getResponse() {
			return response;
		}

		public void setResponse(HttpServletResponse response) {
			this.response = response;
		}
	}

}
