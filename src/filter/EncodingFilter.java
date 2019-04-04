package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

public class EncodingFilter implements Filter {
	private String encoding = "";
    public EncodingFilter() {
    	System.out.println("난무조건1빠");
        
    }

	public void destroy() {
		System.out.println("난 종료되어야 나옴 ");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		chain.doFilter(request, response);
		//web.xml 에  맵핑시켜놔서 어떤 파일을 실행하던 do filter를 한번식 거쳐가기 때문에 인코딩 해줌~~! 
	}


	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter("encoding");
	}//메모리 생성 후 init 은 한번만 ~~!!! 

}
