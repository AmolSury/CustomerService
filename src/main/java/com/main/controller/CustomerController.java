package com.main.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.main.services.MessageSender;
import com.main.services.RabbitMQSender;

@RestController
@RequestMapping("/customer-services")
public class CustomerController {

	private CustomerServices customerServices;
	
	private RabbitMQSender rabbitMQSender;
	
	@Autowired
	private MessageSender messageSender;

	@PostMapping(value = "/create")
	public ResponseEntity<Customer> createCustomer(@RequestBody @Valid Customer customer) {
		
		Customer Customer = getCustomerServices().createCustomers(customer);
		Customer.setMessageStatus("CustomerCreated");
		//for Kafka
		messageSender.send(Customer.toString());
		//for RabbitMQ
		//getRabbitMQSender().send(Customer);
		return new ResponseEntity<Customer>(Customer, HttpStatus.CREATED);
		
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Customer> getAllCustomers() {
		return getCustomerServices().getAllCustomers();
	}

	@GetMapping(value = "/get/{id}")
	public Customer getCustomerById(@PathVariable("id") Long id) {

		Optional<Customer> customer = getCustomerServices().getById(id);
		if (!customer.isPresent()) {
			throw new CustomerNotFoundException("Not found customer with Id is : " + id);
		}
		return customer.get();
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

	@Autowired
	public void setRabbitMQSender(RabbitMQSender rabbitMQSender) {
		this.rabbitMQSender = rabbitMQSender;
	}

	public RabbitMQSender getRabbitMQSender() {
		return rabbitMQSender;
	}

}