package com.redhat.app.kogito;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
import org.kie.api.runtime.rule.FactHandle;
import org.kie.kogito.rules.KieRuntimeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/rest")

public class App {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    RuleService ruleService;    
    
    @POST
    @Path("/event")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String event(Transaction tx) {

        log.info("thread "+Thread.currentThread());
        tx.setTimestamp(new Date());
        this.ruleService.invoke(tx);
        /*
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }        
        Transaction tx1 = new Transaction();
        tx1.setAmount(Double.valueOf(2000));
        Customer<Account> cu1 = new Customer<Account>("Joe");
        OrdinaryAccount acct1 = new OrdinaryAccount(Double.valueOf(5000), 2.0, "acct_1");
        cu1.addAccount(acct1);
        tx1.setId("TX_0001");
        tx1.setAccount(acct1);
        tx1.setTimestamp(new Date());
        tx1.setCustomer(cu1);
        tx1.setLocation(Transaction.TX_LOCATION.SOUTH);

        this.ruleService.invoke(tx1);
        */
        return tx.toString();

    }
    @POST
    @Path("/eventbatch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String eventBatch(List<Transaction> txList) {
        List<Transaction> results = new ArrayList<Transaction>();
        List<FactHandle> handles = new ArrayList<FactHandle>();
        FactHandle handle=null;
        int i = 0;
        for (Transaction tx : txList) {
            log.info("got event:"+tx);
            tx.setTimestamp(new Date());
            if (i == 3) {
                tx.setTimestamp(new Date(System.currentTimeMillis() + 8000));
            }
            i++;
            handle = this.ruleService.invoke(tx);
            try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
            handles.add(handle);
            results.add(tx);
        }
        //workaround to clean up since drl has bug
/*
        for (FactHandle factHandle : handles) {
            log.info("deleting fact handle "+factHandle);
            this.ruleService.getEntryPoint().delete(factHandle);            
        }
*/        
        return results.toString();

    }
    
    //test method
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
        ruleService.invoke(tx1);
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

        ruleService.invoke(tx2);
         r += ruleService.getKieSession().fireAllRules();

        //kSession.insert(tx);
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Transaction tx3 = new Transaction();
        Customer<Account> cu3 = new Customer<Account>("Joey");
        OrdinaryAccount acct3 = new OrdinaryAccount(Double.valueOf(4000), 2.0, "acct_2");

        tx3.setAmount(Double.valueOf(2500));
        tx3.setTimestamp(new Date());
        tx3.setAccount(acct3);
        tx3.setCustomer(cu3);
        tx3.setId("TX_0003");
        tx3.setLocation(Transaction.TX_LOCATION.SOUTH);        
        ruleService.invoke(tx3);

         r += ruleService.getKieSession().fireAllRules();
        log.info("fired "+r);

        return tx1.getStatus();
    }

}