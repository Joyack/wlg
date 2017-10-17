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
public class FaultInfo implements Serializable {

    private String id;

    private String fid;

    private String gid;

    private String fnum;

    private String ftype;

    private String fdate;

    private String fperson;

    private String plotflag;

    private String cid;
    private String xhid;

    @Column(length = 36,nullable=true)
    public String getPlotflag() {
        return plotflag;
    }

    public void setPlotflag(String plotflag) {
        this.plotflag = plotflag;
    }
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36,nullable=false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(length = 36,nullable=true)
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
    @Column(length = 36,nullable=true)
    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
    @Column(length = 36,nullable=true)
    public String getFnum() {
        return fnum;
    }

    public void setFnum(String fnum) {
        this.fnum = fnum;
    }
    @Column(length = 36,nullable=true)
    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }
    @Column(length = 36,nullable=true)
    public String getFdate() {
        return fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
    }
    @Column(length = 36,nullable=true)
    public String getFperson() {
        return fperson;
    }

    public void setFperson(String fperson) {
        this.fperson = fperson;
    }
    @Column(length = 36,nullable=true)
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
    @Column(length = 36,nullable=true)
    public String getXhid() {
        return xhid;
    }

    public void setXhid(String xhid) {
        this.xhid = xhid;
    }
}
