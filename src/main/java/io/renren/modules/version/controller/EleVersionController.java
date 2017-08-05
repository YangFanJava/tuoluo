package io.renren.modules.version.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.renren.common.utils.*;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.version.entity.EleVersionEntity;
import io.renren.modules.version.service.EleVersionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 应用版本记录
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-20 16:46:35
 */
@RestController
@RequestMapping("eleversion")
public class EleVersionController {
	@Autowired
	private EleVersionService eleVersionService;


	private String fileServerUrl = "";
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("eleversion:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<EleVersionEntity> eleVersionList = eleVersionService.queryList(query);
		int total = eleVersionService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(eleVersionList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("eleversion:info")
	public R info(@PathVariable("id") String id){
		EleVersionEntity eleVersion = eleVersionService.queryObject(id);
		
		return R.ok().put("eleVersion", eleVersion);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("eleversion:save")
	public R save(@RequestBody EleVersionEntity eleVersion, HttpServletRequest request){
		if(eleVersion.getCreateDate()==null){
			eleVersion.setCreateDate(new Date());
		}
		if (!StringUtils.hasText(eleVersion.getCreateName())){
			SysUserEntity user=(SysUserEntity)request.getSession().getAttribute("currentUser");
			eleVersion.setCreateName(user.getUsername());
		}
		eleVersionService.save(eleVersion);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("eleversion:update")
	public R update(@RequestBody EleVersionEntity eleVersion){
		eleVersionService.update(eleVersion);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("eleversion:delete")
	public R delete(@RequestBody String[] ids){
		eleVersionService.deleteBatch(ids);
		
		return R.ok();
	}


	/**
	 * 删除
	 */
	@RequestMapping("/upload")
	@RequiresPermissions("eleversion:save")
	public R fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
//		String  serverPath = "http://10.1.1.243:9292/oas/api/version/uploadApk";
		String  serverPath = "http://172.17.196.15:8086/oas/api/version/uploadApk";
		String s = HttpClientUtils.httpClientUploadFile(serverPath, file);
		Map<String,Object> map = FastJosnUtils.jsonToPojo(s, Map.class);
		if (map!=null&&map.get("code")!=null&&"200".equals(map.get("code"))){
			Object message = map.get("data");
			if (message!=null){
				return R.ok().put("url",message);
			}
		}
		return R.error(500,"文件上传失败");
	}
}
