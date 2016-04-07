package com.eugenefe.dao;
// Generated Mar 16, 2016 4:59:04 PM by Hibernate Tools 4.3.1.Final

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eugenefe.entity.OdsKrxCombo;
import com.eugenefe.entity.OdsKrxMeta;
import com.eugenefe.entity.OdsKrxMetaDetail;
import com.eugenefe.enums.PersistenceManager;
import com.eugenefe.utils.FileUtil;
import com.eugenefe.utils.KrxMarketDataJsonUtil;
import com.eugenefe.utils.KrxMarketDataScrapUtil;

/**
 * Scrap Krx Data from KRX homepage and write Json Result to File
 */

public class OdsKrxScrapToFile  {
	private final static Logger logger = LoggerFactory.getLogger(OdsKrxScrapToFile.class);
	
	private EntityManager em  ;
	private OdsKrxMeta meta ;
	private Map<String, String> formData = new HashMap<String, String>();
	private Properties properties = new Properties();
	private String filePath;
	private String viewID;
	
	
	public OdsKrxScrapToFile() {
		
	}
	
	public void writeFileFrom80103Main(String isin) throws IOException{
		writeFileFrom80103Main(isin, true);
	}
	
	public void writeFileFrom80103Main(String isin, boolean fromDB) throws IOException{
		viewID ="80103_MAIN";
		if(fromDB){
			prepareFromDB(viewID);
		}
		else {
			prepareFromProperties(viewID);
		}
		filePath = meta.getFilePath();
		formData.put("isu_cd", isin);
		
		FileUtil.writeFile(filePath + "[80103]"+isin + ".json", KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData));
	}
	
	public void writeFileFrom80104Main(String isin) throws IOException{
		writeFileFrom80104Main(isin, true);
	}

	public void writeFileFrom80104Main(String isin, boolean fromDB) throws IOException{
		viewID ="80104_MAIN";
		if(fromDB){
			prepareFromDB(viewID);
		}
		else {
			prepareFromProperties(viewID);
		}
		filePath = meta.getFilePath();
		formData.put("isu_cd", isin);
		
		FileUtil.writeFile(filePath + "[80104]"+ isin + ".json", KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData));
	}
	
	public void writeFileFrom20001Main(String bssd, String subType, boolean fromDB) throws IOException{
		viewID ="20001_MAIN";
		if(fromDB){
			prepareFromDB(viewID);
		}
		else {
			prepareFromProperties(viewID);
		}
		filePath = meta.getFilePath();
		formData.put("schdate", bssd);
		formData.put("idx_upclss_cd", subType);
		
		FileUtil.writeFile(filePath + "[20001]" + bssd +"_" +subType + ".json", KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData));
	}

	
	
	public void writeFileFrom20001Main(String bssd, String subType) throws IOException{
		writeFileFrom20001Main(bssd, subType, true);
	}
	
	public void writeFileFrom40015Main(String bssd, boolean fromDB) throws IOException{
		viewID ="40015_MAIN";
		if(fromDB){
			prepareFromDB(viewID);
		}
		else {
			prepareFromProperties(viewID);
		}
		filePath = meta.getFilePath();
		formData.put("todate", bssd);
		formData.put("fromdate", bssd);
		formData.put("isu_cd", "ALL");
		
		FileUtil.writeFile(filePath + "[40015]"+bssd + ".json", KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData));
	}
	
	public void writeFileFrom40015Main(String bssd) throws IOException{
		writeFileFrom40015Main(bssd, true);
	}

	
	public void writeFileFrom40020Main(String callPutType, boolean fromDB) throws IOException{
		viewID ="40020_COMBO";
		if(fromDB){
			prepareFromDB(viewID);
		}
		else {
			prepareFromProperties(viewID);
		}
		
		filePath = meta.getFilePath();
		formData.put("cp_type", callPutType);
		formData.put("type", "1");
		
		String isinGroup = KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData);
		
		List<OdsKrxCombo> isinGroupCombo =  KrxMarketDataJsonUtil.convertTo(OdsKrxCombo.class, isinGroup, "result");
		
		for(OdsKrxCombo aa : isinGroupCombo){
			writeFileFrom40020Main( aa.getIsuCd(), callPutType, fromDB);
		}
		
		
//		viewID ="40020_MAIN";
//		String bssd = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
//
//		if(fromDB){
//			prepareFromDB(viewID);
//		}
//		else {
//			prepareFromProperties(viewID);
//		}
//		filePath = meta.getFilePath();
//		
//		formData.put("cp_type", callPutType);
//		formData.put("type", "1");
//		
//		for(OdsKrxCombo aa : isinGroupCombo){
//			formData.put("isu_cd", aa.getIsuCd());
//			BufferedWriter out = new BufferedWriter(new FileWriter(filePath + "[40020]"+bssd+"_" + aa.getIsuCd()+callPutType +".json"));
//    		out.write( KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData) );
//    		out.close();
//		}
		
	}
	
	public void writeFileFrom40020Main(String isinGroup, String callPutType, boolean fromDB) throws IOException{
		
		viewID ="40020_MAIN";
		String bssd = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);

		if(fromDB){
			prepareFromDB(viewID);
		}
		else {
			prepareFromProperties(viewID);
		}
		filePath = meta.getFilePath();
		formData.put("cp_type", callPutType);
		formData.put("type", "1");
		formData.put("isu_cd", isinGroup);
		
		FileUtil.writeFile(filePath + "[40020]"+bssd+"_"+isinGroup+ callPutType +".json", KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData));
			
	}
	
	public void writeFileFrom40020Combo(String callPutType, boolean fromDB) throws IOException{
		viewID ="40020_COMBO";
		if(fromDB){
			prepareFromDB(viewID);
		}
		else {
			prepareFromProperties(viewID);
		}
		filePath = meta.getFilePath();
		formData.put("cp_type", callPutType);
		formData.put("type", "1");
		
		FileUtil.writeFile(filePath + "[40020]"+callPutType + ".json", KrxMarketDataScrapUtil.scrap(meta.getUrl(), meta.getOptUrl(), formData));
	}
	
	
	
	
	private void prepareFromProperties(String viewID){
		String propKey;
		String splitKey ;
		String propValue;
		meta = new OdsKrxMeta();
		try {
			properties.load(OdsKrxScrapToFile.class.getResourceAsStream("/krx.properties"));
			for(Map.Entry<Object, Object> entry : properties.entrySet()){
				propKey = (String)entry.getKey();
				propValue = (String)entry.getValue();
//				logger.info("prop: {},{}", propKey, propValue);
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
