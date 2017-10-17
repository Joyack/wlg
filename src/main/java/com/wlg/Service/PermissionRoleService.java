package com.wlg.Service;

import com.wlg.Model.PageBean;
import java.util.List;
import com.wlg.Model.PermissionRole;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

public interface PermissionRoleService {
    /**
     * 保存PermissionRole实体
     * @param permissionRole
     * @return
     */
    int savePermissionRole(PermissionRole permissionRole);

    /**
     * 更新PermissionRole实体
     * @param permissionRole
     * @return
     */
    int updatePermissionRole(PermissionRole permissionRole);

    /**
     * 删除PermissionRole实体
     * @param permissionRole
     * @return
     */
    int deletePermissionRole(PermissionRole permissionRole);

    /**
     * 根据实体类对象查找所有记录
     * @param permissionRole
     * @return
     */
    List<PermissionRole> getPermissionRoleList(PermissionRole permissionRole);

    /**
     * 分页查询PermissionRole列表
     * @param page 页数
     * @param permissionRole 实体对象
     * @return PageBean
     */
    PageBean sreachPermissionRoleForPage(Integer page, PermissionRole permissionRole);

    /**
     *生成角色权限
     * @return
     */
    String getFilterChainDefinitionsForPermission();
}