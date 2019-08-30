package com.gbastos.bakery.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.gbastos.bakery.exception.InvalidQuantityException;
import com.gbastos.bakery.exception.NullProductException;
import com.gbastos.bakery.model.Cart;
import com.gbastos.bakery.model.Pack;
import com.gbastos.bakery.model.Product;

public class CartService implements ICartService {

  private Cart cart;

  public CartService() {
    cart = new Cart();
  }

  private void validateInputs(int quantity, Product product) {
    if (quantity < 1) {
      throw new InvalidQuantityException(quantity);
    } else if (product == null) {
      throw new NullProductException();
    }
  }

  @Override
  public Boolean isEmpty() {
    return cart.getCart().isEmpty();
  }

  @Override
  public Double calculateItemsTotal() {
    Double total = 0d;
    for (Entry<Product, Integer> entry : cart.getCart().entrySet()) {
      Product product = entry.getKey();
      IProductService productService = new ProductService(product);
      Map<Pack, Integer> packQuantity = productService
          .retrievePacksByQuantity(entry.getValue());
      total += productService.calculateTotalCostOfPacks(packQuantity);
    }
    return total;
  }

  @Override
  public void addProduct(Integer amount, Product product) {
    validateInputs(amount, product);
    if (cart.getCart().containsKey(product)) {
      Integer newQuantity = cart.getCart().get(product) + amount;
      cart.getCart().put(product, newQuantity);
    } else {
      cart.getCart().put(product, amount);
    }
  }

  @Override
  public Map<Product, Integer> retrieveCartItems() {
    Map<Product, Integer> items = new LinkedHashMap<>();
    items.putAll(cart.getCart());
    return items;
  }
}
