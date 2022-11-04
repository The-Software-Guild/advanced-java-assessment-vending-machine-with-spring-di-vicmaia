
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Salajrawi
 */

public class VendingMachineDaoImpl implements VendingMachineDao{

    private Map <String, Item> items = new HashMap<>();
    public static final String DELIMITER = "::";
    private final String VENDING_MACHINE_FILE;

    public VendingMachineDaoImpl() {
        VENDING_MACHINE_FILE = "VendingMachine.txt";
    }
    public VendingMachineDaoImpl(String testFile) {
        VENDING_MACHINE_FILE = testFile;
    }

    //method that gets item from the inventory
    @Override
    public int getItemInventory(String name) throws VendingMachineException {
        loadItem();
        return items.get(name).getNumInventoryItems();
    }

    //remove item from inventory
    @Override
    public void removeItem(String name) throws VendingMachineException {
        loadItem();
        int prevInventory = items.get(name).getNumInventoryItems();
        items.get(name).setNumInventoryItems(prevInventory-1);
        writeItem();
    }

    //Returns item or null if there is no item associated with the given item name
    @Override
    public Item getItem(String name) throws VendingMachineException {
        loadItem();
        return items.get(name);
//        Set<String> names = items.keySet();
//
//        for(String n : names){
//
//            if(name.equalsIgnoreCase(name))
//            {
//                return items.get(n);
//            }
//        }
//
//        return null;
    }

    //Dont think i need the method below, ignore for now.
//    @Override
//    public List<String> getListOfItemNamesInStock()throws VendingMachinePersistenceException {
//        loadMachine();
//        //Return a list of the items names where the item inventory
//        //is greater than 0, i.e. get the keys where the inventory>0
//
//        //Filter the map to begin with
//        Map<String, Item> filteredMap = items.entrySet()
//                .stream()
//                .filter(map -> map.getValue().getInventory() >=0)
//                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
//
//        //Once map is filtered, retrieve the names i.e. the keys
//        List<String> itemsInStock = (List<String>) filteredMap.keySet();
//
//        return itemsInStock;
//    }


    @Override
    public Map<String, BigDecimal> getMapOfItemNamesInStockWithCosts() throws VendingMachineException{
        loadItem();
        //Return a list of the items names where the item inventory
        //is greater than 0, i.e. get the keys where the inventory>0

        Map<String, BigDecimal> itemsInStockWithCosts = items.entrySet()
                .stream()
                .filter(map -> map.getValue().getNumInventoryItems() > 0)
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue().getCost()));

        return itemsInStockWithCosts;

    }


    //Marshall: process of transforming memory represenetation of an object to a data format
    //suit for permanent storage
    private String marshallItem (Item anItem) {
        String itemAsText = anItem.getName() + DELIMITER;
        itemAsText += anItem.getCost() + DELIMITER;
        itemAsText += anItem.getNumInventoryItems();
        return itemAsText;
    }


    //Unmarshall: process of transforming the memory representation of an object
    private Item unmarshallItem (String itemAsText){
        //split the string into an array of strings at the delimiter
        String [] itemTokens = itemAsText.split("::");
        String name = itemTokens[0];
        Item itemFromFile = new Item(name);
        BigDecimal bigDecimal = new BigDecimal(itemTokens[1]);
        itemFromFile.setCost(bigDecimal);
        itemFromFile.setNumInventoryItems(Integer.parseInt(itemTokens[2]));
        return itemFromFile;
    }


    private void loadItem() throws VendingMachineException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(VENDING_MACHINE_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachineException(
                    "-_- Could not load item data into memory.", e);
        }
        String currentLine;
        Item currentItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getName(), currentItem);
        }
        scanner.close();
    }


    //get the list of item available in vending machine
    @Override
    public  List<Item> getAllItems() throws VendingMachineException {
        loadItem();
        return new ArrayList(items.values());
    }


    //method that writes and save data into file
    private void writeItem() throws VendingMachineException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VENDING_MACHINE_FILE));
        } catch (IOException e) {
            throw new VendingMachineException("Could not save student data.", e);
        }
        String itemAsText;
        List <Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }
    
}
