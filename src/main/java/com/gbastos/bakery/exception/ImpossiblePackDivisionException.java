package com.gbastos.bakery.exception;

import com.gbastos.bakery.model.Product;

public class ImpossiblePackDivisionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ImpossiblePackDivisionException(int quantity, Product product) {
    super("Not possible to divide the quantity of [" + quantity + "] for the product ]"
        + product.toString() + "] into package sizes.");
  }
}
