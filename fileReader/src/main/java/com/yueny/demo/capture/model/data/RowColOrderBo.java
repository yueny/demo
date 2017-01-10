package com.yueny.demo.capture.model.data;

import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 带有排序的行中列数据的字段， 权重越高排序越靠前
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月10日 上午10:12:19
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RowColOrderBo implements Comparator<RowColOrderBo> {
	/** 属性名 */
	private String fieldName;
	/** 列顺序， 从1开始 */
	private Integer orderNo;
	/** 排序的表头名 */
	private String titleName;

	@Override
	public int compare(final RowColOrderBo o1, final RowColOrderBo o2) {
		return o1.getOrderNo().compareTo(o2.getOrderNo());
	}
}
