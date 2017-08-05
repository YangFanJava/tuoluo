package io.renren.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.vo.WeiXinUserList;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

public class WeixinUtil {
//	private final static String WECHAT_USER_INFO_URL = "ttps://api.weixin.qq.com/cgi-bin/user/info"; //?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN

	private static Logger _log = LoggerFactory.getLogger(WeixinUtil.class); // 日志记录
//	private final static String WECHAT_USER_GET_URL = "https://api.weixin.qq.com/cgi-bin/user/get"; //?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
	private final static String WECHAT_USER_GET_URL = "https://newyfb.5ifapiao.com/cgi-bin/user/get"; //s?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
//	public final static String qrcode_create_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";


	//二维码接口地址2.24
	public static String qrcode_create_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

	//二维码生成地址2.24
	public static String qrcode_show_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";


//	public final static String USER_GET_URL = "http://wxyfb.5ifapiao.com/wxservice/api/getUserInfoWithOpenId";//调用我的发票获取用户信息
	/**
	 * 获取微信用户基本信息
	 * @param accessToken
	 * @return
	 */
	/*public WeiXinUserInfo getUserInfo(String accessToken, String openid){
	    WeiXinUserInfo weixinUserInfo = null;
	    Map<String,String> map = new TreeMap<String,String>();
	    map.put("access_token", accessToken);
	    map.put("openid", openid);
	    String result = HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.GET_METHOD, WECHAT_USER_INFO_URL, map, "");
	    try {
	        weixinUserInfo = new Gson().fromJson(result, WeiXinUserInfo.class);
	    } catch (JsonSyntaxException e) {
	        weixinUserInfo = null;
	    }
	    return weixinUserInfo;

	}*/

	
	public List<String> findWeiXinUserList(List<String> openidList, String accessToken, String nextOpenid){
	    WeiXinUserList weixinUserList = null;
	    StringBuffer sb = new StringBuffer(WECHAT_USER_GET_URL);
	    sb.append("?access_token=").append(accessToken);

	    if(StringUtils.isNotBlank(nextOpenid)){
			sb.append("&next_openid=").append(nextOpenid);
	    }

		String result = HttpClientUtils.httpGet2(WECHAT_USER_GET_URL);


		_log.info("HTTP数据返回结果: {}",result);
		if(result != null){
	        try {  
	            weixinUserList = new Gson().fromJson(result, WeiXinUserList.class);  
	            openidList = new ArrayList<String>();  
	            if(weixinUserList.getCount() <= 10000 && weixinUserList.getCount() >0){  
	                openidList.addAll(weixinUserList.getData().getOpenid());  
	            }else{  
	                //如果大于10000的,继续查询
					openidList.addAll(weixinUserList.getData().getOpenid());
					findWeiXinUserList(openidList, accessToken, weixinUserList.getNext_openid());
	            }
	        } catch (JsonSyntaxException e) {
	            openidList = null;  
	        }  
	    }  
	    return openidList;  
	}  

	public WeiXinUserList findWeiXinUserListNoLoop(String accessToken,String nextOpenid){
	    WeiXinUserList weixinUserList = null;
	    StringBuffer sb = new StringBuffer(WECHAT_USER_GET_URL);
	    sb.append("?access_token=").append(accessToken);

	    if(StringUtils.isNotBlank(nextOpenid)){
			sb.append("&next_openid=").append(nextOpenid);
	    }

		String result = HttpClientUtils.httpGet2(sb.toString());
		_log.info("HTTP数据返回结果: {}",result);
		if(StringUtils.isNotBlank(result)){
			weixinUserList = new Gson().fromJson(result, WeiXinUserList.class);
	    }
	    return weixinUserList;
	}

	/**
	 * *创建临时二维码ticket2.27
	 * */
	public static JSONObject getQrScene(String url, int expire_seconds, int scene_id){
		JSONObject j=new JSONObject();
		j.put("expire_seconds", expire_seconds);
		j.put("action_name", "QR_SCENE");
		JSONObject scene=new JSONObject();
		scene.put("scene_id", scene_id);
		j.put("action_info",scene);
		//JSONObject job= WeixinUtil.httpRequest(url, "POST", j.toString());
		return HttpClientUtils.doPostJson(url,j.toString());

		//return job;
	}


	/**
	 * 创建永久性二维码ticket2.27
	 * @param scene_id 自行设定的参数
	 * @return
	 */
	public static JSONObject getQrLimitScene(String url,int scene_id){
		JSONObject j=new JSONObject();
		JSONObject action_info=new JSONObject();
		JSONObject scene=new JSONObject();
		scene.put("scene_str", scene_id);
		action_info.put("scene", scene);
		j.put("action_info",action_info);
		j.put("action_name", "QR_LIMIT_STR_SCENE");
//		JSONObject job=WeixinUtil.httpRequest(url, "POST",j.toString());
////		System.out.println("创建永久性二维码ticket："+job);
//		return job;
		return HttpClientUtils.doPostJson(url,j.toString());
	}

	/**
	 * 根据ticket换取二维码2.27
	 * @param ticket 二维码ticket
	 * @param req 当前请求request
	 * @param postfix 二维码后缀名称
	 *
	 * @return 二维码保存路径
	 */
	public static String getQRCode(String ticket, HttpServletRequest req, String postfix) {
		//HTTP GET请求（请使用https协议）TICKET记得进行UrlEncode,ticket正确情况下，http 返回码是200，是一张图片，可以直接展示或者下载。
		String requestUrl = "";
		String filePath = null;
		//String qrcodePath  = "";
		String savePath = req.getSession().getServletContext().getRealPath("/") + "upload/qrCodeFile" ;// 文件的硬盘真实路径
		_log.info("获取二维码保存路径："+savePath);
		File savePathFile = new File(savePath);
		if (!savePathFile.exists()) {
			savePathFile.mkdirs();// 创建文件时间s子目录
		}
		try {
			//编码ticket后的获取二维码请求路径
			requestUrl = WeixinUtil.qrcode_show_url.replace("TICKET", java.net
					.URLEncoder.encode(ticket, "UTF-8"));
			_log.info("获取二维码请求路径（包含ticket）："+requestUrl);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			URL url = new URL(requestUrl);
			//建立新的get请求链接
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			filePath = savePath +postfix + ".jpg";
			//qrcodePath = ResourceUtil.getConfigByName("qrcodeUrl") + timeString+ ".jpg";
			//保存图片到服务器路径
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));

			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);

			fos.close();
			bis.close();

			conn.disconnect();
			_log.info("根据ticket换取二维码成功，二维码存放在：" + filePath);
		} catch (Exception e) {
			e.printStackTrace();
			filePath = null;
			_log.info("根据ticket换取二维码失败");
		}
		return "upload/qrCodeFile/"+postfix + ".jpg";
	}
}
