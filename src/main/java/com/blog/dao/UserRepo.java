package com.blog.dao;

import com.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {


    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmailOrUserName(String email,String userName);

    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
}
