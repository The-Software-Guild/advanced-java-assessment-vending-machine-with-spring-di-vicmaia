
package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author salajrawi
 */
public class VendingMachineDaoImplTest {


    VendingMachineDao testDao = new VendingMachineDaoImpl("VendingMachineTestFile.txt");

    public VendingMachineDaoImplTest() {
    }
    @BeforeAll
    public static void setUpClass() {
    }
    @AfterAll
    public static void tearDownClass() {
    }
    @BeforeEach
    public void setUp() {
    }
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetItem() throws VendingMachineException {
        //ARRANGE
        Item snickersClone = new Item("Twix");
        snickersClone.setCost(new BigDecimal("1.00"));
        snickersClone.setNumInventoryItems(0);

        //ACT
        Item retrievedItem = testDao.getItem("Twix");

        //ASSERT
        assertNotNull(retrievedItem, "Item should not be null");
        assertEquals(retrievedItem, snickersClone,"The item retrieved should be snickers");

    }
    @Test
    public void testRemoveOneItemFromInventory() throws VendingMachineException {
        String itemName = "Twix";


        //get the inventory before we remove one
        int inventoryBefore = testDao.getItemInventory(itemName);

        //remove one item
        testDao.removeItem(itemName);

        //get the inventory after we have removed one
        int inventoryAfter = testDao.getItemInventory(itemName);

        assertTrue(inventoryAfter<inventoryBefore,"The inventory after should be less than before");
        assertEquals(inventoryAfter,inventoryBefore-1,"The inventory after should be one less than it was"
                + "before");

    }
    @Test
    public void testGetItemInventory() throws VendingMachineException {

        String itemName = "Twix";


        int retrievedInventory = testDao.getItemInventory(itemName);


        assertEquals(retrievedInventory,0,"There are 0 items of snickers left.");
    }

    @Test
    public void testGetMapOfItemNamesInStockWithCosts() throws VendingMachineException {


        Map<String,BigDecimal> itemsInStock = testDao.getMapOfItemNamesInStockWithCosts();


        //there are 0 M&Ms left, so it should not be included.
        assertFalse(itemsInStock.containsKey("M&Ms"));
        //There are 7 items in total, only M&Ms  is out of stock, so there should be 6 items
        assertEquals(itemsInStock.size(),6,"The menu list should contain 6 items.");
        assertTrue(itemsInStock.containsKey("Sour Patch") &&
                itemsInStock.containsKey("Skittles") &&
                itemsInStock.containsKey("Goldfish") &&
                itemsInStock.containsKey("Swedish Fish")&&
                itemsInStock.containsKey("M&Ms"));
    }

}
