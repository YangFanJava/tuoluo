package io.renren.modules.version.service;


import io.renren.modules.version.entity.AppUserEntity;

import java.util.List;
import java.util.Map;

/**
 * app用户表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-21 12:14:47
 */
public interface AppUserService {
	
	AppUserEntity queryObject(Long id);
	
	List<AppUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AppUserEntity appUser);
	
	void update(AppUserEntity appUser);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
