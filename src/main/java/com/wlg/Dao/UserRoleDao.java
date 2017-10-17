package com.wlg.Dao;

import com.wlg.Model.UserRole;
import java.io.Serializable;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

public interface UserRoleDao {
    <T> Serializable saveUserRole(UserRole userRole);

    <T> void updateUserRole(UserRole userRole);

    <T> void deleteUserRole(UserRole userRole);
}
