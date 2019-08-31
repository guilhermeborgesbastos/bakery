package com.gbastos.bakery.tdd.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import com.gbastos.bakery.dataloader.CartFeeder;
import com.gbastos.bakery.dataloader.ProductsFeeder;
import com.gbastos.bakery.reader.DataReaderFactory;
import com.gbastos.bakery.reader.TextDatasetReader;
import com.gbastos.bakery.service.ICartService;
import com.gbastos.bakery.service.IOrderService;

public class AbstractDataset {

  protected static final String DATASET_FILE_NAME = "test-dataset.txt";
  protected static final String DATASET_EMPTY_FILE = "test-empty-dataset.txt";

  /**
   * Loads the dataset from a resource file.
   *
   * @return the order service
   */
  protected static IOrderService loadDataset() {
    TextDatasetReader dataReader = DataReaderFactory.getDataReaderForFile(DATASET_FILE_NAME);
    ProductsFeeder textFileProductsLoader = new ProductsFeeder(dataReader);
    return textFileProductsLoader.loadProducts();
  }

  protected static ICartService loadCart(IOrderService orderService, String inputString) {
    ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
    InputStream testInputStream = new ByteArrayInputStream(inputString.getBytes(Charset.forName("UTF-8")));
    CartFeeder cartLoader = new CartFeeder(orderService, testInputStream, testOutputStream);
    return cartLoader.addOrderToCart();
  }
}
