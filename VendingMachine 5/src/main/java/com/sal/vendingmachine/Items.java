package com.sal.vendingmachine;

import com.sal.vendingmachine.dto.Item;
import com.sal.vendingmachine.ui.UserIO;
import com.sal.vendingmachine.ui.UserIOImpl;

/**
 *
 * @author vicmaia
 */
public class Items {
    public static void main(String[] args) {
        UserIO io = new UserIOImpl();

        Item twix = new Item("Twix", "1.00", 10);
        Item skittles = new Item("Skittles", "1.10", 10);
        Item sourPatchs = new Item("SourPatchs", "2.00", 10);
        Item goldfish = new Item("Goldfish", "$3.00", 10);
        Item swedishFish = new Item("SwedishFish", "2.30", 10);
        Item taytoCrisps = new Item("Reeses", "1.50", 10);


        boolean keepGoing = true;
        int itemSelection = 0;
        while(keepGoing) {
            io.print("Vending Machine Selection: ");
            io.print("1. Twix: $1.00");
            io.print("2. Skittles: $1.10");
            io.print("3. Sour Patch: $2.00");
            io.print("4. Goldfish: $3.00");
            io.print("5. Cereal bar, $2.50");
            io.print("6. Reese's: $1.50");
            io.print("7. Exit");

            itemSelection = io.readInt("Please select an item from the above list", 1,7);
            switch (itemSelection) {
                case 1:
                    //kitkat();
                    //System.out.println("1");
                    break;
                case 2:
                    //item2();
                    System.out.println("2");
                    break;
                case 3:
                    //item3();
                    System.out.println("3");
                    break;
                case 4:
                    //item4();
                    System.out.println("4");
                    break;
                case 5:
                    //item5();
                    System.out.println("5");
                    break;
                case 6:
                    System.out.println("exit");
                    keepGoing = false;
                    break;
                default:
                    io.print("unknown command");
            }
        }
        io.print("Good bye");
    }

}





