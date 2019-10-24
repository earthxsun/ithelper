package com.example.ithelper.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "名字不能为空")
    @Column(nullable = false, name = "account_user")
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

    @JsonIgnore
    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<AccountPermissionInfo> accountPermissionInfoSet = new HashSet<>();

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

    public String getOtherPerm() {
        return otherPerm;
    }

    public void setOtherPerm(String otherPerm) {
        this.otherPerm = otherPerm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<AccountPermissionInfo> getAccountPermissionInfoSet() {
        return accountPermissionInfoSet;
    }

    public void setAccountPermissionInfoSet(Set<AccountPermissionInfo> accountPermissionInfoSet) {
        this.accountPermissionInfoSet = accountPermissionInfoSet;
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", email='" + email + '\'' +
                ", post='" + post + '\'' +
                ", tel='" + tel + '\'' +
                ", applicationType='" + applicationType + '\'' +
                ", reasonForApplication='" + reasonForApplication + '\'' +
                ", group='" + group + '\'' +
                ", otherPerm='" + otherPerm + '\'' +
                ", status='" + status + '\'' +
                ", accountPermissionInfoSet=" + accountPermissionInfoSet +
                ", createdBy='" + createdBy + '\'' +
                ", createTime=" + createTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
