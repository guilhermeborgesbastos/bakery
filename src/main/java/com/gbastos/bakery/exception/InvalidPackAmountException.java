package com.gbastos.bakery.exception;

public class InvalidPackAmountException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvalidPackAmountException(int packetSize) {
    super("The product's quantity of " + packetSize + " isn't valid for a package!");
  }
}
