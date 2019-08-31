package com.gbastos.bakery.exception;

public class InvalidAmountException extends RuntimeException  {

  private static final long serialVersionUID = 1L;

  public InvalidAmountException(int amount) {
    super("The amount of " + amount + " is not valid!");
  }

}
