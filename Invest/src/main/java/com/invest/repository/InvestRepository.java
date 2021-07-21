package com.invest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invest.entity.Invest;


@Repository
public interface InvestRepository extends JpaRepository<Invest, Long>{
	List<Invest> findByProductId(Long product_id);
	
	List<Invest> findByUserId(Long user_id);
	
}
