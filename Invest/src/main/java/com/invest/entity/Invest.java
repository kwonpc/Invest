package com.invest.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(
        name="INVEST_SEQ_GEN", 
        sequenceName="INVEST_SEQ", 
        initialValue=1, 
        allocationSize=1 
        )
@Table(name = "invest")
public class Invest {

	@Id
	@GeneratedValue(
            strategy=GenerationType.SEQUENCE, 
            generator="INVEST_SEQ_GEN"         
            )
	@Column (name="invest_id")
	private Long investId;
	
	@Column (name="user_id")
	private Long userId;
	
	@Column (name="product_id")
	private Long productId;
	
	@Column (name="amount")
	private Long amount;
	
	@Column (name="reg_dtm")
	private Date regDtm;

    @Builder
    public Invest(Long  userId, Long productId, Long amount, Date regDtm) {
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
        this.regDtm = regDtm;
    }
    
	@Formula("(select pd.title from product pd where pd.product_id=product_id)")
	private String title;
	
	@Formula("(select pd.total_amount from product pd where pd.product_id=product_id)")
	private String totalAmount;
    
}