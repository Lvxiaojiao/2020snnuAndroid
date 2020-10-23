package com.hodo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodo.bean.Usr;
import com.hodo.common.base.DataGrid;
import com.hodo.common.interceptor.shiro.ShiroRealm;
import com.hodo.common.util.Util;
import com.hodo.common.util.security.Md5Util;
import com.hodo.dao.BaseDaoI;

@Service(value = "usrService")
public class UsrService implements UsrServiceI {

//	@Autowired
//	private UsrDao usrDao;

	@Autowired
	private BaseDaoI<Usr> usrDao;
	
	@Override
	public DataGrid datagrid(Usr usr) {
		String hql=" from Usr t where 1=1 ";
		DataGrid j = new DataGrid();
		j.setRows(editUsrs(find(usr,hql)));
		j.setTotal(total(usr,hql));
		return j;
	}
	private List<Usr> editUsrs(List<Usr> usrList) {
		if (usrList != null && usrList.size() > 0) {
			for (Usr usrs: usrList) {
				String usrGroup = usrs.getUserGroup();
				if("1".equals(usrGroup)) {
					usrs.setUserGroupName("物品");
				} else if("2".equals(usrGroup)){
					usrs.setUserGroupName("管理员");
				}
			}
		}
		return usrList;
	}
	private List<Usr> find(Usr usr,String hql) {
		hql = addWhere(usr, hql);
//		if (usr.getSort() != null && usr.getOrder() != null) {
//			hql += " order by " + usr.getSort() + " " + usr.getOrder();
//		}else {
//			hql+=" order by t.pubDate desc,t.creatDate desc";
//		}
		List<Usr> usrList = usrDao.find(hql, usr.getPage(), usr.getRows());
		return usrList;
	}
	private Long total(Usr usr,String hql) {
		hql = addWhere(usr, hql);
		hql = "select count(*) "+hql;
		return usrDao.count(hql);
	}
	private String addWhere(Usr usr, String hql) {
		
		if(!Util.isEmpty(usr.getCname())){
			hql += " and t.cname like '%" + usr.getCname() + "%' ";
		}
		
		if(!Util.isEmpty(usr.getUserGroup())){
			if("1".equals(usr.getUserGroup())) {
				hql += " and t.userGroup='" + usr.getUserGroup() + "' ";
			}
			if("0".equals(usr.getUserGroup())) {
				hql += " and t.userGroup in('2','3','4')";
			}
		}
		return hql;
	}
	
	@Override
	public Usr findUsrOne(Usr usr) {
		Properties properties = new Properties();
        try {
			properties.load(ShiroRealm.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
        String saltString = properties.getProperty("salt");
		
		usr.setCpwd(Md5Util.md5(usr.getCpwd(), saltString));
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(usr.getCname());
		paramList.add(usr.getCpwd());
		Usr usrDb = usrDao.get("from Usr where cname=? and cpwd=? ", paramList);
		return usrDb;
	}

	@Override
	public void addUsr(Usr usr) {
		usrDao.save(usr);
	}

	@Override
	public boolean isValidUsr(String cname) {
		if (!Util.isEmpty(cname)) {
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(cname);
			Usr usr = usrDao.get("from Usr where cname=? ", paramList);
			if (Util.isEmpty(usr)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public Usr findUsrByCname(String cname) {
		if (!Util.isEmpty(cname)) {
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(cname);
			Usr usr = usrDao.get("from Usr where cname=? ", paramList);
			if (Util.isEmpty(usr)) {
				return null;
			} else {
				return usr;
			}
		}
		return null;
	}

	@Override
	public boolean changePwd(Usr usr) {
		Usr usrDb = usrDao.get(Usr.class, usr.getCid());
		if(!Util.isEmpty(usrDb)){
			Properties properties = new Properties();
            try {
				properties.load(ShiroRealm.class.getResourceAsStream("/config.properties"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
            String saltString = properties.getProperty("salt");
			usrDb.setCpwd(Md5Util.md5(usr.getCpwd(), saltString));
			usrDao.update(usrDb);
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public int update(Usr usr) {
		Usr usrDb = usrDao.get(Usr.class, usr.getCid());
		if(!Util.isEmpty(usr.getCpwd())){
			Properties properties = new Properties();
            try {
				properties.load(ShiroRealm.class.getResourceAsStream("/config.properties"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
            String saltString = properties.getProperty("salt");
			usrDb.setCpwd(Md5Util.md5(usr.getCpwd(), saltString));
		}
		usrDb.setCname(usr.getCname());
		usrDb.setUserGroup(usr.getUserGroup());
//		usrDb.setEduLevel(usr.getEduLevel());
		usrDb.setCrealname(usr.getCrealname());
//		usrDb.setIdCardNo(usr.getIdCardNo());
		usrDb.setTel(usr.getTel());
//		usrDb.setEmail(usr.getEmail());
		
		usrDb.setIcon(usr.getIcon());
		usrDb.setAddr(usr.getAddr());
		usrDb.setPurse(usr.getPurse());
		
		usrDao.update(usrDb);
		
		int r= 1;
		return r;
	}
	@Override
	public int updateMyUserInfo(Usr usr) {
		Usr usrDb = usrDao.get(Usr.class, usr.getCid());
		usrDb.setCrealname(usr.getCrealname());
		usrDb.setTel(usr.getTel());
		usrDb.setUserGroup(usr.getUserGroup());
//		u.setIcon(usr.getIcon());
		usrDb.setAddr(usr.getAddr());
		
		
		usrDao.update(usrDb);
		
		int r= 1;
		return r;
	}
	@Override
	public void delete(Usr usr) {
		String ids = usr.getIds();
		if (!Util.isEmpty(ids)) {
			String stringArray[] = ids.split(",");
			for (String str : stringArray) {
				if (!Util.isEmpty(str)) {
					String cid = str.trim();
					Usr usrDb = usrDao.get(Usr.class, cid);
					usrDao.delete(usrDb);
				}
			}
		}
	}

	@Override
	public Usr findByCid(String cid) {
		if (!Util.isEmpty(cid)) {
			Usr usr = usrDao.get(Usr.class, cid);
			if (Util.isEmpty(usr)) {
				return null;
			} else {
				return usr;
			}
		}
		return null;
	}

	@Override
	public List<Usr> findAll() {
		List<Usr> usrList = usrDao.find("from Usr ");
		return usrList;
	}
	@Override
	public void updateIcon(Usr usrDb) {
		usrDao.update(usrDb);
	}
	@Override
	public void updateAddr(Usr usrDb) {
		usrDao.update(usrDb);
		
	}
}
