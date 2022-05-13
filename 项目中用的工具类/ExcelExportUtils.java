package com.yhl.ros.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @ClassName: ExcelExportUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:10
 */
public class ExcelExportUtils {
	private static String SHEET_NAME = "sheet";
	private WritableSheet sheet = null;
	private WritableWorkbook workBook = null;
	private int row;
	private static final Log LOG = LogFactory.getLog(ExcelExportUtils.class);

	public ExcelExportUtils() {
	}

	public ExcelExportUtils(OutputStream output) {
		try {
			workBook = Workbook.createWorkbook(output);
			sheet = workBook.createSheet(SHEET_NAME, 0);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}

	public void closeBook() {
		try {
			workBook.write();
			workBook.close();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

	/**
	 * 写入excel文件
	 * 
	 * @param out
	 *            输出流
	 * @param labelNames
	 *            列名
	 * @param fields
	 *            对象属性
	 * @param values
	 *            对象
	 */
	public void writeExcel(ExcelTitle title, List<ExcelLabel> labels, String[] fields, List<? extends Object> values) {
		try {
			if (title != null) {
				createTitle(sheet, title, getMaxColspan(labels));
			}
			if (!CollectionUtils.isEmpty(labels)) {
				createLables(sheet, labels);
			}

			int rows = getMaxRow(labels);
			for (Object object : values) {
				++rows;
				for (int i = 0; i < fields.length; i++) {
					WritableCell cell = getCell(i, rows, object, fields[i]);
					if (cell != null) {
						sheet.addCell(cell);
					}
				}
			}
			this.row = rows + 2;
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * 创建标题
	 * 
	 * @param sheet
	 * @param labels
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void createLables(WritableSheet sheet, List<ExcelLabel> labels)
			throws RowsExceededException, WriteException {
		for (int i = 0; i < labels.size(); i++) {
			ExcelLabel label = labels.get(i);
			sheet.addCell(
					new Label(label.getColum() - 1, this.row + label.getRow(), label.getLabelName(), getLabelFormat()));
			sheet.mergeCells(label.getColum() - 1, this.row + label.getRow(),
					label.getColum() - 1 + (label.getColspan() - 1),
					this.row + label.getRow() + (label.getRowspan() - 1));
			sheet.setColumnView(label.getColum() - 1, 15);

		}

	}

	/**
	 * 标题样式
	 * 
	 * @return
	 * @throws WriteException
	 */
	private CellFormat getLabelFormat() throws WriteException {
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false);
		WritableCellFormat wcfF = new WritableCellFormat(wf);
		wcfF.setAlignment(Alignment.CENTRE);
		wcfF.setBorder(Border.ALL, BorderLineStyle.THIN);
		return wcfF;
	}

	/**
	 * 获取EXCEL单元格
	 * 
	 * @param column
	 *            列
	 * @param row
	 *            行
	 * @param object
	 *            对象
	 * @param fieldName
	 *            对象属性
	 * @return
	 */
	private WritableCell getCell(int column, int row, Object object, String fieldName) {
		try {
			Field field = null;
			try {
				field = object.getClass().getDeclaredField(fieldName);
			} catch (Exception e) {
				field = object.getClass().getField(fieldName);
			}
			if (field == null) {
				LOG.warn("field 不存在");
				return null;
			}

			BeanWrapper bw = new BeanWrapperImpl(object);
			Object value = bw.getPropertyValue(fieldName);
			// return new Label(column, row, value.toString());
			// String value = BeanUtils.getProperty(object, fieldName);
			String fieldType = field.getType().getName();
			if (value != null) {
				if (fieldType.equalsIgnoreCase(Integer.class.getName()) || fieldType.equalsIgnoreCase("int")
						|| fieldType.equalsIgnoreCase(Short.class.getName())
						|| fieldType.equalsIgnoreCase(Long.class.getName())
						|| fieldType.equalsIgnoreCase(Float.class.getName())
						|| fieldType.equalsIgnoreCase(Double.class.getName())
						|| fieldType.equalsIgnoreCase(Byte.class.getName())) {
					return new Number(column, row, Double.parseDouble(value.toString()), getContentFormat());
				} else if (fieldType.equalsIgnoreCase("java.util.Date")) {
					// Date dateValue = (Date)field.get(object);
					// return null;
					return new Label(column, row, CommonDateUtils.dateToString((Date) value), getContentFormat());
				} else {
					return new Label(column, row, value.toString(), getContentFormat());
				}
			}
			// 为了保证数据为空时，表格样式一致
			return new Label(column, row, null, getContentFormat());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 内容样式
	 * 
	 * @return
	 * @throws WriteException
	 */
	private CellFormat getContentFormat() throws WriteException {
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 10);
		WritableCellFormat wcfF = new WritableCellFormat(wf);
		wcfF.setBorder(Border.ALL, BorderLineStyle.THIN);
		return wcfF;
	}

	/**
	 * 计算最大列宽
	 * 
	 * @param labels
	 * @return
	 */
	private int getMaxColspan(List<ExcelLabel> labels) {
		ExcelLabel maxColLabel = null;
		for (ExcelLabel excelLabel : labels) {
			if (maxColLabel == null) {
				maxColLabel = excelLabel;
			} else {
				if (maxColLabel.getColum() < excelLabel.getColum()) {
					maxColLabel = excelLabel;
				}
			}
		}
		if (null != maxColLabel) {
			return maxColLabel.getColum() + maxColLabel.getColspan() - 1;
		}
		return 0;
	}

	/**
	 * 创建标题
	 * 
	 * @param sheet
	 * @param title
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	private void createTitle(WritableSheet sheet, ExcelTitle title, int clospan)
			throws RowsExceededException, WriteException {
		sheet.addCell(new Label(0, this.row, title.getTitle(), getTitleFormat()));
		sheet.mergeCells(0, this.row, clospan - 1, this.row);
		sheet.setRowView(row, 500);// 行高
	}

	/**
	 * 获取标题格式
	 * 
	 * @return
	 * @throws WriteException
	 */
	private CellFormat getTitleFormat() throws WriteException {
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD, false);
		WritableCellFormat wcfF = new WritableCellFormat(wf);
		wcfF.setAlignment(Alignment.CENTRE);
		wcfF.setBorder(Border.ALL, BorderLineStyle.THIN);
		return wcfF;
	}

	/**
	 * 获取label占用几行
	 * 
	 * @param labels
	 * @return
	 */
	private int getMaxRow(List<ExcelLabel> labels) {
		int row = 0;
		for (ExcelLabel excelLabel : labels) {
			if (excelLabel.getRow() > row) {
				row = excelLabel.getRow();
			}
		}
		return row + this.row;
	}

	public void createSheet(String sheetName, int sort) {
		this.row = 0;
		sheet = workBook.createSheet(sheetName, sort);
	}

	public void setSheet(WritableSheet sheet) {
		this.sheet = sheet;
	}

	public WritableWorkbook getWorkBook() {
		return workBook;
	}

	public static void main(String[] args) throws FileNotFoundException {

	}

}
