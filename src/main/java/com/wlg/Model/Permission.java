package com.wlg.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by LvLiangFeng on 2016/11/22.
 */
@Entity
public class Permission {
    private String id;
    private String url;
    private String resourceid;
    private String userid;
    private String roleid;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(length = 500,nullable=true)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Column(length = 36,nullable=true)
    public String getUserid() {
        return userid;
    }
    @Column(length = 36,nullable=true)
    public String getRoleid() {
        return roleid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
    @Column(length = 36,nullable=true)
    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }
}
