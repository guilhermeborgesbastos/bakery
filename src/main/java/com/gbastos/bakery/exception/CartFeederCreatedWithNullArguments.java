package com.gbastos.bakery.exception;

public class CartFeederCreatedWithNullArguments extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CartFeederCreatedWithNullArguments() {
    super("Cart canot be loaded with null values.");
  }
}
