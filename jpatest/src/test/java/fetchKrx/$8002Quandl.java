package fetchKrx;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

import com.eugenefe.enums.EFilePath;
import com.eugenefe.scrapper.EDynKrxScrapper;
import com.eugenefe.utils.FileUtil;

public class $8002Quandl {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

	public static void main(String[] args) {
//		getQuandlDataCount();
//		testQuandlData();
//		getQuandlData();
//		getQuandlQuery();
		
//		String url ="https://www.quandl.com/data/FRED/USARGDPQDSNAQ?api_key=W6qxqMz1sZPZcG93SxS4" ;
		romeToRio();
	}
	private static void zzz(){
		String url ="https://www.quandl.com/api/v3/datasets/WIKI/FB.json?api_key=rN1Kd5yuzLA1fcwjLnFy" ;
		
		
		try {
			Response response = Jsoup.connect(url).ignoreContentType(true).execute();
			logger.info("response :  {},{}", response.headers(), response.cookies());
			logger.info("responseCode :  {},{}", response.statusCode(), response.header("X-RateLimit-Remaining"));
			logger.info("bdoy :  {}" ,response.body());
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void romeToRio() { 
		try {
//			String url = "http://evaluate.rome2rio.com/api/1.2/json/Search/";
			String url = "http://www.rome2rio.com/api/1.4/json/Search?key=Z2CA71LM&oName=Bern&dName=Zurich&noRideshare";
			Map<String, String > data = new HashMap<String, String>();
			  // Specify your developer key 
			data.put("key", "Z2CA71LM"); 
	        // Specify values for the following required parameters 
			data.put("oName", "ICN"); 
			data.put("dName", "LAX");
			data.put("flags", "0x000FFFFC");
			
//			Response response = Jsoup.connect(url).ignoreContentType(true).data(data).execute();
			Response response = Jsoup.connect(url).ignoreContentType(true).execute();
			logger.info("response :  {},{}", response.headers(), response.cookies());
			logger.info("responseCode :  {},{}", response.statusCode(), response.header("X-RateLimit-Remaining"));
			logger.info("bdoy :  {}" ,response.body());
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
/*
	public static void getQuandlData() {
		 QConnection q = new QConnection();
		 
		 String qCode = "OFDP/FUTURE_C1";
//		 String qCode = "OFDP/FUTURE_KCN2013";
		 Map<String, String> param = new HashMap<String, String>();

//		 QDataset data1 = q.getQDataset(authToken, qCode);
		 QDataset data1 = q.getQDataset(authToken, qCode,"2014-02-01","2014-02-07");
//		 param.put(EQuandlParam., value)
//		 QDataset data1 = q.getQDataset(qCode,param);
		 
		logger.info("Quandl : {},{}", data1.getColumn_names(),data1.getDescription());
		logger.info("Quandl : {},{}", data1.getDisplay_url(), data1.getName());
		
		List<Object> printList = new ArrayList<Object>();
		printList.add(data1.getId());
		printList.add(data1.getName());
		printList.add(data1.getSource_code());
		printList.add(data1.getCode());
		printList.add(data1.getType());
		printList.add(data1.getFrequency());
		printList.add(data1.getFrom_date());
		printList.add(data1.getTo_date());
		printList.add(data1.getColumn_names());
		printList.add(data1.getDisplay_url());
		printList.add(data1.getUrlize_name());
		printList.add(data1.getUpdated_at());
		printList.add(data1.getDescription());
//		printList.add(data1.getData());
		logger.info("Quandl list; {};{};{};{};{};{};{};{};{};{};{};{};{};{}", printList.toArray());
		logger.info("azz:{}", data1.toString());
	}
	public static void testQuandlData() {
		 QConnection q = new QConnection();
		 
		 String qCode = "OFDP/FUTURE_C1";
//		 String qCode = "OFDP/FUTURE_KCN2013";
		 Map<String, String> param = new HashMap<String, String>();

		 QDataset data1 = q.getQDataset(authToken, qCode);
//		 param.put(EQuandlParam., value)
//		 QDataset data1 = q.getQDataset(qCode,param);
		 
//		logger.info("Quandl : {},{}", data1.getColumn_names(),data1.getDescription());
//		logger.info("Quandl : {},{}", data1.getDisplay_url(), data1.getName());
		
		List<Object> printList = new ArrayList<Object>();
		printList.add(data1.getId());
		printList.add(data1.getName());
		printList.add(data1.getSource_code());
		printList.add(data1.getCode());
		printList.add(data1.getType());
		printList.add(data1.getFrequency());
		printList.add(data1.getFrom_date());
		printList.add(data1.getTo_date());
		printList.add(data1.getColumn_names());
		printList.add(data1.getDisplay_url());
		printList.add(data1.getUrlize_name());
		printList.add(data1.getUpdated_at());
		printList.add(data1.getDescription());
		printList.add(data1.getData());
		logger.info("Quandl list; {};{};{};{};{};{};{};{};{};{};{};{};{};{}", printList.toArray());
	}
	public static void getQuandlDataCount() {
		 QConnection q = new QConnection();
		 
		 String searchString = "ETF";

			QDatasetList dataList = q.getCodeQuery(authToken,searchString);
//			logger.info("Quandl2 : {},{}", dataList.getCurrent_page(),	dataList.getTotal_count());
//			logger.info("Quandl2 : {},{}", dataList.getPer_page(), dataList.getSources());
		 

	}
	public static void getQuandlQuery() {
		QConnection q = new QConnection();
		 
		String searchString = "ETF";

		QDatasetList dataList = q.getCodeQuery(authToken,searchString,2);
//		logger.info("Quandl2 : {},{}", dataList.getCurrent_page(),	dataList.getTotal_count());
//		logger.info("Quandl2 : {},{}", dataList.getPer_page(), dataList.getSources());
//		if(dataList.getTotal_count()>0){
//			 for (QDataset aa : dataList.getDocs()) {
//				 logger.info("Quandl2 : {}, {}", aa.getSource_code(), aa.getCode());
//			 }
//		}
		 int totalSize = dataList.getTotal_count();
		 int totalPageNo = dataList.getTotal_count() / 20 + 1;
//		int totalPageNo = 3;
//		for (int i = 1; i <= totalPageNo; i++) {
			for (int i = 1; i <= 3; i++) {	
//			logger.info("Quandl1111 : {}, {}");
			dataList = q.getCodeQuery(authToken, searchString, i);
//			logger.info("Quandl1111 : {}, {}", i);
			for (QDataset aa : dataList.getDocs()) {
				 logger.info("Quandl222 :{}/{}", aa.getSource_code(), aa.getCode());
//				 logger.info("Quandl2 : {}, {}", aa.getColumn_names() );
			     logger.info("Quandl2 : {}", aa.getDescription());
			 }

		}
	}*/
}
