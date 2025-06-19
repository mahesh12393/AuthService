package org.example.service;

import org.example.entities.UserInfo;
import org.example.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetails extends UserInfo implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


//    Constructor
    public CustomUserDetails(UserInfo userInfo){
        this.username = userInfo.getUserName();
        this.password = userInfo.getPassword();
        List<GrantedAuthority> auth_list = new ArrayList<>();

        for(UserRole role : userInfo.getRoles()){
            auth_list.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }

        this.authorities = auth_list;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}
