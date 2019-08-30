package com.gbastos.bakery.dataloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import com.gbastos.bakery.exception.CartFeederCreatedWithNullArguments;
import com.gbastos.bakery.exception.CartLoaderFailedToInputStream;
import com.gbastos.bakery.model.Product;
import com.gbastos.bakery.service.CartService;
import com.gbastos.bakery.service.ICartService;
import com.gbastos.bakery.service.IOrderService;

public class CartFeeder implements ICartFeeder{

  private IOrderService productService;
  private Map<Product, Integer> order;
  private BufferedReader reader;
  private PrintWriter writer;

  public CartFeeder(IOrderService productService, InputStream in, OutputStream out) {
    validateArguments(productService, in, out);
    this.reader = new BufferedReader(new InputStreamReader(in));
    this.writer = new PrintWriter(out);
    this.productService = productService;
  }

  private void validateArguments(IOrderService orderService, InputStream in, OutputStream out) {
    if (orderService == null || in == null || out == null) {
      throw new CartFeederCreatedWithNullArguments();
    }
  }

  private void doOrderProduct(SimpleEntry<Product, Integer> orderPair) {
    if (orderPair != null) {
      addOrder(orderPair.getKey(), orderPair.getValue());
    }
  }

  private SimpleEntry<Product, Integer> readOrderTerminalInput(String line) {
    SimpleEntry<Product, Integer> orderPair = null;
    line = line.trim();
    String[] words = line.split(" ");

    try {
      if (words.length == 2) {
        orderPair = new SimpleEntry<>(productService.retrieveProduct(words[1]), Integer.parseInt(words[0]));
      } else {
        throw new RuntimeException();
      }
    } catch (RuntimeException e) {
      println("You've entered an invalid input!");
      println("Type following the pattern: 'quantity product-code'.");
      println("E.g: 10 CF");
      println("Give another try.");
    }
    return orderPair;
  }

  private void readOrdersFromInputStream() {
    order = new LinkedHashMap<>();
    String input = null;
    try {
      while ((input = reader.readLine()) != null && !input.equalsIgnoreCase("pay")) {
        if (input == "") {
          continue;
        }
        doOrderProduct(readOrderTerminalInput(input));
      }
    } catch (IOException e) {
      throw new CartLoaderFailedToInputStream(e);
    }
  }

  private void addOrder(Product product, Integer amount) {
    if (order.containsKey(product)) {
      order.put(product, amount + order.get(product));
    } else {
      order.put(product, amount);
    }
  }

  private ICartService fillCart() {
    ICartService cartService = new CartService();
    order.entrySet().forEach(entry -> cartService.addProduct(entry.getValue(), entry.getKey()));
    return cartService;
  }

  private void println(String value) {
    writer.println(value);
    writer.flush();
  }

  @Override
  public ICartService addOrderToCart() {
    readOrdersFromInputStream();
    return fillCart();
  }
}
