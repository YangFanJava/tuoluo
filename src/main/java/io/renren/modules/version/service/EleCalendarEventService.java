package io.renren.modules.version.service;


import io.renren.modules.version.entity.EleCalendarEventEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-28 10:51:24
 */
public interface EleCalendarEventService {
	
	EleCalendarEventEntity queryObject(Long id);
	
	List<EleCalendarEventEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(EleCalendarEventEntity eleCalendarEvent);
	
	void update(EleCalendarEventEntity eleCalendarEvent);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
