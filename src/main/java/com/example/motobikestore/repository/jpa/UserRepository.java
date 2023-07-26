package com.example.motobikestore.repository.jpa;

import com.example.motobikestore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByActivationCode(String code);
    @Query("SELECT user.email FROM User user WHERE user.passwordResetCode = :code")
    Optional<String> getEmailByPasswordResetCode(String code);
}
