package com.gbastos.bakery.exception;

public class DatasetFileNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DatasetFileNotFoundException(String path, Exception e) {
    super("The dataset file (" + path + ") was not found.", e);
  }

}
