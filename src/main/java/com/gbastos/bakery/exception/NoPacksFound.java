package com.gbastos.bakery.exception;

import com.gbastos.bakery.model.Product;

public class NoPacksFound extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NoPacksFound(Product product) {
    super("No packets found for product (" + product.toString() + ")");
  }

}