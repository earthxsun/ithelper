package com.example.ithelper.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "t_account_permission_info")
public class AccountPermissionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String systemName = "";

    @Column(nullable = false)
    private String org = "";

    @Column(nullable = false)
    private String systemPermissions = "";

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getSystemPermissions() {
        return systemPermissions;
    }

    public void setSystemPermissions(String systemPermissions) {
        this.systemPermissions = systemPermissions;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "AccountPermissionInfo{" +
                "id=" + id +
                ", systemName='" + systemName + '\'' +
                ", org='" + org + '\'' +
                ", systemPermissions='" + systemPermissions + '\'' +
                '}';
    }
}
