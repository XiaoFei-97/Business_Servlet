package com.jzfblog.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.store.domain.Cart2;
import com.jzfblog.store.domain.CartItem;
import com.jzfblog.store.domain.Product;
import com.jzfblog.store.service.ProductService;
import com.jzfblog.store.service.impl.ProductServiceImpl;
import com.jzfblog.store.web.base.BaseServlet;


public class CartServlet extends BaseServlet {

	// 添加购物项到购物车
	public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		
		// 从session中获取购物车
		Cart2 cart = (Cart2) request.getSession().getAttribute("cart");
		
		if(null == cart) {
			// 如果获取不到，创建购物车对象，放在session中
			cart = new Cart2();
		}
		// 获取商品的id，数量
		String pid = request.getParameter("pid");
		int num = Integer.parseInt(request.getParameter("quantity"));
		
		// 通过商品id查询商品对象
		ProductService ProductService = new ProductServiceImpl();
		try {
			Product product = ProductService.findProductByPid(pid);
			// 获取到待购买的购物项
			CartItem cartItem = new CartItem();
			cartItem.setNum(num);
			cartItem.setProduct(product);
			
			// 调用购物车上的方法
			cart.addCartItemToCart(cartItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 注意：此处只可使用重定向，不能使用转发，转发会导致数据重复提交
		request.getSession().setAttribute("cart", cart);
		response.sendRedirect("/store/jsp/cart.jsp");
		return null;
	}
	public String removeCartItem(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 获取商品pid值
		String pid = request.getParameter("pid");
		// 获取session中的cart
		Cart2 cart = (Cart2) request.getSession().getAttribute("cart");
		cart.removeCartItem(pid);
		response.sendRedirect("/store/jsp/cart.jsp");
		return null;
	}
	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Cart2 cart = (Cart2) request.getSession().getAttribute("cart");
		cart.clearCart();
		response.sendRedirect("/store/jsp/cart.jsp");
		return null;
	}

}
