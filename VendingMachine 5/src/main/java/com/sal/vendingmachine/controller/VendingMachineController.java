
package com.sal.vendingmachine.controller;

import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Coins;
import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vicmaia
 */
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineService service;

    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        this.view = view;
        this.service = service;
    }
    
    
    
    public void run(){
        boolean keepGoing = true;
        String itemSelection = "";
        BigDecimal inputMoney;
        view.displayMenuBanner();
        try {
            getMenu();
        } catch (VendingMachineException e) {
            view.displayErrorMessage(e.getMessage());
        }
        inputMoney = addFunds();
        while (keepGoing) {
            try {
                //Display the menu and get a selection
                itemSelection = getItemSelection();

                //If the user selects Exit, the program is exited
                if (itemSelection.equalsIgnoreCase("Exit")) {
                    keepGoing = false;
                    break;
                }
                getItem(itemSelection, inputMoney);
                keepGoing = false;
                break;

            } catch (VendingMachineInsufficientFundsException | VendingMachineItemInventoryException | VendingMachineException e) {
                view.displayErrorMessage(e.getMessage());
                view.displayPleaseTryAgainMsg();
            }
        }
        exitMessage();
    }

    private void getMenu() throws VendingMachineException {
        Map<String, BigDecimal> itemsInStockWithCosts = service.getItemsInStockWithCosts();
        view.displayMenu(itemsInStockWithCosts);
    }

    private BigDecimal addFunds() {
        return view.getMoney();
    }

    private String getItemSelection(){
        return view.getItemSelection();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void getItem(String name, BigDecimal money) throws VendingMachineInsufficientFundsException , VendingMachineItemInventoryException, VendingMachineException {
        Item wantedItem = service.getItem(name, money);
        Map<BigDecimal, BigDecimal> changeDuePerCoin = service.getChangePerCoin(wantedItem, money);
        view.displayChangeDuePerCoin(changeDuePerCoin);
        view.displayEnjoyBanner(name);
    }
}
