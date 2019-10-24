package com.example.ithelper.system.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_system")
public class Systems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String defaultPassword = "";

    @Column(nullable = false)
    private String PasswordTip = "";

    @Column(nullable = false)
    private String status;

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
}
