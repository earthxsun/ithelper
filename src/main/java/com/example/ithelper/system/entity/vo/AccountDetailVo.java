package com.example.ithelper.system.entity.vo;

import java.util.List;

public class AccountDetailVo {

    private long id;

    private String name;

    private String dept;

    private String email;

    private String post;

    private String tel;

    private String applicationType;

    private String reason;

    private String group;

    private String otherPerm;

    private List systemInfo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getOtherPerm() {
        return otherPerm;
    }

    public void setOtherPerm(String otherPerm) {
        this.otherPerm = otherPerm;
    }

    public List getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(List systemInfo) {
        this.systemInfo = systemInfo;
    }

    @Override
    public String toString() {
        return "AccountDetailVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", email='" + email + '\'' +
                ", post='" + post + '\'' +
                ", tel='" + tel + '\'' +
                ", applicationType='" + applicationType + '\'' +
                ", reason='" + reason + '\'' +
                ", group='" + group + '\'' +
                ", otherPerm='" + otherPerm + '\'' +
                ", systemInfo=" + systemInfo +
                '}';
    }
}
