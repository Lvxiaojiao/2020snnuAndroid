package com.hodo.service;

import java.util.List;

import com.hodo.bean.Act;
import com.hodo.common.base.DataGrid;

public interface ActServiceI {
	public void addAct(Act act);
	public int update(Act act);
	public void delete(Act act);
	public Act findByCid(String cid);
	public List<Act> findAll();
	public DataGrid datagrid(Act act);
	public List<Act> findByUsrCid(String usrCid);
	public List<Act> findByClsAndKey(String cls, String key);
	public List<Act> findByStatus(String status);
	
}
