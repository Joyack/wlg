package com.wlg.Service.Impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.wlg.Dao.MemberDao;
import com.wlg.Model.PageBean;
import com.wlg.Service.MemberService;
import com.wlg.Util.HqlParamUtil;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.Member;
import org.springframework.stereotype.Component;


@Component
public class MemberServiceImp implements MemberService {
			@Resource
			private MemberDao memberDao;
		    
		    /**
		     * 分页查询
		     * @param
		     * @param pageSize 每页大小
		     * @return 封闭了分页信息(包括记录集list)的Bean
		     */
		    @SuppressWarnings("unchecked")
			public <T> PageBean queryForPage(int pageSize, int page, String hql, T entity, String order){

				//这里加入多条件查询
				if(entity!=null)
					hql = hql + HqlParamUtil.getFieldValue(entity);//自动增加参数
				if(!StringUtils.isEmpty(order))
					hql = hql + order;

		        // final String hql = "from SiWatch";        //查询语句
		        int allRow = memberDao.getAllRowCount(hql);    //总记录数
		        //int totalPage = PageBean.countTotalPage(pageSize, allRow);    //总页数
		       // int pages = PageBean.countCurrentPage(page);//刚刚加进来的，没处理
		       // final int offset = PageBean.getFirst(pageSize, pages);    //当前页开始记录
		        final int length = pageSize;    //每页记录数
		       // final int currentPage = PageBean.countCurrentPage(pages);
		        PageBean<T> pageBean = memberDao.queryForPage(hql,1, length);        //"一页"的记录
		        

		        return pageBean;
		    }


	/**
	 * 分页查询
	 * @param
	 * @param pageSize 每页大小
	 * @return 封闭了分页信息(包括记录集list)的Bean
	 */
		@SuppressWarnings("unchecked")
		public <T> PageBean queryBySQLForPage(int pageSize, int page, String sql,Object...objects){

			// final String hql = "from SiWatch";        //查询语句
			//int allRow = memberDao.getAllRowCountBySql(sql, objects);    //总记录数
			//int totalPage = Pag (pageSize, allRow);    //总页数
			//int pages = PageBean.countCurrentPage(page);//刚刚加进来的，没处理
			//final int offset = PageBean.countOffset(pageSize, pages);    //当前页开始记录
			final int length = pageSize;    //每页记录数
			//final int currentPage = PageBean.countCurrentPage(pages);
			PageBean list = memberDao.queryBySqlForPage(sql);        //"一页"的记录


			return list;
		}

	@Override
	public <T> PageBean queryForPageByParams(int pageSize, int pageno, String sql, Map<String, Object> params) {
		return memberDao.queryForPageByParams(pageSize,pageno,sql);
	}
}
