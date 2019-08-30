package com.gbastos.bakery.tdd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import com.gbastos.bakery.model.Pack;
import com.gbastos.bakery.model.Product;
import com.gbastos.bakery.service.ProductService;
import com.gbastos.bakery.tdd.common.IDataLoader;

public class ProductDataLoader implements IDataLoader {

  private static ProductDataLoader instance = null;
  private List<Product> products = new ArrayList<>();

  public final String DUPLICATE_PRODUCT_CODE = "the_same_code";

  // Exists only to avoid instantiation.
  private ProductDataLoader() {
    super();
  }

  /**
   * Gets the single instance of ProductDataLoader.
   *
   * @return single instance of ProductDataLoader
   */
  public static ProductDataLoader getInstance() {
    if (instance == null) {
      instance = new ProductDataLoader();
    }
    return instance;
  }

  private static List<Product> mimicProducts(int productCount) {
    List<Product> products = new ArrayList<>();
    IntStream.range(0, productCount)
      .forEach(i -> products.add(new Product("code_" + i, "name_" + i)));
    return products;
  }

  public List<Product> getProducts() {
    return products;
  }

  /**
   * Insert the Mock @Product Object into the DataLoader list.
   *
   * @param products
   */
  public void insertProducts(final List<Product> products) {
    for (final Product product : products) {
      this.products.add(product);
    }
  }

  /**
   * Creates mimic @Product object data.
   *
   * @param quantity
   * @param packsAmount
   * @return the product[]
   */
  public List<Product> createSequentialProducts(int quantity, int packsAmount) {
    List<Product> mimickedProducts = mimicProducts(quantity);
    mimickedProducts.forEach(product -> {
      ProductService productsService = new ProductService(product);
      IntStream.rangeClosed(1, packsAmount).forEach(id -> productsService.addPack(new Pack(id, id * 10)));
    });
    return mimickedProducts;
  }

  /**
   * Creates duplicated @Product object data (The same code).
   *
   * @param quantity
   * @param packsAmount
   * @return the product list
   */
  public List<Product> createDuplicateProducts(int quantity, int packsAmount) {
    List<Product> products = new ArrayList<>();
    IntStream.range(0, quantity)
    .forEach(i -> products.add(new Product(DUPLICATE_PRODUCT_CODE, "name_" + i)));
    return products;
  }

  public Product createProductWithPacks(Integer[] packAmount) {
    Product product = new Product("product-code", "product-name");
    Arrays.stream(packAmount).forEach(amount -> product.getPacks().add(new Pack(amount, 0)));
    return product;
  }

  @Override
  public void reloadData() {
    dropData();

    // Insert the complete Product dataset
    insertProducts(createDuplicateProducts(2, 1));
    insertProducts(createSequentialProducts(5, 2));
  }

  @Override
  public void dropData() {
    this.products.clear();
  }
}
