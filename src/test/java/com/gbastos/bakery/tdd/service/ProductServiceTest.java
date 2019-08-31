package com.gbastos.bakery.tdd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.gbastos.bakery.exception.PackAlreadyExists;
import com.gbastos.bakery.exception.PackCannotBeNull;
import com.gbastos.bakery.model.Pack;
import com.gbastos.bakery.model.Product;
import com.gbastos.bakery.service.IProductService;
import com.gbastos.bakery.service.ProductService;
import com.gbastos.bakery.tdd.ProductDataLoader;

public class ProductServiceTest {

  private static final ProductDataLoader dataLoader = ProductDataLoader.getInstance();

  @BeforeAll
  public static void setUp() {
    dataLoader.reloadData();
  }

  /**
   * When null is added as pack into a product then throw pack cannot be null.
   */
  @Test
  void whenNullIsAddedAsPack_thenThrow_PackCannotBeNull() {
    assertThrows(PackCannotBeNull.class, () -> new ProductService(dataLoader.getProducts().get(0)).addPack(null));
  }

  /**
   * When packs of same quantity are added then throws pack already exists.
   */
  @Test
  void whenPacksOfSameQuantityAreAdded_thenThrows_PackAlreadyExists() {
    // Given
    Product product = new Product("product_code", "product_name");
    Pack pack1 = new Pack(2, 5);
    Pack pack2 = new Pack(2, 10);

    IProductService productService = new ProductService(product);
    // When
    productService.addPack(pack1);
    // Then
    assertThrows(PackAlreadyExists.class, () -> productService.addPack(pack2));
  }

  /**
   * When the product has the same code then they are equal.
   */
  @Test
  void whenTheProductHasTheSameCode_then_TheyAreEqual() {
    // Given
    List<Product> duplicatedProducts = dataLoader.createDuplicateProducts(3, 1);

    // When
    String duplicatedCode = dataLoader.DUPLICATE_PRODUCT_CODE;

    // Then
    assertEquals(duplicatedProducts.get(0).getCode(), duplicatedCode);
    assertEquals(duplicatedProducts.get(0), duplicatedProducts.get(1));
    assertNotEquals(duplicatedProducts.get(0), new Object());
  }

  /**
   * The product object sanity test.
   */
  @Test
  void theProduct_ObjectSanityTest() {
    // Given
    String code = "MB11";
    String name = "Blueberry Muffin";

    // When
    Product product = new Product(code, name);

    // Then
    assertEquals(code, product.getCode());
    assertEquals(name, product.getName());
  }
}
