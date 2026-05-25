package com.nihil.clientifyapi.auth.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 16, unique = true, nullable = false)
      private String username;

      @Column(name = "email", length = 50, unique = true, nullable = false)
      private String email;

      @Column(name = "password", length = 72)
      private String password;

      @Column(name = "created_at")
      @Generated(event = EventType.INSERT)
      private LocalDateTime createdAt;

      @Column(name = "updated_at")
      @Generated(event = { EventType.INSERT, EventType.UPDATE })
      private LocalDateTime updatedAt;
}
