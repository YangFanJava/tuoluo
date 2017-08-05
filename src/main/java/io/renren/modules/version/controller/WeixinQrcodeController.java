package io.renren.modules.version.controller;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.*;
import io.renren.modules.version.entity.WeixinQrcodeEntity;
import io.renren.modules.version.service.WeixinQrcodeService;
import io.renren.modules.weixin.service.WxUserinfoService;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-31 11:14:45
 */
@RestController
@RequestMapping("weixinqrcode")
public class WeixinQrcodeController {

	private static Jedis _51FPJEDIS;
	private static Jedis WDFPJEDIS;

	private static Logger _log = LoggerFactory.getLogger(WeixinQrcodeController.class); // 日志记录
	static {
		_51FPJEDIS=getJedisByAccount_id("gh_5c48f34ff74f");
		WDFPJEDIS=getJedisByAccount_id("gh_7184f36eca2b");
	}

	private static Jedis getJedisByAccount_id(String account_id){

		_log.info("初始化Token与Url开始：参数:account_id{}",account_id);

		Jedis jedis=null;
		String  host = "";
		Integer port = 6379;
		String  password = "";
		Integer timeout = 5000;

		switch (account_id){
			//我的发票
			case "gh_7184f36eca2b":
				host = "172.17.164.173";
				port = 6379;
				password = "redis-dev";
				_log.info("我的发票初始化url");

				break;
			//51发票
			case "gh_5c48f34ff74f":
				host = "172.17.164.121";
				port = 6379;
				password = "redis-dev";
				_log.info("51发票初始化url");

				break;
		}

		try{
			JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port,timeout);
			if(!org.apache.commons.lang.StringUtils.isBlank(password)) {
				jedisShardInfo.setPassword(password);
			}
			jedis = jedisShardInfo.createResource();
//			String access_token = jedis.get("access_token");
//			accessToken = access_token;
		}catch (Exception e){
			_log.debug("初始化Token失败",e);
		}
		return jedis;
	}


	@Autowired
	private WeixinQrcodeService weixinQrcodeService;

	@Autowired
	private WxUserinfoService wxUserinfoService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("weixinqrcode:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<WeixinQrcodeEntity> weixinQrcodeList = weixinQrcodeService.queryList(query);
		int total = weixinQrcodeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(weixinQrcodeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("weixinqrcode:info")
	public R info(@PathVariable("id") String id){
		WeixinQrcodeEntity weixinQrcode = weixinQrcodeService.queryObject(id);

		return R.ok().put("weixinQrcode", weixinQrcode);
	}

	/**
	 * 分組
	 */
	@RequestMapping("/group")
	@RequiresPermissions("weixinqrcode:update")
	public R groupName(){
		List<String> groupNames = weixinQrcodeService.queryGroupName();
		return R.ok().put("groupNames", groupNames);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("weixinqrcode:save")
	public R save(@RequestBody WeixinQrcodeEntity weixinQrcode){
		if(!StringUtils.hasText(weixinQrcode.getId())){
			weixinQrcode.setId(UUID.randomUUID().toString().replaceAll("-",""));
		}
		if(weixinQrcode.getCreateDate()==null||weixinQrcode.getCreateDate().equals("")){
			weixinQrcode.setCreateDate(new Date());
		}
		weixinQrcodeService.save(weixinQrcode);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("weixinqrcode:update")
	public R update(@RequestBody WeixinQrcodeEntity weixinQrcode){
		weixinQrcodeService.update(weixinQrcode);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("weixinqrcode:delete")
	public R delete(@RequestBody String[] ids){
		weixinQrcodeService.deleteBatch(ids);
		
		return R.ok();
	}

	/**
	 * 生成二維碼
	 */
	@RequestMapping("/generateQRcode")
	@RequiresPermissions("weixinqrcode:generate")
	public R generateQRcode(@RequestBody WeixinQrcodeEntity[] weixinQrcodes,HttpServletRequest req){
	    //classpath
	    System.out.println(WeixinQrcodeController.class.getResource("/"));
		_log.info("\t================开始遍历需要生成二维码的实体=================\t");
		String message="";
		for (WeixinQrcodeEntity weixinQrcode:weixinQrcodes) {
			_log.info("\t=============开始生成实体："+weixinQrcode.toString()+" 所需ticket============\t");
			String token=null;

//			if("51发票".equals(weixinQrcode.getAccountId()))	{
//				weixinQrcode.setAccountId("gh_5c48f34ff74f");
//                token="aOJ4KLeZKFUaHVWwWrLhvlzHQBkLxhKsTn_mOiydSaND-gRKzSWNx7X98Y9sU-Xi3NJ3rXjFPpQXvjNNq5_YL4XvcRxx67Qa5oR0_AVYn7rGQwJoovFI4FWr4f54u9tJWBRjADAKPF";
//            }else if("我的发票".equals(weixinQrcode.getAccountId())){
//				weixinQrcode.setAccountId("gh_7184f36eca2b");
//                token="yQydtRBMevciyBhYJj9QzSflCBokj5cZP0lqRB_X-vfD7vdREZ-cgoaRf3I9ULWmdmvlSN9BnltlKpCv_3veq-akdlNB8WCah4mLhaDDVPhteJEuvHOvPKOFT0eNu7BlMQOjAAAYCA";
//			}
			if("51发票".equals(weixinQrcode.getAccountId())){
				//还原accountid
				weixinQrcode.setAccountId("gh_5c48f34ff74f");
				if(_51FPJEDIS!=null){
					token=_51FPJEDIS.get("access_token");
					_log.info("\t获取token，类型：51发票,token:"+token+"\t");
				}else{
					_51FPJEDIS=getJedisByAccount_id("gh_5c48f34ff74f");
					token=_51FPJEDIS.get("access_token");
					_log.info("\t获取token，类型：51发票,token:"+token+"\t");
				}
			}else if("我的发票".equals(weixinQrcode.getAccountId())){
				weixinQrcode.setAccountId("gh_7184f36eca2b");
				if(WDFPJEDIS!=null){
					token=WDFPJEDIS.get("access_token");
					_log.info("\t获取token，类型：我的发票,token:"+token+"\t");
				}else{
					WDFPJEDIS=getJedisByAccount_id("gh_7184f36eca2b");
					token=WDFPJEDIS.get("access_token");
					_log.info("\t获取token，类型：我的发票,token:"+token+"\t");
				}
			}
			String requestUrl = WeixinUtil.qrcode_create_url.replace("TOKEN",token);
			_log.info("\t生成请求url："+requestUrl+"\t");

			if("临时".equals(weixinQrcode.getActionName())){//临时
				weixinQrcode.setActionName("0");
				_log.info("\t此实体为临时二维码\t");
				JSONObject  jsonObject = WeixinUtil.getQrScene(requestUrl,weixinQrcode.getExpireSeconds(),weixinQrcode.getSceneId());
				if(jsonObject.containsKey("errcode")){
					_log.info("\t生成二维码失败\t");
                    message+=(weixinQrcode.getId()+"生成失败;");
				}else{
					//换取二维码
					String ticket = jsonObject.getString("ticket");
					_log.info("\t生成ticket："+ticket+"\t开始获取二维码");

                    String savepath="";
					if(StringUtils.hasText(ticket)){
                        String postfix=("gh_5c48f34ff74f".equals(weixinQrcode.getAccountId())?"51fp":"wdfp")+weixinQrcode.getSceneId();
                        savepath=WeixinUtil.getQRCode(ticket,req,postfix);
                        _log.info("\t生成二维码成功，生成路径："+savepath+"\t");
                    }else{
                        _log.info("\t生成的ticket为空\t");
                    }

					weixinQrcode.setTicket(ticket);
                    //请求路径
                    weixinQrcode.setQrcodeUrl(jsonObject.getString("url"));
                    //保存路径
                    weixinQrcode.setSavePath(savepath);
					//生成时间
					weixinQrcode.setGenerationTime(new Date());
					_log.info("===============二维码实体生成完毕，存入数据库："+weixinQrcode.toString()+" ==============");
					weixinQrcodeService.update(weixinQrcode);
				}
			}else if("永久".equals(weixinQrcode.getActionName())){//永久
				weixinQrcode.setActionName("1");
				_log.info("\t此实体为永久二维码\t");
				//此处为生成永久
				JSONObject  jsonObject = WeixinUtil.getQrLimitScene(requestUrl,weixinQrcode.getSceneId());
				if(jsonObject.containsKey("errcode")){
                    message+=(weixinQrcode.getId()+"生成失败;");
					_log.info("\t生成二维码失败\t");
				}else{
					//换取二维码
					String ticket = jsonObject.getString("ticket");

                    String savepath="";
                    if(StringUtils.hasText(ticket)){
                        String postfix=("gh_5c48f34ff74f".equals(weixinQrcode.getAccountId())?"51fp_":"wdfp_")+weixinQrcode.getSceneId();
                        savepath=WeixinUtil.getQRCode(ticket,req,postfix);
                        _log.info("\t生成二维码成功，生成路径："+savepath+"\t");
                    }else{
                        _log.info("\t生成的ticket为空\t");
                    }

                    weixinQrcode.setTicket(ticket);
                    //请求路径
                    weixinQrcode.setQrcodeUrl(jsonObject.getString("url"));
                    //保存路径
                    weixinQrcode.setSavePath(savepath);
                    //生成时间
                    weixinQrcode.setGenerationTime(new Date());
					_log.info("===============二维码实体生成完毕，存入数据库："+weixinQrcode.toString()+" ==============");
					weixinQrcodeService.update(weixinQrcode);
				}
			}
		}
		_log.info("\t================结束遍历需要生成二维码的实体=================\t");
		return R.ok(message);
	}
}
