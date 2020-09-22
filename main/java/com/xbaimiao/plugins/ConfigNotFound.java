package com.xbaimiao.plugins;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ConfigNotFound extends Exception{


    public ConfigNotFound(){

    }
    public ConfigNotFound(String message) {
        super(message);
    }

    public void s(){
        System.out.println(1);
    }

    @EventListener
    public static void main(String[] args) {
        Class<?> a = ConfigNotFound.class;
        try {
            Method v = a.getMethod("s");
            try {
                try {
                    v.invoke(a.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

//        Method[] methods= a.getMethods();
//        for (Method method : methods) {
//
////            System.out.println(method.getName());
////            System.out.println(Arrays.toString(method.getParameters()));
//            Annotation[] annotations = method.getDeclaredAnnotations();
//            for (Annotation annotation : annotations) {
////                System.out.println(a.getSimpleName().concat(".").concat(method.getName()).concat(".")
////                        .concat(annotation.annotationType().getSimpleName()));
//            }
//        }
    }

}
