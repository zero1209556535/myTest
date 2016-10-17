package cn.itcast.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.ssm.po.ItemsCustom;

/**
 * 
 * <p>ClassName: JsonTest </p> 
 * <p>Description: json交互测试 </p>
 * @author zzl
 * @date 2016年8月16日 下午12:13:06
 * @since JDK 1.6 
 * @version v1.0
 */
@Controller
public class JsonTest {

	//客户端请求json（商品信息），服务器输出响应json（商品信息）
	//其中注解@RequestBody是将json串转成java对象（将客户端请求的json串转成controller方法形参中的java对象进行参数绑定），
	//其中@RequestBody是和参数配对使用的，一个形参对应一个@RequestBody。                         
	//其中注解@ResponseBody是将java对象转成json串（将服务器响应的java对象转成json串进行输出）
	@RequestMapping("/requestJson")
	public @ResponseBody ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom)throws Exception{
		
		return itemsCustom;
		
	}
	
	//客户端请求json（商品信息），服务器输出响应json（商品信息），使用GET请求方式
	//其中注解@RequestBody是将json串转成java对象（将客户端请求的json串转成controller方法形参中的java对象进行参数绑定），
	//其中@RequestBody是和参数配对使用的，一个形参对应一个@RequestBody。                         
	//其中注解@ResponseBody是将java对象转成json串（将服务器响应的java对象转成json串进行输出）
	@RequestMapping("/requestJsonGet")
	public @ResponseBody ItemsCustom requestJsonGet(@RequestBody ItemsCustom itemsCustom)throws Exception{
		
		//说明：此方式不可行！！！！！！！
		return itemsCustom;
		
	}
	
	//客户端请求key/value（商品信息），服务器输出响应json（商品信息）
	@RequestMapping("/responseJson")
	public @ResponseBody ItemsCustom responseJson(ItemsCustom itemsCustom)throws Exception{
		
		return itemsCustom;
		
	}
	
}
