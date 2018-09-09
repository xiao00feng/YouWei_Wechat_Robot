package cn.YouWei.STD.robot.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserInterceptor implements HandlerInterceptor {

	//不用拦截的url请求
	private List<String> unCheckUrls;
	
	/**
	 * @return the unCheckUrls
	 */
	public List<String> getUnCheckUrls() {
		return unCheckUrls;
	}

	/**
	 * @param unCheckUrls the unCheckUrls to set
	 */
	public void setUnCheckUrls(List<String> unCheckUrls) {
		this.unCheckUrls = unCheckUrls;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String requestUrl = request.getRequestURI(); 
		if(unCheckUrls.contains(requestUrl))
		{
			return true;
		}
		else
		{
			if(request.getSession().getAttribute("username")!=null)
			{
				return true;
			}
			else
			{
				response.sendRedirect("/bargain/login");
				return false;
			}
		}
		
		
		
		
	}

	public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
