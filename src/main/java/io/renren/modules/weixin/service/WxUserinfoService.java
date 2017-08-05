package io.renren.modules.weixin.service;


import io.renren.common.utils.R;
import io.renren.modules.weixin.entity.WxUserinfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-25 17:42:51
 */
public interface WxUserinfoService {
	
	WxUserinfoEntity queryObject(Long id);
	
	List<WxUserinfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WxUserinfoEntity wxUserinfo);
	
	void update(WxUserinfoEntity wxUserinfo);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	R saveOpenIds(String accoundId, String nextOpenid);

	R saveInfo(String access_id, Integer pageSize);
}
