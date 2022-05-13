package com.yhl.ros.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	private HttpUtils() {
	}

	public static String sendRequest(URI uri) throws IOException {
		return sendRequest(uri, null);
	}
	public static String sendPostRequest(URI uri, String json) throws IOException {
		HttpPost httpRequest = new HttpPost(uri);
		httpRequest.addHeader("Content-type","application/json; charset=utf-8");

		httpRequest.setHeader("Accept", "application/json");

		httpRequest.setEntity(new StringEntity(json, Charset.forName("UTF-8")));

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(40000).setConnectionRequestTimeout(30000)
				.build();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		if (httpRequest instanceof HttpPost) {
			HttpEntity requestEntity = EntityBuilder.create().setText(json).build();
			((HttpPost) httpRequest).setEntity(requestEntity);
		}
		String responseStr;
		try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
			HttpEntity responseEntity = httpResponse.getEntity();
			responseStr = EntityUtils.toString(responseEntity);
		}

		return responseStr;

	}
	public static String sendRequest(URI uri, String json) throws IOException {
		HttpRequestBase httpRequest = (json == null || json.isEmpty()) ? new HttpGet(uri) : new HttpPost(uri);

//		httpRequest.addHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
		httpRequest.addHeader(HTTP.CONTENT_ENCODING, "UTF-8");
		httpRequest.addHeader("Content-type","application/json; charset=utf-8");
		httpRequest.setHeader("Accept", "application/json");

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(40000).setConnectionRequestTimeout(30000)
				.build();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		if (httpRequest instanceof HttpPost) {
			HttpEntity requestEntity = EntityBuilder.create().setText(json).build();
			((HttpPost) httpRequest).setEntity(requestEntity);
		}
		String responseStr;
		try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
			HttpEntity responseEntity = httpResponse.getEntity();
			responseStr = EntityUtils.toString(responseEntity);
		}

		return responseStr;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty(HTTP.CONTENT_ENCODING, "UTF-8");
			conn.setRequestProperty("Content-type","application/json; charset=utf-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

			// 发送请求参数
			out.write(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}

}
