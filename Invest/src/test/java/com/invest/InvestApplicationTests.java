package com.invest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class InvestApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
    @Test
    @Order(1)
    public void product1() throws Exception {
   	
    	System.out.println("########### product test 1 ############");
    	mockMvc.perform(get("/product").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
			    	.andExpect(status().isOk())
			    	.andDo(print())
			    	.andReturn();   	
    	System.out.println("########### product test 1 ############");
    }
    
    @Test
    @Order(2)
    public void invest1() throws Exception {

    	HttpHeaders headers = new HttpHeaders();
    	headers.set("X-USER-ID", "1");
    	
    	Map map = new HashMap();
    	map.put("productId", "1");
    	map.put("invest_amount", "100000");
    	
    	String content = objectMapper.writeValueAsString(map);
    	
    	System.out.println("########### invest test 1 ############");
    	mockMvc.perform(post("/invest").headers(headers).content(content).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
			    	.andExpect(status().isOk())
			    	.andDo(print())
			    	.andReturn();   	
    	System.out.println("########### invest test 1 ############");
    }
    
    @Test
    @Order(3)
    public void invest2() throws Exception {

    	HttpHeaders headers = new HttpHeaders();
    	headers.set("X-USER-ID", "2");
    	
    	Map map = new HashMap();
    	map.put("productId", "1");
    	map.put("invest_amount", "500000");
    	
    	String content = objectMapper.writeValueAsString(map);
    	
    	System.out.println("########### invest test 2 ############");
    	mockMvc.perform(post("/invest").headers(headers).content(content).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
			    	.andExpect(status().isOk())
			    	.andDo(print())
			    	.andReturn();   	
    	System.out.println("########### invest test 2 ############");
    }

    @Test
    @Order(4)
    public void invest3() throws Exception {

    	HttpHeaders headers = new HttpHeaders();
    	headers.set("X-USER-ID", "3");
    	
    	Map map = new HashMap();
    	map.put("productId", "1");
    	map.put("invest_amount", "400000");
    	
    	String content = objectMapper.writeValueAsString(map);
    	
    	System.out.println("########### invest test 3 ############");
    	mockMvc.perform(post("/invest").headers(headers).content(content).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
			    	.andExpect(status().isOk())
			    	.andDo(print())
			    	.andReturn();   	
    	System.out.println("########### invest test 3 ############");
    }

    @Test
    @Order(5)
    public void invest4() throws Exception {

    	HttpHeaders headers = new HttpHeaders();
    	headers.set("X-USER-ID", "4");
    	
    	Map map = new HashMap();
    	map.put("productId", "1");
    	map.put("invest_amount", "300000");
    	
    	String content = objectMapper.writeValueAsString(map);
    	
    	System.out.println("########### invest test 4 ############");
    	mockMvc.perform(post("/invest").headers(headers).content(content).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
			    	.andExpect(status().isOk())
			    	.andDo(print())
			    	.andReturn();   	
    	System.out.println("########### invest test 4 ############");
    }
    
    @Test
    @Order(6)
    public void product2() throws Exception {
   	
    	System.out.println("########### product test 1 ############");
    	mockMvc.perform(get("/product").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
			    	.andExpect(status().isOk())
			    	.andDo(print())
			    	.andReturn();   	
    	System.out.println("########### product test 1 ############");
    }
    
    
    @Test
    @Order(7)
    public void myinvest() throws Exception {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.set("X-USER-ID", "1");
   	
    	System.out.println("########### myinvest test 1 ############");
    	mockMvc.perform(get("/myinvest").headers(headers).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
			    	.andExpect(status().isOk())
			    	.andDo(print())
			    	.andReturn();   	
    	System.out.println("########### myinvest test 1 ############");
    }
    
}
