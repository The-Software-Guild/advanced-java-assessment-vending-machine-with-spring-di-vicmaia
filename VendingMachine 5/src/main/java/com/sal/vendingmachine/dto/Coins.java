
package com.sal.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author salajrawi
 */
public enum Coins {
    //Enum class for final variables for coins values

    PENNY(1),
    NICKEL(5),
    DIME(10),
    QUARTER(25);

    private final int value;

    Coins (int value) {
        this.value = value;
    }

    private int getValue() {
        return value;
    }

    public String toString(){
        switch (this) {
            case QUARTER:
                return "25";
            case DIME:
                return "10";
            case NICKEL:
                return "5";
            case PENNY:
                return "1";
        }
        return null;
    }
}
