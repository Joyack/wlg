package com.wlg.Service;

import com.wlg.Model.PageBean;
import java.util.List;
import java.util.Map;

import com.wlg.Model.ResourcesRole;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

public interface ResourcesRoleService {
    /**
     * 保存ResourcesRole实体
     * @param resourcesRole
     * @return
     */
    int saveResourcesRole(ResourcesRole resourcesRole);

    /**
     * 更新ResourcesRole实体
     * @param resourcesRole
     * @return
     */
    int updateResourcesRole(ResourcesRole resourcesRole);

    /**
     * 删除ResourcesRole实体
     * @param resourcesRole
     * @return
     */
    int deleteResourcesRole(ResourcesRole resourcesRole);

    /**
     * 根据userName查找所有记录
     * @param userName
     * @return
     */
    List<Map<String,Object>> getResourcesRoleList(String userName);

    /**
     * 根据userName查找所有记录
     * @param userName
     * @return
     */
    List<Map<String,Object>> getResourcesRoleListByPrincipal(String userName);

    /**
     * 根据实体类对象查找所有记录
     * @param resourcesRole
     * @return
     */
    List<Map<String,Object>> getResourcesRoleList(ResourcesRole resourcesRole);

    /**
     * 分页查询ResourcesRole列表
     * @param page 页数
     * @param resourcesRole 实体对象
     * @return PageBean
     */
    PageBean sreachResourcesRoleForPage(Integer page, ResourcesRole resourcesRole);

    /**
     * 生成过滤角色资源
     * @return
     */
    String getFilterChainDefinitionsForRole();

}