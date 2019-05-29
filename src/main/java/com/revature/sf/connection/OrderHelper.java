package com.revature.sf.connection;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import com.revature.boot.domain.DomainOrder;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Order;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

public class OrderHelper {

	EnterpriseConnection connection;

	OrderHelper(EnterpriseConnection c) {
		connection = c;
	}

	public List<DomainOrder> getAllOrders() {
		List<DomainOrder> orderList = new LinkedList<DomainOrder>();
		try {
			QueryResult qr = connection.query(
					"SELECT Id, Name, AccountId, CustomerAuthorizedById, Description, Status, TotalAmount, Type FROM Order");
			boolean done = false;
			while (qr.getSize() > 0 && !done) {
				SObject[] records = qr.getRecords();
				for (SObject so : records) {
					Order o = (Order) so;
					Calendar bcal = o.getEffectiveDate();
					Date bdate = null;
					if (bcal != null)
						bdate = bcal.getTime();
					orderList.add(new DomainOrder(o.getId(), o.getName(), o.getAccountId(),
							o.getCustomerAuthorizedById(), o.getDescription(), o.getStatus(),
							BigDecimal.valueOf(o.getTotalAmount()), o.getType(), o.getContractId(), bdate));
				}
				done = qr.isDone();
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	public DomainOrder saveNewOrder(DomainOrder a) {
		Order newOrder = orderFromDomainOrder(a);
		SObject[] sObjects = { newOrder };
		try {
			SaveResult sr = connection.create(sObjects)[0];
			if (sr.getSuccess()) {
				a.setId(sr.getId());
				return a;
			}
			for (com.sforce.soap.enterprise.Error e : sr.getErrors()) {
				System.out.println(e.getMessage());
			}
			return null;
		} catch (ConnectionException e) {
			e.printStackTrace();
			return null;
		}
	}

	public DomainOrder updateOrder(@Valid DomainOrder a) {
		Order newOrder = orderFromDomainOrder(a);
		SObject[] sObjects = { newOrder };
		try {
			SaveResult sr = connection.update(sObjects)[0];
			if (sr.getSuccess()) {
				a.setId(sr.getId());
				return a;
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Order orderFromDomainOrder(DomainOrder a) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(a.getEffectivedate());
		Order newOrder = new Order();
		newOrder.setAccountId(a.getAccountid());
		newOrder.setCustomerAuthorizedById(a.getCustomerauthorizedbyid());
		newOrder.setDescription(a.getDescription());
		newOrder.setId(a.getId());
		newOrder.setName(a.getName());
		newOrder.setStatus(a.getStatus());
		newOrder.setType(a.getType());
		newOrder.setContractId(a.getContractid());
		newOrder.setEffectiveDate(gc);
		return newOrder;
	}

	public DomainOrder domainOrderFromOrder(Order o) {
		Calendar bcal = o.getEffectiveDate();
		Date bdate = null;
		if (bcal != null)
			bdate = bcal.getTime();
		return new DomainOrder(o.getId(), o.getName(), o.getAccountId(), o.getCustomerAuthorizedById(),
				o.getDescription(), o.getStatus(), BigDecimal.valueOf(o.getTotalAmount()), o.getType(),
				o.getContractId(), bdate);
	}

}
