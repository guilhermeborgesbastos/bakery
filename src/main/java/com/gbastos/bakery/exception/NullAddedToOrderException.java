package com.gbastos.bakery.exception;

public class NullAddedToOrderException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NullAddedToOrderException() {
    super("A 'empty' product cannot be added to the order. \nPlease, contact the support!");
  }
}
