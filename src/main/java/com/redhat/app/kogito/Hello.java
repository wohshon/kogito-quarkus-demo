package com.redhat.app.kogito;

import org.kie.kogito.rules.DataSource;
import org.kie.kogito.rules.DataStore;
import org.kie.kogito.rules.RuleUnit;
import org.kie.kogito.rules.RuleUnitData;

/**
 * Testing kogito
 */
public class Hello implements RuleUnitData {
    DataStore<String> strings = DataSource.createStore();

    public DataStore<String> getStrings() {
        return strings;
    }
    
}