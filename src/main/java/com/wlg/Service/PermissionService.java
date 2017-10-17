package com.wlg.Service;

import com.wlg.Model.PageBean;
import java.util.List;
import com.wlg.Model.Permission;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

public interface PermissionService {
    /**
     * 保存Permission实体
     * @param permission
     * @return
     */
    int savePermission(Permission permission);

    /**
     * 保存Permission实体
     * @return
     */
    int add_DefultPermission(String Prefix);

    /**
     * 更新Permission实体
     * @param permission
     * @return
     */
    int updatePermission(Permission permission);

    /**
     * 删除Permission实体
     * @param permission
     * @return
     */
    int deletePermission(Permission permission);

    /**
     * 根据实体类对象查找所有记录
     * @param permission
     * @return
     */
    List<Permission> getPermissionList(Permission permission);

    /**
     * 分页查询Permission列表
     * @param page 页数
     * @param permission 实体对象
     * @return PageBean
     */
    PageBean sreachPermissionForPage(Integer page, Permission permission);

}