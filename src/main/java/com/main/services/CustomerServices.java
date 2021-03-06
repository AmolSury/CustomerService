package com.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.main.entity.Customer;
import com.main.repository.CustomerRepository;

@Service
public class CustomerServices {

	private CustomerRepository customerRepository;

	public Customer createCustomers(Customer customer) {
		// TODO cust
		return getCustomerRepository().save(customer);
	}

	public List<Customer> getAllCustomers() {
		return (List<Customer>) getCustomerRepository().findAll();
	}

	@Cacheable("customer")
	public Optional<Customer> getById(Long id) {
		return getCustomerRepository().findById(id);
	}

	public String updateCustomer(Customer customer) {
		if (getCustomerRepository().existsById(customer.getCustId())) {
			getCustomerRepository().save(customer);
			return "Updated";
		}
		return "NotFound";
	}

	public String deleteCustomer(Long id) {

		if (getCustomerRepository().existsById(id)) {
			getCustomerRepository().deleteById(id);
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