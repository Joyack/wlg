package com.wlg.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 合同
 * Created by LvLiangFeng on 2017/5/17.
 */
public class Contract {
    private String id;//主键
    private Integer type;//合同类型 1购入合同 2销售合同
    private String name;//合同名称


    private String createauthor;//创建用户
    private String createtime;//创建时间
    private String updateauthor;//更新用户
    private String updatetime;//更新时间

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36,nullable=true)
    public String getId() {
        return id;
    }
    @Column(length = 36,nullable=true)
    public Integer getType() {
        return type;
    }
    @Column(length = 36,nullable=true)
    public String getName() {
        return name;
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


    public void setId(String id) {
        this.id = id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
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
}
