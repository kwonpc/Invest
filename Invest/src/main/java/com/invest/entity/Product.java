package com.invest.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "product")
public class Product {

	@Id
	@Column (name="product_id")
	private Long productId;
	
	@Column (name="title")
	private String title;
	
	@Column (name="total_amount")
	private Long totalAmount;
	
	@Column (name="started_at")
	private Date startedAt;

	@Column (name="finished_at")
	private Date finishedAt;
	
	@Formula("(select count(1) from invest iv where iv.product_id=product_id)")
	private int invest_count;
	
	@Formula("(select nvl(sum(iv.amount),0) from invest iv where iv.product_id=product_id)")
	private Long curr_invest_amount;

}
