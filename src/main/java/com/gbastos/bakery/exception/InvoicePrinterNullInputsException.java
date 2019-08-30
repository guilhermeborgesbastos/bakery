package com.gbastos.bakery.exception;

public class InvoicePrinterNullInputsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvoicePrinterNullInputsException() {
    super("The invoice printer cannot receive null values.");
  }
}