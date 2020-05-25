package com.redhat.app.kogito.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.kogito.rules.KieRuntimeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;


@ApplicationScoped
public class RuleService {
    @Inject
    KieRuntimeBuilder runtimeBuilder;
    
    private KieSession kieSession;
    Logger log = LoggerFactory.getLogger(this.getClass());

    void onStart(@Observes StartupEvent ev) {               
        log.info("The application is starting...");
        init();
    }

    void onStop(@Observes ShutdownEvent ev) {               
        log.info("The application is stopping...");
    }

    public KieSession getKieSession() {
        return this.kieSession;
    }
    private void init() {
        kieSession = runtimeBuilder.getKieBase().newKieSession();
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