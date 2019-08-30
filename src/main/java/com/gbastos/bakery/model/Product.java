package com.gbastos.bakery.model;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * An Anemic data container for the Bakery product.
 *
 * @author Guilherme Borges Bastos
 * @date 08/29/2019
 */
public class Product {

  private String name;
  private String code;
  private Set<Pack> packs;

  public Product(String code, String name) {
    this.code = code;
    this.name = name;
    this.packs = new TreeSet<>(Comparator.reverseOrder());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Set<Pack> getPacks() {
    return packs;
  }

  public void setPacks(Set<Pack> packs) {
    this.packs = packs;
  }

  @Override
  public String toString() {
    return "Product [name=" + name + ", code=" + code + ", packs=" + packs + "]";
  }

  /**
   * Products with same code are considered equals.
   *
   * @param object the object to be compared
   * @return true, if successful
   */
  @Override
  public boolean equals(Object object) {
    if (object instanceof Product) {
      Product product = (Product) object;
      return this.code.equalsIgnoreCase(product.getCode());
    }
    return false;
  }
}
