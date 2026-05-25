  package com.nihil.clientifyapi.auth.entities;

  import jakarta.persistence.*;
  import lombok.Getter;
  import lombok.Setter;
    import jakarta.persistence.*;
  import org.hibernate.annotations.ColumnDefault;
  import org.hibernate.generator.EventType;

  import java.time.LocalDateTime;
  import java.util.UUID;

  import org.hibernate.annotations.Generated;
  import org.hibernate.generator.EventType;
  @Getter
  @Setter
  @Entity
  @Table(name = "sessions")
  public class Session {

      @Id
      @Column(name = "id", updatable = false, nullable = false)
      @ColumnDefault("uuid_generate_v7()")
      @Generated(event = EventType.INSERT)
      private UUID id;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "fk_user_session"))
      private User user;

      @Column(name = "token", columnDefinition = "TEXT")
      private String sessionToken;
     @Generated(event = EventType.INSERT)
      @Column(name = "created_at", insertable = false, updatable = false)
      private LocalDateTime createdAt;
    @Generated(event = EventType.INSERT)
      @Column(name = "last_seen")
      private LocalDateTime lastSeen;

      @Column(name = "ip")
      private String ip;

  }
