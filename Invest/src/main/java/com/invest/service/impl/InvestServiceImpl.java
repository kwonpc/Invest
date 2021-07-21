package com.invest.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invest.entity.Invest;
import com.invest.entity.Product;
import com.invest.service.InvestService;

import com.invest.repository.InvestRepository;
import com.invest.repository.ProductRepository;

@Service
public class InvestServiceImpl implements InvestService{
	
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private InvestRepository investRepository;
    
	public List<Product> list() throws Exception{	
		return productRepository.findByStartedAtLessThanAndFinishedAtGreaterThan(new Date(), new Date());
	}
	
	public List<Invest> mylist(Long user_id) throws Exception{	
		return investRepository.findByUserId(user_id);
	}
	
	public boolean chksoldout(Long product_id, Long amount) throws Exception{
		
		Optional<Product> opt_product = productRepository.findById(product_id);
		Product product = opt_product.orElse(null);
		
		long total_investing_amount = 0;
		if(product!=null) {
			total_investing_amount = product.getTotalAmount();
		}
		
		List<Invest> list = investRepository.findByProductId(product_id);
				
		long cur_investing_sum = list.stream()
			.map(Invest::getAmount)
			.mapToLong(i->i).sum();
		
		if(cur_investing_sum + amount > total_investing_amount) {
			return false;
		}		
		
		return true;
	}

    @KafkaListener(topics = "invest", groupId = "investGroup")
	@Transactional
	public void invest(String message) throws Exception{
		
		String [] msg = message.split(":");
		
		boolean ck = chksoldout(Long.parseLong(msg[1]), Long.parseLong(msg[2]));
			
		if(ck){
			
			investRepository.save(Invest.builder()
					.userId(Long.parseLong(msg[0]))
					.productId(Long.parseLong(msg[1]))
					.amount(Long.parseLong(msg[2]))
					.regDtm(new Date())
					.build());
			
		}
		
	}
	
}
