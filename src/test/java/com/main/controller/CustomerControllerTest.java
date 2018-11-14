package com.main.controller;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.main.entity.Customer;
import com.main.services.CustomerServices;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	

	   @Autowired
	   private MockMvc mockMvc;
	   
	   @MockBean
	   private CustomerServices customerServices;
	   
	    Customer mockCustomer = new Customer(new Long(1), "Amol", "Surya", "as@gmail.com");
	    Optional<Customer> cust = Optional.of(mockCustomer);

		String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";

		@Test
		public void retrieveDetailsForCourse() throws Exception {

			Mockito.when(customerServices.getById(Mockito.anyLong())).thenReturn(cust);

			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer-services/get/1").accept(MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();

			System.out.println(result.getResponse());
			String expected = "{id:Course1,name:Spring,description:10 Steps}";

			JSONAssert.assertEquals(expected, result.getResponse()
					.getContentAsString(), false);
		}
		
		@Test
		public void createCustomer() throws Exception {
			String status = "CustomerCreated";
			Mockito.when(
					customerServices.createCustomers(Mockito.any(Customer.class))).thenReturn(status);

			// Send course as body to /students/Student1/courses
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post("/customer-services/create")
					.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
					.contentType(MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();

			MockHttpServletResponse response = result.getResponse();

			assertEquals(HttpStatus.CREATED.value(), response.getStatus());

			assertEquals("localhost:8080/customer-services/get/1",
					response.getHeader(HttpHeaders.LOCATION));
	   
		}
	  

}
