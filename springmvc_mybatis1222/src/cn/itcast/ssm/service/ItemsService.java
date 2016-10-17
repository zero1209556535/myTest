package cn.itcast.ssm.service;

import java.util.List;

import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;

/**
 * 
 * <p>ClassName: ItemsService </p> 
 * <p>Description: 商品管理Service接口 </p>
 * @author zzl
 * @date 2016年8月7日 下午10:12:18
 * @since JDK 1.6 
 * @version v1.0
 */
public interface ItemsService {
	
	//商品查询列表
	//和Mapper接口的方法貌似长得一模一样。
	//Service接口的方法也抛了异常，跟dao接口的方法一样，都抛了异常。
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)throws Exception;

	//说明，一般我们所说的接口其实就是指抽象方法，调用接口其实就是调用的方法（已经实现了的方法）。
	
	//根据id查询商品信息的接口
	/**
	 * 说明：我们一般不使用int类型，而是使用它的对象封装类型Integer，方便于我们Service校验该参数传入是否为空
	 * findItemsById : 根据id查询商品信息
	 * @param id 要查询的商品id主键
	 * @return 返回值是商品的扩展类，不建议使用原始类
	 * @throws Exception
	 */
	public ItemsCustom findItemsById(Integer id)throws Exception;
	
	
	
	//修改商品信息的接口
	/**
	 * 说明：我们一般不使用int类型，而是使用它的对象封装类型Integer，方便于我们Service校验该参数传入是否为空
	 * updateItems : 更新商品信息
	 * @param id 要修改的商品id主键
	 * @param itemsCustom 商品扩展类
	 * @throws Exception
	 */
	public void updateItems(Integer id , ItemsCustom itemsCustom)throws Exception;
	
	/**
	 * 新增商品信息
	 * insertItems : TODO
	 * @param itemsCustom
	 * @throws Exception
	 */
	public void insertItems(ItemsCustom itemsCustom)throws Exception;
	
	/**
	 * 删除商品信息
	 * deleteItems : TODO
	 * @param itemsCustom
	 * @throws Exception
	 */
	public void deleteItems(Integer id)throws Exception;
	
	/**
	 * 根据ids数组批量删除商品信息
	 * deleteItems : TODO
	 * @param itemsCustom
	 * @throws Exception
	 */
	public void deleteItemsPLByArray(Integer[] ids)throws Exception;
	
	/**
	 * 根据id的List集合批量删除商品信息
	 * deleteItems : TODO
	 * @param itemsCustom
	 * @throws Exception
	 */
	public void deleteItemsPLByList(List<Integer> idList)throws Exception;
	
	/**
	 * 批量更新商品信息
	 * deleteItems : TODO
	 * @param itemsCustom
	 * @throws Exception
	 */
	public void updateItemsPL(ItemsCustom itemsCustom)throws Exception;
	
}
