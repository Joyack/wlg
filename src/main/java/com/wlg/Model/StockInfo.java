package com.wlg.Model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zj on 2017/5/2 0002.
 */
@Entity
public class StockInfo implements Serializable {


    private String id;

    private  String sid;

    private  String  cgid;
    private String xhid;
    private String sopertype;

    private String sthstate;//物品出库入库状态

    private String gid;

    private String stype1;


    private String sdate;

    private String stype;

    private  String sperson;

    private String snum;

    private String swnum;

    private String auditstatus;


    private String ternoverp;//去向
    private String museto;//物料用途
    private String fgspec;//成品规格型号


    @Column(length = 36,nullable=true)
    public String getTernoverp() {
        return ternoverp;
    }

    public void setTernoverp(String ternoverp) {
        this.ternoverp = ternoverp;
    }
    @Column(length = 36,nullable=true)
    public String getAuditstatus() {
        return auditstatus;
    }

    public void setAuditstatus(String auditstatus) {
        this.auditstatus = auditstatus;
    }
    @Column(length = 36,nullable=true)
    public String getSwnum() {
        return swnum;
    }

    public void setSwnum(String swnum) {
        this.swnum = swnum;
    }
    @Column(length = 36,nullable=true)
    public String getSnum() {
        return snum;
    }

    public void setSnum(String snum) {
        this.snum = snum;
    }


    @Column(length = 36,nullable=true)
    public String getSopertype() {
        return sopertype;
    }

    public void setSopertype(String sopertype) {
        this.sopertype = sopertype;
    }
    @Column(length = 36,nullable=true)
    public String getSthstate() {
        return sthstate;
    }

    public void setSthstate(String sthstate) {
        this.sthstate = sthstate;
    }
    @Column(length = 36,nullable=true)
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
    @Column(length = 36,nullable=true)
    public String getStype1() {
        return stype1;
    }

    public void setStype1(String stype1) {
        this.stype1 = stype1;
    }
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36,nullable=true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(length = 36,nullable=true)
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }


    @Column(length = 36,nullable=true)
    public String getCgid() {
        return cgid;
    }
    @Column(length = 36,nullable=true)
    public String getXhid() {
        return xhid;
    }

    public void setCgid(String cgid) {
        this.cgid = cgid;
    }

    public void setXhid(String xhid) {
        this.xhid = xhid;
    }

    @Column(length = 36,nullable=true)
    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }
    @Column(length = 36,nullable=true)
    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }
    @Column(length = 36,nullable=true)
    public String getSperson() {
        return sperson;
    }

    public void setSperson(String sperson) {
        this.sperson = sperson;
    }
    @Column(length = 500,nullable=true)
    public String getMuseto() {
        return museto;
    }
    @Column(length = 100,nullable=true)
    public String getFgspec() {
        return fgspec;
    }

    public void setMuseto(String museto) {
        this.museto = museto;
    }

    public void setFgspec(String fgspec) {
        this.fgspec = fgspec;
    }
}
