package com.yueny.demo.dubbo.config.schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * LockBeanDefinitionParser
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月31日 下午6:40:11
 *
 */
public class CodealyBeanDefinitionParser implements BeanDefinitionParser {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CodealyBeanDefinitionParser.class);

	private static BeanDefinition parse(final Element element, final ParserContext parserContext,
			final Class<?> beanClass) {
		final RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClass(beanClass);
		beanDefinition.setLazyInit(false);

		// element.getAttribute就是用配置中取得属性值
		final String id = element.getAttribute("id");
		if (id != null && id.length() > 0) {
			if (parserContext.getRegistry().containsBeanDefinition(id)) {
				throw new IllegalStateException("Duplicate spring bean id " + id);
			}
			parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
			beanDefinition.getPropertyValues().addPropertyValue("id", id);
		}

		// 处理其他节点的配置信息
		parseProperties(element.getAttributes(), beanDefinition);

		return beanDefinition;
	}

	private static void parseProperties(final NamedNodeMap nodeList, final RootBeanDefinition beanDefinition) {
		if (nodeList == null || nodeList.getLength() <= 0) {
			return;
		}

		// contains 'name' 'key' 'group'
		for (int i = 0; i < nodeList.getLength(); i++) {
			final Node node = nodeList.item(i);
			if (node instanceof Element) {
				final String name = ((Element) node).getAttribute("name");
				if (name != null && name.length() > 0) {
					final String value = ((Element) node).getAttribute("value");

					if (StringUtils.hasText(value)) {
						beanDefinition.getPropertyValues().addPropertyValue(name, value);
					} else {
						throw new UnsupportedOperationException("Unsupported <property name=\"" + name
								+ "\"> sub tag, Only supported <property name=\"" + name
								+ "\" ref=\"...\" /> or <property name=\"" + name + "\" value=\"...\" />");
					}
					beanDefinition.getPropertyValues().addPropertyValue(name, value);
				}
			} else {
				final String name = node.getNodeName();
				if (name != null && name.length() > 0) {
					final String value = node.getNodeValue();
					if (StringUtils.hasText(value)) {
						beanDefinition.getPropertyValues().addPropertyValue(name, value);
					} else {
						throw new UnsupportedOperationException("Unsupported <property name=\"" + name
								+ "\"> sub tag, Only supported <property name=\"" + name
								+ "\" ref=\"...\" /> or <property name=\"" + name + "\" value=\"...\" />");
					}
					beanDefinition.getPropertyValues().addPropertyValue(name, value);
				}
			}
		}
	}

	private final Class<?> beanClass;

	public CodealyBeanDefinitionParser(final Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	@Override
	public BeanDefinition parse(final Element element, final ParserContext parserContext) {
		return parse(element, parserContext, beanClass);
	}

}
