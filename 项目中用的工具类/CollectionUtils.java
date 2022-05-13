package com.yhl.ros.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @ClassName: CollectionUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:06
 */
public class CollectionUtils<T> {

	/**
	 * 初始化ArrayList
	 * 
	 * @param elements
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> createArrayList(T... elements) {
		List<T> list = new ArrayList<T>();
		for (T element : elements) {
			list.add(element);
		}
		return list;
	}

	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	public static boolean isNotEmpty(Collection<?> collection) {
		return (collection != null && !collection.isEmpty());
	}

	/**
	 * 获取两个集合的差集
	 * 
	 * @param big
	 *            大集合
	 * @param small
	 *            小集合
	 * @return 两个集合的差集
	 */
	public static <T> Collection<T> getDiffSection(Collection<T> big, Collection<T> small) {
		Set<T> differenceSet = Sets.difference(Sets.newHashSet(big), Sets.newHashSet(small));
		return Lists.newArrayList(differenceSet);
	}

	public static <T> List<T> getDiffSection(Collection<T> big, T obj) {
		Set<T> small = new HashSet<T>();
		small.add(obj);
		Set<T> differenceSet = Sets.difference(Sets.newHashSet(big), small);
		return Lists.newArrayList(differenceSet);
	}

	/**
	 * 获取两个集合的交集
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static <T> List<T> getInterSection(Collection<T> c1, Collection<T> c2) {
		Set<T> intersections = Sets.intersection(Sets.newHashSet(c1), Sets.newHashSet(c2));
		return Lists.newArrayList(intersections);
	}

	/**
	 * 获取两个集合的合集
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static <T> List<T> getUnionSection(Collection<T> c1, Collection<T> c2) {
		c1.addAll(c2);
		Set<T> newHashSet = Sets.newHashSet(c1);
		return Lists.newArrayList(newHashSet);
	}

	public static <T> List<List<T>> splitList(List<T> list, int pageSize) {

		int listSize = list.size(); // list的大小
		int page = (listSize + (pageSize - 1)) / pageSize; // 页数

		List<List<T>> listArray = new ArrayList<List<T>>(); // 创建list数组 ,用来保存分割后的list
		for (int i = 0; i < page; i++) { // 按照数组大小遍历
			List<T> subList = new ArrayList<T>(); // 数组每一位放入一个分割后的list
			for (int j = 0; j < listSize; j++) { // 遍历待分割的list
				int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize; // 当前记录的页码(第几页)
				if (pageIndex == (i + 1)) { // 当前记录的页码等于要放入的页码时
					subList.add(list.get(j)); // 放入list中的元素到分割后的list(subList)
				}

				if ((j + 1) == ((j + 1) * pageSize)) { // 当放满一页时退出当前循环
					break;
				}
			}
			listArray.add(subList); // 将分割后的list放入对应的数组的位中
		}
		return listArray;
	}

	public static <T> boolean isListEqual(Collection<T> l0, Collection<T> l1) {
		if (l0 == l1) {
			return true;
		}
		if (l0 == null && l1 == null) {
			return true;
		}
		if (l0 == null || l1 == null) {
			return false;
		}
		if (l0.size() != l1.size()) {
			return false;
		}
		for (Object o : l0) {
			if (!l1.contains(o)) {
				return false;
			}
		}
		for (Object o : l1) {
			if (!l0.contains(o)) {
				return false;
			}
		}
		return true;
	}

}
