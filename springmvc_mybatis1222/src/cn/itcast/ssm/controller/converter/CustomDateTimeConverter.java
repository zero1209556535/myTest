package cn.itcast.ssm.controller.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * <p>ClassName: CustomDateConverter </p> 
 * <p>Description: 自定义日期类型参数绑定转换器（格式是yyyy-MM-dd HH:mm:ss） </p>
 * @author zzl
 * @date 2016年8月9日 下午11:39:01
 * @since JDK 1.6 
 * @version v1.0
 */
//String转Date，其中String是源source，Date是目标Destination
public class CustomDateTimeConverter implements Converter<String, Date>{

	@Override
	public Date convert(String source) {
		
		//实现将日期数据字符串转换成日期类型（格式是yyyy-MM-dd HH:mm:ss）
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			//如果类型转换成功，那么就直接返回
			//当我们的日期数据字符串完全匹配yyyy-MM-dd HH:mm:ss这个格式的时候才会转换成功
			//否则转换失败，抛出异常
			return simpleDateFormat.parse(source);
		} catch (ParseException e) {
			//实现将日期数据字符串转换成日期类型（格式是yyyy-MM-dd）
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				//如果类型转换成功，那么就直接返回
				//当我们的日期数据字符串完全匹配yyyy-MM-dd这个格式的时候才会转换成功
				//否则转换失败，抛出异常
				return simpleDateFormat.parse(source);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
		}
		
		//如果参数绑定失败(类型转换失败)的话就返回null
		return null;
	}
	
}
