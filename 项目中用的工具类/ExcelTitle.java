package com.yhl.ros.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @ClassName: ExcelTitle
 * @Package: com.yhl.ros.common.utils
 * @Description: 报文标题
 * @Date: 2020-02-12 01:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelTitle {
	// 标题
	private String title;

	// 行高
	private int height;

	public ExcelTitle(String title) {
		this.title = title;
	}

}
