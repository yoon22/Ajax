package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import service.MovieService;
import service.impl.MovieServiceImpl;
import utils.Command;

public class AJAXMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MovieService ms = new MovieServiceImpl();
	private Gson gson = new Gson();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = Command.getCmd(request);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		if ("list".equals(cmd)) {
			PrintWriter pw = response.getWriter();
			pw.println(gson.toJson(ms.selectMovieList()));			
		} else {
			try {
				int miNum = Integer.parseInt(cmd);
				PrintWriter pw = response.getWriter();
				pw.println(gson.toJson(ms.selectMovieByMiNum(miNum)));
			} catch (Exception e) {
				throw new ServletException("올바른 상세조회값이 아닙니다.");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cmd = Command.getCmd(request);
		if ("insert".equals(cmd)) {			
			HttpSession hs = request.getSession();			
			if (hs.getAttribute("user") == null) {
				Command.goResultPage(request, response, "/", "로그인하세요");
				return;
			}
			Map<String, String> movie = Command.getSingleMap(request);
			Map<String,String> rMap = new HashMap<>();
			rMap.put("msg", "등록실패");
			rMap.put("url", "/views/movie/ajax_insert");
			if (ms.insertMovie(movie) == 1) {
				rMap.put("mag", "등록성공");
			}
			Command.printJSON(response, rMap);			
			
		} else if ("delete".equals(cmd)) {			
			HttpSession hs = request.getSession();			
			if (hs.getAttribute("user") == null) {
				Command.goResultPage(request, response, "/", "로그인하세요");
				return;
				// 세션처리!! 로그인한상태가 아니라면 삭제 불가.
			}
			int miNum = Integer.parseInt(request.getParameter("mi_num"));
			Map<String,String> rMap = new HashMap<>();
			rMap.put("msg",  "삭제에 실패하였습니다.");
			rMap.put("url", "/views/movie/ajax_list");
			if(ms.deleteMovie(miNum)==1) {
				rMap.put("msg", "삭제성공");
			}
			Command.printJSON(response, rMap);							
		}
	}

}
