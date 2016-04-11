package com.eugenefe.enums;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.eugenefe.interfaces.UrlMenu;

public enum EKsdMenu implements UrlMenu{
	  KSD( "", "")
	, Ksd193C3	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07001V.xml&menuNo=191"
			  	,   "<reqParam action=\"newnIssuSecnListCnt\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriCommPTask\">"
	  			+ 	"<SECN_TPNM value=\"ELS\"/>"
	  			+ 	"<STD_DT value=\"${STD_DT}\"/>"
	  			+ 	"<MENU_NO value=\"191\"/>"
	  			+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,comparison,favorites,more2,more2,link,link,wide,wide,top,\"/>"
	  			+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07001V.xml\"/>"
	  			+ 	"<START_PAGE value=\"1\"/>"
	  			+ 	"<END_PAGE value=\"10\"/>"
	  			+ 	"</reqParam>"
			  	)
	, Ksd193T3	(
				 	"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07001V.xml&menuNo=191"
				,	"<reqParam action=\"newnIssuSecnPList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriCommPTask\">"
				+ 	"<STD_DT value=\"${STD_DT}\"/>"
				+ 	"<START_PAGE value=\"1\"/>"
				+ 	"<END_PAGE value=\"${END_PAGE}\"/>"
				+ 	"<SECN_TPNM value=\"ELS\"/>"
				+ 	"<MENU_NO value=\"191\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,comparison,favorites,more2,more2,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07001V.xml\"/>"
				+ 	"</reqParam>"
				)
 	,  Ksd200T1	(
//				  	"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/etc/BIP_CMUC01054P.xml&secn_tpcd=41"
 			        "http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml&menuNo=200"
				, 	"<reqParam action=\"issuInfoList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask\">"
				+ 	"<MENU_NO value=\"200\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml\"/>" 
				+ 	"<ISIN value=\"${ISIN}\"/>"
				+ 	"</reqParam>"
 			  	)
	, Ksd200T2	(
				  	"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml&menuNo=200"
				, 	"<reqParam action=\"bassetXrcList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask\">"
				+ 	"<MENU_NO value=\"200\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml\"/>"
				+ 	"<ISIN value=\"${ISIN}\"/>" 
				+ 	"</reqParam>"
				)
	, Ksd200T3 (
				  	"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml&menuNo=200"
				, 	"<reqParam action=\"midValatSkedulRedCondiList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask\">"
				+ 	"<MENU_NO value=\"200\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml\"/>"
				+ 	"<ISIN value=\"${ISIN}\"/>"
				+ 	"</reqParam>"
				)
	, Ksd200T5  (
			      	"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml&menuNo=200"
				, 	"<reqParam action=\"bassetInfoList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask\">"
				+ 	"<MENU_NO value=\"200\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07016V.xml\"/>" 
				+ 	"<ISIN value=\"${ISIN}\"/>"
				+ 	"</reqParam>"
				)
	, Ksd200P1	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/etc/BIP_CMUC01054P.xml&secn_tpcd=41"
				,	"<reqParam action=\"searchIsscoBySecnList\" task=\"ksd.safe.bip.cmuc.User.process.SearchPTask\">"
				+	"<ISSUCO_CUSTNO value=\"${ISSUCO_CUSTNO}\"/>"
				+ 	"<KOR_SECN_NM value=\"\"/>"
				+ 	"<SECN_TPCD value=\"41\"/>"
				+ 	"</reqParam>"
				)
	, Ksd210P1	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/etc/BIP_CMUC01054P.xml&secn_tpcd=43"
				,	"<reqParam action=\"searchIsscoBySecnList\" task=\"ksd.safe.bip.cmuc.User.process.SearchPTask\">"
				+ 	"<ISSUCO_CUSTNO value=\"${ISSUCO_CUSTNO}\"/>"
				+ 	"<KOR_SECN_NM value=\"\"/>"
				+ 	"<SECN_TPCD value=\"43\"/>"
				+ 	"</reqParam>"
				)
	
	, Ksd210T1	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07024V.xml&menuNo=210"
				,	"<reqParam action=\"issuInfoList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriDLSPTask\">"
				+ 	"<MENU_NO value=\"210\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07024V.xml\"/>"
				+  	"<ISIN value=\"${ISIN}\"/>"
				+ 	"</reqParam>"
				)
	, Ksd210T2	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07024V.xml&menuNo=210"
				,	"<reqParam action=\"bassetXrcList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriDLSPTask\">"
				+ 	"<MENU_NO value=\"210\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07024V.xml\"/>"
				+ 	"<ISIN value=\"${ISIN}\"/>"
				+ 	"</reqParam>"
				)
	, Ksd210T3	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07024V.xml&menuNo=210"
				, 	"<reqParam action=\"midValastSkedulRedCondList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriDLSPTask\">"
				+ 	"<MENU_NO value=\"210\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07024V.xml\"/>"
				+ 	"<ISIN value=\"${ISIN}\"/>"
				+ 	"</reqParam>"
				)
	, Ksd210T5	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07024V.xml&menuNo=210"
				, 	"<reqParam action=\"bassetInfoList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask\">"
				+ 	"<MENU_NO value=\"210\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,searchIcon,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07024V.xml\"/>" 
				+ 	"<ISIN value=\"${ISIN}\"/>"
				+ 	"</reqParam>"
				)
//	DLS 상환종목
	, Ksd208C1	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07022V.xml&menuNo=208"
				, 	"<reqParam action=\"redSecnListCnt\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriDLSPTask\">"
				+ 	"<SECN_TPCD value=\"43\"/><MENU_NO value=\"208\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07022V.xml\"/>"
				+ 	"<ISSUCO_CUSTNO value=\"\"/>"
				+ 	"<DERISEC_EXER_TPCD value=\"3 2\"/>"
				+ 	"<RED_DT1 value=\"${RED_DT1}\"/>"
				+ 	"<RED_DT2 value=\"${RED_DT2}\"/>"
				+ 	"<ISIN value=\"\"/>"
				+ 	"<START_PAGE value=\"1\"/>"
				+ 	"<END_PAGE value=\"30\"/>"
				+ 	"<KOR_SECN_NM value=\"\"/>"
				+ 	"<DERICBND_YN value=\"\"/>"
				+ 	"</reqParam>"
				)
	, Ksd208T1	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07022V.xml&menuNo=208"
				,	"<reqParam action=\"redSecnPList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriDLSPTask\">"
				+	"<SECN_TPCD value=\"43\"/>"
				+ 	"<MENU_NO value=\"208\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07022V.xml\"/>"
//				+ 	"<ISSUCO_CUSTNO value=\"${ISSUCO_CUSTNO}\"/>"
//				+ 	"<DERISEC_EXER_TPCD value=\"3 2\"/>"
				+ 	"<RED_DT1 value=\"${RED_DT1}\"/>"
				+ 	"<RED_DT2 value=\"${RED_DT1}\"/>"
//				+ 	"<ISIN value=\"${ISIN}\"/>"
				+ 	"<START_PAGE value=\"1\"/>"
				+ 	"<END_PAGE value=\"${END_PAGE}\"/>"
				+ 	"<KOR_SECN_NM value=\"\"/>"
				+ 	"<DERICBND_YN value=\"\"/>"
				+ 	"</reqParam>"
				)
//	ELS 상환종목	
	, Ksd198C1	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07013V.xml&menuNo=198"
				, 	"<reqParam action=\"redSecnListCnt2\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask\">"
				+ 	"<SECN_TPCD value=\"41\"/>"
				+ 	"<MENU_NO value=\"198\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07013V.xml\"/>"
				+ 	"<ISSUCO_CUSTNO value=\"\"/>"
				+ 	"<DERISEC_EXER_TPCD value=\"3 2\"/>"
				+ 	"<RED_DT1 value=\"${RED_DT1}\"/>"
				+ 	"<RED_DT2 value=\"${RED_DT2}\"/>"
				+ 	"<KOR_SECN_NM value=\"\"/>"
				+ 	"<START_PAGE value=\"1\"/>"
				+ 	"<END_PAGE value=\"30\"/>"
				+ 	"<DERICBND_YN value=\"\"/>"
				+ 	"</reqParam>"
				)
	, Ksd198T1	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07013V.xml&menuNo=198"
				,	"<reqParam action=\"redSecnPList2\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriELSPTask\">"
				+ 	"<SECN_TPCD value=\"41\"/>"
				+ 	"<MENU_NO value=\"198\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07013V.xml\"/>"
				+ 	"<ISSUCO_CUSTNO value=\"\"/>"
				+ 	"<DERISEC_EXER_TPCD value=\"3 2\"/>"
				+ 	"<RED_DT1 value=\"${RED_DT1}\"/>"
				+ 	"<RED_DT2 value=\"${RED_DT2}\"/>"
				+ 	"<KOR_SECN_NM value=\"\"/>"
				+ 	"<START_PAGE value=\"1\"/>"
				+ 	"<END_PAGE value=\"${END_PAGE}\"/>"
				+ 	"<DERICBND_YN value=\"\"/>"
				+ 	"</reqParam>"
				)
//	DLS 청약종목조회
	, Ksd207C1	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07021V.xml&menuNo=207"
				,	"<reqParam action=\"subsIsinListCnt\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriDLSPTask\">"
				+ 	"<SECN_TPCD value=\"43\"/>"
				+ 	"<MENU_NO value=\"207\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07021V.xml\"/>"
				+ 	"<ISSUCO_CUSTNO value=\"\"/>"
				+ 	"<START_PAGE value=\"1\"/>"
				+ 	"<END_PAGE value=\"30\"/>"
				+ 	"</reqParam>"
				)
	, Ksd207T1	(
					"http://www.seibro.co.kr/websquare/control.jsp?w2xPath=/IPORTAL/user/derivCombi/BIP_CNTS07021V.xml&menuNo=207"
				,	"<reqParam action=\"subsIsinPList\" task=\"ksd.safe.bip.cnts.DerivCombi.process.DeriDLSPTask\">"
				+ 	"<SECN_TPCD value=\"43\"/>"
				+ 	"<MENU_NO value=\"207\"/>"
				+ 	"<CMM_BTN_ABBR_NM value=\"allview,allview,print,hwp,word,pdf,seach,link,link,wide,wide,top,\"/>"
				+ 	"<W2XPATH value=\"/IPORTAL/user/derivCombi/BIP_CNTS07021V.xml\"/>"
				+ 	"<ISSUCO_CUSTNO value=\"\"/>"
				+ 	"<START_PAGE value=\"1\"/>"
				+ 	"<END_PAGE value=\"${END_PAGE}\"/>"
				+ 	"</reqParam>"
				)
	;

	private String referer;
	private String payload;
//	private String className;
	private Properties properties;

	private EKsdMenu() {
		properties = new  Properties();
		try {
			properties.load(EKsdMenu.class.getResourceAsStream("/ksd.properties"));
		} catch (Exception ex) {
			System.out.println("Error : ksd.properties not found in class path ");
		}
	}

	private EKsdMenu(String referer, String payload) {
		this();
		this.referer = referer;
		this.payload = payload;
	}

	public String getTargetClassName() {
		return "com.eugenefe.entity."+ this.toString();
	}

	public String getReferer() {
		if( properties.getProperty(this.toString().toUpperCase()+"_referer") != null){
			return  properties.getProperty(this.toString().toUpperCase()+"_referer");
		}
		return referer;
	}

	public String getPayload() {
		if( properties.getProperty(this.toString().toUpperCase()+"_payload") != null){
			return  properties.getProperty(this.toString().toUpperCase()+"_payload");
		}
		return payload;
	}

	public List<String> getParameters() {
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
}
