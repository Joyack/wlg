package com.wlg.Service;

import com.wlg.Model.PageBean;
import com.wlg.Util.HqlParamUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public interface MemberService {
    //省略其他的代码

    /**
     * 分页查询
     * @param currentPage 当前第几页
     * @param pageSize 每页大小
     * @return 封闭了分页信息(包括记录集list)的Bean
     */
	<T> PageBean queryForPage(int pageSize, int currentPage, String hql, T entity,String order);
    <T> PageBean queryBySQLForPage(int pageSize, int page, String sql,Object...objects);

    <T> PageBean queryForPageByParams(int pageSize, int pageno, String sql, Map<String,Object> params);

}