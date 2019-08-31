package com.gbastos.bakery.tdd.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import com.gbastos.bakery.datafeeder.ProductsFeeder;
import com.gbastos.bakery.exception.DatasetReaderNullException;
import com.gbastos.bakery.model.Product;
import com.gbastos.bakery.reader.DataReaderFactory;
import com.gbastos.bakery.reader.TextDatasetReader;
import com.gbastos.bakery.service.CartService;
import com.gbastos.bakery.service.ICartService;
import com.gbastos.bakery.service.IOrderService;

public class ProductsLoaderTest extends AbstractDataset {

  /**
   * Test solving the problem mentioned in the Hexad-Backend_Assignment_Bakery.pdf.
   */
  @Test
  void testSolvingTheAssignmentProblem() {
    // Given
    TextDatasetReader dataFactor = DataReaderFactory.retrieveDataReaderByFile(DATASET_FILE_NAME);
    ProductsFeeder productsFeeder = new ProductsFeeder(dataFactor);
    IOrderService orderService = productsFeeder.loadProducts();
    ICartService cartService = new CartService();

    // When
    cartService.addProduct(10, orderService.retrieveProduct(VS5));
    cartService.addProduct(14, orderService.retrieveProduct(MB11));
    cartService.addProduct(13, orderService.retrieveProduct(CF));

    // Then
    assertEquals(98.63, cartService.calculateItemsTotal());
  }

  /**
   * When dataset file is empty then the products loaded will be empty.
   */
  @Test
  void whenDatasetFileIsEmpty_then_TheLoadedProductsIsEmpty() {
    // Given
    TextDatasetReader dataFactor = DataReaderFactory.retrieveDataReaderByFile(DATASET_EMPTY_FILE);
    ProductsFeeder productsFeeder = new ProductsFeeder(dataFactor);

    // When
    IOrderService orderService = productsFeeder.loadProducts();

    // Then
    assertNotNull(orderService);
  }

  /**
   * When dataset file is valid the products are loaded.
   */
  @Test
  void whenDatasetFileIsValid_TheProductsAreLoaded() {
    // Given
    TextDatasetReader dataFactor = DataReaderFactory.retrieveDataReaderByFile(DATASET_FILE_NAME);
    ProductsFeeder productsFeeder = new ProductsFeeder(dataFactor);
    IOrderService orderService = productsFeeder.loadProducts();

    // When
    Product vegemiteScroll = orderService.retrieveProduct(VS5);
    Product croissant = orderService.retrieveProduct(CF);
    Product blueberryMuffin = orderService.retrieveProduct(MB11);
    Product cupcake = orderService.retrieveProduct(CPK);

    // Then
    assertNotNull(vegemiteScroll);
    assertNotNull(croissant);
    assertNotNull(blueberryMuffin);
    assertNotNull(cupcake);

    assertEquals(VS5, vegemiteScroll.getCode());
    assertEquals(CF, croissant.getCode());
    assertEquals(MB11, blueberryMuffin.getCode());
    assertEquals(CPK, cupcake.getCode());

    assertEquals("Vegemite Scroll", vegemiteScroll.getName());
    assertEquals("Croissant", croissant.getName());
    assertEquals("Blueberry Muffin", blueberryMuffin.getName());
    assertEquals("Cupcake", cupcake.getName());

    assertEquals(2, vegemiteScroll.getPacks().size());
    assertEquals(3, croissant.getPacks().size());
    assertEquals(3, blueberryMuffin.getPacks().size());
    assertEquals(3, cupcake.getPacks().size());
  }

  // EXCEPTION TEST

  /**
   * When dataset reader is null then throws dataset reader null exception.
   */
  @Test
  void whenDatasetReaderIsNull_thenThrows_DatasetReaderNullException() {
    assertThrows(DatasetReaderNullException.class, () -> new ProductsFeeder(null));
  }
}
