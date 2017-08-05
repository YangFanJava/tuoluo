package io.renren.modules.version.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * app用户表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-21 12:14:47
 */
public class AppUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//用户名
	private String username;
	//密码
	private String password;
	//邮箱
	private String email;
	//手机号
	private String mobile;
	//状态  0：禁用   1：正常
	private String status;
	//是否发送短信(0:否 1:是)
	private String issms;
	//角色（备用）
	private String role;
	//创建时间
	private Date createTime;

	/**
	 * 设置：编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：编号
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：状态  0：禁用   1：正常
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态  0：禁用   1：正常
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：是否发送短信(0:否 1:是)
	 */
	public void setIssms(String issms) {
		this.issms = issms;
	}
	/**
	 * 获取：是否发送短信(0:否 1:是)
	 */
	public String getIssms() {
		return issms;
	}
	/**
	 * 设置：角色（备用）
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * 获取：角色（备用）
	 */
	public String getRole() {
		return role;
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
}
