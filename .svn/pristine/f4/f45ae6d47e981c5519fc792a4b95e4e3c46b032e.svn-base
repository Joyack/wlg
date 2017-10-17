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
public class Role {
    private String id;
    private String rname;
    private String name;
    private String rid;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36)
    public String getId() {
        return id;
    }



    @Column(length = 30,nullable=false)
    public String getRname() {
        return rname;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(length = 30,nullable=false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(length = 30,nullable=true)
    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }
}
