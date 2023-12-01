package com.blog.security;

import com.blog.dao.UserRepo;
import com.blog.entity.Role;
import com.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {



    UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByEmailOrUserName(username,username).orElseThrow(()->
                new UsernameNotFoundException("user Not Found"));

      String roles[]=new String[user.getRoles().size()];

      int i=0;
        for (Role role:user.getRoles()
             ) {
            roles[i]=role.getName();
            i++;
        }

        UserDetails userDetails= org.springframework.security.core.userdetails.User.builder().username(user.getUserName()).password(user.getPassword()).roles(roles).build();
        return userDetails;
    }
}
