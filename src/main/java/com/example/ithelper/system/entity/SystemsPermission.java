package com.example.ithelper.system.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_system_perm")
public class SystemsPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {CascadeType.REMOVE,CascadeType.PERSIST},fetch = FetchType.EAGER)
    @JoinColumn(name = "system_id")
    private Systems systems;

    @Column(nullable = false)
    private String permission;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Systems getSystem() {
        return systems;
    }

    public void setSystem(Systems systems) {
        this.systems = systems;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "SystemsPermission{" +
                "id=" + id +
                ", systems=" + systems +
                ", permission='" + permission + '\'' +
                '}';
    }
}
