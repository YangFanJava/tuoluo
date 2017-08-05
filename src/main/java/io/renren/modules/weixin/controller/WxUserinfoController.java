package io.renren.modules.weixin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.*;
import io.renren.common.vo.WxUser;
import io.renren.modules.weixin.entity.WxUserinfoEntity;
import io.renren.modules.weixin.service.WxUserinfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import javax.annotation.Resource;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-25 16:09:18
 */
@RequestMapping("/wxuserinfo")
@RestController
public class WxUserinfoController {
	@Autowired
	private WxUserinfoService wxUserinfoService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("wxuserinfo:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<WxUserinfoEntity> wxUserinfoList = wxUserinfoService.queryList(query);
		int total = wxUserinfoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(wxUserinfoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("wxuserinfo:info")
	public R info(@PathVariable("id") Long id){
		WxUserinfoEntity wxUserinfo = wxUserinfoService.queryObject(id);
		
		return R.ok().put("wxUserinfo", wxUserinfo);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("wxuserinfo:save")
	public R save(@RequestBody WxUserinfoEntity wxUserinfo){
		wxUserinfoService.save(wxUserinfo);
		
		return R.ok();
	}



	@Value("${my.wxUserInfo.accessToken}")
	private String accessToken;


	private static Logger _log = LoggerFactory.getLogger(WxUserinfoController.class); // 日志记录



	/**
	 *   批量抓取用户接口调用
	 *   				只存储openID  存储账号下全部openId
	 */
	@RequestMapping("/selAll2")
	@RequiresPermissions("wxuserinfo:save")
	public R saveAll2(@RequestParam(defaultValue = "gh_5c48f34ff74f")String accountId,String nextOpenid){
		return  wxUserinfoService.saveOpenIds(accountId,nextOpenid);
	}




	/**
	 *  获取数据库中空白数据
	 *   			填充数据
	 */
	@RequestMapping("/selInfo")
	@RequiresPermissions("wxuserinfo:save")
	public R saveInfo(@RequestParam(defaultValue = "gh_5c48f34ff74f")String account_id,String pageSize){
		return  wxUserinfoService.saveInfo(account_id, StringUtils.isNotBlank(pageSize)?Integer.parseInt(pageSize.replaceAll("[^0-9]","")):3000);
	}


	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("wxuserinfo:update")
	public R update(@RequestBody WxUserinfoEntity wxUserinfo){
		wxUserinfoService.update(wxUserinfo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("wxuserinfo:delete")
	public R delete(@RequestBody Long[] ids){
		wxUserinfoService.deleteBatch(ids);
		
		return R.ok();
	}



	@Resource
	private RedisService redisService;


	@RequestMapping("/test/{type}")
	public String test(@PathVariable("type") String type){
		String  host ="";
		Integer  port =6379;
		String  password ="";
		Integer timeout = 5000;

		switch (type){
			//我的发票
			case "0":
				host = "172.17.164.173";
				port = 6379;
				password = "redis-dev";
				break;
			//51发票
			case "1":
				host = "172.17.164.121";
				port = 6379;
				password = "redis-dev";
				break;
		}

		JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port,timeout);
		if(!StringUtils.isBlank(password)) {
			jedisShardInfo.setPassword(password);
		}
		Jedis jedis = jedisShardInfo.createResource();
		String access_token = jedis.get("access_token");
		return access_token;
	}


}
