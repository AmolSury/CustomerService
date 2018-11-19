package com.main.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.main.entity.Customer;
import com.main.repository.CustomerRepository;
import com.main.services.CustomerServices;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerServices customerServices;

	@MockBean
	private CustomerRepository customerRepository;

	@Test
	public void retrieveAllCustomer() throws Exception {

		when(customerServices.getAllCustomers())
				.thenReturn(Arrays.asList(new Customer(new Long(1), "Amol", "Sury", "asury@gmail.com"),
						new Customer(new Long(2), "Amit", "Sury", "amit@gmail.com")));
		when(customerRepository.findAll())
				.thenReturn(Arrays.asList(new Customer(new Long(1), "Amol", "Sury", "asury@gmail.com"),
						new Customer(new Long(2), "Amit", "Sury", "amit@gmail.com")));

		RequestBuilder request = MockMvcRequestBuilders.get("/customer-services/getAll")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().json(
				"[{id:1,firstName:Amol,lastName:Sury,emailId:asury@gmail.com}, {id:2,firstName:Amit,lastName:Sury,emailId:amit@gmail.com}]"))
				.andReturn();

		assertEquals(200, result.getResponse().getStatus());
		// JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(),
		// false);

	}

	@Test
	public void createStudentCourse() throws Exception {
		Customer retCust = new Customer(new Long(1), "Amol", "Sury", "asury@gmail.com");
		Customer customer = new Customer();
		customer.setFirstName("Amol");
		customer.setLastName("Sury");
		customer.setEmailId("asury@gmail.com");
		String custJson = "  {\\n    \\\"firstName\\\":\\\"Amol\\\",\\n    \\\"lastName\\\":\\\"Sury\\\",\\n    \\\"emailId\\\":\\\"asury@gmail.com\\\"\\n  }";
		when(customerServices.createCustomers(customer)).thenReturn("CustomerCreated");
		when(customerRepository.save(customer)).thenReturn(retCust);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer-services/create")
				.accept(MediaType.APPLICATION_JSON).content(custJson).contentType(MediaType.ALL_VALUE);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

	}

	@Test
	public void getCustomerById() throws Exception {
		Customer customer = new Customer(new Long(1), "Amol", "Sury", "asury@gmail.com");

		when(customerServices.getById((long) 1)).thenReturn(Optional.ofNullable(customer));
		when(customerRepository.findById((long) 1)).thenReturn(Optional.ofNullable(customer));

		RequestBuilder request = MockMvcRequestBuilders.get("/customer-services/get/1");

		MvcResult result = mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(content().json("{id:1,firstName:Amol,lastName:Sury,emailId:asury@gmail.com}")).andReturn();

		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	public void deleteCustomer() throws Exception {
		// Customer customer = new Customer(new Long(1),"Amol", "Sury",
		// "asury@gmail.com");

		when(customerServices.deleteCustomer((long) 1)).thenReturn("Deleted");
		when(customerRepository.existsById((long) 1)).thenReturn(true);

		RequestBuilder request = MockMvcRequestBuilders.get("/customer-services/delete/1");

		MvcResult result = mockMvc.perform(request).andExpect(status().isNoContent()).andReturn();

		assertEquals(204, result.getResponse().getStatus());

	}
}
