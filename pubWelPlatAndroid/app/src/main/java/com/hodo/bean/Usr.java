package com.hodo.bean;

import java.util.Date;
import java.util.List;


/**
 * 用户
 */

public class Usr extends BaseObject implements java.io.Serializable {

	private String cid;			
	private String cname;      //用户名
	private String cpwd;	   //密码
	private String crealname;  //真实姓名
	private String tel;        //联系方式
	private String userGroup;  //用户组别 (1:普通用户，2:管理员)
	private String icon;       //头像
	private String addr;       //地址
	private Float purse;       //钱包
	
	//非数据库
	private String validCode;     //验证码图片
	private String rememberMe;    //记住我

	private String userGroupName; //用户组别 (1:普通用户，2:管理员)
	public Usr() {}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCpwd() {
		return cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}

	public String getCrealname() {
		return crealname;
	}

	public void setCrealname(String crealname) {
		this.crealname = crealname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Float getPurse() {
		return purse;
	}

	public void setPurse(Float purse) {
		this.purse = purse;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}


	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
}
