package com.gbastos.bakery.exception;

public class DatasetFilePathIsNullException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DatasetFilePathIsNullException() {
    super("The dataset File path provided to the Data Reader Factor cannot be null.");
  }

}
