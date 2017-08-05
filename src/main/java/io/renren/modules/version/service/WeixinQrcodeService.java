package io.renren.modules.version.service;


import io.renren.modules.version.entity.WeixinQrcodeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-31 11:14:45
 */
public interface WeixinQrcodeService {
	
	WeixinQrcodeEntity queryObject(String id);
	
	List<WeixinQrcodeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WeixinQrcodeEntity weixinQrcode);
	
	void update(WeixinQrcodeEntity weixinQrcode);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

    List<String> queryGroupName();
}
