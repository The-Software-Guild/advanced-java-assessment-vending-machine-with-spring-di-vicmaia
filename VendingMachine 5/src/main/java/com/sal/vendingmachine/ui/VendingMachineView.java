
package com.sal.vendingmachine.ui;

import com.sal.vendingmachine.dto.Coins;
import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.ui.UserIOImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author salajrawi
 */
public class VendingMachineView {
    private UserIO io;

    public VendingMachineView (UserIO io){
        this.io = io;
    }

    public BigDecimal getMoney() {
        //asks user to input the amount of money
        return io.readBigDecimal("Please input the amount money in dollars before making selection");
    }

    public void displayMenuBanner() {
        io.print("=== Menu ===");
    }

    //displays items with costs
    public void displayMenu(Map<String, BigDecimal> itemsInStockWithCosts){
        itemsInStockWithCosts.entrySet().forEach(entry ->{
            System.out.println(entry.getKey() + ": $" +entry.getValue());
        });
    }

    //this message displays once user input money amount
    public String getItemSelection(){
        return io.readString("Please select an item from the menu above or 'exit' to quit");
    }

    public void displayEnjoyBanner(String name) {
        io.print("Here is your change.");
        io.print("Enjoy your " + name + "!");
    }

    public void displayInsufficientFundsMsg(BigDecimal money){
        io.print("Insufficent funds, you only have input $"+ money);
    }

    public void displayItemOutOfStockMsg(String name){
        io.print("Error, " + name + " is out of stock.");
    }

    public void displayChangeDuePerCoin(Map<BigDecimal, BigDecimal> changeDuePerCoin) {
        changeDuePerCoin.entrySet().forEach(entry ->{
            System.out.println(entry.getKey() + "c : " +entry.getValue());
        });
    }

    public void displayExitBanner() {
        io.print("Thank you for purchasing! Good Bye!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage (String errorMsg) {
        io.print("=== Error ===");
        io.print(errorMsg);
    }

    public void displayPleaseTryAgainMsg() {
        io.print("Please select something else.");
    }
}
