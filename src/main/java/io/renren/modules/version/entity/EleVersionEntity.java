package io.renren.modules.version.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 应用版本记录
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-20 16:46:35
 */
public class EleVersionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//应用版本名称
	private String versionName;
	//版本号
	private String versionNum;
	//上传路径(下载路径)
	private String path;
	//更新说明
	private String description;
	//创建人
	private String createName;
	//创建时间
	private Date createDate;
	//状态(0:禁用 1:启用)
	private String state;
	//强制更新(0:否 1:是)
	private String forceupdate;

	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：应用版本名称
	 */
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	/**
	 * 获取：应用版本名称
	 */
	public String getVersionName() {
		return versionName;
	}
	/**
	 * 设置：版本号
	 */
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	/**
	 * 获取：版本号
	 */
	public String getVersionNum() {
		return versionNum;
	}
	/**
	 * 设置：上传路径(下载路径)
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取：上传路径(下载路径)
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置：更新说明
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：更新说明
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateName() {
		return createName;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：状态(0:禁用 1:启用)
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 获取：状态(0:禁用 1:启用)
	 */
	public String getState() {
		return state;
	}
	/**
	 * 设置：强制更新(0:否 1:是)
	 */
	public void setForceupdate(String forceupdate) {
		this.forceupdate = forceupdate;
	}
	/**
	 * 获取：强制更新(0:否 1:是)
	 */
	public String getForceupdate() {
		return forceupdate;
	}
}
