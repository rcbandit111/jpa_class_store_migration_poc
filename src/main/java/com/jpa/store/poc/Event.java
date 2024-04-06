package com.jpa.store.poc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {

  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "system_type")
  @Enumerated(EnumType.STRING)
  private SystemType systemType;
}
