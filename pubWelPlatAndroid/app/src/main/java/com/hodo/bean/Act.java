package com.hodo.bean;

/**
 * 活动
 */

public class Act extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = -5239418625007165221L;

	private String cid; 
	private String nm;		//名称
	private String instro;  //简介
	private String pic;     //图片
	private Integer reqAmt; //所需资金数额
	private Integer reqNum; //所需志愿者人数
	private String status;  //状态(未审核/通过/未通过)
//	//外键
//	private Usr usr; //组织名称

	// 非数据库
	private String crealname;  //真实姓名
	private String usrCid;

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

	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getInstro() {
		return instro;
	}

	public void setInstro(String instro) {
		this.instro = instro;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getReqAmt() {
		return reqAmt;
	}

	public void setReqAmt(Integer reqAmt) {
		this.reqAmt = reqAmt;
	}

	public Integer getReqNum() {
		return reqNum;
	}

	public void setReqNum(Integer reqNum) {
		this.reqNum = reqNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCrealname() {
		return crealname;
	}

	public void setCrealname(String crealname) {
		this.crealname = crealname;
	}

	public String getUsrCid() {
		return usrCid;
	}

	public void setUsrCid(String usrCid) {
		this.usrCid = usrCid;
	}

	public String getSdateString() {
		return sdateString;
	}

	public void setSdateString(String sdateString) {
		this.sdateString = sdateString;
	}

	public String getEdateString() {
		return edateString;
	}

	public void setEdateString(String edateString) {
		this.edateString = edateString;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getDtString() {
		return dtString;
	}

	public void setDtString(String dtString) {
		this.dtString = dtString;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}
}
