package com.gbastos.bakery.exception;

import java.io.IOException;

public class ErrorWhileReadingFileException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ErrorWhileReadingFileException(String fileName, IOException e) {
    super("The Dataset Reader presented an error while reading the file (" + fileName + ").", e);
  }
}
