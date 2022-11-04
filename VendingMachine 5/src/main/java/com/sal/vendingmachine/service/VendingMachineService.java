
package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author salajrawi
 */
public interface VendingMachineService {
    //methods checks if user has put enough money
    void checkIfEnoughMoney(Item item, BigDecimal inputMoney)throws
            VendingMachineInsufficientFundsException;

    void removeItem(String name) throws
            VendingMachineItemInventoryException,
            VendingMachineException;

    Map<String, BigDecimal> getItemsInStockWithCosts () throws
            VendingMachineException;

    Item getItem(String name, BigDecimal inputMoney) throws
            VendingMachineInsufficientFundsException,
            VendingMachineItemInventoryException,
            VendingMachineException;

    Map<BigDecimal, BigDecimal> getChangePerCoin(Item item, BigDecimal money);
}
