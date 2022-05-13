package com.yhl.ros.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName: FileUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:17
 */
public class FileUtils {

	public static File getFile(String fileName) {
		try {
			String path = fileName;
			File file = new File(path);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file
	 * @return
	 */
	public static String readerFile(File file) {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String readLine = br.readLine();
			return readLine;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != br)
					br.close();
				if (null != fr)
					fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 写入文件并覆盖原内容
	 * 
	 * @param str
	 * @param file
	 */
	public static void writeFile(String str, File file) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file, false);
			bw = new BufferedWriter(fw);
			bw.write(str);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != bw)
					bw.close();
				if (null != fw)
					fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除文件或者文件夹
	 * 
	 * @param file
	 * @return
	 */
	public static boolean delFile(File file) {
		if (!file.exists()) {
			return false;
		}

		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				delFile(f);
			}
		}
		return file.delete();
	}

	/**
	 * 删除文件或者文件夹（排除需要过滤的）
	 * 
	 * @param file
	 *            要删除的文件夹、文件
	 * @param filePath
	 *            需要过滤的 文件夹、文件
	 * @return
	 */
	public static boolean delWithOutFile(File file, File filePath) {
		if (!file.exists() || !filePath.exists()) {
			return false;
		}

		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (!f.getAbsolutePath().equals(filePath.getAbsolutePath())) {
					delFile(f);
				}
			}
		}
		return file.delete();
	}

	public static void main(String[] args) {
		File file = new File("C:\\Users\\lenovo\\Desktop\\rosteringValidator");
		String name = "C:\\Users\\lenovo\\Desktop\\rosteringValidator\\2019-03-08";
		delWithOutFile(file, new File(name));
	}
}
