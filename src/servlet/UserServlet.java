package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.UserService;
import service.impl.UserServiceImpl;


@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService us = new UserServiceImpl();


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cmd = request.getParameter("cmd");
		
		if("insert".equals(cmd)) {
			String uiId = request.getParameter("ui_id");
			String uiPwd = request.getParameter("ui_pwd");
			String uiName = request.getParameter("ui_name");
			String uiEmail = request.getParameter("ui_email");
			
			Map<String,String> user = new HashMap<>();
			user.put("ui_id",uiId);
			user.put("ui_pwd",uiPwd);
			user.put("ui_name",uiName);
			user.put("ui_email",uiEmail);
			if(us.insertUser(user)==1) {
				request.setAttribute("msg", "회원가입 성공");
				request.setAttribute("url", "/");
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/views/msg/result");
			rd.forward(request, response);
			
			
		}else if("login".equals(cmd)) {
			String uiId = request.getParameter("ui_id");
			String uiPwd = request.getParameter("ui_pwd");
			Map<String,String> user = us.login(uiId, uiPwd);
			request.setAttribute("msg","아이디나 비밀번호가 잘못되었습니다.");
			if(user!=null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				//request를 안넣는 이유 : 화면이 바뀔때마다 새롭게 로그인을 요청할수있음. 그래서 다시 로그인해야함.
				//그래서 session 으로!!!! 로그인을 유지하기 위하여..?
				//다른 jsp 에서도 다시 인증받지 않고 사용할 수  있도록 하기위해서. (한브라우져일 경우)
				//session에 비밀번호를 저장하면 안되염 				
				request.setAttribute("msg","로그인에 성공하였습니다.");
			}
			request.setAttribute("url", "/");
			RequestDispatcher rd = request.getRequestDispatcher("/views/msg/result");
			//앞에 왜 WEB-INF, '.JSP' 를 안붙이는 이유 :  viewResolverservlet를 가게 한다음  url이 완성되도록 해놓았으니!
			//혹시 확장자 수정시 viewResolverservlet 만 바꾸면 됨.
			rd.forward(request, response);
			
			
		}else if("logout".equals(cmd)) {
			HttpSession session = request.getSession();
			session.invalidate();
			request.setAttribute("msg","로그아웃 되었습니다.");
			request.setAttribute("url", "/");
			RequestDispatcher rd = 
					request.getRequestDispatcher("/views/msg/result");
			rd.forward(request, response);
		}
	}

}

