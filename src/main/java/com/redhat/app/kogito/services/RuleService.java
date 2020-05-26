package com.redhat.app.kogito.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.redhat.app.kogito.models.events.Transaction;

import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.kogito.rules.KieRuntimeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@Singleton
public class RuleService {
    @Inject
    KieRuntimeBuilder runtimeBuilder;
    
    private KieSession kieSession;
    private EntryPoint entryPoint;
    Logger log = LoggerFactory.getLogger(this.getClass());

    void onStart(@Observes StartupEvent ev) {               
        log.info("The application is starting...");
        init();
    }

    void onStop(@Observes ShutdownEvent ev) {               
        log.info("The application is stopping...");
        this.getKieSession().halt();
        this.getKieSession().dispose();
    }

    public FactHandle invoke(Transaction event) {
        FactHandle handle = entryPoint.insert(event);
        System.out.println(entryPoint);
        int r = getKieSession().fireAllRules();
        log.info(r+" rules invoked");
        //dirty workaround 
        if (r > 1 ) { //dirty hack to delete handle as the drl ones does not work, i have a base rule running so minimally is one
            entryPoint.delete(handle);
        }
        return handle;
    }
    public KieSession getKieSession() {
        //log.info("getting kie session:" +this.kieSession);
        return this.kieSession;
    }
    public EntryPoint getEntryPoint() {
        return this.entryPoint;
    }
    private void init() {
        kieSession = runtimeBuilder.getKieBase().newKieSession();
        entryPoint = this.kieSession.getEntryPoint("ATM Stream");
        //debugging
        /*
        Object[] sessions = runtimeBuilder.getKieBase().getKieSessions().toArray();
        Iterator<KiePackage> packages = runtimeBuilder.getKieBase().getKiePackages().iterator();
        while (packages.hasNext()) {
            log.info("packages "+packages.next().getName());
        }
        
        //KiePackage kp = runtimeBuilder.getKieBase().getKiePackage("com.redhat.app.kogito");
        KiePackage kp = runtimeBuilder.getKieBase().getKiePackage("com.redhat.app.kogito.models.events");

        Iterator<Rule> rules = kp.getRules().iterator();
        while (rules.hasNext()) {
            log.info("rules "+rules.next());
        }
        log.info("sessions size "+sessions.length);
        */

    }
}