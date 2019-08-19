package com.bd.post.util;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"unchecked","unsafe"})
public class CopyUtils {
	private static final Map<String, BeanCopier> beanCopierCache = new ConcurrentHashMap<>();

	private static final Map<String, ConstructorAccess> constructorAccessCache = new ConcurrentHashMap<>();
	private static final Map<String, MethodAccess> methodAccessCache = new ConcurrentHashMap<>();

	private static void copyProperties(Object source, Object target) {
		BeanCopier copier = getBeanCopier(source.getClass(), target.getClass(), false);
		copier.copy(source, target, null);
	}

	private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass, boolean userConverter) {
		String beanKey = generateKey(sourceClass, targetClass);
		BeanCopier copier = null;
		if (!beanCopierCache.containsKey(beanKey)) {
			copier = BeanCopier.create(sourceClass, targetClass, userConverter);
			beanCopierCache.put(beanKey, copier);
		} else {
			copier = beanCopierCache.get(beanKey);
		}
		return copier;
	}

	private static String generateKey(Class<?> class1, Class<?> class2) {
		return class1.toString() + class2.toString();
	}

	public static <T> T copyProperties(Object source, Class<T> targetClass) {
		T t = null;
		try {
			t = targetClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
		}
		copyProperties(source, t);
		return t;
	}

	public static <T> T copyProperties(Object source, T target, boolean noNull) {
		if (noNull) {
			BeanCopier copier = getBeanCopier(source.getClass(), target.getClass(), true);
			copier.copy(source, target, (s, clazz, t) -> {
				if (s == null){
					char[] methodName = ((String) t).toCharArray();
					methodName[0] = 'g';
					return getMethodAccess(target.getClass())
						.invoke(target,String.valueOf(methodName));
				}
				return s;
			});
		}else {
			copyProperties(source, target);
		}
		return target;
	}

	public static <T> List<T> copyPropertiesOfList(List<?> sourceList, Class<T> targetClass) {
		if (CollectionUtils.isEmpty(sourceList)) {
			return Collections.emptyList();
		}
		ConstructorAccess<T> constructorAccess = getConstructorAccess(targetClass);
		List<T> resultList = new ArrayList<>(sourceList.size());
		for (Object o : sourceList) {
			T t = null;
			try {
				t = constructorAccess.newInstance();
				copyProperties(o, t);
				resultList.add(t);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return resultList;
	}

	private static <T> ConstructorAccess<T> getConstructorAccess(Class<T> targetClass) {
		ConstructorAccess<T> constructorAccess = constructorAccessCache.get(targetClass.toString());
		if (constructorAccess != null) {
			return constructorAccess;
		}
		try {
			constructorAccess = ConstructorAccess.get(targetClass);
			constructorAccess.newInstance();
			constructorAccessCache.put(targetClass.toString(), constructorAccess);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
		}
		return constructorAccess;
	}
	private static MethodAccess getMethodAccess(Class typeClass) {
		MethodAccess methodAccess = methodAccessCache.get(typeClass.toString());
		if (methodAccess != null) {
			return methodAccess;
		}
		try {
			methodAccess = MethodAccess.get(typeClass);
			methodAccessCache.put(typeClass.toString(), methodAccess);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Create new instance of %s failed: %s", typeClass, e.getMessage()));
		}
		return methodAccess;
	}
}
