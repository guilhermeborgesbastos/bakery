package com.gbastos.bakery.service;

import java.util.Map;
import com.gbastos.bakery.model.Product;

public interface ICartService {

  /**
   * Adds the product to the cart, in case it already exists in the cart, it increases its quantity,
   * otherwise, it basically adds it to the cart.
   *
   * @param amount product's quantity
   * @param product
   */
  void addProduct(Integer amount, Product product);

  /**
   * Retrieves the cart items copy.
   *
   * @return the map with the cart items.
   */
  Map<Product, Integer> retrieveCartItems();

  /**
   * Calculates the total value by summing the price of each item(Pack) into the cart multiplying
   * its quantity by its price.
   *
   * @return the total cart's price.
   */
  Double calculateItemsTotal();

  /**
   * Checks if the cart it is empty.
   *
   * @return the true, if the cart is empty.
   */
  Boolean isEmpty();
}
