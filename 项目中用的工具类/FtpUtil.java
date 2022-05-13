package com.yhl.ros.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: FtpUtil
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:17
 */
@Slf4j
public class FtpUtil {

	/**
	 * 获取FTPClient对象
	 *
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
	public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost);// 连接FTP服务器
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				log.warn("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				log.info("FTP连接成功。");
			}
		} catch (SocketException e) {
			log.error("FtpUtil.getFTPClient.SocketException", e);
		} catch (IOException e) {
			log.error("FtpUtil.getFTPClient.IOException", e);
		}
		return ftpClient;
	}

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param ftpHost
	 *            FTP服务器hostname
	 * @param ftpUserName
	 *            账号
	 * @param ftpPassword
	 *            密码
	 * @param ftpPort
	 *            端口
	 * @param ftpPath
	 *            FTP服务器中文件所在路径 格式： ftptest/aa
	 * @param fileName
	 *            ftp文件名称
	 * @param input
	 *            文件流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String ftpHost, String ftpUserName, String ftpPassword, String ftpPath,
			String fileName, InputStream input) {
		boolean success = false;
		FTPClient ftpClient = null;
		try {
			int reply;
			ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword);
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return success;
			}
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);

			ftpClient.storeFile(fileName, input);

			input.close();
			ftpClient.logout();
			success = true;
		} catch (IOException e) {
			log.error("FtpUtil.uploadFile.IOException", e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

}
