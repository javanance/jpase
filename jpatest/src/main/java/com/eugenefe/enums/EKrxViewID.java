package com.eugenefe.enums;

public enum EKrxViewID {
	 MAIN_80103 ("80103_MAIN", "80013")
   , MAIN_80104 ("80204_MAIN", "80014")
   ;
	
	public String metaId;
	public String viewId;
	private EKrxViewID ( String metaId, String viewId) {
		this.metaId = metaId;
		this.viewId =viewId;
	}
	
	public String getMetaId() {
		return metaId;
	}
	public String getViewId() {
		return viewId;
	}
	
}
