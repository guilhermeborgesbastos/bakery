package com.gbastos.bakery.exception;

public class CartIsEmptyException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CartIsEmptyException() {
    super("The cart was empty during the invoice printing process.");
  }
}
