package com.hodo.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hodo.common.base.BaseObject;

/**
 * 活动
 */
@Entity
@Table(name = "act")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = {"usr"})
public class Act extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = -5239418625007165221L;

	private String cid; 
	private String nm;		//名称
	private String instro;  //简介
	private String pic;     //图片
	private Date sdate;     //开始日期
	private Date edate;     //截止日期
	private Integer reqAmt; //所需资金数额
	private Integer reqNum; //所需志愿者人数
	private String status;  //状态(未审核/通过/未通过)
	//外键
	private Usr usr; //组织

	// 非数据库
	private String usrCid;     //组织ID
	private String crealname;  //组织名称
	
	private String sdateString;     //开始日期
	private String edateString;     //截止日期
	
	private String key;       //搜索关键字
	private String cls;    //种类		

	private String userGroup;  //用户组别 (0:公益组织，1:志愿者，2:管理员)
	
	private String dtString;   //参与时间
	private String tp;        //参与类型(报名/捐款)
	
	private String isReport; //是否已报名(1:是)
	public Act() {
		
	}

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name = "cid")
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	@Column(name = "nm")
	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}
	@Column(name = "instro")
	public String getInstro() {
		return instro;
	}

	public void setInstro(String instro) {
		this.instro = instro;
	}
	@Column(name = "pic")
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Column(name="sdate")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	
	@Column(name="edate")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}
	@Column(name = "reqAmt")
	public Integer getReqAmt() {
		return reqAmt;
	}

	public void setReqAmt(Integer reqAmt) {
		this.reqAmt = reqAmt;
	}
	@Column(name = "reqNum")
	public Integer getReqNum() {
		return reqNum;
	}

	public void setReqNum(Integer reqNum) {
		this.reqNum = reqNum;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "usrCid")
	public Usr getUsr() {
		return usr;
	}

	public void setUsr(Usr usr) {
		this.usr = usr;
	}
	
	@Transient
	public String getCrealname() {
		return crealname;
	}

	public void setCrealname(String crealname) {
		this.crealname = crealname;
	}
	@Transient
	public String getSdateString() {
		return sdateString;
	}

	public void setSdateString(String sdateString) {
		this.sdateString = sdateString;
	}
	@Transient
	public String getEdateString() {
		return edateString;
	}
	public void setEdateString(String edateString) {
		this.edateString = edateString;
	}
	
	@Transient
	public String getUsrCid() {
		return usrCid;
	}

	public void setUsrCid(String usrCid) {
		this.usrCid = usrCid;
	}
	@Transient
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	@Transient
	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}
	@Transient
	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	@Transient
	public String getDtString() {
		return dtString;
	}

	public void setDtString(String dtString) {
		this.dtString = dtString;
	}
	@Transient
	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}
	@Transient
	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}

	

	
}
