package com.jpa.store.poc;

import org.hibernate.annotations.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "enumerated_account_options")
public class EnumeratedAccountOption<T extends Enum<T>> extends AccountOption<T> {

  @Column(name = "OPTION_TYPE")
  @Type(type = "org.hibernate.type.ClassType")
  private Class<T> clazz;

  @Column(name = "OPTION_VALUE")
  private String optionValue;

  public EnumeratedAccountOption() {
  }

  public EnumeratedAccountOption(String name, T value) {
    setName(name);
    this.optionValue = value.name();
    this.clazz = (Class<T>) value.getClass();
  }

  @Override
  public T getOptionValue() {
    return Enum.valueOf(getClazz(), optionValue);
  }

  @Override
  public void setOptionValue(T optionValue) {
    this.optionValue = optionValue.name();
  }

  public Class<T> getClazz() {
    return clazz;
  }

  public void setClazz(Class<T> clazz) {
    this.clazz = clazz;
  }
}
