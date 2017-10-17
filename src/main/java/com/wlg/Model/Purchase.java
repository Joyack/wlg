package com.wlg.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 采购
 * Created by LvLiangFeng on 2017/5/17.
 */
@Entity
public class Purchase implements Serializable {

    private String id;

    private String gid;
    private String cgdate;// 采购日期
    private Integer cgnum;// 采购数量
    private String cgid;
    private String  instate ;//入库状态  01 已完全入库  02 未完全入库
    private Integer instoragednum;//  已入库数量

    private Integer instoragenum ;// 待入库数量

    private String  cgstatus;//采购合同状态

    private float cgprice;
    private float cgmoney;

    private String createauthor;//创建用户
    private String createtime;//创建时间
    private String updateauthor;//更新用户
    private String updatetime;//更新时间

    /*新增comments备注字段*/
    private String comments;//备注

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36,nullable=false)
    public String getId() {
        return id;
    }
    @Column(length = 36,nullable=true)
    public String getGid() {
        return gid;
    }
    @Column(length = 36,nullable=true)
    public String getCgdate() {
        return cgdate;
    }
    @Column(length = 36,nullable=true)
    public Integer getCgnum() {
        return cgnum;
    }
    @Column(length = 36,nullable=true)
    public String getInstate() {
        return instate;
    }
    @Column(length = 36,nullable=true)
    public Integer getInstoragednum() {
        return instoragednum;
    }
    @Column(length = 36,nullable=true)
    public Integer getInstoragenum() {
        return instoragenum;
    }
    @Column(length = 36,nullable=true)
    public String getCgstatus() {
        return cgstatus;
    }
    @Column(length = 36,nullable=true)
    public String getCreateauthor() {
        return createauthor;
    }
    @Column(length = 36,nullable=true)
    public String getCreatetime() {
        return createtime;
    }
    @Column(length = 36,nullable=true)
    public String getUpdateauthor() {
        return updateauthor;
    }
    @Column(length = 36,nullable=true)
    public String getUpdatetime() {
        return updatetime;
    }
    @Column(length = 36,nullable=true)
    public float getCgprice() {
        return cgprice;
    }
    @Column(length = 36,nullable=true)
    public float getCgmoney() {
        return cgmoney;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setCgdate(String cgdate) {
        this.cgdate = cgdate;
    }

    public void setCgnum(Integer cgnum) {
        this.cgnum = cgnum;
    }

    public void setInstate(String instate) {
        this.instate = instate;
    }

    public void setInstoragednum(Integer instoragednum) {
        this.instoragednum = instoragednum;
    }

    public void setInstoragenum(Integer instoragenum) {
        this.instoragenum = instoragenum;
    }

    public void setCgstatus(String cgstatus) {
        this.cgstatus = cgstatus;
    }

    public void setCreateauthor(String createauthor) {
        this.createauthor = createauthor;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setUpdateauthor(String updateauthor) {
        this.updateauthor = updateauthor;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public void setCgprice(float cgprice) {
        this.cgprice = cgprice;
    }

    public void setCgmoney(float cgmoney) {
        this.cgmoney = cgmoney;
    }
    @Column(length = 36,nullable=true)
    public String getCgid() {
        return cgid;
    }

    public void setCgid(String cgid) {
        this.cgid = cgid;
    }


    @Column(length = 36,nullable=true)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
