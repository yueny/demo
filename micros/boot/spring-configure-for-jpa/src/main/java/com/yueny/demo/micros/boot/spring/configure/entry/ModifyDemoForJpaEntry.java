package com.yueny.demo.micros.boot.spring.configure.entry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:57:11
 *
 */
@Entity
@Data
@NoArgsConstructor(force = true)
// @RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "MODIFY_DEMO") // 关联数据库表名
public class ModifyDemoForJpaEntry extends BaseForJpaEntry {
	/** 描述 */
	private String desc;
	/** 事件数据 */
	private String eventData;
	/** 主键 */
	@Id // 定义一条记录的唯一标识
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自动生成
	private Long id;
	/** 名称 */
	private String name;
	/** 类型 */
	private String type;

}
