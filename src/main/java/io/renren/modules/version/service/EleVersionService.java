package io.renren.modules.version.service;


import io.renren.modules.version.entity.EleVersionEntity;

import java.util.List;
import java.util.Map;

/**
 * 应用版本记录
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-20 16:46:35
 */
public interface EleVersionService {
	
	EleVersionEntity queryObject(String id);
	
	List<EleVersionEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(EleVersionEntity eleVersion);
	
	void update(EleVersionEntity eleVersion);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
