package fetchKrx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.omg.PortableServer.ForwardRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenefe.dao.OdsKrxMarketDataDao;
import com.eugenefe.dao.OdsKrxOptionPriceAutoDao;
import com.eugenefe.dao.OdsKrxScrapToFile;
import com.eugenefe.entity.OdsKrxCombo;
import com.eugenefe.entity.OdsKrxFuturesPriceAuto;
import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.entity.OdsKrxMetaDetail;
import com.eugenefe.entity.OdsKrxOptionFinder;
import com.eugenefe.entity.OdsKrxOptionPrice;
import com.eugenefe.entity.OdsKrxOptionPriceAuto;
import com.eugenefe.enums.PersistenceManager;
import com.eugenefe.utils.KrxMarketDataFetch;
import com.eugenefe.utils.KrxMarketDataJsonUtil;
import com.eugenefe.utils.KrxMarketDataScrapUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class OdsKrxScrapToFileTest {
	private final static Logger logger = LoggerFactory.getLogger(OdsKrxScrapToFileTest.class);
	
	public static void main(String[] args) throws Exception{
//		String  isin ="KR4201L42470";
//		String  isin ="KR4201KC2424";
		String  isin ="KR4101L60003";
		
		String isinGroup = "KRDRVOPK2I";        //Futrues, option 's Group 
//		String  viweId ="80103_MAIN";
		String  viweId ="20001_MAIN";
		
		String bssd ="20160321";
		String subType  ="01";      // Index : 01 krx index,  02 :  front month futures   03 : custom index
		
		String callPutType = "C" ;   // C, P
		
		boolean fromDB =false; 
		

		OdsKrxScrapToFile writer = new OdsKrxScrapToFile();
		writer.writeFileFrom80103Main(isin,fromDB);
		writer.writeFileFrom80104Main(isin, fromDB);
		writer.writeFileFrom20001Main(bssd, subType, fromDB);
		writer.writeFileFrom40015Main(bssd, fromDB);
		writer.writeFileFrom40020Combo("C", fromDB);
		writer.writeFileFrom40020Main(isinGroup, callPutType, fromDB);
		writer.writeFileFrom40020Main(callPutType, fromDB);
		
		
	}
	
}
