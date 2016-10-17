package cn.itcast.ssm.po;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>ClassName: ItemsQueryVo </p> 
 * <p>Description: 商品包装类，可以从表现层一直传到持久层 </p>
 * @author zzl
 * @date 2016年8月7日 下午5:53:09
 * @since JDK 1.6 
 * @version v1.0
 */
//尽量不要使用继承，而是要使用组合类型，因为继承不好扩展。
public class ItemsQueryVo {

	//商品信息
	private Items items;
	
	//为了系统的可扩展性，对原始生成的po进行扩展。
	private ItemsCustom itemsCustom;
	
	//为了系统的可扩展性，对原始生成的po进行扩展。
	//private UserCustom userCustom;
	
	//为了系统的可扩展性，对原始生成的po进行扩展。
	//private OrdersCustom OrdersCustom;
	
	//批量商品信息，在pojo的包装类中定义List<pojo>的目的是可以获取前台页面传过来的批量数据。
	private List<ItemsCustom> itemsCustomList;
	
	//为了测试Map集合的参数绑定造的属性
	private Map<String, Object> itemsMap = new HashMap<String, Object>();

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public ItemsCustom getItemsCustom() {
		return itemsCustom;
	}

	public void setItemsCustom(ItemsCustom itemsCustom) {
		this.itemsCustom = itemsCustom;
	}

	public List<ItemsCustom> getItemsCustomList() {
		return itemsCustomList;
	}

	public void setItemsCustomList(List<ItemsCustom> itemsCustomList) {
		this.itemsCustomList = itemsCustomList;
	}

	public Map<String, Object> getItemsMap() {
		return itemsMap;
	}

	public void setItemsMap(Map<String, Object> itemsMap) {
		this.itemsMap = itemsMap;
	}
	
}
