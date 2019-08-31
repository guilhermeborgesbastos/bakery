package com.gbastos.bakery.tdd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.gbastos.bakery.exception.InvalidAmountException;
import com.gbastos.bakery.exception.NullProductException;
import com.gbastos.bakery.model.Product;
import com.gbastos.bakery.service.CartService;
import com.gbastos.bakery.service.ICartService;
import com.gbastos.bakery.tdd.common.ProductDataLoader;

public class CartServiceTest {

  private static final ProductDataLoader productDataLoader = ProductDataLoader.getInstance();

  @BeforeAll
  public static void setUp() {
    productDataLoader.reloadData();
  }

  /**
   * Retrieve cart items test.
   */
  @Test
  void retrieveCartItemsTest() {
    // Given
    List<Product> products = productDataLoader.createSequentialProducts(3, 1);

    ICartService cartService = new CartService();
    products.stream().forEach(product -> cartService.addProduct(1, product));

    // When
    Map<Product, Integer> cartContent = cartService.retrieveCartItems();

    // Then
    assertEquals(products.size(), cartContent.size());
    products.stream().forEach(product -> assertEquals(cartContent.containsKey(product), true));
    products.stream().forEach(product -> assertEquals(products.size(), cartContent.size()));
  }

  /**
   * Checks if is empty test.
   */
  @Test
  void isEmptyTest() {
    // Given
    ICartService cartService = new CartService();
  
    // Then
    assertEquals(true, cartService.isEmpty());
  }

  /**
   * When cart is empty then its amount is zero.
   */
  @Test
  void whenCartIsEmpty_then_ItsAmountIsZero() {
    // Given
    ICartService cartService = new CartService();

    // Then
    assertEquals(true, cartService.isEmpty());
    assertEquals(0, cartService.calculateItemsTotal());
  }

  /**
   * When null product is added to the cart then it throws a null product exception.
   */
  @Test
  void whenNullIsAddedToCart_thenThrow_NullProductException() {
    assertThrows(NullProductException.class, () -> new CartService().addProduct(1, null));
  }

  /**
   * When amount is less than one then throw invalid quantity exception.
   */
  @Test
  void whenAmountLessThanOne_thenThrow_InvalidQuantityException() {
    // Given
    Product product = productDataLoader.getProducts().get(0);
    ICartService cartService = new CartService();
  
    // Then
    assertThrows(InvalidAmountException.class, () -> cartService.addProduct(0, product));
    assertThrows(InvalidAmountException.class, () -> cartService.addProduct(-1, product));
    assertThrows(InvalidAmountException.class, () -> cartService.addProduct(-5, product));
  }

  /**
   * When a product is added more than once, its amount is incremented in the cart.
   */
  @Test
  void whenProductIsAddedMoreThanOnce_ItsAmountIsIncremented() {
    // Given
    Product product = productDataLoader.getProducts().get(0);
    int numberOfInsertions = 5;
  
    // When
    ICartService cartService = new CartService();
    IntStream.rangeClosed(1, numberOfInsertions)
    .forEach(index -> {
      cartService.addProduct(1, product);
    });
  
    // Then
    Integer totalQuantity = cartService.retrieveCartItems().get(product);
    assertEquals(numberOfInsertions, totalQuantity);
  }

  /**
   * When calculate items total in a cart where each product has only one package option.
   */
  @Test
  void whenCalculateItemsTotal_where_EachProductHasOnlyOnePackage() {
    // Given
    List<Product> products = productDataLoader.createSequentialProducts(3, 1);

    // When
    ICartService cartService = new CartService();
    products.stream().forEach(product -> cartService.addProduct(1, product));

    // Then
    assertEquals(30d, cartService.calculateItemsTotal());
  }

  @Test
  void whenCalculateItemsTotal_where_EachProductHasMoreThanOnePackage() {
    // Given
    List<Product> products = productDataLoader.createSequentialProducts(5, 3);

    // When
    ICartService cartService = new CartService();
    products.stream().forEach(product -> cartService.addProduct(3, product));

    // Then
    assertEquals(150d, cartService.calculateItemsTotal());
  }
}
