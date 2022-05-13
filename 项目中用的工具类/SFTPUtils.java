package com.yhl.ros.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: SFTPUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:20
 */
@Slf4j
public class SFTPUtils {

	private static ChannelSftp sftp;

	private static SFTPUtils instance = null;

	private SFTPUtils() {
	}

	public static SFTPUtils getInstance(String host, int port, String username, String password) {
		/*
		 * if (instance == null) { synchronized (SFTPUtils.class) { instance = new
		 * SFTPUtils(); sftp = instance.connect(host, port, username, password); //获取连接
		 * } }
		 */
		instance = new SFTPUtils();
		sftp = instance.connect(host, port, username, password); // 获取连接
		return instance;
	}
	
	public static void main(String[] args) {
		SFTPUtils instance = getInstance("10.7.100.17", 22, "youhl", "youhl");
		instance.upload("/home/youhl/FTP_File/RouteFee/active", "C:/Users/xianh/Desktop/shuhai_test_order_valid_no.sql");
	}
	
	/**
	 * 连接sftp服务器
	 *
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public ChannelSftp connect(String host, int port, String username, String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			log.info("SFTP Session connected.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			log.info("Connected to " + host);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return sftp;
	}

	/**
	 * 上传文件
	 *
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 */
	public boolean upload(String directory, String uploadFile) {
		try {
			createDir(directory, sftp);
			sftp.cd(directory);
			File file = new File(uploadFile);
			FileInputStream fileInputStream = new FileInputStream(file);
			sftp.put(fileInputStream, file.getName());
			fileInputStream.close();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	public void createDir(String createpath, ChannelSftp sftp) {
		try {
			if (isDirExist(createpath)) {
				sftp.cd(createpath);
				return;
			}
			String pathArry[] = createpath.split("/");
			StringBuffer filePath = new StringBuffer("/");
			for (String path : pathArry) {
				if (path.equals("")) {
					continue;
				}
				filePath.append(path + "/");
				if (isDirExist(filePath.toString())) {
					sftp.cd(filePath.toString());
				} else {
					// 建立目录
					sftp.mkdir(filePath.toString());
					// 进入并设置为当前目录
					sftp.cd(filePath.toString());
				}
			}
			sftp.cd(createpath);
		} catch (SftpException e) {
			throw new RuntimeException("创建路径错误：" + createpath);
		}
	}

	public boolean isDirExist(String directory) {
		boolean isDirExistFlag = false;
		try {
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = true;
			return sftpATTRS.isDir();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExistFlag = false;
			}
		}
		return isDirExistFlag;
	}

	/**
	 * 上传文件
	 *
	 * @param directory
	 *            上传的目录
	 * @param file
	 *            要上传的文件
	 */
	public boolean upload(String directory, InputStream inputStream, String fileName) {
		try {
			if (inputStream == null) {
				return false;
			}
			createDir(directory, sftp);
			sftp.cd(directory);
			sftp.put(inputStream, fileName);
			inputStream.close();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 下载文件
	 *
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 */
	public File download(String directory, String downloadFile, String saveFile) {
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			sftp.get(downloadFile, fileOutputStream);
			fileOutputStream.close();
			return file;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param directory 文件所在目录
	 * @param downloadFile 需要下载的文件
	 * @return
	 */
	public InputStream getFromSFTP(String directory, String downloadFile) {
		try {
			sftp.cd(directory);
			InputStream inputStream = sftp.get(downloadFile);
			return inputStream;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 下载文件
	 *
	 * @param downloadFilePath
	 *            下载的文件完整目录
	 * @param saveFile
	 *            存在本地的路径
	 */
	public File download(String downloadFilePath, String saveFile) {
		try {
			int i = downloadFilePath.lastIndexOf('/');
			if (i == -1)
				return null;
			sftp.cd(downloadFilePath.substring(0, i));
			File file = new File(saveFile);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			sftp.get(downloadFilePath.substring(i + 1), fileOutputStream);
			fileOutputStream.close();
			return file;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 删除文件
	 *
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 */
	public void delete(String directory, String deleteFile) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void disconnect() {
		try {
			sftp.getSession().disconnect();
		} catch (JSchException e) {
			log.error("JSchException", e);
		}
		// sftp.quit();
		sftp.disconnect();
	}

	/**
	 * 列出目录下的文件
	 *
	 * @param directory
	 *            要列出的目录
	 * @throws SftpException
	 */
	@SuppressWarnings("unchecked")
	public Vector<LsEntry> listFiles(String directory) {
		Vector<LsEntry> result = new Vector<LsEntry>();
		try {
			result = sftp.ls(directory);
		} catch (SftpException e) {
//			log.error("list files exception: {}", e);
		}
		return result;
	}

}
