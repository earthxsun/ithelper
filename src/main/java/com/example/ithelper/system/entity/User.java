package com.example.ithelper.system.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_user")
public class User {

    public static String STATUS_ENABLE = "1";

    public static String STATUS_DISABLE = "0";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password="jh123456";

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id",nullable = false)
    private Dept dept;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    @CreatedBy
    private String createdBy="";

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Column(nullable = false)
    @LastModifiedBy
    private String updatedBy="";

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime = new Date();

    public static String getStatusEnable() {
        return STATUS_ENABLE;
    }

    public static void setStatusEnable(String statusEnable) {
        STATUS_ENABLE = statusEnable;
    }

    public static String getStatusDisable() {
        return STATUS_DISABLE;
    }

    public static void setStatusDisable(String statusDisable) {
        STATUS_DISABLE = statusDisable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dept=" + dept +
                ", role=" + role +
                ", status='" + status + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createTime=" + createTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", updateTime=" + updateTime +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
