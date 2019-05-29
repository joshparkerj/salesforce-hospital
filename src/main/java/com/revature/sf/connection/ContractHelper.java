package com.revature.sf.connection;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
import com.revature.boot.domain.DomainContract;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Contract;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class ContractHelper {

	EnterpriseConnection connection;

	ContractHelper(EnterpriseConnection c) {
		connection = c;
	}

	public List<DomainContract> getAllContracts() {
		List<DomainContract> contractList = new LinkedList<DomainContract>();
		try {
			QueryResult qr = connection.query("SELECT Id, Status, StartDate, ContractTerm, AccountId FROM Contract");
			boolean done = false;
			while (qr.getSize() > 0 && !done) {
				SObject[] records = qr.getRecords();
				for (SObject so : records) {
					Contract a = (Contract) so;
					Calendar bcal = a.getStartDate();
					Date bdate = null;
					if (bcal != null)
						bdate = bcal.getTime();
					contractList.add(
							new DomainContract(a.getId(), a.getStatus(), bdate, a.getContractTerm(), a.getAccountId()));
				}
				done = qr.isDone();
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return contractList;
	}

	public DomainContract saveNewContract(DomainContract a) {
		Contract newContract = fromDomainContract(a);
		SObject[] sObjects = { newContract };
		try {
			SaveResult[] sr = connection.create(sObjects);
			if (sr[0].getSuccess()) {
				a.setId(sr[0].getId());
				return a;
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public DomainContract updateContract(@Valid DomainContract a) {
		Contract newContract = fromDomainContract(a);
		SObject[] sObjects = { newContract };
		try {
			SaveResult[] sr = connection.update(sObjects);
			if (sr[0].getSuccess()) {
				a.setId(sr[0].getId());
				return a;
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Contract fromDomainContract(DomainContract a) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(a.getStartdate());
		Contract newContract = new Contract();
		newContract.setId(a.getId());
		newContract.setStatus(a.getStatus());
		newContract.setStartDate(gc);
		newContract.setContractTerm(a.getContractterm());
		newContract.setAccountId(a.getAccountid());
		return newContract;
	}

	public DomainContract domainContractFromContract(Contract a) {
		Calendar bcal = a.getStartDate();
		Date bdate = null;
		if (bcal != null)
			bdate = bcal.getTime();
		return new DomainContract(a.getId(), a.getStatus(), bdate, a.getContractTerm(), a.getAccountId());
	}

}
