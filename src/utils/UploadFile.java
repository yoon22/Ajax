package utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFile {
   private static final String TMP_PATH = System.getProperty("java.io.tmpdir");
   private static final String TARGET_PATH = "D:\\study\\workspace\\git\\Ajax\\WebContent\\WEB-INF";
   private static final int MEMORY_SIZE = 1000000*1024*1024;
   private static final int TOTAL_SIZE = 1000000*1024*1024;
   private static final int FILE_SIZE = 2000000*1024*1024;
   private static final File TMP_FOLDER = new File(TMP_PATH);

   private static final DiskFileItemFactory DFI_FACTORY = new DiskFileItemFactory();
   
   static {
      DFI_FACTORY.setSizeThreshold(MEMORY_SIZE);
      DFI_FACTORY.setRepository(TMP_FOLDER);
   }   
   
   public static Map<String,Object> ParseRequest(HttpServletRequest request) throws ServletException{
	   if(!ServletFileUpload.isMultipartContent(request)) {
		   throw new ServletException("올바른 폼 형식이 아닙니다!");
	   }
      ServletFileUpload sfu = new ServletFileUpload(DFI_FACTORY);
      sfu.setSizeMax(FILE_SIZE);
      sfu.setFileSizeMax(TOTAL_SIZE);
      try {
      List<FileItem> fileList = sfu.parseRequest(request);
      Map<String,Object> paramMap = new HashMap<>();
      for(FileItem file : fileList) {
         String key = file.getFieldName();
         if(file.isFormField()) {
            String value = file.getString("utf-8");
            paramMap.put(key, value);
            
         }else {
            paramMap.put(key, file);
         }
      }
      return paramMap;
      }catch (FileUploadException e) {
    	  throw new ServletException(e);
      } catch (UnsupportedEncodingException e) {
    	  throw new ServletException(e);
      }
      
   }
   public static  File writeFile(FileItem file) throws Exception {
	   String fileName = file.getName();
	   String path = TARGET_PATH + File.separator + fileName;
	   File targetFile = new File(path);
	   file.write(targetFile);
	   return targetFile;
   }
}
