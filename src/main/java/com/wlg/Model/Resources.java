package com.wlg.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvlf on 2016/7/13.
 */
@Entity
public class Resources implements Serializable{
    private String id;
    private String resourcename;//资源名称
    private String url;//资源url
    private String parentid;//父级id
    private String sn;//编码
    private Integer number;//顺序编号
    private Integer useto;//0
    private String level;
    private String rooturl;//根目录url
    private List<Resources> menu_list = new ArrayList<>();

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 36)
    public String getId() {
        return id;
    }

    @Column(length = 32,nullable=false)
    public String getParentid() {
        return parentid;
    }
    @Column(length = 100,nullable=false)
    public String getResourcename() {
        return resourcename;
    }

    @Column(length = 200,nullable=false)
    public String getUrl() {
        return url;
    }

    @Column(length = 32,nullable=true)
    public String getSn() {
        return sn;
    }

    @Column(length = 32,nullable=true)
    public Integer getNumber() {
        return number;
    }
    @Column(length = 30,nullable=true)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    @Column(length = 500,nullable=true)
    public String getRooturl() {
        return rooturl;
    }

    public void setRooturl(String rooturl) {
        this.rooturl = rooturl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column(length = 2,nullable=true)
    public Integer getUseto() {
        return useto;
    }

    public void setUseto(Integer useto) {
        this.useto = useto;
    }

    @Transient
    public List<Resources> getMenu_list() {
        return menu_list;
    }

    public void setMenu_list(List<Resources> menu_list) {
        this.menu_list = menu_list;
    }
}
