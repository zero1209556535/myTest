package cn.itcast.ssm.exception;

/**
 * 
 * <p>ClassName: CustomException </p> 
 * <p>Description: 系统自定义异常类（仅用于模拟异常处理），针对我们预期的异常，我们需要在程序中抛出此类的异常</p>
 * @author zzl
 * @date 2016年8月13日 下午5:18:39
 * @since JDK 1.6 
 * @version v1.0
 */
public class CustomException extends Exception{

	/**
	 * serialVersionUID : 随机生成的序列号
	 */
	private static final long serialVersionUID = 6420601895126369715L;
	
	//异常信息
	private String message;
	
	//默认的无参构造器（若定义了有参构造器，必须将无参构造器也定义出来，否则无参构造器失效）
	public CustomException(){
		
	}
	
	//自定义有参构造器的好处是可以直接传入参数，不需要setter和getter方法。
	public CustomException(String message){
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
