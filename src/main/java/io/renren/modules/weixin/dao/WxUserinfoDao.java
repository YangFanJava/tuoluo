package io.renren.modules.weixin.dao;

import io.renren.modules.sys.dao.BaseDao;
import io.renren.modules.weixin.entity.WxUserinfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-25 17:42:51
 */
@Mapper
public interface WxUserinfoDao extends BaseDao<WxUserinfoEntity> {

    Map<String,Object> queryLastInsertId(@Param("account_id") String account_id);

    List<WxUserinfoEntity> queryNoWholeIDS(@Param("account_id") String accunt_id, @Param("pageSize") Integer pageSize);

    void  saveOpenidObj(@Param("accound_id") String accound_id, @Param("openid") String openid);
}
