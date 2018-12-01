package com.jzfblog.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用map存储
 * @author 蒋振飞
 *
 */
public class Cart2 {
	
	// 总计/积分
	private double total;
	// 个数不确定的购物项
	Map<String, CartItem> map = new HashMap<String, CartItem>();
	
	// 添加购物项到购物车上
	public void addCartItemToCart(CartItem cartItem) {
		// 获取正在向购物车添加的商品pid
		String pid = cartItem.getProduct().getPid();
		if(map.containsKey(pid)) {
			CartItem oldItem = map.get(pid);
			oldItem.setNum(oldItem.getNum() + cartItem.getNum());
		}else {
			map.put(pid, cartItem);
		}
	}
	
	// 移出购物项
	public void removeCartItem(String pid) {
		map.remove(pid);
	}
	// 清空购物车
	public void clearCart() {
		map.clear();
	}

	// 计算总计
	public double getTotal() {
		// 要注意这里，每次获取total值时，都要先赋值为0
		total = 0;
		// 获取到Map中所有的购物项
		Collection<CartItem> values = map.values();
		for (CartItem cartItem : values) {
			total += cartItem.getSubTotal();
		}
		return total;
	}
	
	// 返回map中所有的值
	public Collection<CartItem> getCartItems() {
		return map.values();
	}
	
	public void setTotal(double total) {
		
	}

	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
}
