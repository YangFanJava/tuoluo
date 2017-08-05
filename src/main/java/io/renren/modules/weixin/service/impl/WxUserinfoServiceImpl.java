package io.renren.modules.weixin.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.HttpClientUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.WeixinUtil;
import io.renren.common.vo.WeiXinUserList;
import io.renren.common.vo.WxUser;
import io.renren.modules.weixin.dao.WxUserinfoDao;
import io.renren.modules.weixin.entity.WxUserinfoEntity;
import io.renren.modules.weixin.service.WxUserinfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Service("wxUserinfoService")
public class WxUserinfoServiceImpl implements WxUserinfoService {


	private static Logger _log = LoggerFactory.getLogger(WxUserinfoServiceImpl.class); // 日志记录

	@Autowired
	private WxUserinfoDao wxUserinfoDao;
	
	@Override
	public WxUserinfoEntity queryObject(Long id){
		return wxUserinfoDao.queryObject(id);
	}
	
	@Override
	public List<WxUserinfoEntity> queryList(Map<String, Object> map){
		return wxUserinfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return wxUserinfoDao.queryTotal(map);
	}
	
	@Override
	public void save(WxUserinfoEntity wxUserinfo){
		wxUserinfoDao.save(wxUserinfo);
	}
	
	@Override
	public void update(WxUserinfoEntity wxUserinfo){
		wxUserinfoDao.update(wxUserinfo);
	}
	
	@Override
	public void delete(Long id){
		wxUserinfoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		wxUserinfoDao.deleteBatch(ids);
	}

	@Override
	public R saveOpenIds(String accoundId,String nextOpenid) {
		//TODO
		//初始化TOKEN
		initTokenAndUrl(accoundId);
		_log.info("初始化Token与Url：参数:token - {},URL- {}",accessToken,userInfoUrl);

		if (StringUtils.isBlank(nextOpenid)){
			Map<String, Object> map = wxUserinfoDao.queryLastInsertId(accoundId);
			if (map!=null&&map.get("openid")!=null && StringUtils.isNotBlank(map.get("openid").toString())){
				nextOpenid = map.get("openid").toString();
			}
		}
		_log.info("获取的nextOpenid： {}",nextOpenid);

		WeixinUtil util=new WeixinUtil();
		_log.info("查询所有用户信息数据接口：参数:accessToken - {},account_id-nextOpenid - {}",accessToken,accoundId+" - "+nextOpenid);
		WeiXinUserList list = util.findWeiXinUserListNoLoop(accessToken, nextOpenid);

		if (list==null||list.getTotal()==0){
			return R.error(500,"异常");
		}

		for (String open:list.getData().getOpenid()) {
			wxUserinfoDao.saveOpenidObj(accoundId,open);
		}
		if (list.getCount()<10000 && list.getCount()>0){
			//TODO 暂时不处理
		}else{
			//TODO 递归 说明数据还有 再来一次
			saveOpenIds(accoundId,list.getNext_openid());
		}
		return R.ok();
	}


	@Value("${my.wxUserInfo.wdfp.userInfoUrl}")
	private String wdfpUserInfoUrl;

	private static String userInfoUrl;

	@Value("${my.wxUserInfo.51fp.userInfoUrl}")
	private String userInfoUrl251fp ;

	@Value("${my.wxUserInfo.accessToken}")
	private String accessToken ;


	private void initTokenAndUrl(String account_id){

		_log.info("初始化Token与Url开始：参数:account_id{}",account_id);

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

				userInfoUrl = wdfpUserInfoUrl;

				_log.info("我的发票初始化url");

				break;
			//51发票
			case "gh_5c48f34ff74f":
				host = "172.17.164.121";
				port = 6379;
				password = "redis-dev";

				userInfoUrl = userInfoUrl251fp;

				_log.info("51发票初始化url");

				break;
		}

		try{
			JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port,timeout);
			if(!StringUtils.isBlank(password)) {
				jedisShardInfo.setPassword(password);
			}
			Jedis jedis = jedisShardInfo.createResource();
			String access_token = jedis.get("access_token");
			accessToken = access_token;
		}catch (Exception e){
			_log.debug("初始化Token失败",e);
		}

		_log.info("初始化Token与Url：参数:token - {},URL- {}",accessToken,userInfoUrl);
	}

	@Override
	public R saveInfo(String access_id,Integer pageSize) {
		//初始化Token and url
		initTokenAndUrl(access_id);
		//查询库中空数据
		List<WxUserinfoEntity> list = wxUserinfoDao.queryNoWholeIDS(access_id, pageSize);

		_log.info("查询库中空数据，查询数量为{}",list==null?0:list.size());
		//构造请求
		JSONObject jsonParam  = null;
		for(WxUserinfoEntity entity:list){
			jsonParam = new JSONObject();
			_log.info("正在添加的用户 OPEN_ID ：{}",entity.getOpenid());
			jsonParam.put("openid",entity.getOpenid());
			jsonParam.put("lang","zh_CN");
			JSONObject json= HttpClientUtils.httpPost(userInfoUrl, jsonParam);
			if(json!=null){
				try{
					_log.info("响应用户数据：{}",json);
					entity.setOpenid(json.getString("openid"));
					entity.setUnionid(json.getString("unionid"));
					entity.setSubscribe(Integer.parseInt(json.getString("subscribe")));
					entity.setIsWhole(1);
					if(entity.getSubscribe()==1){
						entity.setSubscribe(Integer.valueOf(json.getString("subscribe")));
						entity.setCountry(json.getString("country"));
						entity.setCity(json.getString("city"));
						entity.setGroupid(Integer.valueOf(json.getString("groupid")));
						entity.setLanguage(json.getString("language"));
						entity.setHeadimgurl(json.getString("headimgurl"));
						entity.setOpenid(json.getString("openid"));
						entity.setCity(json.getString("city"));
//						String nickName = new String(json.getString("nickname").getBytes("utf-8"), "UTF-8");
//						String nickName = new String(json.getString("nickname").getBytes("utf-8"), "UTF-8");
						String nickName = URLEncoder.encode(json.getString("nickname"), "utf-8");
						entity.setNickname(nickName);
						entity.setSex(Integer.valueOf(json.getString("sex")));
						entity.setProvince(json.getString("province"));
						entity.setSubscribeTime(json.getString("subscribe_time"));
						entity.setUnionid(json.getString("unionid"));
					}
					wxUserinfoDao.update(entity);
				}catch (Exception e){
					_log.info("数据插入错误，错误数据{}，原因{}",json,e.getMessage());
				}
			}
		}
		//如果集合大小 和 数据大小相等 说明还有数据未填充 继续递归
		if (list.size() == pageSize){
			saveInfo(access_id,pageSize);
		}
		//解析响应
		//填充数据
		return R.ok();
	}
}
