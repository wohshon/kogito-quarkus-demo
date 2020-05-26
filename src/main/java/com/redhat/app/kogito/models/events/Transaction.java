package com.redhat.app.kogito.models.events;

import java.util.Date;

import com.redhat.app.kogito.models.Account;
import com.redhat.app.kogito.models.Customer;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class Transaction {
	
    private Customer<Account> customer;
    private String status;
	private Date timestamp;
	private TX_TYPE transactionType;
    private Double amount;
    private Account account;
	private TX_LOCATION location;
	private String id;
	
	public enum TX_TYPE {
		DEPOSIT,
		WITHDRAWAL
	}

	public enum TX_LOCATION {
		NORTH,
		SOUTH,
		EAST,
		WEST
	}

	/**
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
        sb.append("id: ").append(this.getId())
        .append(" location: ").append(this.getLocation()).append(" customer:")
        .append(customer.getName())
        .append(" amount:").append(this.getAmount())
        .append(("\n-------------accounts-------\n"));
        for (Account account : customer.getAccounts()) {
            sb.append(account.getAccountId()+":"+account.getBalance()).append("\n");
        }
        
		return sb.toString();
	}
	 */
}

