package io.renren.modules.version.dao;

import io.renren.modules.sys.dao.BaseDao;
import io.renren.modules.version.entity.EleVersionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用版本记录
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-20 16:46:35
 */
@Mapper
public interface EleVersionDao extends BaseDao<EleVersionEntity> {
	
}
