package com.wlg.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by wlg on 2016/6/14.
 */
@Entity
public class UserRole {
    private String id;
    private String userid;
    private String roleid;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    public String getId() {
        return id;
    }

    @Column(length = 32,nullable=false)
    public String getUserid() {
        return userid;
    }

    @Column(length = 32,nullable=false)
    public String getRoleid() {
        return roleid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public void setId(String id) {
        this.id = id;
    }
}
