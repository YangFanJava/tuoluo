package io.renren.modules.version.controller;

import java.util.List;
import java.util.Map;

import io.renren.modules.version.entity.AppUserEntity;
import io.renren.modules.version.service.AppUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;




/**
 * app用户表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-21 12:14:47
 */
@RestController
@RequestMapping("appuser")
public class AppUserController {
	@Autowired
	private AppUserService appUserService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("appuser:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<AppUserEntity> appUserList = appUserService.queryList(query);
		int total = appUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(appUserList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("appuser:info")
	public R info(@PathVariable("id") Long id){
		AppUserEntity appUser = appUserService.queryObject(id);
		return R.ok().put("appUser", appUser);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("appuser:save")
	public R save(@RequestBody AppUserEntity appUser){
		appUserService.save(appUser);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("appuser:update")
	public R update(@RequestBody AppUserEntity appUser){
		appUserService.update(appUser);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("appuser:delete")
	public R delete(@RequestBody Long[] ids){
		appUserService.deleteBatch(ids);
		return R.ok();
	}
	
}
