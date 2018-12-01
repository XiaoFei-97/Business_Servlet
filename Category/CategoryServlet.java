package com.jzfblog.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.store.domain.Category;
import com.jzfblog.store.service.CategoryService;
import com.jzfblog.store.service.impl.CategoryServiceImpl;
import com.jzfblog.store.utils.JedisUtils;
import com.jzfblog.store.web.base.BaseServlet;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;


public class CategoryServlet extends BaseServlet {
	
	// 方案2：使用ajax加载，省去每个页面的servlet都要添加查询到吗
	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 从redis中读出全部分类信息，加快访问速度
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		if(null == jsonStr || "".equals(jsonStr)) {
			// 获取全部分类信息，返回集合
			CategoryService CategoryService = new CategoryServiceImpl();
			List<Category> list = null;
			try {
				list = CategoryService.getAllCats();
				// 将分类转换成json格式数据
				jsonStr = JSONArray.fromObject(list).toString();
				jedis.set("allCats", jsonStr);
				// response.setContentType("text/html;charset=utf-8");
				// response.getWriter().write(jsonStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(jsonStr);
		JedisUtils.closeJedis(jedis);
		// 转发到真实的首页
		return null;
	}

}
