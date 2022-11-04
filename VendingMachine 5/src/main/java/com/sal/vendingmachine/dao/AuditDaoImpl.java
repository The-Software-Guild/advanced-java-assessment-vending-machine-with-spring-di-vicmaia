
package com.sal.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author salajrawi
 */
public class AuditDaoImpl implements AuditDao{
    private final String AUDIT_FILE;
    //Default constructor
    public AuditDaoImpl() {
        this.AUDIT_FILE = "audit.txt";
    }
    //Contructor for testing
    public AuditDaoImpl(String auditTestFile) {
        this.AUDIT_FILE = auditTestFile;
    }



    @Override
    public void writeAuditEntry(String entry) throws VendingMachineException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new VendingMachineException("Could not persist audit information", e);
        }
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " +entry);
        out.flush();
    }
    
}
