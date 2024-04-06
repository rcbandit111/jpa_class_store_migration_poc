package com.jpa.store.poc;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type")
public class Account {

  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  private String name;

  @Column(name = "external_account_id", nullable = true)
  private String externalAccountId;

  @Enumerated(EnumType.STRING)
  private AccountStatus status;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<EnumeratedAccountOption<?>> enumeratedAccountOptions;

  public Set<EnumeratedAccountOption<?>> getEnumeratedAccountOptions() {
    if (enumeratedAccountOptions == null) {
      enumeratedAccountOptions = new HashSet<>();
    }
    return enumeratedAccountOptions;
  }

  /**
   * Sets the enumeratedAccountOptions and enforces the relations ship with AccountOption.
   */
  public void setEnumeratedAccountOptions(Set<EnumeratedAccountOption<?>> enumeratedAccountOptions) {
    this.getEnumeratedAccountOptions().clear();
    this.getEnumeratedAccountOptions().addAll(enumeratedAccountOptions);
    enumeratedAccountOptions.forEach(accountOption -> accountOption.setAccount(this));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AccountStatus getStatus() {
    return status;
  }

  public void setStatus(AccountStatus status) {
    this.status = status;
  }

  public String getExternalAccountId() {
    return externalAccountId;
  }

  public void setExternalAccountId(String externalAccountId) {
    this.externalAccountId = externalAccountId;
  }

  /**
   * Merges an Transient {@link Account} into this account. This should be overridden on subclasses.
   */
  public void merge(Account account) {
    setName(account.getName());
    setExternalAccountId(account.getExternalAccountId());
    if (account.getStatus() != null) {
      setStatus(account.getStatus());
    }
    setEnumeratedAccountOptions(account.getEnumeratedAccountOptions());
  }

  public <T> Optional<T> getAccountOption(String name, Class<T> asClass) {
    return (Optional<T>) getEnumeratedAccountOptions().stream()
        .filter(enumeratedAccountOption -> enumeratedAccountOption.getClazz().equals(asClass))
        .filter(enumeratedAccountOption -> enumeratedAccountOption.getName().equals(name))
        .map(AccountOption::getOptionValue)
        .findFirst();
  }

}
