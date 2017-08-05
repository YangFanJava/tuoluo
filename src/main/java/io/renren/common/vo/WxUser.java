package io.renren.common.vo;


public class WxUser  implements java.io.Serializable {
	private String openid;//
	private String unionid;
	
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
    
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}