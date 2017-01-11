package com.yueny.demo.capture.model.data;

import java.util.Comparator;

import com.yueny.demo.capture.enums.ImportDataType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 带有排序的行中列数据的字段， 权重越高排序越靠前
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月10日 上午10:12:19
 *
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RowColOrderBo implements Comparator<RowColOrderBo> {
	/** 导入属性的数据类型Class<?> */
	@Getter
	@Setter
	private ImportDataType dataType;
	/** 属性名 */
	@Getter
	@Setter
	private String fieldName;
	/** 列顺序， 从1开始 */
	@Getter
	@Setter
	private Integer orderNo;
	/** 排序的表头名 */
	@Getter
	@Setter
	private String titleName;

	@Override
	public int compare(final RowColOrderBo o1, final RowColOrderBo o2) {
		return o1.getOrderNo().compareTo(o2.getOrderNo());
	}
}
