package com.hodo.service;

import java.util.List;

import com.hodo.bean.Usr;
import com.hodo.common.base.DataGrid;

public interface UsrServiceI {
	public Usr findUsrOne(Usr usr);
	public void addUsr(Usr usr);
	public boolean isValidUsr(String cname);
	public Usr findUsrByCname(String cname);
	public int update(Usr usr);
	public boolean changePwd(Usr usr);
	public void delete(Usr usr);
	public Usr findByCid(String usr);
	public List<Usr> findAll();
	public DataGrid datagrid(Usr usr);
	public void updateIcon(Usr usrDb);
	public int updateMyUserInfo(Usr usr);
	public void updateAddr(Usr usrDb);
}
