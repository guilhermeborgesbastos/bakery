package com.gbastos.bakery.exception;

public class InvalidPackSizeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvalidPackSizeException(int packetSize) {
    super("The product's quantity of " + packetSize + " isn't valid for a package!");
  }
}
