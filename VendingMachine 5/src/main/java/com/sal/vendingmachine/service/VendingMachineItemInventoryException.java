
package com.sal.vendingmachine.service;

/**
 *
 * @author salajrawi
 */
public class VendingMachineItemInventoryException extends Exception{
    public VendingMachineItemInventoryException(String message){
        super(message);
    }
    
    public VendingMachineItemInventoryException(String message, Throwable cause){
        super(message,cause);
    }
}
