package com.gbastos.bakery.tdd.loader;

import com.gbastos.bakery.dataloader.ProductsFeeder;
import com.gbastos.bakery.reader.DataReaderFactory;
import com.gbastos.bakery.reader.TextDatasetReader;
import com.gbastos.bakery.service.IOrderService;

public class AbstractDataset {

  protected static final String DATASET_FILE_NAME = "test-dataset.txt";

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
}
