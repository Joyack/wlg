package com.wlg.Service;

import com.wlg.Model.GoodsInfo;
import com.wlg.Model.PageBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/27 0027.
 */
public interface GoodsService {

    public PageBean<Map<String,Object>> queryGoodsBySql(int page, int pageSize, Map<String, Object> param);
    public GoodsInfo queryGoodsById(String id);
    public PageBean queryGoodsByName(String id);
    public int deleteGoods(GoodsInfo goodsInfo);
    public int save(GoodsInfo goodsInfo);
    public int updateGoods(GoodsInfo goodsInfo);
    public PageBean getGoodsByName(String gname);
    public String getNextGoodsId();
    public List<Map<String,Object>> getAllGoodsName();
    public List<Map<String,Object>> isExistsGoods(Map<String,Object> params);
}
