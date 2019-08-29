package com.gbastos.bakery.service;

import com.gbastos.bakery.model.Product;

public interface IOrderService {

  void addProduct(Product product);

  Product retrieveProduct(String code);
}
