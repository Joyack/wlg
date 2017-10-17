package com.wlg.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 供应商
 * Created by LvLiangFeng on 2017/5/17.
 */
@Entity
public class Supplier {
    private String id;
    private String providerid;// 编号
    private String proname ;//  供应商名称
    private String industry ;// 所属行业
    private String contractsperosn ;//  联系人
    private String address;
    private String tele;//  联系电话
    private String productid;//  经营产品
    private String productname;//经营产品名称
    private String grade;//  评分

    private String createauthor;//创建用户
    private String createtime;//创建时间
    private String updateauthor;//更新用户
    private String updatetime;//更新时间
    private String status;//启用、禁用
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

    @Column(length = 30,nullable=true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Column(length = 30,nullable=true)
    public String getCreateauthor() {
        return createauthor;
    }

    public void setCreateauthor(String createauthor) {
        this.createauthor = createauthor;
    }
    @Column(length = 30,nullable=true)
    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    @Column(length = 30,nullable=true)
    public String getUpdateauthor() {
        return updateauthor;
    }

    public void setUpdateauthor(String updateauthor) {
        this.updateauthor = updateauthor;
    }
    @Column(length = 30,nullable=true)
    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    @Column(length = 30,nullable=true)
    public String getProviderid() {
        return providerid;
    }

    public void setProviderid(String providerid) {
        this.providerid = providerid;
    }
    @Column(length = 500,nullable=true)
    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }
    @Column(length = 100,nullable=true)
    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
    @Column(length = 50,nullable=true)
    public String getContractsperosn() {
        return contractsperosn;
    }

    public void setContractsperosn(String contractsperosn) {
        this.contractsperosn = contractsperosn;
    }
    @Column(length = 30,nullable=true)
    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }
    @Column(length = 500,nullable=true)
    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
    @Column(length = 500,nullable=true)
    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    @Column(length = 30,nullable=true)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    @Column(length = 30,nullable=true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
