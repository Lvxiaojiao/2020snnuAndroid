package com.hodo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodo.bean.Partin;
import com.hodo.common.base.DataGrid;
import com.hodo.common.util.Util;
import com.hodo.dao.BaseDaoI;

@Service(value = "partinService")
public class PartinService implements PartinServiceI {

	@Autowired
	private BaseDaoI<Partin> partinDao;
	
	@Override
	public DataGrid datagrid(Partin partin) {
		String hql=" from Partin t where 1=1 ";
		DataGrid j = new DataGrid();
		j.setRows(editPartins(find(partin,hql)));
		j.setTotal(total(partin,hql));
		return j;
	}
	private List<Partin> editPartins(List<Partin> partinList) {
		if (partinList != null && partinList.size() > 0) {
			for (Partin partins: partinList) {
//				Good g =partins.getGood();
//				partins.setName(g.getName());
//				partins.setReasons(g.getReasons());
//				partins.setPic(g.getPic());
//				partins.setPrice(g.getPrice());
//				partins.setOldLevel(g.getOldLevel());
//				partins.setPartinNum(g.getPartinNum());
//				partins.setBrand(g.getBrand());
//				partins.setCls(g.getCls());
//				
//				partins.setBuyTimeString(DateUtil.dateToString(g.getBuyTime(), "yyyy-MM-dd"));
//				String deliMode = g.getDeliMode();
//				switch (deliMode) {
//				case "yj":
//					partins.setDeliMode("邮寄");
//					break;
//				case "shsm":
//					partins.setDeliMode("送货上门");
//					break;
//				default:
//					break;
//				}
//				partins.setCrealname(g.getUsr().getCrealname());
//				partins.setTel(g.getUsr().getTel());
//				partins.setAddr(g.getUsr().getAddr());
//				
//				String status = g.getStatus();
//				if(Util.isEmpty(status)){
//					partins.setStatus("");
//				} else {
//					switch (status) {
//					case "1":
//						partins.setStatus("已售");
//						break;
//					case "2":
//						partins.setStatus("已换");
//						break;
//					default:
//						break;
//					}
//				}
//				String isChange = g.getIsChange();
//				if(Util.isEmpty(isChange)){
//					partins.setIsChange("");
//				} else {
//					switch (isChange) {
//					case "1":
//						partins.setIsChange("是");
//						break;
//					default:
//						partins.setIsChange("否");
//						break;
//					}
//				}
			}
		}
		return partinList;
	}
	private List<Partin> find(Partin partin,String hql) {
		hql = addWhere(partin, hql);
//		if (partin.getSort() != null && partin.getOrder() != null) {
//			hql += " order by " + partin.getSort() + " " + partin.getOrder();
//		}else {
//			hql+=" order by t.pubDate desc,t.creatDate desc";
//		}
		List<Partin> partinList = partinDao.find(hql, partin.getPage(), partin.getRows());
		return partinList;
	}
	private Long total(Partin partin,String hql) {
		hql = addWhere(partin, hql);
		hql = "select count(*) "+hql;
		return partinDao.count(hql);
	}
	private String addWhere(Partin partin, String hql) {
//		if(!Util.isEmpty(partin.getUserId())){
//			hql += " and t.usr.cid='"+partin.getUserId()+"' ";
//		}
//		if(!Util.isEmpty(partin.getTit())){
//			hql += " and t.req.tit like '%"+partin.getTit()+"%' ";
//		}
//		if(!Util.isEmpty(partin.getCname())){
//			hql += " and t.cname like '%" + partin.getCname() + "%' ";
//		}
//		
//		if(!Util.isEmpty(partin.getUserGroup())){
//			if("1".equals(partin.getUserGroup())) {
//				hql += " and t.userGroup='" + partin.getUserGroup() + "' ";
//			}
//			if("0".equals(partin.getUserGroup())) {
//				hql += " and t.userGroup in('2','3','4')";
//			}
//		}
		return hql;
	}
	
	
	@Override
	public void addPartin(Partin partin) {
		partinDao.save(partin);
	}

	
	@Override
	public int update(Partin partin) {
		Partin partinDb = partinDao.get(Partin.class, partin.getCid());
//		partinDb.setEmail(partin.getEmail());
		partinDao.update(partinDb);
		int r= 1;
		return r;
	}

	@Override
	public void delete(Partin partin) {
		String ids = partin.getCid();
		if (!Util.isEmpty(ids)) {
			String stringArray[] = ids.split(",");
			for (String str : stringArray) {
				if (!Util.isEmpty(str)) {
					String cid = str.trim();
					Partin partinDb = partinDao.get(Partin.class, cid);
					partinDao.delete(partinDb);
				}
			}
		}
	}

	@Override
	public Partin findByCid(String cid) {
		if (!Util.isEmpty(cid)) {
			Partin partin = partinDao.get(Partin.class, cid);
			if (Util.isEmpty(partin)) {
				return null;
			} else {
				return partin;
			}
		}
		return null;
	}

	@Override
	public List<Partin> findAll() {
		List<Partin> partinList = partinDao.find("from Partin");
		return partinList;
	}

	

	@Override
	public List<Partin> findByUsrCid(String usrCid,String tp) {
		List<Partin> partinList = partinDao.find("from Partin t where t.usr.cid='"+usrCid+"' and t.tp='"+tp+"' ");
		return partinList;
	}
	
	@Override
	public List<Partin> findByUsrCid(String usrCid) {
		List<Partin> partinList = partinDao.find("from Partin t where t.usr.cid='"+usrCid+"' ");
		return partinList;
	}
	
	@Override
	public List<Partin> findByActCid(String actCid,String tp) {
		List<Partin> partinList = partinDao.find("from Partin t where t.act.cid='"+actCid+"' and t.tp='"+tp+"' ");
		return partinList;
	}

	@Override
	public List<Partin> findByActCidUsrCidTp(String actCid, String usrCid, String tp) {
		String sql= "from Partin t where 1=1 ";
		if(!Util.isEmpty(actCid)){
			sql+=" and act.cid='"+actCid+"' ";
		} 
		if(!Util.isEmpty(usrCid)){
			sql+=" and usr.cid='"+usrCid+"' ";
		} 
		if(!Util.isEmpty(tp)){
			sql+=" and tp='"+tp+"' ";
		} 
		List<Partin> partinList = partinDao.find(sql);
		return partinList;
	}

	
}
