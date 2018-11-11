package com.main.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.entity.Customer;
import com.main.exception_handler.CustomerNotFoundException;
import com.main.services.CustomerServices;

@RestController
@RequestMapping("/customer-services")
public class CustomerController {

	@Autowired
	private CustomerServices customerServices;
	
	@Value("${msg}")
    private String msg;

	// @RequestMapping(value="/create/customers", method=RequestMethod.POST,
	// consumes=MediaType.APPLICATION_JSON_VALUE)
	//@ResponseBody
	@PostMapping(value = "/create")
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
		System.out.println("-=-=-=-=-=-=-=-=-=-="+msg);
		
		String status = getCustomerServices().createCustomers(customer);
		return new ResponseEntity<String>(status ,HttpStatus.CREATED);

	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Customer> getAllCustomers() {
		return getCustomerServices().getCustomers();
	}

	@GetMapping(value = "/get/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {

		Optional<Customer> customer = getCustomerServices().getById(id);
		if (!customer.isPresent()) {
			throw new CustomerNotFoundException("Not found customer with Id is : " + id);
		}
		return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
	}

	@PutMapping(value = "/update", headers = "Accept=application/json")
	public ResponseEntity<String> updateCustomer(@RequestBody Customer currentCustomer) {
		String status = getCustomerServices().updateCustomer(currentCustomer);
		if (status == "NotFound") {
			throw new CustomerNotFoundException("Not found customer with Id is" + currentCustomer.getId());
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}", headers = "Accept=application/json")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id) {

		String status = getCustomerServices().deleteCustomer(id);
		if (status == "NotFound") {
			throw new CustomerNotFoundException("Not found customer with Id is" + id);
		}
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}

	
	
	
	
	
	public CustomerServices getCustomerServices() {
		return customerServices;
	}

	@Autowired
	public void setCustomerServices(CustomerServices customerServices) {
		this.customerServices = customerServices;
	}

}