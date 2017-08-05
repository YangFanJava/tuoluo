package io.renren.modules.weixin.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-25 17:42:51
 */
public class WxUserinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Long id;
	//用户是否订阅该公众号标识，0表示未关注
	private Integer subscribe;
	//对当前公众号唯一ID
	private String openid;
	//用户的昵称
	private String nickname;
	//用户的性别，1男、2女
	private Integer sex;
	//用户所在城市
	private String city;
	//用户所在国家
	private String country;
	//用户所在省份
	private String province;
	//用户的语言
	private String language;
	//用户头像地址
	private String headimgurl;
	//用户关注时间
	private String subscribeTime;
	//用户唯一ID
	private String unionid;
	//用户管理界面对粉丝添加备注
	private String remark;
	//用户所在的分组ID
	private Integer groupid;
	//用户被打上的标签ID列表
	private String tagidList;
	//公众号原始ID
	private String accountId;
	//数据是否是完整的
	private Integer isWhole;

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
	 * 设置：用户是否订阅该公众号标识，0表示未关注
	 */
	public void setSubscribe(Integer subscribe) {
		this.subscribe = subscribe;
	}
	/**
	 * 获取：用户是否订阅该公众号标识，0表示未关注
	 */
	public Integer getSubscribe() {
		return subscribe;
	}
	/**
	 * 设置：对当前公众号唯一ID
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：对当前公众号唯一ID
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * 设置：用户的昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：用户的昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置：用户的性别，1男、2女
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 获取：用户的性别，1男、2女
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 设置：用户所在城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：用户所在城市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：用户所在国家
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * 获取：用户所在国家
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * 设置：用户所在省份
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：用户所在省份
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：用户的语言
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * 获取：用户的语言
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * 设置：用户头像地址
	 */
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	/**
	 * 获取：用户头像地址
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}
	/**
	 * 设置：用户关注时间
	 */
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	/**
	 * 获取：用户关注时间
	 */
	public String getSubscribeTime() {
		return subscribeTime;
	}
	/**
	 * 设置：用户唯一ID
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	/**
	 * 获取：用户唯一ID
	 */
	public String getUnionid() {
		return unionid;
	}
	/**
	 * 设置：用户管理界面对粉丝添加备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：用户管理界面对粉丝添加备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：用户所在的分组ID
	 */
	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}
	/**
	 * 获取：用户所在的分组ID
	 */
	public Integer getGroupid() {
		return groupid;
	}
	/**
	 * 设置：用户被打上的标签ID列表
	 */
	public void setTagidList(String tagidList) {
		this.tagidList = tagidList;
	}
	/**
	 * 获取：用户被打上的标签ID列表
	 */
	public String getTagidList() {
		return tagidList;
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
	 * 设置：数据是否是完整的
	 */
	public void setIsWhole(Integer isWhole) {
		this.isWhole = isWhole;
	}
	/**
	 * 获取：数据是否是完整的
	 */
	public Integer getIsWhole() {
		return isWhole;
	}
}
