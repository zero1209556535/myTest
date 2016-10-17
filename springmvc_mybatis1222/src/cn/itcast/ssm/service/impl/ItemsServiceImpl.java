package cn.itcast.ssm.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.mapper.ItemsMapper;
import cn.itcast.ssm.mapper.ItemsMapperCustom;
import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsExample;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;

/**
 * 
 * <p>ClassName: ItemsServiceImpl </p> 
 * <p>Description: 商品管理 </p>
 * @author zzl
 * @date 2016年8月8日 上午10:19:54
 * @since JDK 1.6 
 * @version v1.0
 */
public class ItemsServiceImpl implements ItemsService{

	//这个注解表示自动注入
	//也就是所谓的依赖注入
	//这里定义的itemsMapperCustom对象必须这么写，类名的首字母小写。
	//因为Mapper扫描器扫描之后自动生成的Mapper代理对象的id的默认生成规则就是类名的首字母小写。
	@Autowired
	private ItemsMapperCustom itemsMapperCustom;
	
	@Autowired
	private ItemsMapper itemsMapper;
	
	//Vo直接从Service传到了Dao
	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)
			throws Exception {
		
		//根据ItemsMapperCustom来查询数据库
		return itemsMapperCustom.findItemsList(itemsQueryVo);
		
	}

	// 在service接口的方法上面定义throws Exception的目的是，   
	// 如果方法内部抛出了异常它不管，而是向上层抛出，这里的上层就是controller层（谁调用它，谁就是它的上层）
	// 抛给了controller层，controller层会接着抛给前端控制器，前端控制器找全局异常处理器进行异常处理！
	//（也就是说我们以前在方法上面抛出异常，而不是处理异常原来是这么一回事，往上一级抛，上级去解决，如果上级不解决那么再往它的上级抛出异常，异常总会被处理的。） 
	@Override
	public ItemsCustom findItemsById(Integer id) throws Exception {
		
		Items items = itemsMapper.selectByPrimaryKey(id);
		
		//如果是与业务功能相关的异常，建议在service接口中抛出（实现类方法里）
		//判断商品信息是否为空，根据id查询商品信息，如果没有找到，那么就抛出异常，提示用户商品信息不存在！
		if(items==null){
			//方法上的throws Exception表示service层会把异常向上抛给controller层
			//controller会将异常抛给前端控制器，前端控制器调用全局异常处理器进行异常处理，
			//如果我们没有配置自定义的全局异常处理器，那么就会使用springmvc默认提供的全局异常处理器。
			throw new CustomException("您所要修改的商品信息不存在！");
		}
		
		//中间对商品信息进行一系列的处理
		//...比如根据生产日期来判断商品是否已过期
		//返回ItemsCustom
		ItemsCustom itemsCustom = null;
		//将items的属性值拷贝到itemsCustom中去
		//在进行拷贝之前，我们要判断被拷贝的源是否为空，不为空才进行属性值的拷贝
		if(items!=null){
			itemsCustom = new ItemsCustom();
			BeanUtils.copyProperties(items, itemsCustom);
		}
		
		return itemsCustom;
	}

	@Override
	public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {
		//字段id是主键要求非空是对于表来说的，而我们传入的itemsCustom参数是一个类的对象，是可以没有设置id值的！
		//添加业务校验，通常我们在Service接口中对关键参数进行校验，比如参数是否非空校验
		//校验参数id是否为空，如果为空则抛出异常。
		
		//更新商品信息使用updateByPrimaryKeyWithBLOBs根据id更新items表中的所有字段，包括大文本类型的字段      
		//使用updateByPrimaryKeyWithBLOBs要求必须传入id参数
		itemsCustom.setId(id);//哪怕这是一个重复的操作，因为很有可能itemsCustom中id是存在的。
		
		//使用这个方法进行更新不太好，因为有的字段不为空，但是没有传过来，那么也被重置为null了。
		//itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
		
		//有选择性的进行更新，传入的字段值不为空，才进行更新，否则该字段不更新！
		itemsMapper.updateByPrimaryKeySelective(itemsCustom);
	}

	@Override
	public void insertItems(ItemsCustom itemsCustom) throws Exception {
		//将新增的商品信息插入到数据库
		itemsMapper.insert(itemsCustom);
		
	}

	@Override
	public void deleteItems(Integer id) throws Exception {
		//将指定id的商品删除
		itemsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteItemsPLByArray(Integer[] ids) throws Exception {
		//根据数组ids(多个商品id)批量删除商品信息
		
		//java数组转换成为List。
		//调用Arrays的asList方法.
		/*String[] arr = new String[] {"1", "2"};
		List<String> list = Arrays.asList(arr);
		
		Integer[] idss = new Integer[3];
		idss[0] = 1;
		idss[1] = 2;
		idss[2] = 3;
		List<Integer> idssList = Arrays.asList(idss);*/
		
		//List转换成为java数组。
		/*List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(1, 2);
		Integer idsss[] = new Integer[list.size()];
		list.toArray(idsss);*/
		
		List<Integer> idList = Arrays.asList(ids);
		ItemsExample itemsExample = new ItemsExample();
		ItemsExample.Criteria criteria = itemsExample.createCriteria();
		criteria.andIdIn(idList);
		
		//根据ids数组批量删除商品信息
		itemsMapper.deleteByExample(itemsExample);
	}
	
	@Override
	public void deleteItemsPLByList(List<Integer> idList) throws Exception {
		
		ItemsExample itemsExample = new ItemsExample();
		ItemsExample.Criteria criteria = itemsExample.createCriteria();
		criteria.andIdIn(idList);
		
		//根据id的List集合批量删除商品信息
		itemsMapper.deleteByExample(itemsExample);
	}

	@Override
	public void updateItemsPL(ItemsCustom itemsCustom) throws Exception {
		
		//根据主键id进行批量更新（id必须有值），非空字段不更新。
		itemsMapper.updateByPrimaryKeySelective(itemsCustom);
		
	}
	
	
}
