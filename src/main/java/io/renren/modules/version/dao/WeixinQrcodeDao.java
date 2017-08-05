package io.renren.modules.version.dao;

import io.renren.modules.sys.dao.BaseDao;
import io.renren.modules.version.entity.WeixinQrcodeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-31 11:14:45
 */
@Mapper
public interface WeixinQrcodeDao extends BaseDao<WeixinQrcodeEntity> {

    Integer getSceneidByAccountId(WeixinQrcodeEntity weixinQrcode);

    List<String> queryGroupName();
}
