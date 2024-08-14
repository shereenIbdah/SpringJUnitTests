package com.example.task1.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import com.example.task1.model.Address;
import com.example.task1.service.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
    @WebMvcTest(value= AddressController.class)
    public class AddressControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private AddressService addressService;

        @Test
        public void testCreateAddress() throws Exception {
            Address address = new Address(1L, "aka");

            String inputInJson = this.mapToJson(address);

            String URI = "/api/v1/address/addAddress"; // Add leading slash

            Mockito.when(addressService.addAddress(Mockito.any(Address.class))).thenReturn(address);

            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(URI)
                    .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                    .contentType(MediaType.APPLICATION_JSON);

            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();

            String outputInJson = response.getContentAsString();

            assertThat(outputInJson).isEqualTo(inputInJson);
            assertEquals(HttpStatus.OK.value(), response.getStatus());
        }
    @Test
    public void testGetAddresses() throws Exception {
        // Prepare the data
        List<Address> addressList = Arrays.asList(
                new Address(1L, "Street 1"),
                new Address(2L, "Street 2")
        );

        // Convert the list of addresses to JSON
        String expectedJson = this.mapToJson(addressList);

        // Mock the service to return the list of addresses
        Mockito.when(addressService.getAddresses()).thenReturn(addressList);

        // Define the URI of the endpoint
        String URI = "/api/v1/address/getAddresses";

        // Build the GET request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON); // the expected response type

        // Perform the request
        //nds the request to the controller,
        // and andReturn() captures the response in an MvcResult objec
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Get the response content as JSON
        //retrieves the content of the HTTP response body as a JSON string
        String outputInJson = response.getContentAsString();

        // Log the expected and actual outputs
        System.out.println("Expected JSON: " + expectedJson);
        System.out.println("Actual Output JSON: " + outputInJson);

        // Assert that the response matches the expected output
        assertThat(outputInJson).isEqualTo(expectedJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }




    /**
         * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
         */
    public String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}

