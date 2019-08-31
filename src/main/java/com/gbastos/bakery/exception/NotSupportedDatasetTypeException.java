package com.gbastos.bakery.exception;

public class NotSupportedDatasetTypeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NotSupportedDatasetTypeException(String fileType) {
    super("No DataReader is available for the file type = " + fileType);
  }

}
