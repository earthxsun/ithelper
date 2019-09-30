package com.example.ithelper.system.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "t_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "名字不能为空")
    @Column(nullable = false, name = "account_name")
    private String name;

    @NotBlank(message = "部门不能为空")
    @Column(nullable = false)
    private String dept;

    @NotBlank(message = "Email不能为空")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "岗位不能为空")
    @Column(nullable = false)
    private String post;

    @NotBlank(message = "手机号码不能为空")
    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String system1 = "";

    @Column(nullable = false)
    private String sysOrg1 = "";

    @Column(nullable = false)
    private String sysPerm1 = "";

    @Column(nullable = false)
    private String system2 = "";

    @Column(nullable = false)
    private String sysOrg2 = "";

    @Column(nullable = false)
    private String sysPerm2 = "";

    @Column(nullable = false)
    private String system3 = "";

    @Column(nullable = false)
    private String sysOrg3 = "";

    @Column(nullable = false)
    private String sysPerm3 = "";

    @NotBlank(message = "申请类型不能为空")
    @Column(nullable = false)
    private String applicationType;

    @NotBlank(message = "申请原因不能为空")
    @Column(nullable = false)
    private String reasonForApplication;

    @NotBlank(message = "组别不能为空")
    @Column(nullable = false, name = "account_group")
    private String group;

    @Column(nullable = false)
    private String otherPerm = "";

    @Column(nullable = false)
    private String status = "";

    @Column(nullable = false)
    @CreatedBy
    private String createdBy = "";

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Column(nullable = false)
    @LastModifiedBy
    private String updatedBy = "";

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

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

    public String getSystem1() {
        return system1;
    }

    public void setSystem1(String system1) {
        this.system1 = system1;
    }

    public String getSysOrg1() {
        return sysOrg1;
    }

    public void setSysOrg1(String sysOrg1) {
        this.sysOrg1 = sysOrg1;
    }

    public String getSysPerm1() {
        return sysPerm1;
    }

    public void setSysPerm1(String sysPerm1) {
        this.sysPerm1 = sysPerm1;
    }

    public String getSystem2() {
        return system2;
    }

    public void setSystem2(String system2) {
        this.system2 = system2;
    }

    public String getSysOrg2() {
        return sysOrg2;
    }

    public void setSysOrg2(String sysOrg2) {
        this.sysOrg2 = sysOrg2;
    }

    public String getSysPerm2() {
        return sysPerm2;
    }

    public void setSysPerm2(String sysPerm2) {
        this.sysPerm2 = sysPerm2;
    }

    public String getSystem3() {
        return system3;
    }

    public void setSystem3(String system3) {
        this.system3 = system3;
    }

    public String getSysOrg3() {
        return sysOrg3;
    }

    public void setSysOrg3(String sysOrg3) {
        this.sysOrg3 = sysOrg3;
    }

    public String getSysPerm3() {
        return sysPerm3;
    }

    public void setSysPerm3(String sysPerm3) {
        this.sysPerm3 = sysPerm3;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getReasonForApplication() {
        return reasonForApplication;
    }

    public void setReasonForApplication(String reasonForApplication) {
        this.reasonForApplication = reasonForApplication;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOtherPerm() {
        return otherPerm;
    }

    public void setOtherPerm(String otherPerm) {
        this.otherPerm = otherPerm;
    }
}
