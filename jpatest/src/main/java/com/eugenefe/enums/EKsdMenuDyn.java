package com.eugenefe.enums;

import java.util.ArrayList;
import java.util.List;

import com.eugenefe.utils.JsonDynaEnum;

public class EKsdMenuDyn {
	private String name;
	private String referer;
	private String payload;
	
	private static List<EKsdMenuDyn> values ;
	
	static {
		values = JsonDynaEnum.convertTo(EKsdMenuDyn.class);
	}
	
	
	public EKsdMenuDyn() {
		super();
	}

	public EKsdMenuDyn(String referer, String payload) {
		super();
		this.referer = referer;
		this.payload = payload;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetClassName() {
		return "com.eugenefe.entity."+ this.toString();
	}

	public String getReferer() {
		return referer;
	}

	public String getPayload() {
		return payload;
	}

	public List<String> getParameters()  {
		List<String> rst = new ArrayList<String>();
		String[] splitStr = getPayload().split("\\$\\{");
		for (int i = 1; i < splitStr.length; i++) {
			rst.add(splitStr[i].split("\\}")[0]);
		}
		return rst;
	}
	
	public static String getUrl(){
		return  "http://www.seibro.or.kr/websquare/engine/proworks/callServletService.jsp";
	}
	
	public static List<EKsdMenuDyn> values(){
		return values;
	}
}
