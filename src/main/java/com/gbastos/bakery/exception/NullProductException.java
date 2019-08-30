package com.gbastos.bakery.exception;

public class NullProductException extends RuntimeException  {

  private static final long serialVersionUID = 1L;

  public NullProductException() {
    super("A NULL product cannot be added to the shopping cart.");
  }

}
