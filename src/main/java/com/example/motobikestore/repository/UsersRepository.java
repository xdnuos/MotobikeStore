package com.example.motobikestore.repository;

import com.example.motobikestore.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByActivationCode(String code);
    @Query("SELECT user.email FROM Users user WHERE user.passwordResetCode = :code")
    Optional<String> getEmailByPasswordResetCode(String code);

    Optional<Users> findByPasswordResetCode(String code);
    Boolean existsByEmail(String email);
}
