package servlet;


import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.FileService;
import service.impl.FileServiceImpl;



public class FileServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private FileService fs = new FileServiceImpl();
   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      response.getWriter().append("Served at: ").append(request.getContextPath());
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    fs.parseText(request);
   }

}