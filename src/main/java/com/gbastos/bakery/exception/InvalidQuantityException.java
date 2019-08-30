package com.gbastos.bakery.exception;

public class InvalidQuantityException extends RuntimeException  {

  private static final long serialVersionUID = 1L;

  public InvalidQuantityException(int quantity) {
    super("The quantity of " + quantity + " is not valid!");
  }

}
