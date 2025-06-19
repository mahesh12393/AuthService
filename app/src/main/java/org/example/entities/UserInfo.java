package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor  // when passes values the constructor sets the values of whats provided
@Table(name = "users")
public class UserInfo {

    @Id
    @Column(name = "user_id")
    private String userId;
    private String username;
    private String password;

    //for the many to many mapping between users and roles.

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<UserRole> roles = new HashSet<>(); // so basically this hash set will contain all roles for one user



    public String getUserName(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public Set<UserRole> getRoles(){
        return roles;
    }

};
