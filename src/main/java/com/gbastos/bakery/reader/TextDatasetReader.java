package com.gbastos.bakery.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import com.gbastos.bakery.exception.DatasetFileNotFoundException;
import com.gbastos.bakery.exception.DatasetReaderLoaderPrecedenceException;
import com.gbastos.bakery.exception.ErrorWhileReadingFileException;
import com.gbastos.bakery.model.Pack;
import com.gbastos.bakery.model.Product;
import com.gbastos.bakery.service.IProductService;
import com.gbastos.bakery.service.ProductService;

public class TextDatasetReader {

  private static final String DELIMITER = ",";

  private Set<Product> products;
  private File dataFile;
  private Boolean fileRead;

  protected TextDatasetReader(File dataFile) {
    this.dataFile = dataFile;
    setUp();
  }

  private void setUp() {
    products = new HashSet<>();
    fileRead = false;
  }

  private void readProductsFromLine(String line) {
    String[] words = getWordsForLine(line);
    if (words != null && words.length == 2) {
      products.add(new Product(words[1], words[0]));
    }
  }

  private void readPacksFromLine(String line) {
    String[] words = getWordsForLine(line);
    if (words != null && words.length == 3) {
      readPacksFromWords(words);
    }
  }

  private void readPacksFromWords(String[] words) {
    Optional<Product> product = fetchProductByCode(words[0]);
    if (product.isPresent()) {
      IProductService productService = new ProductService(product.get());
      Pack packet = new Pack(Integer.parseInt(words[1]), Double.parseDouble(words[2]));
      productService.addPack(packet);
    }
  }

  private String[] getWordsForLine(String line) {
    return line.split(DELIMITER);
  }

  private void readClientLines(Consumer<String> clientLine) {
    try (BufferedReader fileReader = new BufferedReader(new FileReader(dataFile))) {
      fileReader.lines().forEach(clientLine);
    } catch (FileNotFoundException e) {
      throw new DatasetFileNotFoundException(dataFile.getPath(), e);
    } catch (IOException e) {
      throw new ErrorWhileReadingFileException(dataFile.getName(), e);
    }
  }

  private Optional<Product> fetchProductByCode(String code) {
    return products.stream().filter(product -> product.getCode().equalsIgnoreCase(code)).findFirst();
  }

  private void assertFileRead() {
    if (!fileRead) {
      throw new DatasetReaderLoaderPrecedenceException();
    }
  }

  public Boolean read() {
    try {
      // Read products
      readClientLines(this::readProductsFromLine);
      // Read packets
      readClientLines(this::readPacksFromLine);
      fileRead = true;
    } catch (RuntimeException e) {
      setUp();
    }
    return fileRead;
  }

  public Set<Product> getAllProducts() {
    assertFileRead();
    return products;
  }
}
