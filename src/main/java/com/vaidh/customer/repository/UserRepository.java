package com.vaidh.customer.repository;

import com.vaidh.customer.model.customer.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByEmailAddress(String emailAddress);
    @Query(value = "SELECT * FROM Users WHERE email_address = ?1 AND password = ?2", nativeQuery = true)
    Optional<UserEntity> findByEmailAndPassword(String email, String ePassword);
}
