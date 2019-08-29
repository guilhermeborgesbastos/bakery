package com.gbastos.bakery.exception;

public class ProductNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ProductNotFoundException(String code) {
    super("The product with code (" + code + ") does not exist in the system.");
  }
}
