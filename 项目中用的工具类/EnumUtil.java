package com.yhl.ros.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.yhl.ros.common.LabelValue;
import com.yhl.ros.common.enums.CommonEnum;

/**
 * @ClassName: EnumUtil
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:09
 */
public class EnumUtil<T> {
	/**
	 * 返回指定编码的'枚举' @param code @return Enum @throws
	 */
	public static <T extends CommonEnum> T getEnumBycode(Class<T> clazz, String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		for (T _enum : clazz.getEnumConstants()) {
			if (code.equals(_enum.getCode())) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 返回指定描述的'枚举' @param desc @return Enum @throws
	 */
	public static <T extends CommonEnum> T getEnumByDesc(Class<T> clazz, String desc) {
		if (StringUtils.isEmpty(desc)) {
			return null;
		}
		for (T _enum : clazz.getEnumConstants()) {
			if (_enum.getDesc().equals(desc)) {
				return _enum;
			}
		}

		return null;
	}

	/**
	 * 根据代码获取描述
	 * 
	 * @param clazz
	 * @param code
	 * @return
	 */
	public static <T extends CommonEnum> String getDescByCode(Class<T> clazz, String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		T t = getEnumBycode(clazz, code);
		if (null != t) {
			return t.getDesc();
		}
		return null;
	}

	/**
	 * 根据描述获取code
	 * 
	 * @param clazz
	 * @param desc
	 * @return
	 */
	public static <T extends CommonEnum> String getCodeByDesc(Class<T> clazz, String desc) {
		if (StringUtils.isEmpty(desc)) {
			return null;
		}
		T t = getEnumByDesc(clazz, desc);
		if (null != t) {
			return t.getCode();
		}
		return null;
	}

	/**
	 * 枚举值转换成 List (主要下拉框使用)
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends CommonEnum> List<LabelValue> getEnumValueList(Class<T> clazz) {
		if (StringUtils.isEmpty(clazz)) {
			return null;
		}
		List<LabelValue> labelValues = new ArrayList<LabelValue>();
		for (T _enum : clazz.getEnumConstants()) {
			LabelValue labelValue = new LabelValue();
			labelValue.setValue(_enum.getCode());
			labelValue.setLabel(_enum.getDesc());
			labelValues.add(labelValue);
		}
		return labelValues;
	}

}
