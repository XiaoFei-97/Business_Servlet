package com.jzfblog.store.domain;

/**
 * 购物车存储的项目
 * @author 蒋振飞
 *
 */
public class CartItem {
	
	private Product product; // 目的：携带购物商品（图片路径，商品名称，商品价格）
	private int num; // 当前类别商品数量
	private double subTotal; // 小计
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	// 小计可经过计算获取
	public double getSubTotal() {
		return product.getShop_price()*num;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
}
