package com.wlg.Dao;

import com.wlg.Model.GoodsInfo;
import com.wlg.Model.PageBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/27 0027.
 */
public interface GoodsDao {

    public List<String> queryMapsBypage(String GoodTypeId);
    public GoodsInfo queryGoodsById(String gid);
    public int deleteGoods(GoodsInfo goodsInfo);
    public <T>Serializable saveGoods(GoodsInfo goodsInfo);
    public int updateGoods(GoodsInfo goodsInfo);
    public PageBean getGoodsByName(String gname);
}
