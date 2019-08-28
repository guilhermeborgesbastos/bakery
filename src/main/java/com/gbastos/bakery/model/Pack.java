package com.gbastos.bakery.model;

import com.gbastos.bakery.exception.InvalidPackPriceException;
import com.gbastos.bakery.exception.InvalidPackAmountException;

/**
 * An Anemic data container for the package configuration of each @Product. Basically, each product
 * can allow one or many prepackaged configurations. It implements the Comparable interface in order
 * to better fit in the logic that calculates the packages for each order.
 *
 * @author Guilherme Borges Bastos
 * @date 08/29/2019
 */
public class Pack implements Comparable<Pack> {

  private Integer amount;
  private Double price;

  private void validateInputs(int amount, double price) {
    if (amount <= 0)
      throw new InvalidPackAmountException(amount);
    if (price < 0)
      throw new InvalidPackPriceException(price);
  }

  public Pack(int amount, double price) {
    validateInputs(amount, price);
    this.setAmount(amount);
    this.setPrice(price);
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public int compareTo(Pack packet) {
    return this.getAmount().compareTo(packet.getAmount());
  }
}
