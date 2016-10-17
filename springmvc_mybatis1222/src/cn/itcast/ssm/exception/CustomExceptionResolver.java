package cn.itcast.ssm.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * <p>ClassName: CustomExceptionResolver </p> 
 * <p>Description: 全局异常处理器 (实现springmvc提供的Handler接口)</p>
 * @author zzl
 * @date 2016年8月13日 下午5:53:25
 * @since JDK 1.6 
 * @version v1.0
 */
public class CustomExceptionResolver implements HandlerExceptionResolver{

	//其中Object handler就是处理器适配器要执行的Handler对象（对象中只有一个Method方法）
	//其中Exception ex是前端控制器截获的系统抛出的异常信息
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//先是解析出异常的类型：
		//String message = null;
		//ex instanceof CustomException这句代码的意思是ex对象是CustomException类的一个实例
		/*if(ex instanceof CustomException){
			//1、如果该异常类型是系统自定义的异常(预期异常)，直接取出异常信息，在错误页面进行显示。
			message = ((CustomException)ex).getMessage();
		}else{
			//2、如果该异常类型不是系统自定义的异常（基本上是Runtime异常），那么就构造出一个自定义的异常类型（信息为“未知错误”）
			message = "未知错误";
		}*/
		
		//上面的代码可以改写为
		CustomException customException = null;
		if(ex instanceof CustomException){
			customException = (CustomException) ex;
		}else{
			customException = new CustomException("未知错误，请与管理员进行联系！");
		}
		//错误信息
		String errorMessage = customException.getMessage();
		//定义ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		//将错误信息传到前台页面(错误页面)
		modelAndView.addObject("errorMessage", errorMessage);
		//指定前台页面(指向错误页面)
		modelAndView.setViewName("error");
		
		return modelAndView;
	}

}
