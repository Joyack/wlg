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
public class XhContract implements Serializable {

    private String id;

    private String gid;
    private String xhdate;// 采购日期
    private Integer xhnum;// 销货数量
    private String xhid;
    private String  outstate ;//入库状态  01 已完全入库  02 未完全入库
    private Integer outednum;//  已出库数量

    private Integer outnum ;// 待出库数量
    private String customer;
    private String  xhstatus;//销货合同状态

    private float xhprice;
    private float xhmoney;

    private String createauthor;//创建用户
    private String createtime;//创建时间
    private String updateauthor;//更新用户
    private String updatetime;//更新时间
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
    public String getXhdate() {
        return xhdate;
    }
    @Column(length = 36,nullable=true)
    public Integer getXhnum() {
        return xhnum;
    }
    @Column(length = 36,nullable=true)
    public String getXhid() {
        return xhid;
    }
    @Column(length = 36,nullable=true)
    public String getOutstate() {
        return outstate;
    }
    @Column(length = 36,nullable=true)
    public Integer getOutednum() {
        return outednum;
    }
    @Column(length = 36,nullable=true)
    public Integer getOutnum() {
        return outnum;
    }
    @Column(length = 36,nullable=true)
    public String getXhstatus() {
        return xhstatus;
    }
    @Column(length = 36,nullable=true)
    public float getXhprice() {
        return xhprice;
    }
    @Column(length = 36,nullable=true)
    public float getXhmoney() {
        return xhmoney;
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
    public String getCustomer() {
        return customer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setXhdate(String xhdate) {
        this.xhdate = xhdate;
    }

    public void setXhnum(Integer xhnum) {
        this.xhnum = xhnum;
    }

    public void setXhid(String xhid) {
        this.xhid = xhid;
    }

    public void setOutstate(String outstate) {
        this.outstate = outstate;
    }

    public void setOutednum(Integer outednum) {
        this.outednum = outednum;
    }

    public void setOutnum(Integer outnum) {
        this.outnum = outnum;
    }

    public void setXhstatus(String xhstatus) {
        this.xhstatus = xhstatus;
    }

    public void setXhprice(float xhprice) {
        this.xhprice = xhprice;
    }

    public void setXhmoney(float xhmoney) {
        this.xhmoney = xhmoney;
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

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
