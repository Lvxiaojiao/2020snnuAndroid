package com.hodo.bean;

/**
 * 参与(报名/捐款)
 */

public class Partin extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = -5239418625007165221L;

	private String cid; 
	private String tp;   //参与类型(报名/捐款)
	private Integer amt; //捐款金额

	// 非数据库
	private String nm;		//名称
	private String pic;     //图片
	
	private String crealname;  //真实姓名

	private String actCid;
	private String usrCid;

	private String dtString;   //参与时间
	private String isReport;
	public Partin() {
	}

	public String getCrealname() {
		return crealname;
	}

	public void setCrealname(String crealname) {
		this.crealname = crealname;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public Integer getAmt() {
		return amt;
	}

	public void setAmt(Integer amt) {
		this.amt = amt;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getActCid() {
		return actCid;
	}

	public void setActCid(String actCid) {
		this.actCid = actCid;
	}

	public String getUsrCid() {
		return usrCid;
	}

	public void setUsrCid(String usrCid) {
		this.usrCid = usrCid;
	}

	public String getDtString() {
		return dtString;
	}

	public void setDtString(String dtString) {
		this.dtString = dtString;
	}

	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}

}
