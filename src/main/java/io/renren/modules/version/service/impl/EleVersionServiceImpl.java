package io.renren.modules.version.service.impl;

import io.renren.modules.version.dao.EleVersionDao;
import io.renren.modules.version.entity.EleVersionEntity;
import io.renren.modules.version.service.EleVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("eleVersionService")
public class EleVersionServiceImpl implements EleVersionService {
	@Autowired
	private EleVersionDao eleVersionDao;
	
	@Override
	public EleVersionEntity queryObject(String id){
		return eleVersionDao.queryObject(id);
	}
	
	@Override
	public List<EleVersionEntity> queryList(Map<String, Object> map){
		return eleVersionDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return eleVersionDao.queryTotal(map);
	}
	
	@Override
	public void save(EleVersionEntity eleVersion){
		eleVersion.setId(UUID.randomUUID().toString().replaceAll("-",""));
		eleVersionDao.save(eleVersion);
	}
	
	@Override
	public void update(EleVersionEntity eleVersion){
		eleVersionDao.update(eleVersion);
	}
	
	@Override
	public void delete(String id){
		eleVersionDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		eleVersionDao.deleteBatch(ids);
	}
	
}
