package com.gbastos.bakery.exception;

public class DatasetReaderLoaderPrecedenceException  extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DatasetReaderLoaderPrecedenceException() {
    super("The Dataset Reader cannot work with products before load the products.");
  }
}
