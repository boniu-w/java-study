package com.yhl.ros.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.poifs.filesystem.FileMagic;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ExcelUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:16
 */
@Slf4j
public final class ExcelUtils {

	@SuppressWarnings("unchecked")
	public static <T> List<T> readExcel(InputStream inputStream, Class<T> clazz, int headRowNumber) {
		if (null == inputStream) {
			log.error("InputStream can not be null!");
			return new ArrayList<T>();
		}
		SyncReadListener listener = new SyncReadListener();
		ReadSheet readSheet = new ReadSheet();
		readSheet.setClazz(clazz);
		readSheet.setHeadRowNumber(headRowNumber);
		ExcelReader reader = EasyExcel.read(inputStream).registerReadListener(listener).build();
		reader.read(readSheet);
		reader.finish();
		return (List<T>) listener.getList();
	}
	
	@SuppressWarnings("deprecation")
	public static List<Object> readBySAX(InputStream inputStream, int sheetNumber, int headRowNumber) {
		if (null == inputStream) {
			log.error("InputStream can not be null!");
			return new ArrayList<Object>();
		}
		Sheet sheet = new Sheet(sheetNumber, headRowNumber);
		SyncReadListener excelListener = new SyncReadListener();
        EasyExcelFactory.readBySax(inputStream, sheet, excelListener);
        return excelListener.getList();
	}
	
	public static <T> void writeExcelWithSimpleHead(OutputStream outputStream, List<T> data, Class<T> clazz,
			List<String> simpleHead) {
		WriteSheet writeSheet = new WriteSheet();
		writeSheet.setSheetNo(0);
		// writeSheet.setSheetName(DateUtils.dateToString(new Date(),
		// DateUtils.COMMON_DATE_STR5));
		writeSheet.setClazz(clazz);
		writeSheet.setNeedHead(true);
		List<List<String>> head = simpleHead.stream().map(item -> Arrays.asList(item)).collect(Collectors.toList());
		writeSheet.setHead(head);

		ExcelWriter writer = EasyExcel.write(outputStream).build();
		writer.write(data, writeSheet);
		writer.finish();
	}

	public static ExcelTypeEnum suffixOfExcel(InputStream inputStream) {
		try {
			FileMagic fileMagic = FileMagic.valueOf(inputStream);
			if (FileMagic.OLE2.equals(fileMagic)) {
				return ExcelTypeEnum.XLS;
			}
			if (FileMagic.OOXML.equals(fileMagic)) {
				return ExcelTypeEnum.XLSX;
			}
			throw new IllegalArgumentException("Unrecongnized file type");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
