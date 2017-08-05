package io.renren.modules.version.service.impl;

import io.renren.modules.version.dao.EleCalendarEventDao;
import io.renren.modules.version.entity.EleCalendarEventEntity;
import io.renren.modules.version.service.EleCalendarEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




@Service("eleCalendarEventService")
public class EleCalendarEventServiceImpl implements EleCalendarEventService {
	@Autowired
	private EleCalendarEventDao eleCalendarEventDao;
	
	@Override
	public EleCalendarEventEntity queryObject(Long id){
		return eleCalendarEventDao.queryObject(id);
	}
	
	@Override
	public List<EleCalendarEventEntity> queryList(Map<String, Object> map){
		return eleCalendarEventDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return eleCalendarEventDao.queryTotal(map);
	}
	
	@Override
	public void save(EleCalendarEventEntity eleCalendarEvent){
		eleCalendarEventDao.save(eleCalendarEvent);
	}
	
	@Override
	public void update(EleCalendarEventEntity eleCalendarEvent){
		eleCalendarEventDao.update(eleCalendarEvent);
	}
	
	@Override
	public void delete(Long id){
		eleCalendarEventDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		eleCalendarEventDao.deleteBatch(ids);
	}
	
}
