package com.blog.entity;

import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_users",
uniqueConstraints = @UniqueConstraint(
        name = "userNameConstraint",
        columnNames = {"userName","email"}
) )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;


    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_roles",joinColumns = @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    ),inverseJoinColumns = @JoinColumn(
            name = "role_id",
            referencedColumnName = "roleId"
    ))
    public Set<Role> roles;

}
