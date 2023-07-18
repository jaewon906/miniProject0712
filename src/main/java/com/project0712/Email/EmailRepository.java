package com.project0712.Email;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
    Optional<EmailEntity> findByuserEmail(String email);

    void deleteByUserEmail(String email);
}
