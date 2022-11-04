
package com.sal.vendingmachine.service;

import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.dao.AuditDao;
import com.sal.vendingmachine.dao.AuditDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineException;
import com.sal.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author salajrawi
 */
public class VendingMachineServiceImplTest {
    VendingMachineDao testDao = new VendingMachineDaoImpl("VendingMachineTestFile.txt");
    String testAuditFile = "testAuditFile.txt";
    AuditDao testAuditDao = new AuditDaoImpl(testAuditFile);
    VendingMachineService testService = new VendingMachineServiceImpl(testAuditDao,testDao);


    VendingMachineService service;


    public VendingMachineServiceImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.service = ctx.getBean("serviceLayer", VendingMachineService.class);
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
    public void testSomeMethod() {

    }
    @Test
    public void testCheckIfEnoughMoney() {
        //ARRANGE
        Item hariboClone = new Item("Twix");
        hariboClone.setCost(new BigDecimal("1.00"));
        hariboClone.setNumInventoryItems(18);

        BigDecimal enoughMoney = new BigDecimal("2.00");
        BigDecimal notEnoughMoney = new BigDecimal("1.59");

        //enough money
        try {
            testService.checkIfEnoughMoney(hariboClone, enoughMoney);
        } catch (VendingMachineInsufficientFundsException  e){
            fail("There is sufficient funds, exception should not have been thrown");
        }
        //not enough money
        try {
            testService.checkIfEnoughMoney(hariboClone, notEnoughMoney);
            fail("There insufficient funds, exception should have been thrown");
        } catch (VendingMachineInsufficientFundsException  e){
        }
    }

    @Test
    public void testGetItemsInStockWithCosts() {

    }

    @Test
    public void testGetChangePerCoin(){

        Item hariboClone = new Item("Twix");
        hariboClone.setCost(new BigDecimal("1.00"));
        hariboClone.setNumInventoryItems(18);

        BigDecimal money = new BigDecimal("2.50");

        //Change should be $0.90: 25c: 3, 10c: 1, 5c:1
        Map<BigDecimal, BigDecimal> expectedChangePerCoin = new HashMap<>();
        expectedChangePerCoin.put(new BigDecimal("0.25"), new BigDecimal("3"));
        expectedChangePerCoin.put(new BigDecimal("0.10"), new BigDecimal("1"));
        expectedChangePerCoin.put(new BigDecimal("0.05"), new BigDecimal("1"));


        Map<BigDecimal, BigDecimal> changePerCoin = testService.getChangePerCoin(hariboClone, money);


        assertEquals(changePerCoin.size(), 3, "There should be three coins");


    }

    @Test
    public void testGetItem() throws VendingMachineInsufficientFundsException , VendingMachineException, VendingMachineItemInventoryException {

        Item snickersClone = new Item("M&Ms");
        snickersClone.setCost(new BigDecimal("2.50"));
        snickersClone.setNumInventoryItems(0);
        BigDecimal money = new BigDecimal("3.00");

        Item malteasersClone = new Item("Malteasers");
        malteasersClone.setCost(new BigDecimal("2.10"));
        malteasersClone.setNumInventoryItems(testDao.getItemInventory("Malteasers"));

        Item itemWanted = null;

        //to see if item is out of stock
        try {
            itemWanted = testService.getItem("M&Ms", money);
            fail("The item wanted is out of stock.");
        }catch (VendingMachineItemInventoryException e) {
        }
        try {
            itemWanted = testService.getItem("Malteasers", money);
        }catch (VendingMachineItemInventoryException e) {
            if (testDao.getItemInventory("Malteasers")>0){
                fail("The item wanted is in stock.");
            }

            assertNotNull(itemWanted, "change should not be null");
            assertEquals(itemWanted, malteasersClone,"The item retrieved should be snickers");
        }
    }

    @Test
    public void testRemoveOneItemFromInventory() throws VendingMachineException {
        String name = "M&Ms";

        //There are no M&Ms left
        try{
            testService.removeItem(name);
            fail("There are no M&Ms left, exception should be thrown");
        } catch (VendingMachineItemInventoryException e) {
        }

        String malteasers = "Twix";
        try{
            //twix is in stock
            testService.removeItem(malteasers);
        } catch (VendingMachineItemInventoryException e) {
            if (testDao.getItemInventory(malteasers)>0){
                fail("Twix are in stock, exception should not be thrown");
            }
        }
    }


}
