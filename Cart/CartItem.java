package com.jzfblog.store.domain;

/**
 * ���ﳵ�洢����Ŀ
 * @author �����
 *
 */
public class CartItem {
	
	private Product product; // Ŀ�ģ�Я��������Ʒ��ͼƬ·������Ʒ���ƣ���Ʒ�۸�
	private int num; // ��ǰ�����Ʒ����
	private double subTotal; // С��
	
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
	// С�ƿɾ��������ȡ
	public double getSubTotal() {
		return product.getShop_price()*num;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
}