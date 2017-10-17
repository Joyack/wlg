package com.wlg.Test;

import com.wlg.Model.User;

import java.util.Comparator;

/**
 * Created by LvLiangFeng on 2016/12/22.
 */
public class CusCompare implements Comparator {
    @Override
    public int compare(Object object1, Object object2) {
        User u1 = (User)object1;
        User u2 = (User)object2;
        if(Integer.valueOf(u1.getId())>Integer.valueOf(u2.getId()))
                return 1;
            else
                return -1;
        }
}
