package com.redhat.app.kogito.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class OrdinaryAccount extends Account{
	private Double yearlyInterest;

	public OrdinaryAccount(Double balance, Double yearlyInterest, String id) {
		super(balance, id);
		this.setYearlyInterest(yearlyInterest);
	}

	

    
}