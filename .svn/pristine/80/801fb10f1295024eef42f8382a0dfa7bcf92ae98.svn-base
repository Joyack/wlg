package com.wlg.Service;

import com.wlg.Model.PageBean;
import java.util.List;
import java.util.Set;

import com.wlg.Model.UserRole;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

public interface UserRoleService {
    /**
     * 保存UserRole实体
     * @param userRole
     * @return
     */
    int saveUserRole(UserRole userRole);

    /**
     * 更新UserRole实体
     * @param userRole
     * @return
     */
    int updateUserRole(UserRole userRole);

    /**
     * 删除UserRole实体
     * @param userRole
     * @return
     */
    int deleteUserRole(UserRole userRole);

    /**
     * 根据实体类对象查找所有记录
     * @param userRole
     * @return
     */
    List<UserRole> getUserRoleList(UserRole userRole);

    /**
     * 分页查询UserRole列表
     * @param page 页数
     * @param userRole 实体对象
     * @return PageBean
     */
    PageBean sreachUserRoleForPage(Integer page, UserRole userRole);

    Set<String> sreachRolesForUserName(String userName);

}