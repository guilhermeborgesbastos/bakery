package com.gbastos.bakery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.gbastos.bakery.datafeeder.CartFeeder;
import com.gbastos.bakery.datafeeder.ProductsFeeder;
import com.gbastos.bakery.printer.InvoicePrinter;
import com.gbastos.bakery.reader.DataReaderFactory;
import com.gbastos.bakery.reader.TextDatasetReader;
import com.gbastos.bakery.service.ICartService;
import com.gbastos.bakery.service.IOrderService;
import com.gbastos.bakery.utils.InterfaceUtils;
import com.gbastos.bakery.utils.PropertiesUtils;

public class Main {

  private static IOrderService orderService;
  private static TextDatasetReader dataReader;

  // Loads the Products
  static {
    orderService = loadBakeryProducts();
  }

  private static IOrderService loadBakeryProducts() {
    String dataloaderFilePatch = PropertiesUtils.retrievePropertyValue("dataloader.file.path", "application.properties");
    dataReader = DataReaderFactory.retrieveDataReaderByFile(dataloaderFilePatch);
    ProductsFeeder textFileBakeryLoader = new ProductsFeeder(dataReader);
    return textFileBakeryLoader.loadProducts();
  }

  private static ICartService readOrdersToFillCart() {
    CartFeeder cartLoader = new CartFeeder(orderService, System.in, System.out);
    return cartLoader.addOrderToCart();
  }

  private static void printBillForCart(ICartService cartService) {
    System.out.println("\n\n##### ORDER RESUME #####");
    InvoicePrinter bakeryBillPrinter = new InvoicePrinter(cartService, System.out);
    bakeryBillPrinter.printBill();
  }

  private static void enterNewOrder() {
    try {
      ICartService cartService = readOrdersToFillCart();
      printBillForCart(cartService);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static void loopProgram() throws IOException {
    Boolean hasNextOrder = true;
    while (hasNextOrder) {
      enterNewOrder();
      System.out.println("\n\nDo you want to insert another order? [Enter y/n] : ");
      String input = (new BufferedReader(new InputStreamReader(System.in))).readLine();
      if (!input.equalsIgnoreCase("y")) {
        hasNextOrder = false;
      }
    }
  }

  public static void main(String[] args) throws IOException {
    System.out.println("Be welcome! Are you a new user?");
    if(InterfaceUtils.askQuestion("We can help you! Do you want help to use the system?")) {
      InterfaceUtils.showQuickStartGuide(dataReader);
    }

    System.out.println(InterfaceUtils.DIVIDER);
    System.out.println("Enter your first order:\n");

    loopProgram();
  }
}
