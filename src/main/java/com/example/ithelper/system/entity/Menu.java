package com.example.ithelper.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_menu")
public class Menu {

    public static final String TYPE_MENU = "0";

    public static final String TYPE_BUTTON = "1";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuId;

    @Column(nullable = false)
    private long parentId=0;

    @Column(nullable = false)
    private String menuName="";

    @Column(nullable = false)
    private String path="";

    @Column(nullable = false)
    private String component="";

    @Column(nullable = false)
    private String perms="";

    @Column(nullable = false,length = 1)
    private String type="";

    @Column(nullable = false)
    private String icon="";

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH,mappedBy = "menus")
    private Set<Role> roles = new HashSet<>();

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuId=" + menuId +
                ", parentId=" + parentId +
                ", menuName='" + menuName + '\'' +
                ", path='" + path + '\'' +
                ", component='" + component + '\'' +
                ", perms='" + perms + '\'' +
                ", type='" + type + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
