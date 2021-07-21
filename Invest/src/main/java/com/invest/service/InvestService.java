package com.invest.service;

import java.util.List;

import com.invest.entity.Invest;
import com.invest.entity.Product;




public interface InvestService {
	
	public List<Product> list() throws Exception;
	
	public List<Invest> mylist(Long user_id) throws Exception;
	
	public boolean chksoldout(Long product_id, Long amount) throws Exception;
	
	public void invest(String message) throws Exception;
}
