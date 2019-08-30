package com.gbastos.bakery.exception;

public class PackCannotBeNull extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public PackCannotBeNull() {
    super("A null pack cannot be added to a product.");
  }

}
