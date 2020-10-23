package com.hodo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodo.bean.Act;
import com.hodo.common.base.DataGrid;
import com.hodo.common.util.Util;
import com.hodo.dao.BaseDaoI;

@Service(value = "actService")
public class ActService implements ActServiceI {

	@Autowired
	private BaseDaoI<Act> actDao;
	
	@Override
	public DataGrid datagrid(Act act) {
		String hql=" from Act t where 1=1 ";
		DataGrid j = new DataGrid();
		j.setRows(editActs(find(act,hql)));
		j.setTotal(total(act,hql));
		return j;
	}
	private List<Act> editActs(List<Act> actList) {
		if (actList != null && actList.size() > 0) {
			for (Act acts: actList) {
//				Good g =acts.getGood();
//				acts.setName(g.getName());
//				acts.setReasons(g.getReasons());
//				acts.setPic(g.getPic());
//				acts.setPrice(g.getPrice());
//				acts.setOldLevel(g.getOldLevel());
//				acts.setActNum(g.getActNum());
//				acts.setBrand(g.getBrand());
//				acts.setCls(g.getCls());
//				
//				acts.setBuyTimeString(DateUtil.dateToString(g.getBuyTime(), "yyyy-MM-dd"));
//				String deliMode = g.getDeliMode();
//				switch (deliMode) {
//				case "yj":
//					acts.setDeliMode("邮寄");
//					break;
//				case "shsm":
//					acts.setDeliMode("送货上门");
//					break;
//				default:
//					break;
//				}
//				acts.setCrealname(g.getUsr().getCrealname());
//				acts.setTel(g.getUsr().getTel());
//				acts.setAddr(g.getUsr().getAddr());
//				
//				String status = g.getStatus();
//				if(Util.isEmpty(status)){
//					acts.setStatus("");
//				} else {
//					switch (status) {
//					case "1":
//						acts.setStatus("已售");
//						break;
//					case "2":
//						acts.setStatus("已换");
//						break;
//					default:
//						break;
//					}
//				}
//				String isChange = g.getIsChange();
//				if(Util.isEmpty(isChange)){
//					acts.setIsChange("");
//				} else {
//					switch (isChange) {
//					case "1":
//						acts.setIsChange("是");
//						break;
//					default:
//						acts.setIsChange("否");
//						break;
//					}
//				}
			}
		}
		return actList;
	}
	private List<Act> find(Act act,String hql) {
		hql = addWhere(act, hql);
//		if (act.getSort() != null && act.getOrder() != null) {
//			hql += " order by " + act.getSort() + " " + act.getOrder();
//		}else {
//			hql+=" order by t.pubDate desc,t.creatDate desc";
//		}
		List<Act> actList = actDao.find(hql, act.getPage(), act.getRows());
		return actList;
	}
	private Long total(Act act,String hql) {
		hql = addWhere(act, hql);
		hql = "select count(*) "+hql;
		return actDao.count(hql);
	}
	private String addWhere(Act act, String hql) {
//		if(!Util.isEmpty(act.getUserId())){
//			hql += " and t.usr.cid='"+act.getUserId()+"' ";
//		}
//		if(!Util.isEmpty(act.getTit())){
//			hql += " and t.req.tit like '%"+act.getTit()+"%' ";
//		}
//		if(!Util.isEmpty(act.getCname())){
//			hql += " and t.cname like '%" + act.getCname() + "%' ";
//		}
//		
//		if(!Util.isEmpty(act.getUserGroup())){
//			if("1".equals(act.getUserGroup())) {
//				hql += " and t.userGroup='" + act.getUserGroup() + "' ";
//			}
//			if("0".equals(act.getUserGroup())) {
//				hql += " and t.userGroup in('2','3','4')";
//			}
//		}
		return hql;
	}
	
	
	@Override
	public void addAct(Act act) {
		actDao.save(act);
	}

	
	@Override
	public int update(Act act) {
		Act actDb = actDao.get(Act.class, act.getCid());
//		actDb.setEmail(act.getEmail());
		actDao.update(actDb);
		int r= 1;
		return r;
	}

	@Override
	public void delete(Act act) {
		String ids = act.getCid();
		if (!Util.isEmpty(ids)) {
			String stringArray[] = ids.split(",");
			for (String str : stringArray) {
				if (!Util.isEmpty(str)) {
					String cid = str.trim();
					Act actDb = actDao.get(Act.class, cid);
					actDao.delete(actDb);
				}
			}
		}
	}

	@Override
	public Act findByCid(String cid) {
		if (!Util.isEmpty(cid)) {
			Act act = actDao.get(Act.class, cid);
			if (Util.isEmpty(act)) {
				return null;
			} else {
				return act;
			}
		}
		return null;
	}

	@Override
	public List<Act> findAll() {
		List<Act> actList = actDao.find("from Act");
		return actList;
	}
	@Override
	public List<Act> findByUsrCid(String usrCid) {
		List<Act> actList = actDao.find("from Act a where a.usr.cid='"+usrCid+"' ");
		return actList;
	}
	@Override
	public List<Act> findByClsAndKey(String cls, String key) {
		if(Util.isEmpty(cls) || "all".equals(cls)){
			if(!Util.isEmpty(key)) {
				List<Act> actList = actDao.find("from Act where nm like '%"+key+"%' and status='通过' ");
				return actList;
			} else {
				List<Act> actList = actDao.find("from Act where status='通过' ");
				return actList;
			}
		} else {
			List<Act> actList = actDao.find("from Act where cls='"+cls+"' and status='通过' ");
			return actList;
		}
	}
	@Override
	public List<Act> findByStatus(String status) {
		List<Act> actList = actDao.find("from Act where status='"+status+"' ");
		return actList;
	}
	
	
}
