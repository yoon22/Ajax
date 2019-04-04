package service.impl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import dao.FileDAO;
import dao.impl.FileDAOImpl;
import service.FileService;
import utils.UploadFile;

public class FileServiceImpl implements FileService {
	private FileDAO fdao = new FileDAOImpl();
	
	@Override
	public Map<String, String> insertAddrFromFile(File file) {
		List<String> colList = new ArrayList<>();
		colList.add("ad_code");
		colList.add("ad_sido");
		colList.add("ad_gugun");
		colList.add("ad_dong");
		colList.add("ad_lee");
		colList.add("ad_san");
		colList.add("ad_bunji");
		colList.add("ad_ho");
		colList.add("ad_roadcode");
		colList.add("ad_isbase");
		colList.add("ad_orgnum");
		colList.add("ad_subnum");
		colList.add("ad_jinum");
		colList.add("ad_etc");
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			List<Map<String,String>> addrList = new ArrayList<>();
			while((line=br.readLine())!=null) {
				String[] lines = line.split("\\|");
				Map<String,String> addrMap = new HashMap<>();
				for(int i=0;i<lines.length;i++) {
					addrMap.put(colList.get(i),lines[i]);
				}
				addrList.add(addrMap);
				if(addrList.size()==10000) {			
					int result = fdao.insertAddressList(addrList);
					addrList.clear();
					System.out.println("반영된 건수 : " + result);
				}				
			}	
			int result = fdao.insertAddressList(addrList);
			addrList.clear();	
			System.out.println("반영된 건수 : " + result);
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public Map<String, String> parseText(HttpServletRequest request) throws ServletException {
		Map<String,Object> pMap = UploadFile.ParseRequest(request);
		Iterator<String> it = pMap.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			Object obj = pMap.get(key);
			if(obj instanceof FileItem) {
				//파일아이템인지 아닌지 물어보는것. (상속받은 모든것들도 파일아이템이라고 할수있음)
				//파일아이템일 경우 세이브 로직이 들어가야함. 
				try {
					File tFile = UploadFile.writeFile((FileItem)obj);
					return insertAddrFromFile(tFile);
				} catch (Exception e) {
					throw new ServletException();							
				}				
			}
		}				
		return null;
	
	}
}
