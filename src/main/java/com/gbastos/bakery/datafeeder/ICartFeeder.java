package com.gbastos.bakery.datafeeder;

import com.gbastos.bakery.service.ICartService;

public interface ICartFeeder {

  /**
   * Adds the order to the cart, reading them from the InputStream.
   */
  ICartService addOrderToCart();
}
