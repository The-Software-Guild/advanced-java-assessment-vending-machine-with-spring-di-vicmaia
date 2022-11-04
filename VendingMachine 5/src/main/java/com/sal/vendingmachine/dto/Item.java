
package com.sal.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author salajrawi
 */
public class Item {
    //Item DTO Data Transfer Object
    //User should not be able to change any of these properties

    private String name;
    private BigDecimal cost;
    private int inventory; // no of items in inventory



    public Item(String name, String cost, int inventory) {
        this.name = name;
        this.cost = new BigDecimal(cost);
        this.inventory = inventory;
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getNumInventoryItems() { return inventory; }

    public void setNumInventoryItems(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.cost);
        hash = 97 * hash + this.inventory;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.inventory != other.inventory) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.cost, other.cost)) {
            return false;
        }
        return true;
    }

    //returns the name, cost, and number of items of a specific item
    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", cost=" + cost + ", inventory=" + inventory + '}';
    }



}
