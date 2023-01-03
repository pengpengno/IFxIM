package com.ifx.common.utils;

import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class ClassUtil {
    private ClassLoader classLoader;


    /**
     * Suffix for array class names: {@code "[]"}.
     */
    public static final String ARRAY_SUFFIX = "[]";

    /**
     * Prefix for internal array class names: {@code "["}.
     */
    private static final String INTERNAL_ARRAY_PREFIX = "[";

    /**
     * Prefix for internal non-primitive array class names: {@code "[L"}.
     */
    private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";

    /**
     * A reusable empty class array constant.
     */
    private static final Class<?>[] EMPTY_CLASS_ARRAY = {};

    /**
     * The package separator character: {@code '.'}.
     */
    private static final char PACKAGE_SEPARATOR = '.';

    /**
     * The path separator character: {@code '/'}.
     */
    private static final char PATH_SEPARATOR = '/';

    /**
     * The nested class separator character: {@code '$'}.
     */
    private static final char NESTED_CLASS_SEPARATOR = '$';

    /**
     * The CGLIB class separator: {@code "$$"}.
     */
    public static final String CGLIB_CLASS_SEPARATOR = "$$";

    /**
     * The ".class" file suffix.
     */
    public static final String CLASS_FILE_SUFFIX = ".class";

    public ClassUtil() {
        classLoader = getClass().getClassLoader();
    }

    private static ConcurrentHashMap<String,Class<?>> classConcurrentHashMap;


    private static Class<?> reflectClass (String path) throws ClassNotFoundException{
        return Optional.ofNullable(classConcurrentHashMap.computeIfAbsent(path, (key) -> {
            try {
                return Class.forName(path);
            } catch (ClassNotFoundException ex) {
                log.error("class  not fount the path is {} ", path);
                return null;
            }
        })).orElseThrow(() -> new ClassNotFoundException("class  not fount the path is "+ path));
    }


    private static boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private static boolean isDirectory(String name) {
        return !name.contains(".");
    }

    private static List<String> readFromDirectory(String path) {
        if (path == null) {
            return null;
        }
        return readFromFileDirectory(path);
    }

    private static List<String> readFromFileDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();
        if (null == names) {
            return null;
        } else {
            return Arrays.asList(names);
        }
    }


    public static boolean hasDeclaredAnnotation(Class<?> clazz, Class<?> annotation) {
        if (annotation == null) {
            return false;
        }
        return hasAnnotationInList(annotation, clazz.getDeclaredAnnotations());
    }

    public static boolean hasAnnotation(Class<?> clazz, Class<?> annotation) {
        if (annotation == null) {
            return false;
        }
        return hasAnnotationInList(annotation, clazz.getAnnotations());
    }

    public static boolean hasAnnotationInList(Class<?> annotation, Annotation[] annotations2) {
        return getAnnotationInList(annotation, annotations2) != null;
    }

    public static Annotation getAnnotationInList(Class<?> annotation, Annotation[] annotations) {
        if (annotations == null || annotation == null) {
            return null;
        }
        for (Annotation annotation1 : annotations) {
            if (annotation1.annotationType().equals(annotation)) {
                return annotation1;
            }
        }
        return null;
    }

    public static void copyField(Object target, Object base) {
        Class<?> clazz = base.getClass();
        Class<?> targetClass = target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }
    }
}
