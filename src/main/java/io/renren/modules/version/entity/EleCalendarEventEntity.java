package io.renren.modules.version.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-28 10:51:24
 */
public class EleCalendarEventEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Long id;
	//触发时间
	private Date triggerTime;
	//创建时间
	private Date createTime;
	//事件名称
	private String name;
	//事件详情
	private String info;
	//排序字段
	private Integer sort;
	//备注
	private String remarks;

	/**
	 * 设置：ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：触发时间
	 */
	public void setTriggerTime(Date triggerTime) {
		this.triggerTime = triggerTime;
	}
	/**
	 * 获取：触发时间
	 */
	public Date getTriggerTime() {
		return triggerTime;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：事件名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：事件名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：事件详情
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 获取：事件详情
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * 设置：排序字段
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序字段
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * 设置：备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：备注
	 */
	public String getRemarks() {
		return remarks;
	}
}
