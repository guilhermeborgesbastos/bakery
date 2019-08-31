package com.gbastos.bakery.tdd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.gbastos.bakery.exception.ImpossiblePackDivisionException;
import com.gbastos.bakery.exception.InvalidAmountException;
import com.gbastos.bakery.exception.InvalidPackAmountException;
import com.gbastos.bakery.exception.InvalidPackPriceException;
import com.gbastos.bakery.exception.NoPacksFound;
import com.gbastos.bakery.model.Pack;
import com.gbastos.bakery.model.Product;
import com.gbastos.bakery.service.IProductService;
import com.gbastos.bakery.service.ProductService;
import com.gbastos.bakery.tdd.common.ProductDataLoader;

public class PackTest  {

  private static final ProductDataLoader productDataLoader = ProductDataLoader.getInstance();

  @BeforeAll
  public static void setUp() {
    productDataLoader.reloadData();
  }

  private static Stream<Arguments> generateTestCasesData() {
    return Stream.of(
        Arguments.of(2, new Integer[] {2, 4, 15}, 1),
        Arguments.of(5, new Integer[] {5, 3}, 1),
        Arguments.of(6, new Integer[] {1, 2, 3}, 2),
        Arguments.of(12, new Integer[] {5, 2, 1}, 3),
        Arguments.of(32, new Integer[] {5, 3}, 8),
        Arguments.of(100, new Integer[] {100, 1}, 1)
        );
  }

  private void checkPackMinimunDivision(Integer amount, Integer[] packAmounts, Integer expected) {
    Product product = productDataLoader.createProductWithPacks(packAmounts);
    IProductService productService = new ProductService(product);

    final Map<Pack, Integer> packetsForTotalQuantity =
        productService.retrievePacksByAmount(amount);

    assertEquals(expected, productService.calculateSumOfPackQuantity(packetsForTotalQuantity));
  }

  @ParameterizedTest
  @MethodSource("generateTestCasesData")
  public void packDivisionParametizedTests(Integer amount, Integer[] packs, Integer expected) {
    checkPackMinimunDivision(amount, packs, expected);
  }

  // EXCEPTION TESTS

  /**
   * When pack amount is zero or less then throw invalid pack amount exception.
   */
  @Test
  public void whenPackAmountIsZeroOrLess_thenThrow_InvalidPackAmountException() {
    assertThrows(InvalidPackAmountException.class, () -> new Pack(-5, 5));
    assertThrows(InvalidPackAmountException.class, () -> new Pack(-1, 11));
    assertThrows(InvalidPackAmountException.class, () -> new Pack(0, 25));
  }

  /**
   * When pack price lower than zero then throw invalid pack price exception.
   */
  @Test
  public void whenPackPriceLowerThanZero_thenThrow_InvalidPackPriceException() {
    assertThrows(InvalidPackPriceException.class, () -> new Pack(1, -1));
    assertThrows(InvalidPackPriceException.class, () -> new Pack(2, -10));
  }

  /**
   * When there is no path divisions then throw no packs found.
   */
  @Test
  public void whenThereIsNoPathDivisions_thenThrow_NoPacksFound() {
    assertThrows(NoPacksFound.class, () -> checkPackMinimunDivision(3, new Integer[] {}, 5));
  }

  /**
   * When amount is lower than any Pack option then throw impossible pack division exception.
   */
  @Test
  public void whenAmountIsLowerThanAnyPackOption_thenThrow_ImpossiblePackDivisionException() {
    assertThrows(ImpossiblePackDivisionException.class, () -> checkPackMinimunDivision(2, new Integer[] { 3, 9, 16 }, 0));
  }

  /**
   * When amount is less than one then throw invalid amount exception.
   */
  @Test
  public void whenAmountIsLessThanOne_thenThrow_InvalidAmountException() {
    assertThrows(InvalidAmountException.class, () -> checkPackMinimunDivision(0, new Integer[] { 3, 9, 16 }, 0));
    assertThrows(InvalidAmountException.class, () -> checkPackMinimunDivision(-10, new Integer[] { 3, 4, 12 }, 0));
  }

  /**
   * The pack object sanity test.
   */
  @Test
  public void thePack_ObjectSanityTest() {
    // Given
    Integer amount = 5;
    Double price = 26.98;

    // When
    Pack pack = new Pack(amount, price);

    // Then
    assertEquals(amount, pack.getAmount());
    assertEquals(price, pack.getPrice());
  }
}
