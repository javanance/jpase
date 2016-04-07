package com.eugenefe.entity;

import java.math.BigDecimal;

public class Ksd200T5 {
	private String isin ;
	private String underlyingId ;
	private String underlyingNm ;
	private double basePrice ;
	private double lowerBarrier ;
	private double lowerBarrierRatio ;
	private double upperBarrier ;
	private double upperBarrierRatio ;
	
	public Ksd200T5() {

	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getUnderlyingId() {
		return underlyingId;
	}

	public void setUnderlyingId(String underlyingId) {
		this.underlyingId = underlyingId;
	}

	public String getUnderlyingNm() {
		return underlyingNm;
	}

	public void setUnderlyingNm(String underlyingNm) {
		this.underlyingNm = underlyingNm;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public double getLowerBarrier() {
		return lowerBarrier;
	}

	public void setLowerBarrier(double lowerBarrier) {
		this.lowerBarrier = lowerBarrier;
	}

	public double getLowerBarrierRatio() {
		return lowerBarrierRatio;
	}

	public void setLowerBarrierRatio(double lowerBarrierRatio) {
		this.lowerBarrierRatio = lowerBarrierRatio;
	}

	public double getUpperBarrier() {
		return upperBarrier;
	}

	public void setUpperBarrier(double upperBarrier) {
		this.upperBarrier = upperBarrier;
	}

	public double getUpperBarrierRatio() {
		return upperBarrierRatio;
	}

	public void setUpperBarrierRatio(double upperBarrierRatio) {
		this.upperBarrierRatio = upperBarrierRatio;
	}
	
	

}
