package com.invest.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invest.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByStartedAtLessThanAndFinishedAtGreaterThan(Date started, Date finished);
	
}
