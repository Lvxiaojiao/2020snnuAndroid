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
 * 参与(报名/捐款)
 */
@Entity
@Table(name = "partin")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = {"act","usr"})
public class Partin extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = -5239418625007165221L;

	private String cid; 
	private String tp;   //参与类型(报名/捐款)
	private Date dt;     //参与时间
	private Integer amt; //捐款金额
	//外键
	private Act act;     //活动
	private Usr usr;     //志愿者

	// 非数据库
	private String actCid;	//活动Id
	private String nm;		//名称
	private String pic;     //图片
	
	private String usrCid;  //职员者Id
	private String crealname;  //真实姓名
	
	private String dtString;   //参与时间
	public Partin() {
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
	@Column(name = "tp")
	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}
	
	@Column(name="dt")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	@Column(name = "amt")
	public Integer getAmt() {
		return amt;
	}

	public void setAmt(Integer amt) {
		this.amt = amt;
	}


	@ManyToOne
	@JoinColumn(name = "actCid")
	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	
	@ManyToOne
	@JoinColumn(name = "usrId")
	public Usr getUsr() {
		return usr;
	}

	public void setUsr(Usr usr) {
		this.usr = usr;
	}
	@Transient
	public String getActCid() {
		return actCid;
	}

	public void setActCid(String actCid) {
		this.actCid = actCid;
	}

	@Transient
	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}
	@Transient
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	@Transient
	public String getUsrCid() {
		return usrCid;
	}

	public void setUsrCid(String usrCid) {
		this.usrCid = usrCid;
	}

	@Transient
	public String getCrealname() {
		return crealname;
	}

	public void setCrealname(String crealname) {
		this.crealname = crealname;
	}
	
	@Transient
	public String getDtString() {
		return dtString;
	}

	public void setDtString(String dtString) {
		this.dtString = dtString;
	}

	
}
