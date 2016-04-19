package com.eugenefe.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileUtil {
	private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	
	public static String readFile(String filePath) throws IOException {
		String returnValue = "";
		String line = "";
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		int cnt=0;
		
		while ((line = reader.readLine()) != null) {
			cnt = cnt+1;
			returnValue += line + "\n";
			String[] arr = line.split(";");
		}
		reader.close();
//		logger.info("return size : {},{}" , cnt,returnValue.replaceAll("\n", ""));
		return returnValue;
	}

	
	public static String readFile(File file) throws IOException {
		String returnValue = "";
		String line = "";
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int cnt=0;
		
		while ((line = reader.readLine()) != null) {
			cnt = cnt+1;
			returnValue += line + "\n";
			String[] arr = line.split(";");
		}
//		logger.info("return size : {}" , cnt);
		reader.close();
		return returnValue;
	}
	
	public static Map<String, String> readFileToMap(String filePath) throws IOException {
		Map<String, String> rst = new HashMap<String, String>();
		String line = "";
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		int cnt=0;
		
		while ((line = reader.readLine()) != null) {
			String[] arr = line.split(";");
			rst.put(arr[0], arr[1]);
		}
		return rst;
	}
	
	public static Map<String, String> readFileToMap(String filePath, String delimeter) throws IOException {
		Map<String, String> rst = new HashMap<String, String>();
		String line = "";
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		int cnt=0;
		
		while ((line = reader.readLine()) != null) {
			String[] arr = line.split(delimeter);
			rst.put(arr[0], arr[1]);
		}
		return rst;
	}

	public static List<String> readFileToList(String filePath) throws IOException {
		List<String> rst = new ArrayList<String>();
		String line = "";
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		int cnt=0;
		
		while ((line = reader.readLine()) != null) {
			rst.add(line);
		}
		reader.close();
		return rst;
	}
	
	public static void writeFile(String filePath, String str) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		out.write(str);
		out.close();
	}
	
	
	public static void writeFile(String filePath, Map<String, String> map) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));

		for (Map.Entry<String, String> entry : map.entrySet()) {
			StringBuffer str = new StringBuffer();
			str.append(entry.getKey()).append(";").append(entry.getValue());
			out.write(str.toString());
			out.newLine();
		}
		out.close();
	}
	
	public static void writeFile(String filePath, Map<String, String> map, String delimeter) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));

		for (Map.Entry<String, String> entry : map.entrySet()) {
			StringBuffer str = new StringBuffer();
			str.append(entry.getKey()).append(delimeter).append(entry.getValue());
			out.write(str.toString());
			out.newLine();
		}
		out.close();
	}
	
	public static void writeFile(String filePath, List<String> list) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		logger.info("write");
		for (String aa : list) {
			StringBuffer str = new StringBuffer();
			str.append(aa);
			out.write(str.toString());
			out.newLine();
		}
		out.close();
	}

	public static void makeDir(String directory) {
		File theDir = new File(directory);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			logger.info("creating directory: {} " , directory);
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				logger.info("successfully created: {} " , directory);
			}
		}
	}
}
