package com.wlg.Model;

import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;

public class PageBean<T> {
//
//
//	@SuppressWarnings("unchecked")
//	private List list;        //要返回的某一页的记录列表
//
//    private int allRow;         //总记录数
//    private int totalPageBean;        //总页数
//    private int currentPageBean;    //当前页
//    private int PageBeanSize;        //每页记录数
//
//    @SuppressWarnings("unused")
//	private boolean isFirstPageBean;    //是否为第一页
//    @SuppressWarnings("unused")
//	private boolean isLastPageBean;        //是否为最后一页
//    @SuppressWarnings("unused")
//	private boolean hasPreviousPageBean;    //是否有前一页
//    @SuppressWarnings("unused")
//	private boolean hasNextPageBean;        //是否有下一页
//
//
//	@SuppressWarnings("rawtypes")
//	public List getList() {
//        return list;
//    }
//	@SuppressWarnings("rawtypes")
//	public void setList(List list) {
//        this.list = list;
//    }
//    public int getAllRow() {
//        return allRow;
//    }
//    public void setAllRow(int allRow) {
//        this.allRow = allRow;
//    }
//    public int getTotalPageBean() {
//        return totalPageBean;
//    }
//    public void setTotalPageBean(int totalPageBean) {
//        this.totalPageBean = totalPageBean;
//    }
//    public int getCurrentPageBean() {
//        return currentPageBean;
//    }
//    public void setCurrentPageBean(int currentPageBean) {
//        this.currentPageBean = currentPageBean;
//    }
//    public int getPageBeanSize() {
//        return PageBeanSize;
//    }
//    public void setPageBeanSize(int PageBeanSize) {
//        this.PageBeanSize = PageBeanSize;
//    }
//
//    /**
//     * 初始化分页信息
//     */
//    public void init(){
//        this.isFirstPageBean = isFirstPageBean();
//        this.isLastPageBean = isLastPageBean();
//        this.hasPreviousPageBean = isHasPreviousPageBean();
//        this.hasNextPageBean = isHasNextPageBean();
//    }
//
//    /**
//     * 以下判断页的信息,只需getter方法(is方法)即可
//     * @return
//     */
//
//    public boolean isFirstPageBean() {
//        return currentPageBean == 1;    // 如是当前页是第1页
//    }
//    public boolean isLastPageBean() {
//        return currentPageBean == totalPageBean;    //如果当前页是最后一页
//    }
//    public boolean isHasPreviousPageBean() {
//        return currentPageBean != 1;        //只要当前页不是第1页
//    }
//    public boolean isHasNextPageBean() {
//        return currentPageBean != totalPageBean;    //只要当前页不是最后1页
//    }
//
//
//    /**
//     * 计算总页数,静态方法,供外部直接通过类名调用
//     * @param PageBeanSize 每页记录数
//     * @param allRow 总记录数
//     * @return 总页数
//     */
//    public static int countTotalPageBean(final int PageBeanSize,final int allRow){
//        int totalPageBean = allRow % PageBeanSize == 0 ? allRow/PageBeanSize : allRow/PageBeanSize+1;
//        return totalPageBean;
//    }
//
//    /**
//     * 计算当前页开始记录
//     * @param PageBeanSize 每页记录数
//     * @param currentPageBean 当前第几页
//     * @return 当前页开始记录号
//     */
//    public static int countOffset(final int PageBeanSize,final int currentPageBean){
//        final int offset = PageBeanSize*(currentPageBean-1);
//        return offset;
//    }
//
//    /**
//     * 计算当前页,若为0或者请求的URL中没有"?PageBean=",则用1代替
//     * @param PageBean 传入的参数(可能为空,即0,则返回1)
//     * @return 当前页
//     */
//    public static int countCurrentPageBean(int PageBean){
//        final int curPageBean = (pageBean==0?1:PageBean);
//        return curPageBean;
//    }










    /**
     * 文件名：PageBean.java
     * 创建日期： 2012-6-12
     * Copyright (c) 2011-2011 广电运通
     * All rights reserved.

     * 修改记录：
     * 1.修改时间, 修改人：
     *   修改内容：
     */




        // -- 公共变量 --//
        /** 升序 */
        public static final String ASC = "asc";
        /** 降序 */
        public static final String DESC = "desc";

        // -- 分页参数 --//
        /** 页号 */
        protected int pageNo = 1;
        /** 页大小 */
        protected int pageSize = 1;
        /** 排序field */
        protected String orderBy;
        /** 排序方式: ASC/DESC */
        protected String order = ASC;
        /** 是否自动统计记录条数 */
        protected boolean autoCount = true;

        // -- 返回结果 --//
        /** 返回记录结果 */
        protected List<T> result = Collections.emptyList();
        /** 记录条数 */
        protected long totalCount = 0;

        /** -- 构造函数 -- */
        public PageBean() {
        }
    public PageBean(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

        /**
         * 带参构造函数
         *
         *            每页大小
         */
        public PageBean(int pageSize) {
            this.pageSize = pageSize;
        }

        // -- 访问查询参数函数 --//
        /**
         * 获得当前页的页号,序号从1开始,默认为1.
         *
         * @return 页号
         */
    public int getPageNo() {
        return pageNo;
    }

    /**
         * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
         *
         * @param pageNo
         *            页号
         */
        public void setPageNo(final int pageNo) {
            this.pageNo = pageNo;

            if (pageNo < 1) {
                this.pageNo = 1;
            }
        }

        /**
         * 获得每页的记录数量,默认为1.
         *
         * @return 返回页面大小
         */
        public int getPageSize() {
            return pageSize;
        }

        /**
         * 设置每页的记录数量,低于1时自动调整为1.
         *
         *            页面大小
         */
        public void setPageSize(final int pageSize) {
            this.pageSize = pageSize;

            if (pageSize < 1) {
                this.pageSize = 1;
            }
        }

        /**
         * 根据PageBeanNo和PageBeanSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
         *
         * @return 页面第一条记录的序号
         */
        public int getFirst() {
            return ((pageNo - 1) * pageSize) + 1;
        }

        /**
         * 获得排序字段,无默认值.多个排序字段时用','分隔.
         *
         * @return 排序字段
         */
        public String getOrderBy() {
            return orderBy;
        }

        /**
         * 设置排序字段,多个排序字段时用','分隔.
         *
         * @param orderBy
         *            排序字段
         */
        public void setOrderBy(final String orderBy) {
            this.orderBy = orderBy;
        }

        /**
         * 获得排序方向.
         *
         * @return 排序方向
         */
        public String getOrder() {
            return order;
        }

        /**
         * 设置排序方式向.
         *
         * @param order
         *            可选值为desc或asc,多个排序字段时用','分隔.
         */
        public void setOrder(final String order) {
            // 检查order字符串的合法值
            String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
            for (String orderStr : orders) {
                if (!StringUtils.equals(DESC, orderStr)
                        && !StringUtils.equals(ASC, orderStr))
                    throw new IllegalArgumentException("order orientation"
                            + orderStr + "not legal");
            }

            this.order = StringUtils.lowerCase(order);
        }

        /**
         * 是否已设置排序字段,无默认值.
         *
         * @return 是否已设置排序字段
         */
        public boolean isOrderBySetted() {
            return (StringUtils.isNotBlank(orderBy) && StringUtils
                    .isNotBlank(order));
        }

        /**
         * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
         *
         * @return 是否自动统计
         */
        public boolean isAutoCount() {
            return autoCount;
        }

        /**
         * 查询对象时是否自动另外执行count查询获取总记录数.
         *
         * @param autoCount
         *            是否自动统计
         */
        public void setAutoCount(final boolean autoCount) {
            this.autoCount = autoCount;
        }

        /**
         * 设置是否自动统计
         *
         * @param theAutoCount
         *            是否自动统计
         * @return 当前页面对象
         */
        public PageBean<T> autoCount(final boolean theAutoCount) {
            setAutoCount(theAutoCount);
            return this;
        }

        // -- 访问查询结果函数 --//

        /**
         * 取得页内的记录列表.
         *
         * @return 返回查询结果
         */
        public List<T> getResult() {
            return result;
        }

        /**
         * 设置页内的记录列表.
         *
         * @param result
         *            查询结果
         */
        public void setResult(final List<T> result) {
            this.result = result;
        }

        /**
         * 取得总记录数, 默认值为0.
         *
         * @return 记录总数
         */
        public long getTotalCount() {
            return totalCount;
        }

        /**
         * 设置总记录数.
         *
         * @param totalCount
         *            设置记录总数
         */
        public void setTotalCount(final long totalCount) {
            this.totalCount = totalCount;
        }

        /**
         * 根据PageBeanSize与totalCount计算总页数, 默认值为0.
         *
         * @return 总页数
         */
        public long getTotalPageBeans() {
            if (totalCount < 0)
                return 0;

            long count = totalCount / pageSize;
            if (totalCount % pageSize > 0) {
                count++;
            }
            return count;
        }

        /**
         * 是否还有下一页.
         *
         * @return 是否有下一页
         */
        public boolean isHasNext() {
            return (pageNo + 1 <= getTotalPageBeans());
        }

        /**
         * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
         *
         * @return 下页页号
         */
        public int getNextPage() {
            if (isHasNext())
                return pageNo + 1;
            else
                return pageNo;
        }

        /**
         * 是否还有上一页.
         *
         * @return 返回是否有前一页
         */
        public boolean isHasPre() {
            return (pageNo - 1 >= 1);
        }

        /**
         * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
         *
         * @return 返回前一页叶号
         */
        public int getPrePage() {
            if (isHasPre())
                return pageNo - 1;
            else
                return pageNo;
        }




}