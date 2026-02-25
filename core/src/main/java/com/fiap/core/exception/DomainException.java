package com.fiap.core.exception;

public class DomainException extends Exception {
  private final String code;

  public DomainException(String message, String code) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
