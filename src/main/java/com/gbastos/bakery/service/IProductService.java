package com.gbastos.bakery.service;

import java.util.Map;
import com.gbastos.bakery.model.Pack;

public interface IProductService {

  /**
   * Adds the pack into a product.
   *
   * @param pack
   */
  public void addPack(Pack pack);

  /**
   * Retrieves the packs distribution of the product based on a given quantity.
   *
   * @param amount
   * @return the Map<Pack, Integer>
   */
  public Map<Pack, Integer> retrievePacksByAmount(int amount);

  /**
   * Calculates total cost of packs by getting the sum of all packs multiplied by the pack quantity.
   *
   * @param pack
   * @return the total cost of packs
   */
  public Double calculateTotalCostOfPacks(Map<Pack, Integer> pack);

  /**
   * Calculates the sum up of the pack's amount.
   *
   * @param packetMap the packet map
   * @return the sum
   */
  public Integer calculateSumOfPackQuantity(Map<Pack, Integer> packs);
}
