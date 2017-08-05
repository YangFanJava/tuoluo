package io.renren.modules.version.service.impl;

import com.dxhy.basic.SMS.SmsException;
import com.dxhy.basic.SMS.bean.ReturnMsg;
import com.dxhy.basic.SMS.bean.Sms;
import com.dxhy.basic.SMS.service.SmsService;
import io.renren.common.utils.MD5Util;
import io.renren.modules.version.dao.AppUserDao;
import io.renren.modules.version.entity.AppUserEntity;
import io.renren.modules.version.service.AppUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;




@Service("appUserService")
public class AppUserServiceImpl implements AppUserService {
	@Autowired
	private AppUserDao appUserDao;
	
	@Override
	public AppUserEntity queryObject(Long id){
		return appUserDao.queryObject(id);
	}
	
	@Override
	public List<AppUserEntity> queryList(Map<String, Object> map){
		return appUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return appUserDao.queryTotal(map);
	}


	@Resource
	private SmsService smsService;

	@Override
	public void save(AppUserEntity appUser){
		String password = "dxhy" + appUser.getMobile().substring(5);
		appUser.setPassword(password);

		if(appUser.getCreateTime()==null)
			appUser.setCreateTime(new Date());

		if (StringUtils.isNotBlank(appUser.getPassword())){
			appUser.setPassword(MD5Util.MD5(appUser.getPassword()));
		}


			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Sms sms = new Sms();
						sms.setPhone(appUser.getMobile());
						sms.setParams(appUser.getMobile(),password);
						sms.setTemplateCode(1010);
						sms.setClientId("0UUsnjveBhc40XxHJcd7");
						//sms.setSerialNum("2");
						sms.setSerialNum(appUser.getUsername()+appUser.getMobile());

						ReturnMsg msg = smsService.smsRequest(sms);
						System.out.println(msg.getReturnMsg());
					} catch (SmsException e) {
						e.printStackTrace();
					}
				}
			}).start();

		appUserDao.save(appUser);
	}
	
	@Override
	public void update(AppUserEntity appUser){
		if(appUser.getCreateTime()==null)
			appUser.setCreateTime(new Date());
		appUserDao.update(appUser);
	}
	
	@Override
	public void delete(Long id){
		appUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		appUserDao.deleteBatch(ids);
	}
	
}
