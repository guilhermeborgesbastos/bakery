package com.gbastos.bakery.tdd.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.gbastos.bakery.datafeeder.CartFeeder;
import com.gbastos.bakery.exception.CartFeederCreatedWithNullArguments;
import com.gbastos.bakery.service.ICartService;
import com.gbastos.bakery.service.OrderService;

public class CartFeederTest extends AbstractDataset {

  private static Stream<Arguments> generateTestCasesData() {
    return Stream.of(
        Arguments.of("cart-feeder-test-case-1.txt", 3, 98.63),
        Arguments.of("cart-feeder-test-case-2.txt", 1, 17.98),
        Arguments.of("cart-feeder-test-case-3.txt", 2, 38.92)
        );
  }

  @ParameterizedTest
  @MethodSource("generateTestCasesData")
  public void addOrderToCartTests(String testCaseFileName, Integer expectedCartItemsSize, Double expectedCartTotal) throws FileNotFoundException {
    checkAddOrderToCartTest(testCaseFileName, expectedCartItemsSize, expectedCartTotal);
  }

  private void checkAddOrderToCartTest(String testCaseFileName, Integer expectedCartItemsSize, Double expectedCartTotal) throws FileNotFoundException {
    // Given
    ClassLoader classLoader = getClass().getClassLoader();
    File testCaseFile = new File(classLoader.getResource(testCaseFileName).getFile());
    InputStream testInputStream = new FileInputStream(testCaseFile);
    CartFeeder cartFeeder = new CartFeeder(loadDataset(), testInputStream, new ByteArrayOutputStream());

    // When
    ICartService cartService = cartFeeder.addOrderToCart();

    // Then
    assertNotNull(cartService);
    assertEquals(false, cartService.isEmpty());
    assertEquals(expectedCartItemsSize, cartService.retrieveCartItems().size());
    assertEquals(expectedCartTotal, cartService.calculateItemsTotal());
  }

  @Test
  void whenCartFeederReceivesNullArguments_thenThrow_CartFeederCreatedWithNullArguments() {
    assertThrows(CartFeederCreatedWithNullArguments.class, () -> new CartFeeder(null, System.in, System.out));
    assertThrows(CartFeederCreatedWithNullArguments.class, () -> new CartFeeder(new OrderService(), System.in, null));
    assertThrows(CartFeederCreatedWithNullArguments.class, () -> new CartFeeder(new OrderService(), null, System.out));
  }
}