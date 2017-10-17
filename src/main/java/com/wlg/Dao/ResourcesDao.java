package com.wlg.Dao;

import com.wlg.Model.Permission;
import com.wlg.Model.Resources;
import java.io.Serializable;
import java.util.List;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

public interface ResourcesDao {
    <T> Serializable saveResources(Resources resources);

    <T> void updateResources(Resources resources);

    <T> void deleteResources(Resources resources);

}
