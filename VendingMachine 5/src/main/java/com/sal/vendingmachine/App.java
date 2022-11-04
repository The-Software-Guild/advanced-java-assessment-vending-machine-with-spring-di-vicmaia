
package com.sal.vendingmachine;

import com.sal.vendingmachine.controller.VendingMachineController;
import com.sal.vendingmachine.dao.*;
import com.sal.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sal.vendingmachine.service.VendingMachineItemInventoryException;
import com.sal.vendingmachine.service.VendingMachineService;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.ui.UserIO;
import com.sal.vendingmachine.ui.UserIOImpl;
import com.sal.vendingmachine.ui.VendingMachineView;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 *
 * @author salajrawi
 */
public class App {
    public static void main(String[] args) throws VendingMachineException{
        /*
        UserIO io = new UserIOImpl();
        VendingMachineView view = new VendingMachineView(io);
        AuditDao auditDao = new AuditDaoImpl();
        VendingMachineDao dao = new VendingMachineDaoImpl();
        VendingMachineService service = new VendingMachineServiceImpl(auditDao, dao);

        VendingMachineController controller = new VendingMachineController(view, service);

        controller.run();

         */

        /*
        Instantiate ApplicationContext and VendingMachineController and call the run method
         */

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        controller.run();
    }
}
