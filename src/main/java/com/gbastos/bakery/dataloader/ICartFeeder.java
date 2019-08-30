package com.gbastos.bakery.dataloader;

import com.gbastos.bakery.service.ICartService;

public interface ICartLoader {

  /**
   * Adds the order to the cart, reading them from the InputStream.
   */
  ICartService addOrderToCart();
}
