package cn.itcast.ssm.controller;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.controller.validation.ValidationGroup1;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;

/**
 * 
 * <p>ClassName: ItemsController </p> 
 * <p>Description: 商品管理的Controller（也就是Handler） </p>
 * @author zzl
 * @date 2016年8月8日 下午12:52:36
 * @since JDK 1.6 
 * @version v1.0
 */
@Controller
//因为我们的系统可能有多个controller，所以为了对我们的URL进行分类管理，可以在类上通过注解定义根路径
//最终我们访问的url是根路径+子路径，比如说${pageContext.request.contextPath }/items/queryItems.action  
//其实最终的路径就是http://localhost:8080/springmvc_mybatis1222/items/queryItems.action
@RequestMapping("/items")
public class ItemsController {
	
	//依赖注入
	@Autowired
	private ItemsService itemsService;
	
	//商品分类
	//itemsTypes表示最终将方法的返回值放到request域中的key(貌似我现在使用的Spring版本行不通)
	//可行的办法是在Handler方法中调用此方法，将其引用放到request域中，然后再在页面获取。
	@ModelAttribute("itemsTypes")
	public Map<String, String> getItemsTypes(){
		Map<String, String> itemsTypes = new HashMap<String, String>();
		itemsTypes.put("101", "家电");
		itemsTypes.put("102", "数码");
		return itemsTypes;
	}
	
	//商品查询
	@RequestMapping("/queryItems")
	public ModelAndView queryItems(HttpServletRequest request, ItemsQueryVo itemsQueryVo)throws Exception{
		//仅仅是为了测试forward页面转发后request域是否可以实现共享
		System.out.println(request.getParameter("id"));
		
		//调用Service的接口查找数据库，查询商品列表
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
		
		//返回ModelAndView
		ModelAndView modelAndView = new ModelAndView();
		//这个方法相当于request的setAttribute方法，在jsp页面中通过itemsList获取数据。
		modelAndView.addObject("itemsList", itemsList);
		
		//指定视图
		//下面的路径，如果在视图解析器中配置jsp路径的前缀和jsp路径的后缀，可以修改为更简单的写法
		//modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		modelAndView.setViewName("items/itemsList");
		
		//其实就相当于页面转发
		//request.getRequestDispatcher("/WEB-INF/jsp/items/itemsList.jsp").forward(request, response);
		return modelAndView;
	}
	
	//返回值类型为void的情况
	//商品查询，使用页面转发
	@RequestMapping("/queryItems2")
	public void queryItems2(HttpServletRequest request , HttpServletResponse response, ItemsQueryVo itemsQueryVo)throws Exception{
		//调用Service的接口查找数据库，查询商品列表
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
		
		request.setAttribute("itemsList", itemsList);
		
		//调用本controller里的非Handler方法。
		Map<String, String> itemsTypes = getItemsTypes();
		request.setAttribute("itemsTypes", itemsTypes);
		
		//貌似返回值为void，是不会去找视图解析器进行视图解析的，也就是说不能使用逻辑视图名。
		//request转发的路径要么是有后缀.jsp的，要么是有.action的。
		
		//正确，貌似request页面转发不支持逻辑视图名，仅支持全路径，也就是绝对路径形式
		request.getRequestDispatcher("/WEB-INF/jsp/items/itemsList.jsp").forward(request, response);
		
		//错误，貌似request页面转发不支持逻辑视图名，仅支持全路径，也就是绝对路径形式
		//request.getRequestDispatcher("items/itemsList").forward(request, response);
		
		//正确，跟返回值类型为String一样，支持action转发
		//request.getRequestDispatcher("queryItems.action").forward(request, response);
		
		//正确，跟返回值类型为String一样，支持action重定向
		//response.sendRedirect("queryItems.action");
		
		//使用此方法可以通过修改response，设置响应的数据格式，比如响应json数据
		//利用注解@ResponseBody可以很轻松地实现java对象转换成json格式数据。
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("application/json;charset=utf-8");
//		response.getWriter().write("json串");
		
		
	}
	
	//商品查询（批量修改用的）
	@RequestMapping("/queryItems3")
	public ModelAndView queryItems3(HttpServletRequest request, ItemsQueryVo itemsQueryVo)throws Exception{
		//调用Service的接口查找数据库，查询商品列表
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsList", itemsList);
		modelAndView.setViewName("items/editItemsList");
		return modelAndView;
	}
	
	//商品信息修改页面展示
	//@RequestMapping("/editItems")
	//如果method={RequestMethod.POST}，那么则限制http请求为post方式，如果你的http请求使用get方式那么则会报错。
	//如果method={RequestMethod.POST,RequestMethod.GET}，则post和get请求方式都可以
	@RequestMapping(value="/editItems",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView editItems() throws Exception {
		
		//Model，调用Service根据id查询商品信息
		ItemsCustom itemsCustom = itemsService.findItemsById(1);
		
		//实例化modelAndView
		ModelAndView modelAndView = new ModelAndView();
		
		//将商品信息放入model里面
		modelAndView.addObject("itemsCustom", itemsCustom);
		
		//View，返回商品的修改页面
		modelAndView.setViewName("items/editItems");
		
		//当在springmvc.xml文件中配置了视图解析器的前缀和后缀之后，就不允许使用这种形式了
		//只能使用逻辑视图名，不能使用真正的视图名（也就是jsp路径）
		//modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		
		//返回modelAndView，其实就相当于页面转发
		return modelAndView;
		
	}
	
	//商品信息修改页面展示
	//返回值类型为String，返回逻辑视图名，其实就是我们利用的视图解析器的简单写法，去掉前缀和后缀
	//@RequestParam(value="")这个注解可以指定request传入参数名称和形参进行绑定。
	//required属性表示该参数是否必须传入，如果为true，那么必须传入，如果不传的话请求则会报错。
	//通过defaultValue属性可以设置默认值，如果id参数没有传入的话，将默认值和形参进行绑定。
	@RequestMapping(value="/editItems2",method={RequestMethod.POST,RequestMethod.GET})
	public String editItems2(Model model,@RequestParam(value="id",required=true)Integer items_id) throws Exception {
		
		//Boolean boolean1 = new Boolean(true);	//对应 boolean boolean1 = true;
		//Integer integer = new Integer(2);	//对应 int integer = 2;
		//String string = new String("你好"); //对应 String string = "你好";
		//Float float1 = new Float(1f); //对应 float float1 = 1f;
		//Double double1 = new Double(22); //对应 double double1 = 22;
		
		//调用service接口根据传入的参数商品主键id查询商品信息
		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);
		
		//判断商品信息是否为空，根据id查询商品信息，如果没有找到，那么就抛出异常，提示用户商品信息不存在！
		/*if(itemsCustom==null){
			//controller会将异常抛给前端控制器，前端控制器调用全局异常处理器进行异常处理，
			//如果我们没有配置自定义的全局异常处理器，那么就会使用springmvc默认提供的全局异常处理器。
			//如果是与业务功能相关的异常，建议在service接口中抛出（实现类方法里）
			throw new CustomException("您所要修改的商品信息不存在！");
		}*/
		
		//通过形参中的model将model数据传到前台页面
		//相当于modelAndView.addObject("itemsCustom", itemsCustom);
		model.addAttribute("itemsCustom", itemsCustom);
		
		//返回逻辑视图名
		//真正的视图（jsp路径）=前缀+逻辑视图名+后缀
		return "items/editItems";
		
		//当在springmvc.xml文件中配置了视图解析器的前缀和后缀之后，就不允许使用这种形式了
		//只能使用逻辑视图名，不能使用真正的视图名（也就是jsp路径）
		//return "/WEB-INF/jsp/items/itemsList.jsp";
		
	}
	
	
	//商品信息修改提交
	@RequestMapping("/editItemsSubmit")
	public ModelAndView editItemsSubmit(){
		
		//调用Service里的接口更新商品信息，页面需要将商品的信息传入到此方法
		//......参数绑定
		
		
		//实例化modelAndView
		ModelAndView modelAndView = new ModelAndView();
		
		//返回一个成功页面
		modelAndView.setViewName("success");
		
		//返回modelAndView，其实就相当于页面转发
		return modelAndView;
		
	}
	
	//商品信息修改提交，在实际开发中我们这里就需要使用重定向，防止用户提交完数据后点浏览器刷新或点后退之后产生重复提交
	//使用返回值类型为String可以实现重定向
	//pojo类型的参数绑定，会有乱码，我们可以在web.xml中配置post乱码的过滤器Filter。
	//在需要校验的pojo前面添加@Validated注解，在需要校验的pojo后面添加BindingResult bindingResult接收校验错误信息
	//注意，@Validated和BindingResult bindingResult是配对使用，在需要校验的pojo的一前一后，如果有多个pojo类型的参数需要校验，那么就配置多次。
	//其中value={ValidationGroup1.class}表示使用ValidationGroup1定义的分组校验，即使商品生产日期为空我们也不会去进行校验了！
	//其中注解@ModelAttribute表示我们不使用springmvc自动放入request域中的pojo用于数据回显的key的默认定义，
	//默认是pojo类名首字母小写，我们可以自定义，只需要保证和页面定义的pojo的引用名一致即可。     
	@RequestMapping("/editItemsSubmitCDX")
	public String editItemsSubmitCDX(
			Model model, 
			HttpServletRequest request, 
			Integer id, 
			@ModelAttribute("itemsCustom") @Validated(value={ValidationGroup1.class}) ItemsCustom itemsCustom, 
			BindingResult bindingResult,
			MultipartFile items_pic//用来接收上传过来的商品图片
			) throws Exception{
		
		//获取校验提示的错误信息
		if(bindingResult.hasErrors()){
			//输出错误信息
			List<ObjectError> allErrors= bindingResult.getAllErrors();
			for(ObjectError objectError : allErrors){
				System.out.println(objectError.getDefaultMessage());
			}
			//将校验错误信息传到前台页面
			model.addAttribute("allErrors", allErrors);
			//如果校验出错的话，那么就重新进入商品修改页面，相当于页面转发（貌似有问题，其实没问题，页面还是可以获取到商品信息数据的）
			//其实这是数据回显造成的结果，springmvc默认提供pojo的数据回显
			
			//在pojo传入controller的方法之后，springmvc会自动地将提交过来的pojo数据放入到request域中，
			//其中默认key就是pojo的类名（首字母小写），也就是说我们向页面响应的pojo的写法要遵循一定的规则，pojo类的首字母小写格式才可以进行数据回显。
			//但是我们可以使用注解@ModelAttribute来解决前台页面使用的pojo的引用不是pojo类名首字母小写的情况。
			
			//我们可以直接使用model将前台页面请求提交的pojo数据回显到页面中去，页面中的pojo引用很可能不是itemsCustom，如果是items，我们的写法应该是
			//model.addAttribute("items", itemsCustom);
			model.addAttribute("itemsCustom", itemsCustom);
			
			return "items/editItems";
		}
		
		//上传图片
		//说明：即使当我们没有在前台页面执行上传文件操作的时候(不管页面有没有显示图片)，items_pic也是不为空的，
		//图片解析器会帮我们先实例化一个多部件文件对象CommonsMultipartFile，但是这个对象并没有绑定任何图片文件，
		//因为我们没有进行上传操作，没有上传任何的图片文件（即使页面展示了之前的图片），所以我们获取不到上传图片的原始名称（因为我们没有执行上传文件操作）
		//因此我们需要进行判断，否则会报java.lang.StringIndexOutOfBoundsException: String index out of range: -1这个字符串角标索引越界异常，
		//因为获取的文件名称为""，当然没办法进行子串查询和字符串截取操作！
		/*if(items_pic!=null){
			
			//获取存储图片的物理路径，路径中之所以有两个反斜杠，是因为其中一个反斜杠是转义字符
			String picpath = "F:\\develop\\upload\\temp\\";
			//获取上传图片的原始名称，包括后缀名(扩展名)
			String originalfilename = items_pic.getOriginalFilename();
			//定义用于存储的新的图片名称
			String newfilename = UUID.randomUUID()+originalfilename.substring(originalfilename.lastIndexOf("."));
			//创建新图片对象，也就是File文件对象
			File newfile = new File(picpath+newfilename);
			//将内存中的数据写入磁盘，其实就是将上传的图片数据写入到我们的物理目录下面，只不过是换了一个文件名称而已。
			items_pic.transferTo(newfile);
			//如果上传图片成功，那么就将新的图片的名称写到itemsCustom中去，存储到数据库的items表里
			itemsCustom.setPic(newfilename);
		}*/
		
		//不管有没有执行上传图片的操作，springmvc都会给我们实例化一个CommonsMultipartFile对象绑定到我们的形参MultipartFile items_pic上
		//org.springframework.web.multipart.commons.CommonsMultipartFile@da1809
		//因此我们可以获取上传图片的原始名称，包括后缀名(扩展名)，不用担心java.lang.NullPointerException的问题
		String originalfilename = items_pic.getOriginalFilename();
		//其实没必要判断originalfilename!=null，因为originalfilename的值是""，只需要进行originalfilename.length()>0判断就行了。
		if(originalfilename!=null && originalfilename.length()>0){
			//获取存储图片的物理路径，路径中之所以有两个反斜杠，是因为其中一个反斜杠是转义字符
			String picpath = "F:\\develop\\upload\\temp\\";
			//定义用于存储的新的图片名称
			String newfilename = UUID.randomUUID()+originalfilename.substring(originalfilename.lastIndexOf("."));
			//创建新图片对象，也就是File文件对象
			File newfile = new File(picpath+newfilename);
			//将内存中的数据写入磁盘，其实就是将上传的图片数据写入到我们的物理目录下面，只不过是换了一个文件名称而已。
			items_pic.transferTo(newfile);
			//如果上传图片成功，那么就将新的图片的名称写到itemsCustom中去，存储到数据库的items表里
			itemsCustom.setPic(newfilename);
		}
		
		//测试get请求乱码，下面这样写会有get乱码出现
		String getQingQiu = request.getParameter("getQingQiu");
		System.out.println(getQingQiu);
		
		//解决get请求乱码的方式一：不能从根源解决
		//String getQingQiu = new String(request.getParameter("getQingQiu").getBytes("ISO8859-1"),"UTF-8");
		//System.out.println(getQingQiu);
		
		//解决get请求乱码的方式二：能从根源解决
		//修改tomcat的配置文件，添加编码与我们工程页面编码一致即可。
		
		//调用Service里的接口更新商品信息，页面需要将商品的信息传入到此方法
		//......参数绑定
		itemsService.updateItems(id, itemsCustom);
		
		//重定向到商品信息查询列表页面
		//因为在同一个controller里面，不需要加/items/
		return "redirect:queryItems2.action";
		
	}
	
	//这里的pojo参数就应该使用pojo的包装类，因为扩展性比较好。
	@RequestMapping("/editItemsVoSubmitCDX")
	public String editItemsVoSubmitCDX(HttpServletRequest request , Integer id , ItemsQueryVo itemsQueryVo) throws Exception{
		
		//调用Service里的接口更新商品信息，页面需要将商品的信息传入到此方法
		//页面中的参数名称应该写成itemsCustom.属性名的格式。
		itemsService.updateItems(id, itemsQueryVo.getItemsCustom());
		
		//重定向到商品信息查询列表页面
		//因为在同一个controller里面，不需要加/items/
		return "redirect:queryItems2.action";
		
	}
	
	//使用返回值类型为String可以实现页面转发，仅仅是为了测试request域是否可以共享
	@RequestMapping("/editItemsSubmitZF")
	public String editItemsSubmitZF(HttpServletRequest request){
		
		//调用Service里的接口更新商品信息，页面需要将商品的信息传入到此方法
		//......参数绑定
		
		
		//转发到商品信息查询列表页面
		//因为在同一个controller里面，不需要加/items/
		return "forward:queryItems2.action";
		
	}
	
	//商品信息添加页面展示
	@RequestMapping("/insertItems")
	public void insertItems(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//其实在插入数据的时候我们没必要实例化一个po扩展类的对象，因为页面有一个name属性进行数据绑定就够了，根本不需要value属性。
		ItemsCustom itemsCustom = new ItemsCustom();
		request.setAttribute("itemsCustom", itemsCustom);
		request.getRequestDispatcher("/WEB-INF/jsp/items/addItems.jsp").forward(request, response);
	}
	
	//商品信息的添加提交操作
	@RequestMapping("/insertItemsSubmitCDX")
	public void insertItemsSubmitCDX(HttpServletResponse response, ItemsCustom itemsCustom) throws Exception{
		int id =0;
		if(itemsCustom.getId()==null){
			System.out.println(id);
		}
		itemsService.insertItems(itemsCustom);
		//如果在通过表生成代码的时候没有指定主键的生成方式的话，这里肯定会报空指针异常。
		//因为我们使用的是自增长的主键生成方式，因此我们需要按照主键自增长的方式来生成我们的mapper.xml。
		id = itemsCustom.getId();
		//应该在console控制台上面打印数据库表中最大id值+1（没有执行插入操作之前的id）
		System.out.println(id);
		response.sendRedirect("queryItems2.action");
		
	}
	
	//商品信息的删除操作
	@RequestMapping("/deleteItems")
	public String deleteItems(Integer id) throws Exception{
		itemsService.deleteItems(id);
		return "redirect:queryItems2.action";
	}
	
	//商品信息的批量删除操作方式一
	//说明，页面传过来的items_id是字符串，但是经过springmvc的类型转换之后就可以转换成Integer类型，然后进行数组参数绑定。
	@RequestMapping("/deleteItemsPL")
	public String deleteItemsPL(Integer[] items_id) throws Exception{
		itemsService.deleteItemsPLByArray(items_id);
		return "redirect:queryItems2.action";
	}
	
	//商品信息的批量删除操作方式二
	@RequestMapping("/deleteItemsPL2")
	public String deleteItemsPL2(Integer[] items_id) throws Exception{
		List<Integer> idList = Arrays.asList(items_id);
		itemsService.deleteItemsPLByList(idList);
		return "redirect:queryItems2.action";
	}
	
	//批量修改商品页面，先将商品信息查询出来，然后在页面中可以修改编辑商品信息
	@RequestMapping("/editItemsPL")
	public ModelAndView editItemsPL(HttpServletRequest request, ItemsQueryVo itemsQueryVo)throws Exception{
		
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsList", itemsList);
		modelAndView.setViewName("items/editItemsList");
		
		return modelAndView;
	}
	
	//批量修改商品提交
	//通过itemsQueryVo接收页面提交过来的批量商品信息数据，把接收的数据传入到itemsQueryVo的itemsCustomList属性中。
	//页面中传入的数组转成java中的java.util.List
	@RequestMapping("/editItemsPLSubmit")
	public String editItemsPLSubmit(ItemsQueryVo itemsQueryVo)throws Exception{
		List<ItemsCustom> list= itemsQueryVo.getItemsCustomList();
		for(ItemsCustom itemsCustom : list){
			//非空才更新，例如页面没传过来pic这个参数，itemsCustom对象中的pic属性值为null不会被更新。
			//但是特别要强调的一点是，主键id必须有值，否则没办法更新！
			itemsService.updateItemsPL(itemsCustom);
		}
		return "redirect:queryItems2.action";
	}
	
	//测试Map集合参数绑定页面
	@RequestMapping("/ceshiMap")
	public String ceshiMapSubmit()throws Exception{
		return "items/ceshiMap";
	}
	
	//测试Map集合的参数绑定
	@RequestMapping("/ceshiMapSubmit")
	public String ceshiMapSubmit(ItemsQueryVo itemsQueryVo)throws Exception{
		Map<String, Object> itemsMap = itemsQueryVo.getItemsMap();
		String name = (String) itemsMap.get("name");
		//Float price = (Float) itemsMap.get("price");
		float price =Float.parseFloat( (String) itemsMap.get("price"));
		System.out.println(name+" ***** "+price);
		return "success";
	}
	
	/**
	 * 说明：操作数据库无非就是增删查改，查询和删除我们一般传的参数是主键id，但是查询更多的时候是传的pojo的包装类
	 * 	         增加和更新我们一般传的参数是po的扩展类，但是特别需要注意的是，在更新的时候，我们传的对象中的id不能为null，否则没办法进行更新，因为就是根据主键id来进行更新数据的！！！！
	 * 		我们所说的上述增删查改是相对于dao层的mapper来说的。
	 */
	
	
	//查询商品信息，输出json格式数据（实现RESTful的URL映射）
	//在web.xml中要配置RESTful风格的url地址解析方式(再配置RESTful的前端控制器，前端控制器可以有多个)
	//"/itemsView/{id}"这里的{id}不一定要和Integer id参数名称中的id一致，可以写成"/itemsView/{abc}"，不过一定要写成@PathVariable("abc")。
	//其中@PathVariable注解是和参数配对使用的，多个参数的话配置多个@PathVariable注解。
	//这里的{abc}没有实质性作用，只是用来说明问题而已。
	//说明：因为返回json数据，我们的返回类型不是void，string，ModelAndView，而是@ResponseBody ItemsCustom
	//我们一般会在客户端写回调函数获取json数据（页面post请求），如果是通过url的get请求方式（没有写回调函数），那么就会在一个空白页面显示json格式的java对象的数据。
	@RequestMapping("/itemsView/{id}/{abc}")
	public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id, @PathVariable("abc") String name)throws Exception{
		
		//调用Service根据id查询商品信息
		ItemsCustom itemsCustom = itemsService.findItemsById(id);
		
		return itemsCustom;
	
	}
	
}


