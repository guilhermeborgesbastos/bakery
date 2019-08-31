package com.gbastos.bakery.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.gbastos.bakery.model.Product;
import com.gbastos.bakery.printer.InvoicePrinter;
import com.gbastos.bakery.reader.TextDatasetReader;
import com.gbastos.bakery.service.ICartService;

public class InterfaceUtils {

  public static final String DIVIDER = "\n##################################################";

  public static void printBillForCart(ICartService cart) {
    InvoicePrinter bakeryBillPrinter = new InvoicePrinter(cart, System.out);
    bakeryBillPrinter.printBill();
  }

  public static boolean askQuestion(String phrase) throws IOException {
    System.out.println(phrase + " [Enter y/n]:");
    String response = (new BufferedReader(new InputStreamReader(System.in))).readLine().toLowerCase();
    if (response.equals("y")) {
      return true;
    }
    return false;
  }

  public static void showQuickStartGuide(TextDatasetReader datasetReader) throws IOException {
    System.out.println(DIVIDER);
    System.out.println("Below are the instructions about how to add a product to the cart:");
    System.out.println("- Add the quantity, followed by the code of the product.\n");
    System.out.println("- Add the word 'pay' when you want to finish the order.");
    System.out.println("For example:");
    System.out.println("10 VS5");
    System.out.println("2 VS5");
    System.out.println("14 MB11");
    System.out.println("14 VS5");;
    System.out.println("pay");

    System.out.println("\nP.S: Notice that you can add the same product multiple times to the same cart, if needed.\n");

    // Show table of products available
    drawProductsContentTable(datasetReader);
    System.out.println();
  }

  public static void drawProductsContentTable(TextDatasetReader datasetReader) {

    // The Header of the table
    System.out.println(DIVIDER);
    System.out.printf("%30s", "Product Table");
    System.out.println(DIVIDER);
    System.out.printf("%-25s", "Product Code:");
    System.out.printf("%-25s", "Product Name:");
    System.out.println(DIVIDER);

    // Table rows
    datasetReader.getAllProducts().forEach(product -> printTableRow(product));
  }

  private static void printTableRow(Product product) {
    System.out.println();
    System.out.printf("%-25s", product.getCode().toUpperCase());
    System.out.printf("%-25s", product.getName());
  }
}
