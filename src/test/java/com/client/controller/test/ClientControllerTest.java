package com.client.controller.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.client.controller.TestClientController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientControllerTest {

	/*
	 * @MockBean private RestServiceInvoker invoker;
	 * 
	 * @Autowired private TestClientController controller;
	 * 
	 * private MockMvc mockMvc;
	 * 
	 * @Before public void setUp() throws Exception { mockMvc =
	 * MockMvcBuilders.standaloneSetup(controller).build();
	 *//**
		 * Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(
		 * HttpMethod.class), Mockito.<HttpEntity<?>> any(), Mockito.<Class
		 * <String>> any())).thenReturn(new ResponseEntity<String>("Welcome",
		 * HttpStatus.OK));
		 * 
		 * 
		 * Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(
		 * HttpMethod.class), Mockito.<HttpEntity<?>> any(), Mockito.<Class
		 * <String>> any())).thenReturn(new ResponseEntity<String>("Welcome",
		 * HttpStatus.OK));
		 * 
		 * 
		 * 
		 **//*
			 * Mockito.when(invoker.get()).thenReturn(invoker);
			 * Mockito.when(invoker.path(Mockito.anyString())).thenReturn(
			 * invoker); Mockito.when(invoker.queryParam(Mockito.anyString(),
			 * Mockito.anyString())).thenReturn(invoker);
			 * Mockito.when(invoker.invoke(Mockito.<Class<Object>>
			 * any())).thenReturn("Welcome"); }
			 * 
			 * 
			 * @Test public void getMessageTest() throws Exception {
			 * mockMvc.perform(MockMvcRequestBuilders.get("/client/message").
			 * param("name", "Dhananjaya"))
			 * .andExpect(MockMvcResultMatchers.status().isOk())
			 * .andExpect(MockMvcResultMatchers.content().string("Welcome")); }
			 */

	@Autowired
	private TestClientController controller;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetMessage() throws Exception{
		mockMvc.perform(
				MockMvcRequestBuilders.get("/client/message")
				.param("name","Dhananjay")
				).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("hello"));
		
		
	}
}
