package com.wlg.Model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by LvLiangFeng on 2017/3/27.
 */
@Entity
public class AuditInfo implements Serializable {

    private String id;//递增id

     private String title;//标题

     private String content;//内容

    private String fileurl;//文件路径
    private String sid;

   //审批类型  01发货出库  04 领料出库  03 领用出库
    private String  audittype;

    private String auditstatus;//审核状态 00通过 01审核中 02审核不通过

    private String createtime;//申请时间

    private String updateauthor;//更新人

    private String updatetime;//更新时间String

     private String createauthor;//创建人


    private String stockid;//库存单号
   private String audittime; //审批时间
     private String auditperson;//审批人
    private String auditagree;//审批意见
    private String auditnote;//审批步骤过程  申请-审批通过-仓储发货
    private String audittime1;//审批时间1
    private String auditperson1;//审批人1
    private String auditagree1;//审批意见2

    private String subPerson;//提交人
    private String csPerson;//抄送人

    private String status;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36,nullable=false)
    public String getId() {
        return id;
    }
    @Column(length = 36,nullable=true)
    public String getTitle() {
        return title;
    }
    @Column(length = 36,nullable=true)
    public String getContent() {
        return content;
    }
    @Column(length = 36,nullable=true)
    public String getFileurl() {
        return fileurl;
    }
    @Column(length = 36,nullable=true)
    public String getAudittype() {
        return audittype;
    }
    @Column(length = 36,nullable=true)
    public String getAuditstatus() {
        return auditstatus;
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
    public String getCreateauthor() {
        return createauthor;
    }
    @Column(length = 36,nullable=true)
    public String getStockid() {
        return stockid;
    }
    @Column(length = 36,nullable=true)
    public String getAudittime() {
        return audittime;
    }
    @Column(length = 36,nullable=true)
    public String getAuditperson() {
        return auditperson;
    }
    @Column(length = 36,nullable=true)
    public String getAuditagree() {
        return auditagree;
    }
    @Column(length = 36,nullable=true)
    public String getAuditnote() {
        return auditnote;
    }
    @Column(length = 36,nullable=true)
    public String getAudittime1() {
        return audittime1;
    }
    @Column(length = 36,nullable=true)
    public String getAuditperson1() {
        return auditperson1;
    }
    @Column(length = 36,nullable=true)
    public String getAuditagree1() {
        return auditagree1;
    }
    @Column(length = 36,nullable=true)
    public String getStatus() {
        return status;
    }
    @Column(length = 36,nullable=true)
    public String getSubPerson() {
        return subPerson;
    }
    @Column(length = 36,nullable=true)
    public String getCsPerson() {
        return csPerson;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public void setAudittype(String audittype) {
        this.audittype = audittype;
    }

    public void setAuditstatus(String auditstatus) {
        this.auditstatus = auditstatus;
    }

    public void setUpdateauthor(String updateauthor) {
        this.updateauthor = updateauthor;
    }


    public void setCreateauthor(String createauthor) {
        this.createauthor = createauthor;
    }

    public void setStockid(String stockid) {
        this.stockid = stockid;
    }

    public void setAudittime(String audittime) {
        this.audittime = audittime;
    }

    public void setAuditperson(String auditperson) {
        this.auditperson = auditperson;
    }

    public void setAuditagree(String auditagree) {
        this.auditagree = auditagree;
    }

    public void setAuditnote(String auditnote) {
        this.auditnote = auditnote;
    }

    public void setAudittime1(String audittime1) {
        this.audittime1 = audittime1;
    }

    public void setAuditperson1(String auditperson1) {
        this.auditperson1 = auditperson1;
    }

    public void setAuditagree1(String auditagree1) {
        this.auditagree1 = auditagree1;
    }

    public void setSubPerson(String subPerson) {
        this.subPerson = subPerson;
    }

    public void setCsPerson(String csPerson) {
        this.csPerson = csPerson;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    @Column(length = 36,nullable=true)
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
