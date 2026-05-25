package com.nihil.clientifyapi.auth.repositories;

import com.nihil.clientifyapi.auth.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Boolean existsBySessionToken(String token);

    Session findSessionBySessionToken(String sessionToken);
}
