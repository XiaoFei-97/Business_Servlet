package com.jzfblog.store.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车抽取类
 * @author 蒋振飞
 *
 */
public class Cart {
	// 总计/积分
	private double total;
	// 个数不确定的购物项
	private List<CartItem> list = new ArrayList<CartItem>();
	
	// 添加购物项到购物车
	public void addCartItemToCart(CartItem cartItem) {
		
		// 设置变量，默认为false，没有买过此商品
		boolean flag = false;
		
		// 存储原先的购物项
		CartItem old = null;
		
		// 在将当前购物项加入购物车之前，首先需要判断之前是否买过这类商品
		for(CartItem cartItem2: list) {
			if(cartItem.equals(cartItem2.getProduct().getPid())){
				flag = true;
				old = cartItem2;
			}
		}
		if(flag == false) {
			// 没有买过，直接list.add(CartItem cartItem)
			list.add(cartItem);
		}else {
			// 买过，获取原来的数量和本次数量，相加之后s设置到原购物项上
			old.setNum(old.getNum() + cartItem.getNum());
		}
	}
	// 移除购物项
	public void removeCartItem(String pid) {
		for (CartItem cartItem : list) {
			// 判断购物项中是否有项目的pid与此相同
			if(pid.equals(cartItem.getProduct().getPid())) {
				// 删除当前cartItem
				// 直接调用list.remove(cartItem)无法删除cartItem，需要使用迭代器删除
			}
		}
	}
	// 清空购物车
	public void clearCart() {
		list.clear();
	}
}
