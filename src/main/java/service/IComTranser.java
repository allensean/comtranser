package service;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface IComTranser<E,T,K,V> {
	
	/**
	 * compare two list ,return result. 比较两个list，并返回结果
	 * @param sources 数据源
	 * @param targets 目标源
	 * @param targetClassName 目标Java类名
	 * @param fieldMap 字段对应关系
	 * @param compareMap 要比较的字段键值对
	 * @return return array[List<T>newAdds,List<T>updates，List<T>deletes]. 返回数组[List<T>新增，List<T>修改，List<T>删除]
	 */
	List[] compareAndTransfer(List<E> sources, List<T> targets, String targetClassName, Map<K, V> fieldMap,
			Map<K, V> compareMap);
	
	/**
	 * compare two list ,return result. 比较两个list，并返回结果
	 * @param sources 数据源
	 * @param targets 目标源
	 * @param targetClassName 目标Java类名
	 * @param fieldMap 字段对应关系
	 * @param compareMap 要比较的字段键值对
	 * @param valueFrom targetClassName or sourceClassName, default sources,填写targetClassName or sourceClassName,默认从sources取值
	 * @return return array[List<T>newAdds,List<T>updates，List<T>deletes]. 返回数组[List<T>新增，List<T>修改，List<T>删除]
	 */
	List[] compareAndTransfer(List<E> sources, List<T> targets, String targetClassName, Map<K, V> fieldMap,
			Map<K, V> compareMap,String valueFrom);
	
	
	/**
	 * according fieldMap key/value pair  to transform list<T> to entity
	 * 按fieldMap转换list到新实体类，并返新的List<新实体类>
	 * @param sources 数据源List<E>
	 * @param targetClassName 新实体类名
	 * @param fieldMap 字段对应关系
	 * @param compareMap 要比较的字段键值对
	 * @return
	 */
	List compareAndTransfer(List<E> sources, String targetClassName, Map<K, V> fieldMap,
			Map<K, V> compareMap);
}
