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

import com.eugenefe.entity.OdsKrxFuturesPriceAuto;
import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.entity.OdsKrxMetaDetail;
import com.eugenefe.entity.OdsKrxOptionFinder;
import com.eugenefe.entity.OdsKrxOptionPrice;
import com.eugenefe.entity.OdsKrxOptionPriceAuto;
import com.eugenefe.enums.PersistenceManager;
import com.eugenefe.utils.KrxMarketDataFetch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class KrxFetchNew2 {
	private final static Logger logger = LoggerFactory.getLogger(KrxFetchNew2.class);
	private static Properties properties = new Properties();
	private static String filePath = "D:\\Dev\\isincode\\isinMaster\\";

	public static void main(String[] args) throws Exception{
//		 bondInfoTest();
//		fetchKrxViewNew("80104_MAIN", "isu_cd","KR4201KC2424");
		fetchKrxViewNew("80103_MAIN", "isu_cd","KR4101L60003");
		
		
		
//		persistenceTest();
	}

	private static void bondInfoTest() {
		Document doc;
		Response aa;

		try {
			properties.load(KrxFetchNew2.class.getResourceAsStream("/url.properties"));
			logger.info("AAA: {}", properties.getProperty("krxOpt80104Pop"));

			String optUrlSelectbox = properties.getProperty("krx80104OptPopSelectBox");
			String optUrlForm = properties.getProperty("krx80104OptPopForm");

			String url = properties.getProperty("krx80104Pop");
			String pagepath = properties.getProperty("krx80104PopPagepath");

			Response opt = Jsoup.connect(optUrlSelectbox)
							    .method(Connection.Method.GET)
							    .execute();
			String optUrlSelectboxString = opt.parse().select("body").text().toString();

			logger.info("Opt : {}", optUrlSelectboxString);

			Response optForm = Jsoup.connect(optUrlForm).method(Connection.Method.GET)
					// .followRedirects(false)
					.execute();
			String optFormString = optForm.parse().select("body").text().toString();

			Response response = Jsoup.connect(url).timeout(100000000).data("mktsel", "IDX").data("lang", "ko")
					// .data("optype", "KRDRVOPMKI")
					.data("optype", "KRDRVOPK2I")
					// .data("schdate", "201604")
					// .data("pagePath", "/contents/COM/FinderOpIsu.jsp")
					.data("pagePath", pagepath)
					// .data("gNo", "093f65e080a295f8076b1c5722a46aa2")
					// .data("code",
					// "qhgj3siCktaRaW/ZkmyAi+tIwBzjDEp8BcYd4uAH+wOP2eIbuLagJivz/GKC4S0uoaJfTsWUEL3rZrPsMSsYxnzfSliP5ONKEUhzwPDepzY=")
					.data("code", optFormString).method(Connection.Method.POST)
					// .followRedirects(false)
					.execute();

			//
			// logger.info("BBB :{}", response.cookies().get("JSESSIONID"));
			// logger.info("ccc :{}", response.url());
			// logger.info("ccc :{}", response.header("location"));
			// logger.info("ccc :{}", response.parse().toString());

			BufferedWriter out = new BufferedWriter(new FileWriter(filePath + "reponse.html"));
			out.write(response.parse().toString());
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void fetchKrxView(String viewId) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		em.getTransaction().begin();
		OdsKrxMeta meta = em.find(OdsKrxMeta.class, "80104_MAIN");

		logger.info("META : {}", meta.getUrl());
		int cnt = 0;
		try {
			Response opt = Jsoup.connect(meta.getOptUrl()).method(Connection.Method.GET)
					// .followRedirects(false)
					.execute();

			String optCode = opt.parse().select("body").text().toString();

			logger.info("Opt : {}", optCode);

			Response response = Jsoup.connect(meta.getUrl())
									 .timeout(100000000)
									 .data("isu_cd","KR4201KC2424")
									 .data("pagePath", "/contents/MKD/10/1004/10041200/MKD10041200.jsp")
									 .data("code", optCode)
									 .data("bldcode", "MKD/10/1004/10041200/mkd10041200_02")
									 .method(Connection.Method.POST)
									 .execute();
			
			logger.info("rst :{}", response.parse().select("body").text());
			
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath + "reponse5.html"));
			out.write(response.parse().toString());
			out.close();

			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			
			
			JsonNode resultNode = mapper.readTree(response.parse().select("body").text());
			String str2 =resultNode.path("block1").toString();
			logger.info("List Size :{}", str2);
			
//			with(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
			
			List<OdsKrxOptionPriceAuto> myObjects = Arrays.asList(mapper.readValue(str2, OdsKrxOptionPriceAuto[].class));
			 
			 
			logger.info("List Size :{}", myObjects.size());
			for(OdsKrxOptionPriceAuto aa: myObjects){
				aa.setIsuCd("KR4201KC2424");
				em.persist(aa);
//				em.merge(aa);
			}
			 em.getTransaction().commit();
			 em.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void fetchKrxViewNew(String viewId, String...args ) {
		List<OdsKrxOptionPriceAuto> rst = new ArrayList<OdsKrxOptionPriceAuto>();
		List<OdsKrxFuturesPriceAuto> rst2 = new ArrayList<OdsKrxFuturesPriceAuto>();
		Map<String, String> formData = new HashMap<String, String>();

		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		em.getTransaction().begin();
		String className ="OdsKrxOptionPriceAuto";
		
		
		OdsKrxMeta meta = em.find(OdsKrxMeta.class, viewId);
		for(OdsKrxMetaDetail aa : meta.getOdsKrxMetaDetails()){
			formData.put(aa.getId().getMapKey(), aa.getMapValue());
		}
		for(int i=0; i< args.length; i=i+2){
			formData.put(args[i], args[i+1]);
		}
		
		try {
			Class klass = Class.forName(className);
//			List<klass.> rst = new ArrayList<OdsKrxOptionPriceAuto>();
//			rst =  KrxMarketDataFetch.scrap(meta, formData, klass.getClass());
		}catch(ClassNotFoundException ex){
			
		}
//		rst =  fetchKrxViewNewMeta(meta, formData, OdsKrxOptionPriceAuto.class);
//		rst =  KrxMarketDataFetch.scrap(meta, formData OdsKrxOptionPriceAuto.class);
//		rst =  KrxMarketDataFetch.scrap(meta, formData, OdsKrxOptionPriceAuto.class, 100000000);
		
		rst2 =  KrxMarketDataFetch.scrap(meta, formData, OdsKrxFuturesPriceAuto.class, 100000000);
		

		logger.info("META : {},{}", meta.getUrl(), formData);
		logger.info("META : {},{}", rst2.get(0).getEndPr7(), rst2.get(0).getWorkDt());
//		return rst;

	}	
	
	public static void fetchKrxViewNewZZ(String viewId, String...strings ) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		em.getTransaction().begin();
		OdsKrxMeta meta = em.find(OdsKrxMeta.class, viewId);
		Map<String, String> formData = new HashMap<String, String>();
		for(OdsKrxMetaDetail aa : meta.getOdsKrxMetaDetails()){
			formData.put(aa.getId().getMapKey(), aa.getMapValue());
		}
		formData.put(strings[0], strings[1]);
		
		

		logger.info("META : {},{}", meta.getUrl(), formData);
		/*int cnt = 0;
		try {
			Response opt = Jsoup.connect(meta.getOptUrl()).method(Connection.Method.GET).execute();

			String optCode = opt.parse().select("body").text().toString();
			formData.put("code", optCode);

			logger.info("Opt : {}", optCode);

			Response response = Jsoup.connect(meta.getUrl())
									 .timeout(100000000)
									 .data(formData)
									 .method(Connection.Method.POST)
									 .execute();
			
			logger.info("rst :{}", response.parse().select("body").text());
			
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath + "reponse5.html"));
			out.write(response.parse().toString());
			out.close();

			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			
			
			JsonNode resultNode = mapper.readTree(response.parse().select("body").text());
			String str2 =resultNode.path("block1").toString();
			logger.info("List Size :{}", str2);
			
			
			List<OdsKrxOptionPriceAuto> myObjects = Arrays.asList(mapper.readValue(str2, OdsKrxOptionPriceAuto[].class));
			 
			 
			logger.info("List Size :{}", myObjects.size());
			for(OdsKrxOptionPriceAuto aa: myObjects){
				aa.setIsuCd("KR4201KC2424");
				em.persist(aa);
			}
			 em.getTransaction().commit();
			 em.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}
	public static void persistenceTest() {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		OdsKrxMeta meta = em.find(OdsKrxMeta.class, "80104_MAIN");
		logger.info("List Size :{}", meta.getOdsKrxMetaDetails().get(0).getMapValue());
		
	}
	
	public static <E> List<E> fetchKrxViewNewMeta(OdsKrxMeta meta, Map<String,  String> formData, Class<E> klass) {
		List<E> rst = new ArrayList<E>();
		
		
		try {
			Response opt = Jsoup.connect(meta.getOptUrl()).timeout(1000000000).method(Connection.Method.GET).execute();

			String optCode = opt.parse().select("body").text().toString();
			formData.put("code", optCode);

			logger.info("Opt : {}", optCode);

			Response response = Jsoup.connect(meta.getUrl())
									 .timeout(100000000)
									 .data(formData)
									 .method(Connection.Method.POST)
									 .execute();
			
			logger.info("rst :{}", response.parse().select("body").text());
			
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath + "reponse5.html"));
			out.write(response.parse().toString());
			out.close();

			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(new PropertyNamingStrategy().SNAKE_CASE);
			
			
			JsonNode resultNode = mapper.readTree(response.parse().select("body").text());
			String str2 =resultNode.path("block1").toString();
			logger.info("List Size :{}", str2);
			
//			mapper.readV
//			rst = mapper.readValue(str2, new TypeReference<List<klass>>(){} );
			rst = mapper.readValue(str2, mapper.getTypeFactory().constructCollectionType(List.class, klass));
			
					
//			rst = Arrays.asList(mapper.readValue(str2, aa[].class));
			 
			 
			logger.info("List Size :{}", rst);
			return rst;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
