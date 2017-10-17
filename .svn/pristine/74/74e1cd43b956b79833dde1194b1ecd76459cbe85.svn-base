package com.wlg.Service;

import com.wlg.Model.PageBean;
import java.util.List;
import java.util.Map;

import com.wlg.Model.Resources;
import com.wlg.Model.ResourcesRole;
import com.wlg.Model.Role;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

public interface RoleService {
    /**
     * 保存Role实体
     * @param role
     * @return
     */
    int saveRole(Role role);

    /**
     * 更新Role实体
     * @param role
     * @return
     */
    int updateRole(Role role);

    /**
     * 删除Role实体
     * @param role
     * @return
     */
    int deleteRole(Role role);

    /**
     * 根据实体类对象查找所有记录
     * @param role
     * @return
     */
    List<Role> getRoleList(Role role);

    /**
     * 根据用户名获取角色ID 没有则生成
     * @param userName
     * @return
     */
    Role RoleByUserName(String userName);

    /**
     * 分页查询Role列表
     * @param page 页数
     * @param role 实体对象
     * @return PageBean
     */
    PageBean sreachRoleForPage(Integer page, Role role,Map<String,Object> map);

    List<Role> searchRolesByResourceId(String roleid);

    void saveRoleResources(List<ResourcesRole> rrs);

}