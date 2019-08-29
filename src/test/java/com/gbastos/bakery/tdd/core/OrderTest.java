package com.gbastos.bakery.tdd.core;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.gbastos.bakery.exception.NullAddedToOrderException;
import com.gbastos.bakery.exception.ProductAlreadyExistsException;
import com.gbastos.bakery.exception.ProductNotFoundException;
import com.gbastos.bakery.model.Product;
import com.gbastos.bakery.service.IOrderService;
import com.gbastos.bakery.service.OrderService;
import com.gbastos.bakery.tdd.ProductDataLoader;


public class OrderTest {

  private static final ProductDataLoader dataLoader = ProductDataLoader.getInstance();

  @BeforeAll
  public static void setUp() {
    dataLoader.reloadData();
  }

  /**
   * When the product is not found then throw product not found exception.
   */
  @Test
  void whenTheProductIsNotFound_thenThrow_ProductNotFoundException() {
    // Given
    IOrderService orderService = new OrderService();
  
    // When
    Product product = dataLoader.getProducts().get(0);
    orderService.addProduct(product);
  
    // Then
    assertThrows(ProductNotFoundException.class, () -> orderService.retrieveProduct("Do-Not-Exist"));
  }

  /**
   * When the product is added twice then throw product already exists exception.
   */
  @Test
  void whenTheProductIsAddedTwice_thenThrow_ProductAlreadyExistsException() {
    // Given
    List<Product> products = dataLoader.createDuplicateProducts(2, 1);
  
    // When
    OrderService orderService = new OrderService();
    orderService.addProduct(products.get(0));
  
    // Then
    assertThrows(ProductAlreadyExistsException.class, () -> orderService.addProduct(products.get(1)));
  }

  /**
   * When the null product is added to an order then throw null added to order exception.
   */
  @Test
  void whenTheNullProductIsAddedToAnOrder_thenThrow_NullAddedToOrderException() {
    assertThrows(NullAddedToOrderException.class, () -> new OrderService().addProduct(null));
  }
}
