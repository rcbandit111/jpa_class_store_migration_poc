package com.jpa.store.poc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AccountOption<T> {

  @Id
  @Column(name = "internal_id", nullable = false)
  private Long internalId;

  @ManyToOne
  private Account account;

  @Column(name = "option_name")
  private String name;

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Long getInternalId() {
    return internalId;
  }

  public void setInternalId(Long internalId) {
    this.internalId = internalId;
  }

  public abstract T getOptionValue();

  public abstract void setOptionValue(T optionValue);

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
