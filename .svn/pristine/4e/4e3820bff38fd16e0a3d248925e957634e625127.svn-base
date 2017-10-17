package com.wlg.Service;

import com.wlg.Model.PageBean;
import java.util.List;
import java.util.Map;

import com.wlg.Model.Permission;
import com.wlg.Model.Resources;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

public interface ResourcesService {
    /**
     * 保存Resources实体
     * @param resources
     * @return
     */
    int saveResources(Resources resources);

    /**
     * 更新Resources实体
     * @param resources
     * @return
     */
    int updateResources(Resources resources);

    /**
     * 删除Resources实体
     * @param resources
     * @return
     */
    int deleteResources(Resources resources);

    /**
     * 根据实体类对象查找所有记录
     * @param resources
     * @return
     */
    List<Resources> getResourcesList(Resources resources);

    /**
     * 分页查询Resources列表
     * @param page 页数
     * @param resources 实体对象
     * @return PageBean
     */
    PageBean sreachResourcesForPage(Integer page, Resources resources);

    List<Resources> queryResourceByRoleId(String roleId);

    public List<Map<String,Object>> getMenuListByUser(String username,String roleid);
    public List<Map<String,Object>> getMenuListByParentid(int page,int pageSize,String parentid);
    public List<Map<String,Object>> getAllResources();
   public List<Map<String,Object>> getPermissionByRoleorUser(String ruflag,String ruid);
    void saveRoleorUserRes(List<Permission> rrs);
    public Resources getResourceByid(String id);
}