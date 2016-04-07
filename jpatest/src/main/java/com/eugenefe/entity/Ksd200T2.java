package com.eugenefe.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Ksd200T2 {
	private long seqId;
	private String isin ;
	private String korSecnNm ;
	private double xrcStdRatio ;
	private double xrcStdprc;
	private String scmntContent;
	public Ksd200T2() {

	}
	
	@JsonIgnoreProperties
	public long getSeqId() {
		return seqId;
	}

	public void setSeqId(long seqId) {
		this.seqId = seqId;
	}


	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getKorSecnNm() {
		return korSecnNm;
	}
	public void setKorSecnNm(String korSecnNm) {
		this.korSecnNm = korSecnNm;
	}
	public double getXrcStdRatio() {
		return xrcStdRatio;
	}
	public void setXrcStdRatio(double xrcStdRatio) {
		this.xrcStdRatio = xrcStdRatio;
	}
	public double getXrcStdprc() {
		return xrcStdprc;
	}
	public void setXrcStdprc(double xrcStdprc) {
		this.xrcStdprc = xrcStdprc;
	}
	public String getScmntContent() {
		return scmntContent;
	}
	public void setScmntContent(String scmntContent) {
		this.scmntContent = scmntContent;
	}
	
	

}
