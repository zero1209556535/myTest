package cn.itcast.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * <p>ClassName: HandlerInterceptor1 </p> 
 * <p>Description: 测试拦截器1 </p>
 * @author zzl
 * @date 2016年8月17日 上午9:33:29
 * @since JDK 1.6 
 * @version v1.0
 */
public class HandlerInterceptor1 implements HandlerInterceptor{

	//在进入Handler的方法之前执行
	//用于身份认证（登陆认证）和身份授权（权限校验）
	//比如身份认证，如果身份认证不通过说明此用户还没有登录，需要此方法进行拦截不再向下执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		System.out.println("HandlerInterceptor1......preHandle");
		
		//return false;表示拦截，不再向下执行，即中止执行
		//return true;表示放行，即继续执行
		return true;
	}
	
	//在进入Handler的方法之后，方法返回modelAndView之前执行
	//应用场景从modelAndView出发，将共用的模型数据在这里统一传到视图，也可以在这里根据判断条件统一指定视图地址
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		
		System.out.println("HandlerInterceptor1......postHandle");
	}
	
	//在Handler的方法执行完成之后再执行此方法
	//应用场景：统一异常处理，统一日志处理
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		System.out.println("HandlerInterceptor1......afterCompletion");
	}

}
