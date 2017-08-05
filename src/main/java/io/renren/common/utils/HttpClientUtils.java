package io.renren.common.utils;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * HttpClient4.3工具类
 * 
 * @author 
 * @date 
 *
 */
public class HttpClientUtils {
	private static Logger logger = LoggerFactory
			.getLogger(HttpClientUtils.class); // 日志记录

	
	/**
	 * post请求传输json参数
	 * 
	 * @param url
	 *            url地址
	 *            参数
	 * @return
	 */
	public static JSONObject httpPost(String url, JSONObject jsonParam) {
		// post请求返回结果
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject jsonResult = null;
		HttpPost httpPost = new HttpPost(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);
		try {
			if (null != jsonParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonParam.toString(),
						"utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				httpPost.setEntity(entity);
			}
			CloseableHttpResponse result = httpClient.execute(httpPost);
			//请求发送成功，并得到响应
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					//读取服务器返回过来的json字符串数据 
					str = EntityUtils.toString(result.getEntity(), "utf-8");
					logger.info("post请求提交成功,请求地址：{} 返回信息:{}" ,url, str);
					//把json字符串转换成json对象 
					jsonResult = JSONObject.parseObject(str);
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		} finally {
			httpPost.releaseConnection();
		}
		return jsonResult;
	}
	
	
	/**
	 * post请求传输String参数
	 * 例如：name=Jack&sex=1&type=2
	 * Content-type:application/x-www-form-urlencoded
	 * @param url
	 *            url地址
	 * @param strParam
	 *            参数
	 *            不需要返回结果
	 * @return
	 */
	public static JSONObject httpPost(String url, String strParam) {
		// post请求返回结果
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject jsonResult = null;
		HttpPost httpPost = new HttpPost(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);
		try {
			if (null != strParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(strParam,"utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/x-www-form-urlencoded");
				httpPost.setEntity(entity);
			}
			CloseableHttpResponse result = httpClient.execute(httpPost);
			//请求发送成功，并得到响应
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str = "";
				try {
					//读取服务器返回过来的json字符串数据
					str = EntityUtils.toString(result.getEntity(), "utf-8");
					//把json字符串转换成json对象
					jsonResult = JSONObject.parseObject(str);
				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		} finally {
			httpPost.releaseConnection();
		}
		return jsonResult;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static JSONObject httpGet(String url) {
		// get请求返回结果
		JSONObject jsonResult = null;
		CloseableHttpClient client = HttpClients.createDefault();
		// 发送get请求
		HttpGet request = new HttpGet(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		request.setConfig(requestConfig);
		try {
			CloseableHttpResponse response = client.execute(request);

			//请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//读取服务器返回过来的json字符串数据
				HttpEntity entity = response.getEntity();
				String strResult = EntityUtils.toString(entity, "utf-8");
				//把json字符串转换成json对象
				jsonResult = JSONObject.parseObject(strResult);
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		} finally {
			request.releaseConnection();
		}
		return jsonResult;
	}
	/**
	 * 发送get请求
	 *
	 * @param url
	 *            路径
	 * @return
	 */
	public static String httpGet2(String url) {
		// get请求返回结果
		CloseableHttpClient client = HttpClients.createDefault();
		// 发送get请求
		HttpGet request = new HttpGet(url);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		request.setConfig(requestConfig);
		try {
			CloseableHttpResponse response = client.execute(request);

			//请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				//读取服务器返回过来的json字符串数据
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, "utf-8");
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		} finally {
			request.releaseConnection();
		}
		return null;
	}



	/**
	 * 中转文件
	 *
	 * @param file
	 *            上传的文件
	 * @return 响应结果
	 */
	public static String httpClientUploadFile(String serverPath, MultipartFile file) {
		final String remote_url = serverPath;// 第三方服务器请求地址
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = "";
		try {
			String fileName = file.getOriginalFilename();
			HttpPost httpPost = new HttpPost(remote_url);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
			builder.addTextBody("filename", fileName);// 类似浏览器表单提交，对应input的name和value
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);// 执行提交
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				// 将响应内容转换为字符串
				result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static JSONObject doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return JSONObject.parseObject(resultString);
	}
}