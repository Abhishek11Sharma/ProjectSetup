package com.reuse.predefined.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reuse.predefined.entities.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmailId(String emailId);
}
