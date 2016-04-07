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
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class OdsKrxMarketDataDao implements java.io.Serializable {
	private final static Logger logger = LoggerFactory.getLogger(OdsKrxMarketDataDao.class);
	private final static String viewID = "80103_MAIN";
	
	private EntityManager em  ;
	private OdsKrxMeta meta ;
	private Map<String, String> formData = new HashMap<String, String>();
	private Properties properties = new Properties();
	private String filePath;
	private Enum EViewID ;
	
	
	public OdsKrxMarketDataDao() {
		try {
			properties.load(OdsKrxMarketDataDao.class.getResourceAsStream("/url.properties"));
//			filePath = properties.getProperty("fileKrxOptionPrice");
		}catch(IOException ex){
			
		}
	}
	
	
	
	public void writeJsonToFile(String isin) throws IOException{
		persistenceSetup(viewID);
		filePath = meta.getFilePath();
		formData.put("isu_cd", isin);
		logger.info("aaa: {},{}", meta.getUrl(), formData.get("isu_cd"));
		FileUtil.writeFile(filePath + isin + ".json", KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData));
	}
	

	public <E> List<E> getListData(Class<E> klass, String isin, boolean fromWeb) throws IOException{
		if(fromWeb){
			return getListDataFromWeb(klass,isin);
		}
		else{
			return getListDataFromFile(klass,isin);
		}	
		
	}
	
	public <E> List<E> getListDataFromWeb(Class<E> klass, String isin){
		persistenceSetup(viewID);
		List<E> rst = new ArrayList<E>();
		formData.put("isu_cd", isin);
		
		String jsonString =KrxMarketDataFetch.scrap(meta, formData);
		rst = KrxMarketDataJsonUtil.convertTo(klass, jsonString);
		
		return rst;
	}
	
	public <E> List<E> getListDataFromFile(Class<E> klass, String isin) throws IOException{
		File file = new File(filePath + isin + ".json");
		return getListDataFromFile(klass, file);
	}
	
	
	public <E> List<E> getListDataFromFile(Class<E> klass, File file) throws IOException{
		List<E> rst = new ArrayList<E>();	
		
		String jsonString = FileUtil.readFile(file.getAbsolutePath());
				
		rst = KrxMarketDataJsonUtil.convertTo(klass, jsonString);
		
		return rst;
	}
	
	public <E>  Map<String, List<E>> getAllDataFrom(Class<E> klass) throws IOException{
		Map<String, List<E>> rst = new HashMap<String, List<E>>();
		File folder =  new File(filePath);
		
		
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				rst.put(fileEntry.getName().split(".json")[0], getListDataFromFile(klass,fileEntry));
			}
		}
		return rst;
	}
	
	public <E extends IMarketData> void writeAllDataToDB(Class<E> klass) throws IOException{
		persistenceSetup(viewID);
		int cnt =0;
		em.getTransaction().begin();
		
		Map<String, List<E>> rstMap = getAllDataFrom(klass);
		for(Map.Entry<String, List<E>> entry : rstMap.entrySet()){
			for(E aa : entry.getValue()){
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
	
	private void  persistenceSetup(String viewID){
		if(em == null){
			em  = PersistenceManager.INSTANCE.getEntityManager();
//			Query qr = em.createQuery("from OdsKrxMeta ");
//			List<OdsKrxMeta> qrRst = qr.getResultList();
			
			meta = em.find(OdsKrxMeta.class, viewID);
			for(OdsKrxMetaDetail aa : meta.getOdsKrxMetaDetails()){
				formData.put(aa.getId().getMapKey(), aa.getMapValue());
			}
		}
	}
}