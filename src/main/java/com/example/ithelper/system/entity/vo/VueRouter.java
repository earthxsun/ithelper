package com.example.ithelper.system.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VueRouter implements Serializable,Comparable<VueRouter> {

    @JsonIgnore
    private long menuId;

    @JsonIgnore
    private long parentId;

    private String path;

    private String component;

    private String name;

    private String icon;

    private List<VueRouter> children;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<VueRouter> getChildren() {
        return children;
    }

    public void setChildren(List<VueRouter> children) {
        this.children = children;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "VueRouter{" +
                "menuId=" + menuId +
                ", parentId=" + parentId +
                ", path='" + path + '\'' +
                ", component='" + component + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", children=" + children +
                '}';
    }

    @Override
    public int compareTo(VueRouter o) {
        return (int) (this.menuId-o.getMenuId());
    }
}
