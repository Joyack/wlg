package com.wlg.Dao;

import com.wlg.Model.Role;
import java.io.Serializable;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

public interface RoleDao {
    <T> Serializable saveRole(Role role);

    int updateRole(Role role);

    int deleteRole(Role role);

    Role getRoleByName(String name);

}
