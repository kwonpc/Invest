package com.invest.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invest.dto.RequestDTO;
import com.invest.dto.ResponseDTO;
import com.invest.dto.ResponseDTO.ResponseDTOBuilder;
import com.invest.kafka.KafkaProducer;
import com.invest.service.InvestService;



@RestController
public class InvestController {
	
	
    @Autowired
    private InvestService investService;
	
    @Autowired
	private KafkaProducer producer;

	@GetMapping(value="/product", produces="application/json; charset=UTF-8")
	public ResponseEntity<ResponseDTO> product (HttpServletRequest request ) throws Exception {
	
		ResponseDTOBuilder responseDTOBuilder = ResponseDTO.builder();
		
		try {
			responseDTOBuilder.productList(investService.list());
		}catch(Exception e){
			responseDTOBuilder.code("9999");
			responseDTOBuilder.message("server-error !!");
			return new ResponseEntity<ResponseDTO>(responseDTOBuilder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

			
		return new ResponseEntity<ResponseDTO>(responseDTOBuilder.build(), HttpStatus.OK);
	}
	
	@PostMapping(value="/invest", produces="application/json; charset=UTF-8")
	public ResponseEntity<ResponseDTO> invest (@RequestBody RequestDTO requestdto, HttpServletRequest request) throws Exception {
	
		ResponseDTOBuilder responseDTOBuilder = ResponseDTO.builder();
		
		try {
			
			boolean chksoldout = investService.chksoldout(requestdto.getProductId(), requestdto.getInvest_amount());
			
			if(chksoldout) {
				this.producer.sendMessage(requestdto.getUserId()+":"+requestdto.getProductId()+":"+requestdto.getInvest_amount());
				
				responseDTOBuilder.code("0000");
				responseDTOBuilder.message("requested invest !!");
				return new ResponseEntity<ResponseDTO>(responseDTOBuilder.build(), HttpStatus.OK);
				
			}else {
				
				responseDTOBuilder.code("1001");
				responseDTOBuilder.message("sold-out !!");
				return new ResponseEntity<ResponseDTO>(responseDTOBuilder.build(), HttpStatus.OK);
			}
			
		}catch(Exception e){
			responseDTOBuilder.code("9999");
			responseDTOBuilder.message("server-error !!");
			return new ResponseEntity<ResponseDTO>(responseDTOBuilder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping(value="/myinvest", produces="application/json; charset=UTF-8")
	public ResponseEntity<ResponseDTO> myinvest (RequestDTO requestdto, HttpServletRequest request ) throws Exception {
	
		ResponseDTOBuilder responseDTOBuilder = ResponseDTO.builder();

		try {		
			responseDTOBuilder.myinvest(investService.mylist(requestdto.getUserId()));
		}catch(Exception e){
			responseDTOBuilder.code("9999");
			responseDTOBuilder.message("server-error !!");
			return new ResponseEntity<ResponseDTO>(responseDTOBuilder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ResponseDTO>(responseDTOBuilder.build(), HttpStatus.OK);
	}
	
	
}
