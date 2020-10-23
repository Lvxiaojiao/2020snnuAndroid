package com.hodo.service;

import java.util.List;

import com.hodo.bean.Partin;
import com.hodo.common.base.DataGrid;

public interface PartinServiceI {
	public void addPartin(Partin partin);
	public int update(Partin partin);
	public void delete(Partin partin);
	public Partin findByCid(String partin);
	public List<Partin> findAll();
	public List<Partin> findByUsrCid(String usrCid,String tp);
	public List<Partin> findByActCid(String actCid,String tp);
	public DataGrid datagrid(Partin partin);
	public List<Partin> findByActCidUsrCidTp(String actCid, String usrCid, String tp);
	public List<Partin> findByUsrCid(String usrCid);
}
