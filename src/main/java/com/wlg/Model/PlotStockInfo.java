package com.wlg.Model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by zj on 2017/4/27 0027.
 */
@Entity
public class PlotStockInfo implements Serializable {

    private String id;

    private String psid;
    private String lid;
    private String gid;
    private String psopertype;
    private String pstype;

    private String pstype1;
    private String ternoverp;

    private String psperson;
    private String psdate;
    private String psnum;
    private String ginstate;

    private String pauditstatus;
    @Column(length = 36,nullable=true)
    public String getPauditstatus() {
        return pauditstatus;
    }

    public void setPauditstatus(String pauditstatus) {
        this.pauditstatus = pauditstatus;
    }
    @Column(length = 36,nullable=true)
    public String getPstype1() {
        return pstype1;
    }

    public void setPstype1(String pstype1) {
        this.pstype1 = pstype1;
    }
    @Column(length = 36,nullable=true)
    public String getGinstate() {
        return ginstate;
    }

    public void setGinstate(String ginstate) {
        this.ginstate = ginstate;
    }
    @Column(length = 36,nullable=true)
    public String getPsnum() {
        return psnum;
    }


    private String pswnum;





    @Column(length = 36,nullable=true)
    public String getPswnum() {
        return pswnum;
    }

    public void setPswnum(String pswnum) {
        this.pswnum = pswnum;
    }

    public void setPsnum(String psnum) {
        this.psnum = psnum;
    }
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(length = 36,nullable=false)
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
    @Column(length = 36,nullable=true)
    public String getPsid() {
        return psid;
    }

    public void setPsid(String psid) {
        this.psid = psid;
    }
    @Column(length = 36,nullable=true)
    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }
    @Column(length = 36,nullable=true)
    public String getPsopertype() {
        return psopertype;
    }

    public void setPsopertype(String psopertype) {
        this.psopertype = psopertype;
    }
    @Column(length = 36,nullable=true)
    public String getPstype() {
        return pstype;
    }

    public void setPstype(String pstype) {
        this.pstype = pstype;
    }
    @Column(length = 36,nullable=true)
    public String getTernoverp() {
        return ternoverp;
    }

    public void setTernoverp(String ternoverp) {
        this.ternoverp = ternoverp;
    }
    @Column(length = 36,nullable=true)
    public String getPsperson() {
        return psperson;
    }

    public void setPsperson(String psperson) {
        this.psperson = psperson;
    }
    @Column(length = 36,nullable=true)
    public String getPsdate() {
        return psdate;
    }

    public void setPsdate(String psdate) {
        this.psdate = psdate;
    }
}
