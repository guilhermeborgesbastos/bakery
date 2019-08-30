package com.gbastos.bakery.tdd.printer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;
import com.gbastos.bakery.exception.CartIsEmptyException;
import com.gbastos.bakery.exception.ImpossiblePackDivisionException;
import com.gbastos.bakery.exception.InvoicePrinterNullInputsException;
import com.gbastos.bakery.printer.InvoicePrinter;
import com.gbastos.bakery.service.CartService;
import com.gbastos.bakery.service.ICartService;
import com.gbastos.bakery.service.IOrderService;
import com.gbastos.bakery.tdd.loader.AbstractDataset;

public class InvoicePrinterTest extends AbstractDataset {

  private static void printBill(ICartService cart) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    InvoicePrinter invoicePrinter = new InvoicePrinter(cart, outputStream);
    invoicePrinter.printBill();
  }

  // EXCEPTION TESTS

  /**
   * When invoice printer receives an empty cart then throw cart is empty exception.
   */
  @Test
  void whenInvoicePrinterReceivesAnEmptyCart_thenThrow_CartIsEmptyException() {
    assertThrows(CartIsEmptyException.class, () -> new InvoicePrinter(new CartService(), new ByteArrayOutputStream()));
  }

  /**
   * When invoice printer receives null arguments then throw invoice printer null inputs exception.
   */
  @Test
  void whenInvoicePrinterReceivesNullArguments_thenThrow_InvoicePrinterNullInputsException() {
    assertThrows(InvoicePrinterNullInputsException.class, () -> new InvoicePrinter(new CartService(), null));
    assertThrows(InvoicePrinterNullInputsException.class, () -> new InvoicePrinter(null, new ByteArrayOutputStream()));
  }

  /**
   * When amount cannot be divided in packs then throws impossible pack division exception.
   */
  @Test
  void whenAmountCannotBeDividedInPacks_thenThrows_ImpossiblePackDivisionException() {
    // Given
    IOrderService orderService = loadDataset();
    // When
    ICartService cartService = loadCart(orderService, "1 MB11\npay\n");
    // Then
    assertThrows(ImpossiblePackDivisionException.class, () -> printBill(cartService));

  }
}
