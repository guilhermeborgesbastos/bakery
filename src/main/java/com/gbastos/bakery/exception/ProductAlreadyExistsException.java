package com.gbastos.bakery.exception;

public class ProductAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ProductAlreadyExistsException(String code) {
    super("The product with code (" + code + ") already exists in the bakery.");
  }
}