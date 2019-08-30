package com.gbastos.bakery.exception;

import java.io.IOException;

public class CartLoaderFailedToInputStream extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CartLoaderFailedToInputStream(IOException e) {
    super("CartLoader failed to read orders. Please, contact the support!", e);
  }
}