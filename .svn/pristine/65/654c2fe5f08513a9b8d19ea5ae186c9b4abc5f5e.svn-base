package com.wlg.Model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by LvLiangFeng on 2016/11/22.
 */
@Entity
public class ResourcesRole implements Serializable {
    private String roleid;
    private String resid;
    private String rolename;
    private String resname;


    @Id
    @Column(length = 32)
    public String getResid() {
        return resid;
    }

    @Id
    @Column(length = 32)
    public String getRoleid() {
        return roleid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getResname() {
        return resname;
    }

    public void setResname(String resname) {
        this.resname = resname;
    }
}
