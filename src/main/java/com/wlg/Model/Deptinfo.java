package com.wlg.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by wlg on 2016/6/13.
 */
@Entity
public class Deptinfo {
    private String id;
    private String deptid;//部门编号
    private String deptname;//用户名
    private String status;//是否有效


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36)
    public String getId() {
        return id;
    }
    @Column(length = 36, nullable=true)
    public String getDeptname() {
        return deptname;
    }
    @Column(length = 36, nullable=true)
    public String getStatus() {
        return status;
    }
    @Column(length = 36, nullable=true)
    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
