package com.example.motobikestore.repository;

import com.example.motobikestore.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Modifying
    @Query("UPDATE Users u SET u.active = :status WHERE u.userID = :id")
    void changeStatusByID(@Param("id") UUID id, @Param("status") Boolean status);
    @Query("select u.active from Users u where u.userID=:id")
    Boolean getStatusByID(@Param("id") UUID id);
}
