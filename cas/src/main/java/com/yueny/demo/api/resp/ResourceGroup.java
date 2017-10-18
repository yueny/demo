package com.yueny.demo.api.resp;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.yueny.demo.api.CollectConst;
import com.yueny.demo.api.ExecutorBean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ResourceGroup {
	/**
	 * 系统标识
	 */
	@Getter
	@Setter
	private String casClientSystem;

	/**
	 * 类实例地址
	 */
	@Getter
	private List<ResourceForExecutorBean> resourcesForSys;

	public ResourceGroup(final Map<String, ExecutorBean<? extends Annotation>> rs) {
		super();

		if (rs != null) {
			// 资源整合
			final List<ResourceForExecutorBean> data = new ArrayList<>();
			for (final Entry<String, ExecutorBean<? extends Annotation>> entry : rs.entrySet()) {
				if (StringUtils.equals(entry.getKey(), CollectConst.CAS_COLLECT_RESOURCE)) {
					continue;
				}

				final ResourceForExecutorBean resourceInfo = new ResourceForExecutorBean();

				// 将 ${} 部分替换为 *
				resourceInfo.setResource(entry.getKey());

				if (entry.getValue().getControl() != null) {
					resourceInfo.setDesc(entry.getValue().getControl().desc());
				} else {
					final StringBuilder sb = new StringBuilder(entry.getValue().getTarget().getClass().getSimpleName());
					sb.append("#");
					sb.append(entry.getValue().getMethod().getName());

					resourceInfo.setDesc(sb.toString());
				}

				data.add(resourceInfo);
			}

			if (data != null && !data.isEmpty()) {
				this.resourcesForSys = data;
			}
		}
	}

}
