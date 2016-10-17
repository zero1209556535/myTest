package cn.itcast.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * <p>ClassName: HandlerInterceptor1 </p> 
 * <p>Description: 登陆认证拦截器 </p>
 * @author zzl
 * @date 2016年8月17日 上午9:33:29
 * @since JDK 1.6 
 * @version v1.0
 */
public class LoginInterceptor implements HandlerInterceptor{

	//在进入Handler的方法之前执行
	//用于身份认证（登陆认证）和身份授权（权限校验）
	//比如身份认证，如果身份认证不通过说明此用户还没有登录，需要此方法进行拦截不再向下执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		//获取请求的URL
		String url = request.getRequestURI();
		//判断URL是否是公开地址（实际开发中我们一般将公开地址配置到配置）
		//我们这里的公开地址仅仅指的是登陆提交的地址
		if(url.indexOf("login.action")>=0){
			//如果进行登陆提交，那么我们放行，因为此时Session还没有，不需要判断Session
			return true;
		}
		
		//判断Session中是否存在用户身份信息
		HttpSession session = request.getSession();
		//从Session中获取用户的身份信息
		String usercode = (String) session.getAttribute("usercode");
		
		if(usercode!=null){
			//如果Session中存在用户身份信息，那么放行
			return true;
		}
		
		//执行到这里表示用户身份需要进行认证，那么就跳转到登陆页面（用转发还是重定向？）
		//response.sendRedirect("/springmvc_mybatis1222/jsonTest.jsp");//可以访问非WEB-INF下面的jsp
		//response.sendRedirect("/springmvc_mybatis1222/WEB-INF/jsp/login.jsp");//不可以访问WEB-INF下面的jsp
		//response.sendRedirect("redirect:/loginPage.action");//肯定不可以，又不是在Handler方法里，不可以这么写    
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		
		//return false;表示拦截，不再向下执行，即中止执行
		//return true;表示放行，即继续执行
		return false;
	}
	
	//在进入Handler的方法之后，方法返回modelAndView之前执行
	//应用场景从modelAndView出发，将共用的模型数据在这里统一传到视图，也可以在这里根据判断条件统一指定视图地址
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		
		System.out.println("HandlerInterceptor2......postHandle");
	}
	
	//在Handler的方法执行完成之后再执行此方法
	//应用场景：统一异常处理，统一日志处理
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		System.out.println("HandlerInterceptor2......afterCompletion");
	}

}
