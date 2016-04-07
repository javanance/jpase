package com.eugenefe.enums;

import java.util.Calendar;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//public enum MaturityE implements org.hibernate.usertype.UserType{
public enum EMaturity implements Comparable<EMaturity> { 
	
	SPOT		(1,Calendar.DATE, 1, 0),
	D001		(1,Calendar.DATE, 1, 0),
	D002		(2,Calendar.DATE, 2, 0),
	D003		(3,Calendar.DATE, 3, 0),
	D004		(4,Calendar.DATE, 4, 0),
	D005		(5,Calendar.DATE, 5, 0),
	D006		(6,Calendar.DATE, 6, 0),
	W01         (7,Calendar.DATE,7, 0) ,  
	W02         (14,Calendar.DATE,14, 0) ,
	M01         (1,Calendar.MONTH,30, 1) ,    
	M02         (2,Calendar.MONTH,60, 2) ,    
	M03         (3,Calendar.MONTH,90, 3) ,  
	M04			(4,Calendar.MONTH,120, 4),
	M05			(5, Calendar.MONTH,150, 5),
	M06         (6,Calendar.MONTH,180, 6) ,    
	M09         (9,Calendar.MONTH,270, 9) ,    
	Y01         (1,Calendar.YEAR,365, 12) ,    
	M18         (18,Calendar.MONTH,540, 18) ,
	Y02         (2,Calendar.YEAR,730, 24) ,    
	M30			(30,Calendar.MONTH, 900, 30),
	Y03         (3,Calendar.YEAR,1095, 36) ,  
	Y04			(4,Calendar.YEAR,1460, 48),
	Y05         (5,Calendar.YEAR,1825, 60) ,    
	Y07         (7,Calendar.YEAR,2555, 84) ,    
	Y10         (10,Calendar.YEAR,3650, 120) ,   
	Y20         (20,Calendar.YEAR,7300, 240) ,   
	MATURITY 	 (1000,Calendar.YEAR,100000, 12000);     


//	private final static Logger logger = LoggerFactory.getLogger(EMaturity.class);
	public int num;
	public int Type;
	public int numDays;
	public int numMonths;
	
	private EMaturity ( int num, int type, int numDays, int numMonths) {
		this.num = num;
		this.Type =type;
		this.numDays = numDays;
		this.numMonths = numMonths;
	}
	public double getFractionTo(EMaturity maturity){
		if(this.Type == maturity.Type){
			return this.num/(double)maturity.num;
		}
		else if ( this.Type == Calendar.DATE || maturity.Type==Calendar.DATE){
			return this.numDays / (double) maturity.numDays;
		}
		
		else if(this.Type== Calendar.YEAR) {
			return this.num * 12 / (double) maturity.num;
		}
		else if(maturity.Type == Calendar.YEAR){
			return this.num /  ((double) maturity.num * 12);
		}
		return 0.0;
		
	}
	public double getFrequency(){
		if( this.Type== Calendar.MONTH) return 12/(double)this.num;
		else if (this.Type == Calendar.YEAR) return 1/(double) this.num;
		else return 365/(double)this.numDays;
	}
	public EMaturity getMulitpleMaturity(int multiplier){
		
		for(EMaturity aa: EMaturity.values()){
//			logger.debug("Fraction To:{}", getFractionTo(aa));
			if( 1.0 == multiplier*getFractionTo(aa)){
				return aa;
			}
		}
		return this;
	}
	public String getName(){
		return this.name();
	}
	
	public int getNumDays() {
		return numDays;
	}
	
	public int getNumMonths() {
		return numMonths;
	}
	
	public static EMaturity getEMaurityByDay(int dayNum){
		EMaturity rst = EMaturity.D001;
		EMaturity[] temp = EMaturity.values();
		for(int i=0 ; i< temp.length-1; i++){
			if(temp[i].numDays >= dayNum && temp[i+1].numDays < dayNum ){
				return temp[i];
			}
		}
		return rst;
	}
	
	public static EMaturity getEMaurityByMonth(int monthNum){
		EMaturity rst = EMaturity.M01;
		EMaturity[] temp = EMaturity.values();
		for(int i=0 ; i< temp.length-1; i++){
			if(temp[i].numMonths >= monthNum && temp[i+1].numDays < monthNum ){
				return temp[i];
			}
		}
		return rst;
	}
	public static EMaturity getEMaurityByMonth(List<EMaturity> list, int monthNum){
		EMaturity rst = EMaturity.M01;
		for(int i=0 ; i< list.size()-1; i++){
			if(list.get(i).getNumMonths() <= monthNum && list.get(i+1).getNumMonths() > monthNum ){
				return list.get(i+1);
			}
		}
		return rst;
	}
//	@Override
//	public public int compareTo(EMaturity other){
//		return ((Integer)this.numDays).compareTo(other.numDays);
//	}
	
}
