package com.yhl.ros.common.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * @ClassName: CSVUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: CSV操作工具类
 * @Date: 2020-02-12 01:07
 */
public class CSVUtils {

	public static void exportByList(String[] headers, List<List<String>> dataList) throws FileNotFoundException {
		FileOutputStream fileos = new FileOutputStream("E:/abc.csv");
		exportByList(headers, dataList, fileos);
	}

	public static void exportByList(String[] headers, List<List<String>> dataList, OutputStream os) {
		OutputStreamWriter osw = null;
		CSVFormat csvFormat = null;
		CSVPrinter csvPrinter = null;
		try {
			osw = new OutputStreamWriter(os, "GBK");// 如果是UTF-8时，WPS打开是正常显示，而微软的excel打开是乱码,
			csvFormat = CSVFormat.DEFAULT.withHeader(headers);
			csvPrinter = new CSVPrinter(osw, csvFormat);
			for (int i = 0; i < dataList.size(); i++) {
				List<String> values = dataList.get(i);
				csvPrinter.printRecord(values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(os, csvPrinter);
		}
	}

	public static void exportByLinked(String[] headers, List<LinkedHashMap<String, Object>> dataList)
			throws FileNotFoundException {
		FileOutputStream fileos = new FileOutputStream("E:/abc.csv");
		exportByLinked(headers, dataList, fileos);
	}

	public static void exportByLinked(String[] headers, List<LinkedHashMap<String, Object>> dataList, OutputStream os) {
		OutputStreamWriter osw = null;
		CSVFormat csvFormat = null;
		CSVPrinter csvPrinter = null;
		try {

			osw = new OutputStreamWriter(os, "GBK");
			csvFormat = CSVFormat.DEFAULT.withHeader(headers);
			csvPrinter = new CSVPrinter(osw, csvFormat);

			for (int i = 0; i < dataList.size(); i++) {
				List<String> values = new ArrayList<String>();
				LinkedHashMap<String, Object> rowHashMap = dataList.get(i);
				Set<String> keys = rowHashMap.keySet();
				for (String key : keys) {
					values.add(String.valueOf(rowHashMap.get(key)));
				}
				csvPrinter.printRecord(values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(os, csvPrinter);
		}
	}

	private static void close(OutputStream os, CSVPrinter csvPrinter) {
		if (csvPrinter != null) {
			try {
				csvPrinter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				csvPrinter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (os != null) {
			try {
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
