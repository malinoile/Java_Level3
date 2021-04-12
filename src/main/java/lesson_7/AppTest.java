package lesson_7;

import lesson_7.annotations.AfterSuite;
import lesson_7.annotations.BeforeSuite;
import lesson_7.annotations.Test;
import lesson_7.classes.Test1;
import lesson_7.classes.Test2;
import lesson_7.classes.Test3;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppTest {
    public static void main(String[] args) {
        start(Test1.class);
        start(Test2.class);
        start(Test3.class);
    }

    private static void start(Class<?> aClass) {
        Object obj = getObjectFromClass(aClass);
        try {
            runMethodsForAnnotation(obj, BeforeSuite.class);
            runMethodsForAnnotation(obj, Test.class);
            runMethodsForAnnotation(obj, AfterSuite.class);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    private static <T> T getObjectFromClass(Class<T> aClass) {
        T obj = null;
        try {
            obj = (T) getNewInstanceFromConstructor(aClass.getConstructor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static Object getNewInstanceFromConstructor(Constructor constructor) {
        Object obj = null;
        try {
            obj = constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static void runMethodsForAnnotation(Object testObject, Class<? extends Annotation> classAnnotation) {
        Method[] methods = testObject.getClass().getDeclaredMethods();
        if(classAnnotation == BeforeSuite.class || classAnnotation == AfterSuite.class) {
            if(!isUniqueMethodWithAnnotation(testObject, methods, classAnnotation)) {
                throw new RuntimeException("В классе-теста " + testObject.getClass().getName() + " может присутствовать не более одного класса "
                        + classAnnotation.getName());
            }
        }

        if (classAnnotation == Test.class) {
            methods = getListSortByPriority(methods).toArray(new Method[0]);
        }

        for (Method method : methods) {
            if (method.getAnnotation(classAnnotation) != null) {
                invokeMethod(testObject, method);
            }
        }
    }

    private static boolean isUniqueMethodWithAnnotation(Object testObject, Method[] methods, Class<? extends Annotation> classAnnotation) {
        methods = testObject.getClass().getDeclaredMethods();
        int count = 0;
        for (Method method : methods) {
            if (method.getAnnotation(classAnnotation) != null) {
                count++;
            }
        }

        return count <= 1;
    }

    private static List<Method> getListSortByPriority(Method[] methods) {
        Map<Method, Integer> mapMethods = new HashMap<>();
        for (Method method : methods) {
            if (method.getAnnotation(Test.class) != null)
                mapMethods.put(method, method.getAnnotation(Test.class).priority());
        }

        List<Method> list = new ArrayList<>();
        mapMethods.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue()))
                .forEach(e -> list.add(e.getKey()));
        Collections.reverse(list);

        return list;
    }


    private static void invokeMethod(Object testObject, Method method, Object... args) {
        try {
            method.invoke(testObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
