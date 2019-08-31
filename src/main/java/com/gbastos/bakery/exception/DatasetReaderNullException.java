package com.gbastos.bakery.exception;

public class DatasetReaderNullException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DatasetReaderNullException() {
    super("The Dataset reader cannot be null.");
  }

}
