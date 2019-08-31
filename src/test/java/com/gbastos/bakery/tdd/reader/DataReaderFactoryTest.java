package com.gbastos.bakery.tdd.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import com.gbastos.bakery.exception.DatasetFileNotFoundException;
import com.gbastos.bakery.exception.DatasetFilePathIsEmptyException;
import com.gbastos.bakery.exception.DatasetFilePathIsNullException;
import com.gbastos.bakery.exception.NotSupportedDatasetTypeException;
import com.gbastos.bakery.model.Pack;
import com.gbastos.bakery.model.Product;
import com.gbastos.bakery.reader.DataReaderFactory;
import com.gbastos.bakery.reader.TextDatasetReader;
import com.gbastos.bakery.tdd.loader.AbstractDataset;

public class DataReaderFactoryTest extends AbstractDataset {

  /**
   * When data reader factory retrieves all products.
   */
  @Test
  void whenDataReaderFactoryRetrievesAllProducts() {
    // Given
    TextDatasetReader readerFactory = DataReaderFactory.retrieveDataReaderByFile(DATASET_FILE_NAME);
    readerFactory.read();

    // When
    Set<Product> products = readerFactory.getAllProducts();

    // Then
    assertNotNull(products);
    assertEquals(4, products.size());
  }

  /**
   * When data reader factory retrieves all the packs.
   */
  @Test
  void whenDataReaderFactoryRetrievesAllPacks() {
    // Given
    TextDatasetReader readerFactory = DataReaderFactory.retrieveDataReaderByFile(DATASET_FILE_NAME);
    readerFactory.read();
    Set<Product> products = readerFactory.getAllProducts();
    List<Pack> productsPacks = new ArrayList<Pack>();

    // When
    products.stream().map(product -> product.getPacks()).forEach(packs -> productsPacks.addAll(packs));

    // Then
    assertEquals(11, productsPacks.size());
  }

  /**
   * When a valid dataset file is used then returns a instance of TextDatasetReader.
   */
  @Test
  void whenValidDatasetFileIsUsed_then_ReturnsTextDatasetReader() {
    assertTrue(DataReaderFactory.retrieveDataReaderByFile(DATASET_FILE_NAME) instanceof TextDatasetReader);
  }

  // EXCEPTIONS TESTS

  /**
   * When dataset file does not exist then throws dataset file not found exception.
   */
  @Test
  void whenDatasetFileDoesNotExist_thenThrows_DatasetFileNotFoundException() {
    assertThrows(DatasetFileNotFoundException.class, () -> DataReaderFactory.retrieveDataReaderByFile("Data-File-Not-Exists.txt"));
  }

  /**
   * When dataset file path is null then throws data file path is null.
   */
  @Test
  void whenDatasetFilePathIsNull_thenThrows_DataFilePathIsNull() {
    assertThrows(DatasetFilePathIsNullException.class, () -> DataReaderFactory.retrieveDataReaderByFile(null));
  }

  /**
   * When dataset file path is empty then throws dataset file path is empty.
   */
  @Test
  void whenDatasetFilePathIsEmpty_thenThrows_DatasetFilePathIsEmpty() {
    assertThrows(DatasetFilePathIsEmptyException.class, () -> DataReaderFactory.retrieveDataReaderByFile(" "));
  }

  /**
   * When dataset file type is not supported then throws not supported dataset type exception.
   */
  @Test
  void whenDatasetFileTypeIsNotSupported_thenThrows_NotSupportedDatasetTypeException() {
    assertThrows(NotSupportedDatasetTypeException.class,
        () -> DataReaderFactory.retrieveDataReaderByFile("invalid-data-reader-file.csv"));
  }
}
