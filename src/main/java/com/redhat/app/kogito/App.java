package com.redhat.app.kogito;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.redhat.app.kogito.models.Account;
import com.redhat.app.kogito.models.Customer;
import com.redhat.app.kogito.models.OrdinaryAccount;
import com.redhat.app.kogito.models.TransactionUnit;
import com.redhat.app.kogito.models.events.Transaction;
import com.redhat.app.kogito.services.RuleService;
import com.redhat.app.kogito.services.RuleService;

import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.kogito.rules.KieRuntimeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/rest")
public class App {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    RuleService ruleService;    
    @GET
    @Path("/hello")
    public String hellosvc() {
        

        Transaction tx1 = new Transaction();
        tx1.setAmount(Double.valueOf(2000));
        Customer<Account> cu1 = new Customer<Account>("Joe");
        OrdinaryAccount acct1 = new OrdinaryAccount(Double.valueOf(5000), 2.0, "acct_1");
        cu1.addAccount(acct1);
        tx1.setId("TX_0001");
        tx1.setAccount(acct1);
        tx1.setTimestamp(new Date());
        tx1.setCustomer(cu1);
        tx1.setLocation(Transaction.TX_LOCATION.WEST);
        //kSession.insert(tu);
        EntryPoint atmStream = ruleService.getKieSession().getEntryPoint("ATM Stream");
        atmStream.insert(tx1);
        int r = ruleService.getKieSession().fireAllRules();

        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Transaction tx2 = new Transaction();
        Customer<Account> cu2 = new Customer<Account>("Joey");
        OrdinaryAccount acct2 = new OrdinaryAccount(Double.valueOf(4000), 2.0, "acct_2");
        cu2.addAccount(acct2);        
        tx2.setAmount(Double.valueOf(250000));
        tx2.setTimestamp(new Date());
        tx2.setAccount(acct2);
        tx2.setCustomer(cu2);
        tx2.setId("TX_0002");
        tx2.setLocation(Transaction.TX_LOCATION.EAST);

        atmStream.insert(tx2);
         r += ruleService.getKieSession().fireAllRules();

        //kSession.insert(tx);
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Transaction tx3 = new Transaction();
        tx3.setAmount(Double.valueOf(2500));
        tx3.setTimestamp(new Date());
        tx3.setAccount(acct2);
        tx3.setCustomer(cu2);
        tx3.setId("TX_0003");
        atmStream.insert(tx3);

        tx3.setLocation(Transaction.TX_LOCATION.SOUTH);        
         r += ruleService.getKieSession().fireAllRules();
        log.info("fired "+r);

        return tx1.getStatus();
    }
}