package service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import service.IComTranser;


/**
 * @author Allen Sean
 * @version 1.0
 * @param <K>
 * @param <V>
 * @param <T>
 * @date 2020/6/15 17:01
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ListTranser implements IComTranser {

	@Override
	public List[] compareAndTransfer(List sources, List targets, String targetClassName, Map fieldMap, Map compareMap,
			String valueFrom) {
		List[] results = new List[3];
		List newAdds = new ArrayList<>();
		List updates = new ArrayList<>();
		List deletes = new ArrayList<>();
		if (sources == null || sources.size() == 0) {
			return results;
		}
		if (targets == null || targets.size() == 0) {
			results[0] = compareAndTransfer(sources, targetClassName, fieldMap, compareMap);
			return results;
		}
		Set<Object> diffSet = new HashSet<>();
		Set<Object> sameSet = new HashSet<>();
		Set<Object> delSet = new HashSet<>();

		String sourceKey = null;
		String targetKey = null;
		Iterator<Entry<Object, Object>> entries = compareMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<Object, Object> entry = entries.next();
			sourceKey = (String) entry.getKey();
			targetKey = (String) entry.getValue();
		}
		try {
			String sourceClsName = sources.get(0).getClass().getName();
			Class<?> sourceClass = Class.forName(sourceClsName);

			String targetClsName = targets.get(0).getClass().getName();
			Class<?> targetClass = Class.forName(targetClsName);

			for (int s = 0; s < sources.size(); s++) {
				Object sourceObj = sources.get(s);
				// 获取sources的get值
				Method sgetMethod = sourceClass.getMethod("get" + captureName(sourceKey));
				Object sourceValue = sgetMethod.invoke(sourceObj);

				for (int t = 0; t < targets.size(); t++) {
					Object targetObj = targets.get(t);
					// 获取targets的get值
					Method tgetMethod = targetClass.getMethod("get" + captureName(targetKey));
					Object targetValue = tgetMethod.invoke(targetObj);

					if (sourceValue != null && sourceValue.equals(targetValue)) {
						sameSet.add(sourceValue);

						if (diffSet.contains(targetValue)) {
							diffSet.remove(targetValue);
						}
						if (delSet.contains(targetValue)) {
							delSet.remove(targetValue);
						}
						// 修改的
						//保留目标值
						if(targetClassName.equals(valueFrom)) {
							Object newTargetObj = targetClass.newInstance();
							newTargetObj = trans(fieldMap, targetClass, targetObj, newTargetObj);
							updates.add(newTargetObj);
						}else {
							Object newTargetObj = targetClass.newInstance();
							newTargetObj = trans(fieldMap, sourceClass, targetClass, sourceObj, newTargetObj);
							updates.add(newTargetObj);
						}
						

					} else {
						// sourceValue不在相同的set里
						if (!sameSet.contains(sourceValue)) {
							diffSet.add(sourceValue);
						}
						// targetValue也不在差异的set里,
						if (!sameSet.contains(targetValue) && !diffSet.contains(targetValue)) {
							// 说明源表不存在，目标表还存在，要存在delSet里
							delSet.add(targetValue);
						}

					}

				} // end targets cycle

				// 新增的 /newAdd
				if (diffSet.size() > 0 && !sameSet.contains(sourceValue)) {
					// 实例化新对象
					Object newTargetObj = targetClass.newInstance();
					newTargetObj = trans(fieldMap, sourceClass, targetClass, sourceObj, newTargetObj);
					newAdds.add(newTargetObj);
				}

			} // end source cycle

			for (int t = 0; t < targets.size(); t++) {
				Object targetObj = targets.get(t);
				// 获取targets的get值
				Method tgetMethod = targetClass.getMethod("get" + captureName(targetKey));
				Object targetValue = tgetMethod.invoke(targetObj);
				if (delSet.contains(targetValue)) {
					// 实例化新对象
					Object newTargetObj = targetClass.newInstance();
					newTargetObj = trans(fieldMap, targetClass, targetObj, newTargetObj);
					deletes.add(newTargetObj);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		results[0] = newAdds;
		results[1] = updates;
		results[2] = deletes;

		return results;
	}
	
	@Override
	public List[] compareAndTransfer(List sources, List targets, String targetClassName, Map fieldMap, Map compareMap) {
		List[] results = new List[3];
		if (sources == null || sources.size() == 0) {
			return results;
		}
		String sourceClsName = sources.get(0).getClass().getName();
		return compareAndTransfer(sources,targets,targetClassName,fieldMap,compareMap,sourceClsName);
	}

	@Override
	public List compareAndTransfer(List sources, String targetClassName, Map fieldMap, Map compareMap) {
		List results = new ArrayList<>();
		for (int i = 0; i < sources.size(); i++) {
			try {
				String sourceClsName = sources.get(i).getClass().getName();
				Class<?> sourceClass = Class.forName(sourceClsName);
				Object sourceObj = sources.get(i);
				Class<?> targetClass = Class.forName(targetClassName);
				Object targetObj = targetClass.newInstance();
				targetObj = trans(fieldMap, sourceClass, targetClass, sourceObj, targetObj);
				results.add(targetObj);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} // end cycle
		return results;
	}

	// 数据获取转化Object
	private Object trans(Map<Object, Object> fieldMap, Class<?> sourceClass, Class<?> targetClass, Object sourceObj,
			Object newTargetObj) {

		try {
			// 第一种ma循环方式，下面有第二种方式
			for (Map.Entry<Object, Object> entry : fieldMap.entrySet()) {
				String mapKey = (String) entry.getKey();
				String mapValue = (String) entry.getValue();
				// 获取get值
				PropertyDescriptor sourceDescriptor = new PropertyDescriptor(mapKey, sourceClass);
				Method getMethod = sourceDescriptor.getReadMethod();
				Object value = getMethod.invoke(sourceObj);
				// set值
				PropertyDescriptor targetDescriptor = new PropertyDescriptor(mapValue, targetClass);
				Method setMethod = targetDescriptor.getWriteMethod();
				setMethod.invoke(newTargetObj, value);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return newTargetObj;

	}

	private Object trans(Map<Object, Object> fieldMap, Class<?> clazz, Object obj, Object newObj) {

		try {
			// 第二种ma循环方式
			Iterator<Entry<Object, Object>> entries = fieldMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<Object, Object> entry = entries.next();
				String mapKey = (String) entry.getKey();
				String mapValue = (String) entry.getValue();
				// 获取get值
				PropertyDescriptor sourceDescriptor = new PropertyDescriptor(mapKey, clazz);
				Method getMethod = sourceDescriptor.getReadMethod();
				Object value = getMethod.invoke(obj);
				// set值
				PropertyDescriptor targetDescriptor = new PropertyDescriptor(mapValue, clazz);
				Method setMethod = targetDescriptor.getWriteMethod();
				setMethod.invoke(newObj, value);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return newObj;

	}

	// 首字母大写
	private String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;// 进行字母的ascii编码前移
		return String.valueOf(cs);
	}

	

}
