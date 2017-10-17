package com.wlg.Dao.Impl;
import java.util.List;
import java.util.Map;

import com.wlg.Dao.BaseDao;
import com.wlg.Dao.MemberDao;
import com.wlg.Model.PageBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MemberDaoImpl extends com.wlg.Dao.Impl.hibernate.BaseDao implements MemberDao {
	/**
	 * 注入一个sessionFactory属性,并注入到父类(HibernateDaoSupport)
	 * **/
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Resource
	private BaseDao baseDao;

	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	    /**
	     * 分页查询
	     * @param hql 查询的条件
	     * @param offset 开始记录
	     * @param length 一次查询几条记录
	     * @return
	     */
		@SuppressWarnings("rawtypes")
		public PageBean queryForPage(final String hql,final int offset,final int length){
	    			Query query = this.getSession().createQuery(hql);
	                query.setFirstResult(offset);
	                query.setMaxResults(length);
	                List list = query.list();
	                PageBean pageBean=new PageBean();
	                pageBean.setTotalCount(list.size());
                    pageBean.setPageNo(1);
                    pageBean.setResult(list);
                    pageBean.setPageSize(10);
	                return pageBean;

	    }


		@Override
		public <T> int getAllRowCountBySql(String sql,Object...objects) {
			String sqls[] = sql.split("FROM");
			sql = "SELECT count(1) as counts FROM " + sqls[1];
			return Integer.valueOf(String.valueOf(this.baseDao.findForJdbc(sql,objects).get(0).get("counts")));
		}

		@Override
		public PageBean queryBySqlForPage(String sql) {
			//sql = sql  + " limit " + offset +","+length;
			sql = sql  ;
			List list= this.baseDao.findForJdbc(sql);
			PageBean pageBean=new PageBean();
			pageBean.setTotalCount(list.size());
			pageBean.setPageNo(1);
			pageBean.setResult(list);
			pageBean.setPageSize(10);
			return pageBean;
		}

	@Override
	public PageBean queryForPageByParams(int pageSize, int pageno, String sql) {
			List list=this.baseDao.queryForList(sql);
		PageBean pageBean=new PageBean();
		pageBean.setTotalCount(list.size());
		pageBean.setPageNo(pageno);
		pageBean.setResult(list);
		pageBean.setPageSize(pageSize);
		return pageBean;
	}


	/**
	     * 查询所有记录数
	     * @return 总记录数
	     */
	    public int getAllRowCount(String hql){
	        return this.getSession().createQuery(hql).list().size();
	    }


	@Override
	public PageBean<Map<String, Object>> queryMapsBypage(Object[] params, int page, int pageSize, String sql) {
		return queryEntitys( sql, params,page, pageSize);
	}

	}
