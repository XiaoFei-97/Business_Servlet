package com.jzfblog.store.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {
	
	/**
	 * 解析xml
	 * @param name
	 * @return
	 * @throws DocumentException 
	 */
	public static Object createObject(String name) {
		
		try {
			// 通过传递过来的name获取application.xml中name对应的class值 
			
			// 获取到document对象
			SAXReader saxReader = new SAXReader();
			// 获取xml文件的输入流（application.xml）必须位于src下
			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
			Document read = saxReader.read(is);
			// 通过document对象获取根节点 beans
			Element rootElement = read.getRootElement();
			// 通过根节点获取到根节点下所有的子节点 bean，返回集合
			List<Element> elements = rootElement.elements();
			// 遍历集合，判断每个元素上的id值是否和当前的name一致 
			for (Element element : elements) {
				// 获取到节点的id属性值
				String id = element.attributeValue("id");
				// 如果一致 获取到当前元素上class的属性值
				if(id.equals(name)) {
					String str = element.attributeValue("class");
					// 获取字节码对象
					Class clazz = Class.forName(str);
					// 通过反射创建对象，并且返回
					return clazz.newInstance();
				}
			}

			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
