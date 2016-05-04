package fetchKrx;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;

import com.eugenefe.enums.EFilePath;
import com.eugenefe.scrapper.EDynKrxScrapper;
import com.eugenefe.utils.FileUtil;

public class $8001TradinEconomics {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
	private static String uri = "http://api.tradingeconomics.com//calendar";
	public static void main(String[] args) {
		ccc();
	}	
	private static void aaa(){

		// TODO Auto-generated method stub
		try {
			String uri = "http://api.tradingeconomics.com//calendar";
			
			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");
			InputStream xml = connection.getInputStream();
			logger.info("aaa : {}", xml);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private static void bbb(){
		try {
			
			Response opt = Jsoup.connect(uri)
					.method(Connection.Method.GET)
					.execute();
			logger.info("aaa:  {}", opt);
//			String optUrlSelectboxString = opt.parse().select("body").text().toString();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private static void ccc(){
		try {
			Response response = Jsoup.connect(uri).ignoreContentType(true).execute();
			logger.info("response :  {},{}", response.headers(), response.cookies());
			logger.info("responseCode :  {},{}", response.statusCode(), response.header("X-RateLimit-Remaining"));
//			rst = response.body();
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
