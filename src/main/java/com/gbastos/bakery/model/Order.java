package com.gbastos.bakery.model;

import java.util.HashMap;
import java.util.Map;

/**
 * An Anemic data container for the Bakery's order.
 *
 * @author Guilherme Borges Bastos
 * @date 08/29/2019
 */
public class Order {

  private Map<String, Product> order = new HashMap<>();

  public Order() {
    super();
  }

  public Map<String, Product> getOrder() {
    return order;
  }

  public void setOrder(Map<String, Product> order) {
    this.order = order;
  }

  @Override
  public String toString() {
    return "Order [order=" + order + "]";
  }
}
