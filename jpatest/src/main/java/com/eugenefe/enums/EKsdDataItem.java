package com.eugenefe.enums;

public enum EKsdDataItem {

//	 XRC_STD_RATIO ("xrc_std_ratio","DOUBLE")
//	,SCMNT_CONTENT ("scmnt_content","STRING")
//	,XRC_STDPRC ("xrc_stdprc","DOUBLE")
//	,KOR_SECN_NM ("kor_secn_nm","STRING")
//	,isin ("isin","STRING")
//	,BASSET_LAST_VALAT_TPCD ("basset_last_valat_tpcd","STRING")
//	,ISSU_CUR_CD ("issu_cur_cd","STRING")
//	,RECU_WHCD ("recu_whcd","STRING")
//	,FIRST_ISSU_QTY ("first_issu_qty","DOUBLE")
//	,PRCP_PRSV_RATE ("prcp_prsv_rate","DOUBLE")
//	,ISSU_DT ("issu_dt","STRING")
//	,XPIR_DT ("xpir_dt","STRING")
//	,VALAT_PRICE ("valat_price","DOUBLE")
//	,BASSET_CNT ("basset_cnt","DOUBLE")
//	,MID_CNT ("mid_cnt","DOUBLE")
//	,REP_SECN_NM ("rep_secn_nm","STRING")
//	,RED_FORMULA_CONTENT ("red_formula_content","STRING")
//	,RED_CONDI_TPCD ("red_condi_tpcd","STRING")
//	,MID_VALAT_EXPRY_DT ("mid_valat_expry_dt","STRING")
//	,MID_VALAT_BEGIN_DT ("mid_valat_begin_dt","STRING")
//	,RED_CONDI_CONTENT ("red_condi_content","STRING")
//	,BASSET3 ("basset3","STRING")
//	,no ("no","DOUBLE")
//	,BASSET1 ("basset1","STRING")
//	,gubun ("gubun","STRING")
//	,BASSET2 ("basset2","STRING")
	xrc_std_ratio ("xrc_std_ratio","DOUBLE")
	,scmnt_content ("scmnt_content","STRING")
	,xrc_stdprc ("xrc_stdprc","DOUBLE")
	,kor_secn_nm ("kor_secn_nm","STRING")
	,isin ("isin","STRING")
	,basset_last_valat_tpcd ("basset_last_valat_tpcd","STRING")
	,issu_cur_cd ("issu_cur_cd","STRING")
	,recu_whcd ("recu_whcd","STRING")
	,first_issu_qty ("first_issu_qty","DOUBLE")
	,prcp_prsv_rate ("prcp_prsv_rate","DOUBLE")
	,issu_dt ("issu_dt","STRING")
	,xpir_dt ("xpir_dt","STRING")
	,valat_price ("valat_price","DOUBLE")
	,basset_cnt ("basset_cnt","DOUBLE")
	,mid_cnt ("mid_cnt","DOUBLE")
	,rep_secn_nm ("rep_secn_nm","STRING")
	,red_formula_content ("red_formula_content","STRING")
	,red_condi_tpcd ("red_condi_tpcd","STRING")
	,mid_valat_expry_dt ("mid_valat_expry_dt","STRING")
	,mid_valat_begin_dt ("mid_valat_begin_dt","STRING")
	,red_condi_content ("red_condi_content","STRING")
	,basset3 ("basset3","STRING")
	,no ("no","DOUBLE")
	,basset1 ("basset1","STRING")
	,gubun ("gubun","STRING")
	,basset2 ("basset2","STRING")

	;
	 
	private String label;
	private String dataType;

	private EKsdDataItem() {
	}

	private EKsdDataItem(String label, String dataType) {
		this.label = label;
		this.dataType = dataType;
	}

	public String getLabel() {
		return label;
	}
	public String getDataType(){
		return dataType;
	}

//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return getLabel();
//	}
	
}
