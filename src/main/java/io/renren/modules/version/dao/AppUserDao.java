package io.renren.modules.version.dao;

import io.renren.modules.sys.dao.BaseDao;
import io.renren.modules.version.entity.AppUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * app用户表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-21 12:14:47
 */
@Mapper
public interface AppUserDao extends BaseDao<AppUserEntity> {
	
}
