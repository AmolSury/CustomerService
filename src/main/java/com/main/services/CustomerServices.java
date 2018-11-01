package com.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.entity.Customer;
import com.main.repository.CustomerRepository;


@Service
public class CustomerServices {

	private CustomerRepository customerRepository;

	public String createCustomers(Customer customer) {
		Customer cust = getCustomerRepository().save(customer);
		System.out.println("cust---"+cust.getEmailId());
		return "CustomerCreated";
	}
	
	public List<Customer> getCustomers() {
		return (List<Customer>) getCustomerRepository().findAll();
	}
	
	public Optional<Customer> getById(Long id) {
		return getCustomerRepository().findById( id);
	}
	
	public String updateCustomer(Customer customer) {
		if(Optional.ofNullable(getCustomerRepository().findById(customer.getId())).isPresent()) {
			getCustomerRepository().save(customer);
			return "Updated";
		}
		return "NotFound";
	}

	public String deleteCustomer(Long id) {
		
		if(Optional.ofNullable(getCustomerRepository().findById(id)).isPresent()) {
			getCustomerRepository().deleteById(id);;
			return "Deleted";
		}
		return "NotFound";
	}
	
	
	@Autowired
	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public CustomerRepository getCustomerRepository() {
		return customerRepository;
	}
	
	
}