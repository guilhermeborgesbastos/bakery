package com.gbastos.bakery.tdd.common;

/**
 * The Interface IDataLoader has the intention to be a contract for each LoaderData class in order
 * to force the implementation of the primary methods for each Unit Test.
 *
 * @author Guilherme Borges Bastos(guilhermeborgesbastos@gmail.com)
 */
public interface IDataLoader {

  /**
   * It deletes and reloads the data before each Unit Test in order to guarantee that if any other
   * Unit Test has tampered the collections data, this does not affect the coming tests (
   * guarantee the Isolation aspect ).
   */
  public void reloadData();

  /**
   * It drops the data for each collection that the Unit test is handling.
   */
  public void dropData();
}
