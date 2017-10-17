package com.wlg.Dao;

import com.wlg.Model.Permission;
import java.io.Serializable;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

public interface PermissionDao {
    <T> Serializable savePermission(Permission permission);

    <T> void updatePermission(Permission permission);

    <T> void deletePermission(Permission permission);

}
