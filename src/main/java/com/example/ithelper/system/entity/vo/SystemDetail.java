package com.example.ithelper.system.entity.vo;

import java.util.List;

public class SystemDetail {

    private long id;

    private String name;

    private List permission;

    private String defaultPassword;

    private String PasswordTip;

    private String status;

    private String index;

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

    public List getPermission() {
        return permission;
    }

    public void setPermission(List permission) {
        this.permission = permission;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getPasswordTip() {
        return PasswordTip;
    }

    public void setPasswordTip(String passwordTip) {
        PasswordTip = passwordTip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "SystemDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permission=" + permission +
                ", defaultPassword='" + defaultPassword + '\'' +
                ", PasswordTip='" + PasswordTip + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
