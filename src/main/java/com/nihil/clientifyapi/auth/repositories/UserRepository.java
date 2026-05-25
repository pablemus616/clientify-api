package com.nihil.clientifyapi.auth.repositories;

import com.nihil.clientifyapi.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    User findOneByEmail(String email);
}
