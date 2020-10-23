package com.hodo.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.hodo.common.base.BaseObject;


/**
 * 用户
 */
@Entity
@Table(name = "usr")
@Cache(region="all",usage=CacheConcurrencyStrategy.READ_WRITE)
//@JsonIgnoreProperties(value={"dept","role","resourceList"})
public class Usr extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = -738561781839115132L;
	
	private String cid;			
	private String cname;      //用户名
	private String cpwd;	   //密码
	private String crealname;  //真实姓名
	private String tel;        //联系方式
	private String userGroup;  //用户组别 (0:公益组织，1:志愿者，2:管理员)
	private String icon;       //头像
	private String addr;       //地址(常用地址)
	private Float purse;       //钱包
	
	//非数据库
	private String validCode;     //验证码图片
	private String rememberMe;    //记住我
	private String ids;
	private String userGroupName; //用户组别 (0:公益组织，1:志愿者，2:管理员)
	public Usr() {}
	
	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name="cid")
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	@Column(name="cname")
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	@Column(name="cpwd")
	public String getCpwd() {
		return cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}
	@Column(name="crealname")
	public String getCrealname() {
		return crealname;
	}

	public void setCrealname(String crealname) {
		this.crealname = crealname;
	}
	@Column(name="tel")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	@Column(name="userGroup")
	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	@Column(name="icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Column(name="addr")
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	@Column(name="purse")
	public Float getPurse() {
		return purse;
	}

	public void setPurse(Float purse) {
		this.purse = purse;
	}
	
	@Transient
	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	@Transient
	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}
	@Transient
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	@Transient
	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
}
