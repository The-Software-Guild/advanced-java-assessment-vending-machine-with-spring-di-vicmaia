
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Salajrawi
 */
public interface VendingMachineDao {
    /**
     * Removes from the Vending Machine the item associated with the given name.
     */
    void removeItem(String name) throws VendingMachineException;


    List<Item> getAllItems() throws VendingMachineException ;


    //List<String> getListOfItemNamesInStock()throws VendingMachinePersistenceException;

    int getItemInventory(String name) throws VendingMachineException;

    Item getItem(String name)throws VendingMachineException;

    Map<String, BigDecimal> getMapOfItemNamesInStockWithCosts()throws VendingMachineException;
}
