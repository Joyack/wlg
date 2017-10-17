package com.wlg.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Administrator on 2017/6/12 0012.
 */
@Entity
public class SysDataDir {
    private String id;
    private String parentid;
    private String ckey;
    private String cvalue;
    private String cname;
    private int corder ;
    private String note_en ;
    private String cstatus ;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36,nullable=false)
    public String getId() {
        return id;
    }
    @Column(length = 36,nullable=true)
    public String getParentid() {
        return parentid;
    }
    @Column(length = 36,nullable=true)

    public String getCkey() {
        return ckey;
    }

    @Column(length = 36,nullable=true)
    public String getNote_en() {
        return note_en;
    }
    @Column(length = 36,nullable=true)
    public String getCvalue() {
        return cvalue;
    }
    @Column(length = 36,nullable=true)
    public String getCname() {
        return cname;
    }
    @Column(length = 36,nullable=true)
    public int getCorder() {
        return corder;
    }
    @Column(length = 36,nullable=true)
    public String getCstatus() {
        return cstatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public void setCkey(String ckey) {
        this.ckey = ckey;
    }

    public void setCvalue(String cvalue) {
        this.cvalue = cvalue;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setCorder(int corder) {
        this.corder = corder;
    }

    public void setNote_en(String note_en) {
        this.note_en = note_en;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus;
    }


}
