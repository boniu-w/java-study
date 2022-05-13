package com.yhl.ros.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ExcelLabel
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelLabel {
	private String labelName;

	// 第几行
	private int row = 1;

	// 占几行
	private int rowspan = 1;

	// 第几列
	private int colum;

	// 占几列
	private int colspan = 1;

	public ExcelLabel(String labelName, int colum) {
		this.labelName = labelName;
		this.colum = colum;
	}

}
