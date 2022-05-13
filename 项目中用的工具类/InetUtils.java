package com.yhl.ros.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: InetUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:18
 */
@Slf4j
public class InetUtils {

	public static HttpServletRequest httpRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	public static String remoteHost() {
		String remoteHost = null;
		HttpServletRequest request = httpRequest();
		if (request != null) {
			remoteHost = request.getRemoteHost();
		}
		return remoteHost;
	}

	public static String localHost() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.info(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
