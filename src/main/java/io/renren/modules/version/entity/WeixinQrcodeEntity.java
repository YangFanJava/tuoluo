package io.renren.modules.version.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-31 11:14:45
 */
public class WeixinQrcodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//渠道名称
	private String name;
	//类型(永久、临时)
	private String actionName;
	//公众号原始ID
	private String accountId;
	//二维码中间Logo存放地址
	private String imageHref;
	//创建时间
	private Date createDate;
	//获取的二维码ticket
	private String ticket;
	//二维码有效时间
	private Integer expireSeconds;
	//场景ID
	private Integer sceneId;
	//生成时间
	private Date generationTime;
	//二维码保存路径
	private String savePath;
	//二维码图片解析后的地址
	private String qrcodeUrl;
	//描述
	private String description;
	//分组名称
	private String groupName;

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
	 * 设置：渠道名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：渠道名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：类型(永久、临时)
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	/**
	 * 获取：类型(永久、临时)
	 */
	public String getActionName() {
		return actionName;
	}
	/**
	 * 设置：公众号原始ID
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * 获取：公众号原始ID
	 */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * 设置：二维码中间Logo存放地址
	 */
	public void setImageHref(String imageHref) {
		this.imageHref = imageHref;
	}
	/**
	 * 获取：二维码中间Logo存放地址
	 */
	public String getImageHref() {
		return imageHref;
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
	 * 设置：获取的二维码ticket
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	/**
	 * 获取：获取的二维码ticket
	 */
	public String getTicket() {
		return ticket;
	}
	/**
	 * 设置：二维码有效时间
	 */
	public void setExpireSeconds(Integer expireSeconds) {
		this.expireSeconds = expireSeconds;
	}
	/**
	 * 获取：二维码有效时间
	 */
	public Integer getExpireSeconds() {
		return expireSeconds;
	}
	/**
	 * 设置：场景ID
	 */
	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}
	/**
	 * 获取：场景ID
	 */
	public Integer getSceneId() {
		return sceneId;
	}
	/**
	 * 设置：生成时间
	 */
	public void setGenerationTime(Date generationTime) {
		this.generationTime = generationTime;
	}
	/**
	 * 获取：生成时间
	 */
	public Date getGenerationTime() {
		return generationTime;
	}
	/**
	 * 设置：二维码保存路径
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	/**
	 * 获取：二维码保存路径
	 */
	public String getSavePath() {
		return savePath;
	}
	/**
	 * 设置：二维码图片解析后的地址
	 */
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	/**
	 * 获取：二维码图片解析后的地址
	 */
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "WeixinQrcodeEntity{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", actionName='" + actionName + '\'' +
				", accountId='" + accountId + '\'' +
				", imageHref='" + imageHref + '\'' +
				", createDate=" + createDate +
				", ticket='" + ticket + '\'' +
				", expireSeconds=" + expireSeconds +
				", sceneId=" + sceneId +
				", generationTime=" + generationTime +
				", savePath='" + savePath + '\'' +
				", qrcodeUrl='" + qrcodeUrl + '\'' +
				", description='" + description + '\'' +
				'}';
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
