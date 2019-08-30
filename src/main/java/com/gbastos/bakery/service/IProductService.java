package com.gbastos.bakery.service;

import java.util.Map;
import com.gbastos.bakery.model.Pack;

public interface IProductService {

  public void addPack(Pack pack);

  public Map<Pack, Integer> retrievePacksByQuantity(int quantity);

  public Double calculateTotalCostOfPacks(Map<Pack, Integer> packetMap);

  public Integer calculateSumOfPackQuantity(Map<Pack, Integer> packetMap);
}
