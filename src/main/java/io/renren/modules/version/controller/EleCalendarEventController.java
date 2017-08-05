package io.renren.modules.version.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.modules.version.entity.EleCalendarEventEntity;
import io.renren.modules.version.service.EleCalendarEventService;
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
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-28 10:51:24
 */
@RestController
@RequestMapping("elecalendarevent")
public class EleCalendarEventController {
	@Autowired
	private EleCalendarEventService eleCalendarEventService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("elecalendarevent:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<EleCalendarEventEntity> eleCalendarEventList = eleCalendarEventService.queryList(query);
		int total = eleCalendarEventService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(eleCalendarEventList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("elecalendarevent:info")
	public R info(@PathVariable("id") Long id){
		EleCalendarEventEntity eleCalendarEvent = eleCalendarEventService.queryObject(id);
		
		return R.ok().put("eleCalendarEvent", eleCalendarEvent);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("elecalendarevent:save")
	public R save(@RequestBody EleCalendarEventEntity eleCalendarEvent){
		if(eleCalendarEvent.getCreateTime()==null){
			eleCalendarEvent.setCreateTime(new Date());
		}
		eleCalendarEventService.save(eleCalendarEvent);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("elecalendarevent:update")
	public R update(@RequestBody EleCalendarEventEntity eleCalendarEvent){
		eleCalendarEventService.update(eleCalendarEvent);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("elecalendarevent:delete")
	public R delete(@RequestBody Long[] ids){
		eleCalendarEventService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
