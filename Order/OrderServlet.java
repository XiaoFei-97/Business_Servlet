package com.jzfblog.store.web.servlet;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.store.domain.Cart2;
import com.jzfblog.store.domain.CartItem;
import com.jzfblog.store.domain.Order;
import com.jzfblog.store.domain.OrderItem;
import com.jzfblog.store.domain.PageModel;
import com.jzfblog.store.domain.User;
import com.jzfblog.store.service.OrderService;
import com.jzfblog.store.service.impl.OrderServiceImpl;
import com.jzfblog.store.utils.PaymentUtil;
import com.jzfblog.store.utils.UUIDUtils;
import com.jzfblog.store.web.base.BaseServlet;

public class OrderServlet extends BaseServlet {
	
	/**
	 * 将购物车信息以订单的形式保存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 确认用户登录状态
		User user = (User) request.getSession().getAttribute("loginUser");
		if(null == user) {
			request.setAttribute("msg", "请登录之后再下单");
			return "/jsp/info.jsp";
		}
		// 获取购物车
		Cart2 cart = (Cart2) request.getSession().getAttribute("cart");
		// 创建订单对象，为订单赋值
		Order order = new Order();
		order.setOid(UUIDUtils.getCode());
		order.setOrdertime(new Date());
		order.setTotal(cart.getTotal());
		order.setState(1);
		order.setUser(user);
		// 遍历购物项的同时，创建好订单项
		for (CartItem item: cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getCode());
			orderItem.setQuantity(item.getNum());
			System.out.println(item);
			orderItem.setTotal(item.getSubTotal());
			
			orderItem.setProduct(item.getProduct());
			orderItem.setOrder(order);
			
			// 将订单项加到订单集合里
			order.getList().add(orderItem);
		}
		// 调用业务层：保存订单项
		OrderService OrderService = new OrderServiceImpl();
		OrderService.saveOrder(order);
		// 清空购物车
		cart.clearCart();
		// 将订单放入request
		request.setAttribute("order", order);
		// 转发到/jsp/order_info.jsp
		return "/jsp/order_info.jsp";
	}
	
	public String findMyOrderWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 获取用户信息
		User user = (User) request.getSession().getAttribute("loginUser");
		// 获取当前页
		int currentPage = Integer.parseInt(request.getParameter("num"));
		// 调用业务层功能：查询当前用户订单信息，返回PageModel
		OrderService OrderService = new OrderServiceImpl();
		PageModel pageModel = OrderService.findMyOrderWithPage(user, currentPage);
		// 将PageModel放入request
		request.setAttribute("page", pageModel);
		// 转发到/jsp/order_list.jsp
		return "/jsp/order_list.jsp";
		
	}
	
	/**
	 * 查询订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 获取订单oid
		String oid = request.getParameter("oid");
		// 调用业务层功能：根据订单编号查找订单信息
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		// 将order放入request中
		request.setAttribute("order", order);
		
		// 转发到/jsp/order_info.jsp
		return "jsp/order_info.jsp";
	}
	
	/**
	 * 支付功能
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String payOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 获取订单的oid，收货人地址，姓名，电话，银行
		String oid = request.getParameter("oid");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String pd_FrpId = request.getParameter("pd_FrpId");
		// 更新订单上收货人的地址，姓名，电话
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		order.setName(name);
		order.setTelephone(telephone);
		order.setAddress(address);
		orderService.updateOrder(order);
		// 向易宝支付发送参数
		String p0_Cmd = "Buy";
		//商户编号
		String p1_MerId = "10001126856";
		//订单编号
		String p2_Order = oid;
		//金额
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		//接受响应参数的Servlet
		String p8_Url = "http://localhost:8080/store/OrderServlet?method=callBack";
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		//公司的秘钥
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
			
		//调用易宝的加密算法,对所有数据进行加密,返回电子签名
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
				
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);

		System.out.println(sb.toString());
		// 使用重定向：
		response.sendRedirect(sb.toString());

		//response.sendRedirect("https://www.yeepay.com/app-merchant-proxy/node?p0_cmd=Buy&p1_MerId=111111&k1=v1&k2=v2");
		return null;
	}
	
	public String callBack(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 接收易宝支付数据
		// System.out.println("==============================================");
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");

		// hmac
		String hmac = request.getParameter("hmac");
		// 利用本地密钥和加密算法 加密数据
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				
				// 如果支付成功，更新订单状态
				OrderService orderService = new OrderServiceImpl();
				Order order = orderService.findOrderByOid(r6_Order);
				order.setState(2);
				orderService.updateOrder(order);
				// 向request放入提示信息
				request.setAttribute("msg", "支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);
			
			} else if (r9_BType.equals("2")) {
				// 修改订单状态:
				// 服务器点对点，来自于易宝的通知
				System.out.println("收到易宝通知，修改订单状态！");//
				// 回复给易宝success，如果不回复，易宝会一直通知
				response.getWriter().print("success");
			}

		} else {
			throw new RuntimeException("数据被篡改！");
		}
		
		return null;
	}
}
