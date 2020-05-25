package com.redhat.app.kogito.models;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Customer<T extends Account> {
	private String name;
	private ArrayList<T> accounts = new ArrayList<T>();
	
	public Customer(String name) {
		this.setName(name);
	}
    public void addAccount(T account) {
		this.accounts.add(account);
	}
}