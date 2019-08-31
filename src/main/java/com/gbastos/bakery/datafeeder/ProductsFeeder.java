package com.gbastos.bakery.datafeeder;

import com.gbastos.bakery.exception.DataReaderIsNull;
import com.gbastos.bakery.reader.TextDatasetReader;
import com.gbastos.bakery.service.IOrderService;
import com.gbastos.bakery.service.OrderService;

public class ProductsFeeder {

  private TextDatasetReader datasetReader;

  public ProductsFeeder(TextDatasetReader datasetReader) {
    if (datasetReader == null)  {
      throw new DataReaderIsNull();
    }

    this.datasetReader = datasetReader;
  }

  public IOrderService loadProducts() {
    datasetReader.read();
    IOrderService orderService = new OrderService();
    datasetReader.getAllProducts().forEach(orderService::addProduct);
    return orderService;
  }
}
