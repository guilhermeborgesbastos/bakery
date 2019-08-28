package com.gbastos.bakery.exception;

public class InvalidPackPriceException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvalidPackPriceException(double price) {
    super("The pack price of" + price + " isn't a valid input.");
  }
}
