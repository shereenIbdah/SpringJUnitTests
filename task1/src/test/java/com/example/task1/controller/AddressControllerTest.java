package com.example.task1.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        /**
         * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
         */
        private String mapToJson(Object object) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        }
    }

