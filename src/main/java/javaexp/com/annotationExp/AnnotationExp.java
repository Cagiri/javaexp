package javaexp.com.annotationExp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationExp {

	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		
		Class<AnnotationExpBean> obj = AnnotationExpBean.class;
		
		for (Field f : obj.getDeclaredFields()) {
			AnnotationExpForField a = f.getAnnotation(AnnotationExpForField.class);
			
			if (a!= null && a.fastDb()) {
				Method m = obj.getMethod("get"+f.getName().substring(0, 1).toUpperCase()+f.getName().substring(1));
				System.out.println(m.invoke(obj.newInstance()));
				System.out.println(f.getName() + " : Written to fast one...");
			} else {
				System.out.println(f.getName() + " : Written to slowest one");
			}
		}
	}
}
