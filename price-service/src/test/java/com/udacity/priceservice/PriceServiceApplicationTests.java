package com.udacity.priceservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PriceServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	//Unit Tests
	@Test
	public void testAllPrices() throws Exception {
		mockMvc.perform(get("/prices"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/hal+json"))
				.andDo(print())
				.andExpect(jsonPath("$..prices[*].currency", hasSize(19)));
	}

	@Test
	public void testValidPriceById() throws Exception {
		mockMvc.perform(get("/prices/search/getPriceByVehicleId?vehicleId=1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/hal+json"))
				.andDo(print())
				.andExpect(jsonPath("$.currency").value("USD"))
				.andExpect(jsonPath("$.price").exists());
	}

	@Test
	public void testInvalidPriceById() throws Exception {
		mockMvc.perform(get("/prices/search/getPriceByVehicleId?vehicleId=22"))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

}
