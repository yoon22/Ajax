package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.MovieService;
import service.impl.MovieServiceImpl;
import utils.Command;

public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MovieService ms = new MovieServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd = Command.getCmd(request);
//		String uri = request.getRequestURI();
//		int idx = uri.lastIndexOf("/");
//		// 마지막에서 가까운 /이 위치한 자릿수를 찾음
//		if (idx == 0) {
//			throw new ServletException("원하시는 서비스가 부정확합니다.");
//			// 사용자가 url을 잘못 입력했을 경우!
//			// http://localhost/movie까지 쓸 경우도 !!! 오류나옴.
//		} else {
//			String cmd = uri.substring(idx + 1);
		if ("list".equals(cmd)) {
			request.setAttribute("list", ms.selectMovieList());
			RequestDispatcher rd = request.getRequestDispatcher("/views/movie/movie_list");
			rd.forward(request, response);
		} else {
			try {
				int miNum = Integer.parseInt(cmd);
				request.setAttribute("movie", ms.selectMovieByMiNum(miNum));
				RequestDispatcher rd = request.getRequestDispatcher("/views/movie/view");
				rd.forward(request, response);

			} catch (Exception e) {
				throw new ServletException("올바른 상세조회값이 아닙니다.");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cmd = Command.getCmd(request);
//		String uri = request.getRequestURI();
//		int idx = uri.lastIndexOf("/");
//		if (idx == 0) {
//			throw new ServletException("원하시는 서비스가 부정확합니다.");
//		} else {
//			String cmd = uri.substring(idx + 1);
		if ("insert".equals(cmd)) {
			HttpSession hs = request.getSession();
			if (hs.getAttribute("user") == null) {
				Command.goResultPage(request, response, "/", "로그인하세요");
//					request.setAttribute("msg", "로그인하세요");
//					request.setAttribute("url", "/");
//					RequestDispatcher rd = request.getRequestDispatcher("/views/msg/result");
//					rd.forward(request, response);
				return;
			}
			Map<String, String> movie = Command.getSingleMap(request);
			String msg = "영화등록실패";
			String url = "/movie/list";
			if (ms.insertMovie(movie) == 1) {
				msg = "영화등록성공!";
			}
			Command.goResultPage(request, response, url, msg);
		} else if ("delete".equals(cmd)) {
			HttpSession hs = request.getSession();
			if (hs.getAttribute("user") == null) {
				Command.goResultPage(request, response, "/", "로그인하세요");
				return;
				// 세션처리!! 로그인한상태가 아니라면 삭제 불가.
			}
			int miNum = Integer.parseInt(request.getParameter("mi_num"));
			String msg = "삭제에 실패하였습니다.";
			String url = "/movie/" + miNum;
			// 삭제실패를 먼저 가정해놓음.
			if (ms.deleteMovie(miNum) == 1) {
				msg = "삭제에 성공하였습니다.";
				url = "/movie/list";
			}
			Command.goResultPage(request, response, url, msg);
		}
	}

}
