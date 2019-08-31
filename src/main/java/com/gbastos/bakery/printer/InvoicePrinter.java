package com.gbastos.bakery.printer;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import com.gbastos.bakery.exception.CartIsEmptyException;
import com.gbastos.bakery.exception.InvoicePrinterNullInputsException;
import com.gbastos.bakery.model.Pack;
import com.gbastos.bakery.model.Product;
import com.gbastos.bakery.service.ICartService;
import com.gbastos.bakery.service.IProductService;
import com.gbastos.bakery.service.ProductService;

public class InvoicePrinter {

  private static final String TOTAL_PAYMENT_PRINT_FORMAT = "Total for payment is: $%.2f";
  private static final String PRODUCT_PRINT_FORMAT = "%d units of %s = $%.2f\n";
  private static final String PACK_PRINT_FORMAT = "\t%d x %d = $%.2f\n";

  private ICartService cartService;
  private PrintWriter writer;

  private void validateInputs(ICartService cartService, OutputStream output) {
    if (cartService == null || output == null) {
      throw new InvoicePrinterNullInputsException();
    } else if (cartService.isEmpty()) {
      throw new CartIsEmptyException();
    }
  }

  private void printProductResume(Product product, Integer amount) {
    IProductService productSevice = new ProductService(product);
    Map<Pack, Integer> packs = productSevice.retrievePacksByAmount(amount);
    writer.printf(PRODUCT_PRINT_FORMAT, amount, product.getCode(), productSevice.calculateTotalCostOfPacks(packs));

    packs.entrySet().stream().filter(entry -> entry.getValue() > 0)
    .sorted(this::comparePackagesByPackSizeDescending)
    .forEach(entry -> writer.printf(PACK_PRINT_FORMAT, entry.getValue(),
        entry.getKey().getAmount(), entry.getKey().getPrice()));
    writer.flush();
  }

  private int comparePackagesByPackSizeDescending(Entry<Pack, Integer> packA, Entry<Pack, Integer> packB) {
    return packB.getKey().getAmount().compareTo(packA.getKey().getAmount());
  }

  public InvoicePrinter(ICartService cartService, OutputStream out) {
    validateInputs(cartService, out);
    this.cartService = cartService;
    this.writer = new PrintWriter(out);
  }

  public void printBill() {
    cartService.retrieveCartItems().forEach(this::printProductResume);
    writer.printf(TOTAL_PAYMENT_PRINT_FORMAT, cartService.calculateItemsTotal());
    writer.flush();
  }
}
