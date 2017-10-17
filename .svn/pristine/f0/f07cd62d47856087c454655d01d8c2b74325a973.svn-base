package com.wlg.Dao;

import com.wlg.Model.PageBean;

import java.util.List;
import java.util.Map;

public interface MemberDao {
//省略了其他的代码

    /**
     * 查询所有记录数
     * @param hql 查询的条件
     * @return 总记录数
     */
    public <T> int getAllRowCount(String hql);

    
    /**
     * 分页查询
     * @param hql 查询的条件
     * @param offset 开始记录
     * @param length 一次查询几条记录
     * @return
     */
	@SuppressWarnings("rawtypes")
	public PageBean queryForPage(final String hql, final int offset, final int length);



    public <T> int getAllRowCountBySql(String sql,Object... objects);

    public PageBean queryBySqlForPage(String sql);

    PageBean queryForPageByParams(int pageSize, int pageno, String sql);

    PageBean queryMapsBypage(Object[] params, int page, int pageSize, String sql) ;


}
