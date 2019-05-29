package com.revature.boot.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.revature.boot.domain.DomainContract;
import com.revature.boot.service.ContractService;

@RestController
@RequestMapping("/contracts")
public class ContractController {

	@Autowired
	ContractService contractService;

	@GetMapping
	public List<DomainContract> getAll() {
		return contractService.getAllContracts();
	}

	@GetMapping("/{id}")
	public DomainContract getById(@PathVariable("id") String id) {
		return contractService.getById(id);
	}

	@PostMapping
	public DomainContract add(@RequestBody @Valid DomainContract a, Errors errors) {
		if (errors.hasErrors())
			return null;
		return contractService.saveNewContract(a);
	}

	@PutMapping
	public DomainContract update(@RequestBody @Valid DomainContract a, Errors errors) {
		if (errors.hasErrors())
			return null;
		return contractService.updateContract(a);
	}

	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable("id") String id) {
		contractService.deleteById(id);
		return "deleted!";
	}

}
