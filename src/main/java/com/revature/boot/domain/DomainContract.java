package com.revature.boot.domain;

import java.util.Date;

public class DomainContract {

	String id;
	String status;
	Date startdate;
	int contractterm;
	String accountid;

	public DomainContract() {
		super();
	}

	public DomainContract(String id, String status, Date startdate, int contractterm, String accountid) {
		super();
		this.id = id;
		this.status = status;
		this.startdate = startdate;
		this.contractterm = contractterm;
		this.accountid = accountid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public int getContractterm() {
		return contractterm;
	}

	public void setContractterm(int contractterm) {
		this.contractterm = contractterm;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	@Override
	public String toString() {
		return "DomainContract [id=" + id + ", status=" + status + ", startdate=" + startdate + ", contractterm="
				+ contractterm + ", accountid=" + accountid + "]";
	}

}
