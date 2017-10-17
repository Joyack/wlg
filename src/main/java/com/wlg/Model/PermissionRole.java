package com.wlg.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by LvLiangFeng on 2016/11/22.
 */
@Entity
public class PermissionRole implements Serializable{
    private String permid;
    private String roleid;

    @Id
    @Column(length = 32,nullable=false)
    public String getPermid() {
        return permid;
    }

    public void setPermid(String permid) {
        this.permid = permid;
    }

    @Id
    @Column(length = 32,nullable=false)
    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
}
