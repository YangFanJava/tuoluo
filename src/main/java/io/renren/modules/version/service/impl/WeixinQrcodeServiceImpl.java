package io.renren.modules.version.service.impl;

import io.renren.modules.version.dao.WeixinQrcodeDao;
import io.renren.modules.version.entity.WeixinQrcodeEntity;
import io.renren.modules.version.service.WeixinQrcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;




@Service("weixinQrcodeService")
public class WeixinQrcodeServiceImpl implements WeixinQrcodeService {
	@Autowired
	private WeixinQrcodeDao weixinQrcodeDao;
	
	@Override
	public WeixinQrcodeEntity queryObject(String id){
		return weixinQrcodeDao.queryObject(id);
	}
	
	@Override
	public List<WeixinQrcodeEntity> queryList(Map<String, Object> map){
		return weixinQrcodeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return weixinQrcodeDao.queryTotal(map);
	}
	
	@Override
	public void save(WeixinQrcodeEntity weixinQrcode){
		weixinQrcode.setSceneId(weixinQrcodeDao.getSceneidByAccountId(weixinQrcode)+1);
		weixinQrcodeDao.save(weixinQrcode);
	}
	
	@Override
	public void update(WeixinQrcodeEntity weixinQrcode){
		weixinQrcodeDao.update(weixinQrcode);
	}
	
	@Override
	public void delete(String id){
		weixinQrcodeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		weixinQrcodeDao.deleteBatch(ids);
	}

	@Override
	public List<String> queryGroupName() {
		return weixinQrcodeDao.queryGroupName();
	}

}
