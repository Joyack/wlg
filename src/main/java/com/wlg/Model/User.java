package com.wlg.Model;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
/**
 * Created by wlg on 2016/6/13.
 */
@Entity
public class User{
    private String id;
    private String username;//用户名
    private String password="12345678";//密码
    private String uname;//用户名称
    private String deptid;
    private String phonenumber;
    private String email;
    private String post;
    private String roleid;
    private String logourl;
    private String deptname;
    private String rolename;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    public String getId() {
        return id;
    }

    @Column(length = 30,nullable=false)
    public String getUsername() {
        return username;
    }

    @Column(length = 30,nullable=true)
    public String getUname() {
        return uname;
    }

    @Column(length = 32,nullable=false)
    public String getPassword() {
        return password;
    }

    @Column(length = 100,nullable=true)
    public String getLogourl() {
        return logourl;
    }


    @Column(length = 30,nullable=true)
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Column(length = 50,nullable=true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(length = 50,nullable=true)
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    @Column(length = 36,nullable=true)
    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
    @Column(length = 36,nullable=true)
    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
