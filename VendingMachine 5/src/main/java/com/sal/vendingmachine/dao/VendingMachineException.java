
package com.sal.vendingmachine.dao;

/**
 *
 * @author Salajrawi
 */
public class VendingMachineException extends Exception{
    public VendingMachineException (String message) {
        super(message);
    }
    public VendingMachineException(String message, Throwable cause) {
        super(message, cause);
    }
}
