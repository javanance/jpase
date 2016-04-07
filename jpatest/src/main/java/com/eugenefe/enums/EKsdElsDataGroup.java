package com.eugenefe.enums;

public enum EKsdElsDataGroup {
	 BASIC 		("湲곕낯�젙蹂�", "issuInfoList")
	,STRIKE 	("�뻾�궗媛�寃�", "bassetXrcList")
	,UNDERLYING	("湲곗큹�옄�궛","bassetInfoList")
	,PAYOFF		("�닔�씡援ъ“","midValatSkedulRedCondiList")
	;
	 
	private String korName;
	private String sebCode;

	private EKsdElsDataGroup() {
	}

	private EKsdElsDataGroup(String korName, String sebCode) {
		this.korName = korName;
		this.sebCode = sebCode;
	}

	public String getKorName() {
		return korName;
	}
	public String getSebCode(){
		return sebCode;
	}
}
