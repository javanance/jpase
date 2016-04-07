package com.eugenefe.dao;
// Generated Mar 16, 2016 4:59:04 PM by Hibernate Tools 4.3.1.Final

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenefe.entity.OdsKrxFuturesPriceAuto;
import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.entity.OdsKrxMetaDetail;
import com.eugenefe.entity.OdsKrxOptionPriceAuto;
import com.eugenefe.enums.PersistenceManager;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.KrxMarketDataFetch;
import com.eugenefe.utils.KrxMarketDataJsonUtil;
import com.eugenefe.utils.KrxMarketDataScrapUtil;

import fetchKrx.KrxFetchNew2;

/**
 * OdsKrxOptionPriceAuto generated by hbm2java
 */

public class OdsKrxFuturesPriceAutoDao implements java.io.Serializable {
	private final static Logger logger = LoggerFactory.getLogger(OdsKrxFuturesPriceAutoDao.class);
	private final static String ViewID = "80103_MAIN";
	
	private EntityManager em  ;
	private OdsKrxMeta meta ;
	private Map<String, String> formData = new HashMap<String, String>();
	private Properties properties = new Properties();
	private String filePath;
	
	
	public OdsKrxFuturesPriceAutoDao() {
		try {
			properties.load(OdsKrxFuturesPriceAutoDao.class.getResourceAsStream("/url.properties"));
			filePath = properties.getProperty("fileKrxOptionPrice");
		}catch(IOException ex){
			
		}
	}
	
	
	
	public void writeJsonToFile(String isin) throws IOException{
		persistenceSetup();
		formData.put("isu_cd", isin);
		logger.info("aaa: {},{}", meta.getUrl(), formData.get("isu_cd"));
		FileUtil.writeFile(filePath + isin + ".json", KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData));
	}
	

	public List<OdsKrxFuturesPriceAuto> getListData(String isin, boolean fromWeb) throws IOException{
		if(fromWeb){
			return getListDataFromWeb(isin);
		}
		else{
			return getListDataFromFile(isin);
		}	
		
	}
	
	public List<OdsKrxFuturesPriceAuto> getListDataFromWeb(String isin){
		persistenceSetup();
		List<OdsKrxFuturesPriceAuto> rst = new ArrayList<OdsKrxFuturesPriceAuto>();
		formData.put("isu_cd", isin);
		
		String jsonString =KrxMarketDataFetch.scrap(meta, formData);
		rst = KrxMarketDataJsonUtil.convertTo(OdsKrxFuturesPriceAuto.class, jsonString);
		
		return rst;
	}
	
	public List<OdsKrxFuturesPriceAuto> getListDataFromFile(String isin) throws IOException{
		File file = new File(filePath + isin + ".json");
		return getListDataFromFile(file);
	}
	
	
	public List<OdsKrxFuturesPriceAuto> getListDataFromFile(File file) throws IOException{
		List<OdsKrxFuturesPriceAuto> rst = new ArrayList<OdsKrxFuturesPriceAuto>();	
		
		String jsonString = FileUtil.readFile(file.getAbsolutePath());
				
		rst = KrxMarketDataJsonUtil.convertTo(OdsKrxFuturesPriceAuto.class, jsonString);
		
		return rst;
	}
	
	public Map<String, List<OdsKrxFuturesPriceAuto>> getAllDataFrom() throws IOException{
		Map<String, List<OdsKrxFuturesPriceAuto>> rst = new HashMap<String, List<OdsKrxFuturesPriceAuto>>();
		File folder =  new File(filePath);
		
		
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
//				logger.info("file: {},{}", folder.getAbsoluteFile(), fileEntry.getName().split(".json")[0]);
				rst.put(fileEntry.getName().split(".json")[0], getListDataFromFile(fileEntry));
//				logger.info("size :{}", rst.size());
			}
		}
		return rst;
	}
	
	public void writeAllDataToDB() throws IOException{
		persistenceSetup();
		int cnt =0;
		em.getTransaction().begin();
		
		Map<String, List<OdsKrxFuturesPriceAuto>> rstMap = getAllDataFrom();
		for(Map.Entry<String, List<OdsKrxFuturesPriceAuto>> entry : rstMap.entrySet()){
			for(OdsKrxFuturesPriceAuto aa : entry.getValue()){
				cnt =cnt+1;
				aa.setIsuCd(entry.getKey());
				em.persist(aa);
				if(cnt % 20 ==0){
					em.flush();
					em.clear();
				}
			}
		}
		em.getTransaction().commit();
		
	}
	
	private void  persistenceSetup(){
		if(em == null){
			em  = PersistenceManager.INSTANCE.getEntityManager();
			meta = em.find(OdsKrxMeta.class, ViewID);
			for(OdsKrxMetaDetail aa : meta.getOdsKrxMetaDetails()){
				formData.put(aa.getId().getMapKey(), aa.getMapValue());
			}
		}
	}
}
