package com.eugenefe.dao;
// Generated Mar 16, 2016 4:59:04 PM by Hibernate Tools 4.3.1.Final

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.entity.OdsKrxMetaDetail;
import com.eugenefe.enums.PersistenceManager;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.KrxMarketDataScrapUtil;

/**
 * Scrap Krx Data from KRX homepage and write Json Result to File
 */

public class OdsKrxScrapToFileOld  {
	private final static Logger logger = LoggerFactory.getLogger(OdsKrxScrapToFileOld.class);
	private String viewID ;
	
	private EntityManager em  ;
	private OdsKrxMeta meta ;
	private Map<String, String> formData = new HashMap<String, String>();
	private Properties properties = new Properties();
	private String filePath;
	
	
	public OdsKrxScrapToFileOld() {
		
	}
	
	public OdsKrxScrapToFileOld(String viewId) {
		this.viewID = viewId;
	}
	
	public String getViewID() {
		return viewID;
	}
	public void setViewID(String viewID) {
		this.viewID = viewID;
	}


	public void writeJsonToFile(String isin) throws IOException{
		writeJsonToFile(isin, true);
	}
	
	public void writeJsonToFile(String isin, boolean fromDB) throws IOException{
		
		if(fromDB){
			prepareFromDB(viewID);
		}
		else {
			prepareFromProperties(viewID);
		}
		filePath = meta.getFilePath();
		formData.put("isu_cd", isin);
		
		logger.info("aaa: {},{}", meta.getUrl(), formData.get("isu_cd"));
		FileUtil.writeFile(filePath + isin + ".json", KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData));
	}
	
	public void writeJsonDailyToFile(String bssd, String subType) throws IOException{
		writeJsonDailyToFile(bssd, subType, true);
	}
	
	public void writeJsonDailyToFile(String bssd, String subType, boolean fromDB) throws IOException{
		if(fromDB){
			prepareFromDB(viewID);
		}
		else {
			prepareFromProperties(viewID);
		}
		filePath = meta.getFilePath();
		formData.put("schdate", bssd);
		formData.put("idx_upclss_cd", "01");
		
		
		logger.info("aaa: {},{}", meta.getUrl(), formData.get("isu_cd"));
		FileUtil.writeFile(filePath + bssd + ".json", KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData));
	}

	private void prepareFromProperties(String viewID){
		String propKey;
		String splitKey ;
		String propValue;
		meta = new OdsKrxMeta();
		try {
			properties.load(OdsKrxScrapToFileOld.class.getResourceAsStream("/krx.properties"));
			for(Map.Entry<Object, Object> entry : properties.entrySet()){
				propKey = (String)entry.getKey();
				propValue = (String)entry.getValue();
				logger.info("prop: {},{}", propKey, propValue);
				if(propKey.contains(viewID)){
					splitKey = propKey.split("]")[1];                                   // the sample of property key : [80104_MAIN]url = ....
					logger.info("split: {},{}", splitKey);
					if(splitKey.equals("url")){
						meta.setUrl(propValue);
					}
					else if (splitKey.equals("optUrl")) {
						meta.setOptUrl(propValue);
					}
					else if (splitKey.equals("filePath")) {
						meta.setFilePath(propValue);
					}
					else if(splitKey.contains("Form")){
						formData.put(splitKey.split("Form_")[1], (String)entry.getValue());  // the sample of property  used in form Data : [80104_MAIN]Form_gNo = ....
					}
				}	
			}
		}catch(IOException ex){
			
		}
	}
	
	private void  prepareFromDB(String viewID){
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
