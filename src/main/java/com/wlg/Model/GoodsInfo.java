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
public class GoodsInfo implements Serializable {

    private String id;

    private String gid;
    private String gname;
    private String createtime;
    private String modifytime;
    private  String mperson;
    private String gtid;
    private String gspec;
    private String providerid;
    private String unit;
    private Integer storagenum;//库存数量
    private Integer faultnum;//故障数量
    private Integer warnnum;//预警数量
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36)
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
    @Column(length = 200,nullable=false)
    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }
    @Column(length = 36,nullable=true)
    public String getGtid() {
        return gtid;
    }

    public void setGtid(String gtid) {
        this.gtid = gtid;
    }
    @Column(length = 150,nullable=true)
    public String getGspec() {
        return gspec;
    }

    public void setGspec(String gspec) {
        this.gspec = gspec;
    }
    @Column(length = 36,nullable=true)
    public String getProviderid() {
        return providerid;
    }

    public void setProviderid(String providerid) {
        this.providerid = providerid;
    }
    @Column(length = 36,nullable=true)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    @Column(length = 36,nullable=true)
    public Integer getStoragenum() {
        return storagenum;
    }

    public void setStoragenum(Integer storagenum) {
        this.storagenum = storagenum;
    }
    @Column(length = 36,nullable=true)
    public Integer getFaultnum() {
        return faultnum;
    }

    public void setFaultnum(Integer faultnum) {
        this.faultnum = faultnum;
    }
    @Column(length = 36,nullable=true)
    public String getCreatetime() {
        return createtime;
    }
    @Column(length = 36,nullable=true)
    public String getModifytime() {
        return modifytime;
    }
    @Column(length = 36,nullable=true)
    public String getMperson() {
        return mperson;
    }
    @Column(length = 36,nullable=true)
    public Integer getWarnnum() {
        return warnnum;
    }

    public void setWarnnum(Integer warnnum) {
        this.warnnum = warnnum;
    }

    public void setMperson(String mperson) {
        this.mperson = mperson;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }
}
