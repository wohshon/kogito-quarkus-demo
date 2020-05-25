package com.redhat.app.kogito.models;

import com.redhat.app.kogito.models.events.Transaction;

import org.kie.kogito.rules.DataSource;
import org.kie.kogito.rules.DataStore;
import org.kie.kogito.rules.RuleUnitData;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TransactionUnit implements RuleUnitData {
    private DataStore<Transaction> transactions;

    public TransactionUnit() {
        this(DataSource.createStore());
    }
    public TransactionUnit(DataStore<Transaction> transactions) {
        this.transactions = transactions;
    }


}