package com.yueny.demo.capture.model.data;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 导入的行数据
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月9日 下午1:22:24
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImportLineDataBo {
	/**
	 * 数据行<br>
	 */
	private List<String> dataList;

}
