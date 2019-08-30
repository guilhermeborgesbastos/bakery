package com.gbastos.bakery.service;

import com.gbastos.bakery.exception.NullAddedToOrderException;
import com.gbastos.bakery.exception.ProductAlreadyExistsException;
import com.gbastos.bakery.exception.ProductNotFoundException;
import com.gbastos.bakery.model.Order;
import com.gbastos.bakery.model.Product;

public class OrderService implements IOrderService {

  private Order order;

  public OrderService() {
    order = new Order();
  }

  private void validateInput(Product product) {
    if (product == null) {
      throw new NullAddedToOrderException();
    } else if (order.getOrder().containsKey(product.getCode().toUpperCase())) {
      throw new ProductAlreadyExistsException(product.getCode().toUpperCase());
    }
  }

  @Override
  public void addProduct(Product product) {
    validateInput(product);
    order.getOrder().put(product.getCode().toUpperCase(), product);
  }

  @Override
  public Product retrieveProduct(String code) {
    Product product = order.getOrder().get(code.toUpperCase());
    if (product == null) {
      throw new ProductNotFoundException(code.toUpperCase());
    }
    return product;
  }
}
