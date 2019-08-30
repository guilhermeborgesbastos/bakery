package com.gbastos.bakery.service;

import java.util.HashMap;
import java.util.Map;
import com.gbastos.bakery.exception.ImpossiblePackDivisionException;
import com.gbastos.bakery.exception.InvalidAmountException;
import com.gbastos.bakery.exception.NoPacksFound;
import com.gbastos.bakery.exception.PackAlreadyExists;
import com.gbastos.bakery.exception.PackCannotBeNull;
import com.gbastos.bakery.model.Pack;
import com.gbastos.bakery.model.Product;

public class ProductService implements IProductService {

  private Product product;

  public ProductService(Product product) {
    this.product = product;
  }

  private void validatePackInput(Pack pack) {
    if (pack == null) {
      throw new PackCannotBeNull();
    } else if (this.product.getPacks().contains(pack)) {
      throw new PackAlreadyExists(pack.getAmount(), product);
    }
  }

  private void validateRetrievePacksByQuantityInput(Integer quantity) {
    if (quantity <= 0) {
      throw new InvalidAmountException(quantity);
    } else if (this.product.getPacks().isEmpty()) {
      throw new NoPacksFound(product);
    }
  }

  /**
   * Return the minimum sum of quantities between two packs.
   *
   * @param packA the pack A for comparison.
   * @param packB the pack B for comparison.
   * @return the map Map<Pack, Integer>
   */
  private Map<Pack, Integer> returnMinimumPack(Map<Pack, Integer> packA, Map<Pack, Integer> packB) {
    Map<Pack, Integer> minimumPack = packB != null ? packB : packA;
    if (packA != null && packB != null) {
      Integer sumPackA = calculateSumOfPackQuantity(packA);
      Integer sumPackB = calculateSumOfPackQuantity(packB);
      if (sumPackA < sumPackB) {
        minimumPack = packA;
      } else if (sumPackA == sumPackB
          && calculateTotalCostOfPacks(packA) > calculateTotalCostOfPacks(packB)) {
        minimumPack = packA;
      }
    }
    return minimumPack;
  }

  /**
   * Divides the remaining quantity from not modular divisions.
   *
   * @param pack
   * @param div
   * @param rem
   * @param packIndex the current package index
   * @param packs the packets for total quantity
   * @return the map
   */
  private Map<Pack, Integer> divideRemainingPacksForNotModularDivision(Integer div, Integer rem,
      int packIndex, Map<Pack, Integer> packs, Pack pack) {

    Map<Pack, Integer> minPacks = null;

    for (int counter = 0; counter <= div; counter++) {

      // Incrementally check remaining packets for remaining quantity
      Integer remainingTotal = rem + (counter * pack.getAmount());
      Integer newPackageIndex = packIndex + 1;
      packs.put(pack, div - counter);

      // Make a recursive call for remaining total quantity
      Map<Pack, Integer> recursivePacks = calculatePackDivisionIntoSmallerPacks(
          newPackageIndex, remainingTotal, new HashMap<>(packs));

      /*
       * Match found but some other combination might have a better match. So we store the current
       * minimum and continue trying out other combinations
       */
      minPacks = returnMinimumPack(minPacks, recursivePacks);
    }

    return minPacks;
  }

  /**
   * Calculates the pack division into smaller packs.
   *
   * @param packIndex
   * @param quantity
   * @param packs
   * @return the Map<Pack, Integer>
   */
  private Map<Pack, Integer> calculatePackDivisionIntoSmallerPacks(int packIndex,
      int quantity, Map<Pack, Integer> packs) {

    if (packIndex < this.product.getPacks().size()) {

      Pack pack = this.product.getPacks().toArray(new Pack[0])[packIndex];
      Integer div = quantity / pack.getAmount();
      Integer rem = quantity % pack.getAmount();

      // A division without remaining values
      if (rem == 0) {
        packs.put(pack, div);
        return packs;
      }

      /*
       * The quantity could not be fully divided using only the current pack, so it's necessary to
       * divide the remaining quantity into smaller packs.
       */
      return divideRemainingPacksForNotModularDivision(div, rem, packIndex, packs, pack);
    }
    return null;
  }

  @Override
  public void addPack(Pack packet) {
    validatePackInput(packet);
    this.product.getPacks().add(packet);
  }

  @Override
  public Integer calculateSumOfPackQuantity(Map<Pack, Integer> packs) {
    return packs.values().stream().mapToInt(Integer::intValue).sum();
  }

  @Override
  public Double calculateTotalCostOfPacks(Map<Pack, Integer> packs) {
    return packs.entrySet().parallelStream()
        .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
  }

  @Override
  public Map<Pack, Integer> retrievePacksByAmount(int quantity) {
    validateRetrievePacksByQuantityInput(quantity);

    Map<Pack, Integer> minPackets = calculatePackDivisionIntoSmallerPacks(0, quantity,
        new HashMap<>());

    if (minPackets == null) {
      throw new ImpossiblePackDivisionException(quantity, product);
    }

    return minPackets;
  }
}
