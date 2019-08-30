package com.gbastos.bakery.exception;

import com.gbastos.bakery.model.Product;

public class PackAlreadyExists extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public PackAlreadyExists(int packetSize, Product product) {
    super("The pack of size (" + packetSize + ") already exists for the  product (" + product.toString() + ").");
  }

}
