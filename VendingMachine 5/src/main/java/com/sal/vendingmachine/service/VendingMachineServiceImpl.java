
package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.AuditDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author salajrawi
 */
public class VendingMachineServiceImpl implements VendingMachineService{

    //The service layer is responsible for the business logic of an application. It sits between
    //the controller and DAOs.

    private AuditDao auditDao;
    private VendingMachineDao dao;

    public VendingMachineServiceImpl(AuditDao auditDao, VendingMachineDao dao) {
        this.auditDao = auditDao;
        this.dao = dao;
    }

    @Override
    public void checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws VendingMachineInsufficientFundsException {
        //Checks if the user has input enough money to buy selected item
        //If the cost of the item is greater than the amount of money put in
        if (item.getCost().compareTo(inputMoney)==1) {
            throw new VendingMachineInsufficientFundsException  (
                    "ERROR: insufficient funds, you have only input "+ inputMoney);
        }
    }

    //gets the list of items with their costs
    @Override
    public Map<String, BigDecimal> getItemsInStockWithCosts () throws VendingMachineException{
        //Map of key=name, value=cost
        Map<String, BigDecimal> itemsInStockWithCosts = dao.getMapOfItemNamesInStockWithCosts();
        return itemsInStockWithCosts;
    }

    //gets money and converts to coins
    @Override
    public Map<BigDecimal, BigDecimal> getChangePerCoin(Item item, BigDecimal money) {
        BigDecimal itemCost = item.getCost();
        Map<BigDecimal, BigDecimal> changeDuePerCoin = Change.changeDuePerCoin(itemCost, money);
        return changeDuePerCoin;
    }

    @Override
    public Item getItem(String name, BigDecimal inputMoney) throws VendingMachineInsufficientFundsException, VendingMachineItemInventoryException, VendingMachineException {
        Item wantedItem = dao.getItem(name);   //the inputs are case sensitive.

        //If the wanted item returns null, the item does not exist in the items map
        if (wantedItem == null) {
            throw new VendingMachineItemInventoryException (
                    "ERROR: there are no " + name + "'s in the vending machine.");
        }

        //Check if the user has input enough money
        checkIfEnoughMoney(wantedItem,inputMoney);

        //If they have, check that the item is in stock and if so, remove one item from the inventory
        removeItem(name);
        return wantedItem;
//        //Give user their change
//        return getChangePerCoin(wantedItem,inputMoney);
    }


    public void removeItem (String name) throws VendingMachineItemInventoryException, VendingMachineException {
        //Remove one item from the inventory only when there are items to be removed, i.e. inventory>0.
        if (dao.getItemInventory(name)>0) {
            dao.removeItem(name);
            //if an items removed, write to the audit log
            auditDao.writeAuditEntry(" One " + name + " removed");
        } else {
            //If there are no items left to remove, throw an exception
            throw new VendingMachineItemInventoryException (
                    "ERROR: " + name + " is out of stock.");
        }
    }
    
}
