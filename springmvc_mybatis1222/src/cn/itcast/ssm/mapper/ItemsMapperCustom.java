package cn.itcast.ssm.mapper;

import java.util.List;

import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;

/**
 * 
 * <p>ClassName: ItemsMapperCustom </p> 
 * <p>Description: 商品综合查询映射接口 </p>
 * @author zzl
 * @date 2016年8月7日 下午9:54:51
 * @since JDK 1.6 
 * @version v1.0
 */
public interface ItemsMapperCustom {
	
	//public abstract 可以省略不写，因为默认就是由public abstract 来修饰的。
	//参数是包装对象
	//返回值是扩展对象
	//这里为什么要在方法上面抛异常呢？异常处理
	//商品查询列表
	public abstract List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)throws Exception;
	
}
