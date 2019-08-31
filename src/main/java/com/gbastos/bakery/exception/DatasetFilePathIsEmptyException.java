package com.gbastos.bakery.exception;

public class DatasetFilePathIsEmptyException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DatasetFilePathIsEmptyException() {
    super("The Dataset File path provided to Data Reader Factor cannot be empty.");
  }

}
