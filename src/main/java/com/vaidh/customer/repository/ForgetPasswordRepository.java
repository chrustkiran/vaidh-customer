package com.vaidh.customer.repository;

import com.vaidh.customer.model.customer.ForgetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword, Long> {
    Optional<ForgetPassword> findByUsername(String username);
}
