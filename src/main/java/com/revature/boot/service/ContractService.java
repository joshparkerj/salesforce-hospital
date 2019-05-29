package com.revature.boot.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Service;
import com.revature.boot.domain.DomainContract;
import com.revature.sf.connection.EnterpriseConnectionHandler;

@Service
public class ContractService {

	private EnterpriseConnectionHandler con = EnterpriseConnectionHandler.INSTANCE;

	public List<DomainContract> getAllContracts() {
		return con.getAllContracts();
	}

	public DomainContract getById(String id) {
		return con.getContractById(id);
	}

	public DomainContract saveNewContract(DomainContract a) {
		return con.saveNewContract(a);
	}

	public void deleteById(String id) {
		con.deleteById(id);
	}

	public DomainContract updateContract(@Valid DomainContract a) {
		return con.updateContract(a);
	}

}
