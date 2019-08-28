package com.gbastos.bakery.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An Anemic data container for the Bakery shopping cart. That has the @Product as key, the Integer
 * as its ordering quantity.
 *
 * @author Guilherme Borges Bastos
 * @date 08/29/2019
 */
public class Cart {

  private Map<Product, Integer> cart = new LinkedHashMap<>();

  public Cart() {
    super();
  }

  public Map<Product, Integer> getCart() {
    return cart;
  }

  public void setCart(Map<Product, Integer> cart) {
    this.cart = cart;
  }
}
