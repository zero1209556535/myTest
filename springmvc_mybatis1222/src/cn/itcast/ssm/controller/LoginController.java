package cn.itcast.ssm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * <p>ClassName: LoginController </p> 
 * <p>Description: 登陆认证controller </p>
 * @author zzl
 * @date 2016年8月17日 上午11:39:07
 * @since JDK 1.6 
 * @version v1.0
 */
@Controller
public class LoginController {
	
	//登录
	@RequestMapping("/login")
	public String login(HttpSession session, String usercode, String password){
		
		//调用Service接口进行用户身份验证
		//...
		
		//在Session中保存用户的身份信息
		session.setAttribute("usercode", usercode);
		
		//重定向到商品列表页面
		return "redirect:/items/queryItems.action";
	}
	
	
	//退出登录
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		
		session.setAttribute("usercode", null);
		//清空Session，其实就是使Session过期
		//session.invalidate();
		
		//重定向到商品列表页面
		return "redirect:/items/queryItems.action";
	}
	
	//这个方法的目的仅仅只是转发到登陆页面，不过这个方法没有使用到
	@RequestMapping("/loginPage")
	public String loginPage(){
		
		return "login";
	}

}
