package fetchKrx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.eugenefe.enums.EFilePath;
import com.eugenefe.scrapper.EDynKrxApi;
import com.eugenefe.scrapper.EDynKrxScrapper;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.JsonDynaEnum;
import com.eugenefe.utils.JsonUtil;

public class $5002KrxApiDynEnumTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
	// private final static Logger _logger = LoggerFactory.getLogger("EUGENE");

	public static void main(String[] args) throws Exception {
//		getMethodTest();
		dynamicEnumKrxApiTest();
	}
	
	

	private static void dynamicEnumKrxApiTest() {
		try {
			for (EDynKrxApi aa : EDynKrxApi.values()) {
//				if(aa.getName().equals("KrxApiKospiStockList")){
					if(aa.getName().equals("KrxApiStockMaster")){	
					
					logger.info("name : {}, {}", aa.getName(), aa.getUrl());
					logger.info("name : {}, {}", aa.getName(), aa.getParameterMeta());
					
					FileUtil.writeFile(EFilePath.KRX_DATA.getFilePath() + "stockMaster" + ".json",aa.getListJson());
					logger.info("scrapResult : {}, {}", aa.getListJson());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	private static void getMethodTest(){
		String url ="https://testbed.koscom.co.kr/gateway/v1/market/stocks/lists?infoTpCd=01&mktTpCd=1";
//		String url ="https://testbed.koscom.co.kr/gateway/v1/market/stocks/lists?infoTpCd=01&mktTpCd=3";
//		String url ="https://jsoup.org/cookbook/input/load-document-from-url";
		
//		             https://testbed.koscom.co.kr/gateway/v1/market/stocks/lists?infoTpCd=01&mktTpCd=2
		String  Accept ="text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
		String AcceptEncoding = "gzip, deflate, sdch" ;
		String AcceptLanguage ="ko,en-US;q=0.8,en;q=0.6,ja;q=0.4";
		String CacheControl  = "no-cache";
		String  Connection = "keep-alive";
		String 	Host ="testbed.koscom.co.kr";
		String 	Pragma =":no-cache";
		String 	UpgradeInsecureRequests  = "1";
		String  UserAgent ="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36";
		
		StringBuffer buffer = new StringBuffer();
		try {
//			org.jsoup.Connection con = Jsoup.connect(url).timeout(100000000);
//			con.referrer(url);
//			con.header("Accept-Language", AcceptLanguage);
//			con.header("Accept", Accept);
//			con.header("Connection", Connection);
//			con.header("Host", Host);
//			con.header("Pragma", Pragma);
//			con.header("Upgrade-Insecure-Requests", UpgradeInsecureRequests);
//			con.header("User-Agent", UserAgent);
//			logger.info("rst :{},{}",con.request().headers());
//			logger.info("rst :{},{}",con.execute().toString());
			
			Document rstDoc = Jsoup.connect(url).ignoreContentType(true).get();
			buffer.append(rstDoc.body().text().split("isuLists\"\\s*:")[1]);
			if(buffer.lastIndexOf("}") >0){
				buffer.deleteCharAt(buffer.lastIndexOf("}"));
			}
			
			String  aa =JsonUtil.getFieldValue(rstDoc.body().text(), "isuLists");
			List<String> bb =JsonUtil.getFieldValues(aa, "isuCd");
			
			logger.info("rst :{},{}",JsonUtil.getFieldValues(aa, "isuCd"), aa);
			
//			logger.info("rst :{},{}",JsonUtil.convertToMap("[" + rstDoc.body().text()+"]").get(0).get("isuLists"));
//			Map<String, String> map = (Map<String, String>) JsonUtil.convertToMap("[" + rstDoc.body().text()+"]").get(0).get("isuLists");
//			FileUtil.writeFile(EFilePath.KRX_DATA.getFilePath() + "aaaaaa" + ".json", buffer.toString());
//			FileUtil.writeFile(EFilePath.KRX_DATA.getFilePath() + "aaaaaa" + ".json", rstDoc.body().text().split("isuLists\"\\s*:")[1])
		}
		catch(IOException  ex){
			logger.error("sys","coudnt get the html");
		    ex.printStackTrace();
		}
	}	
}
