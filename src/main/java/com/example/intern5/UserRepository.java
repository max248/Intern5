package com.example.intern5;

import com.example.intern5.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u from Users u where u.username = ?1")
    Users findByUserName(String username);

    @Query("SELECT u from Users u where u.id = ?1")
    Users findUserById(Long id);

}
