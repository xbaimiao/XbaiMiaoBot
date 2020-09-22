package com.xbaimiao.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

public class yuq {

    private static final Logger logger = LoggerFactory.getLogger(JavaPlugin.class);
    protected static final HashMap<Class<?>,Method> GROUP_MESSAGE = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Class<?> a = EventTrigger.class;

        for (Method method : a.getMethods()) { //遍历方法
            Parameter[] parameters = method.getParameters();
            if (parameters.length == 1){
                if (parameters[0].toString().equalsIgnoreCase("com.xbaimiao.plugins.event.GroupMessageEvent")){
//                    GROUP_MESSAGE.add(method);
                }
            }
//            System.out.println(method.getName()); // 方法名字
//            System.out.println(Arrays.toString(method.getParameters())); //方法参数
//            Annotation[] annotations = method.getDeclaredAnnotations();
//            for (Annotation annotation : annotations) {
//                System.out.println(annotation.annotationType().getName());
//                System.out.println(a.getSimpleName().concat(".").concat(method.getName()).concat(".")
//                        .concat(annotation.annotationType().getSimpleName())); //注解
//            Method v = a.getMethod("s");
//            v.invoke(a.newInstance());
        }
    }


    /**
     * 注册一个监听器
     *
     * @param listener Listener对象
     */
    public static void regEvent(Listener listener) {
        Class<?> l = listener.getClass();
        Method[] methods= l.getMethods();
        for (Method method : methods) {
            Parameter[] parameters = method.getParameters();
            if (parameters.length == 1){
                Annotation[] annotations = method.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType().getName().equalsIgnoreCase("com.xbaimiao.plugins.EventListener")){
                        System.out.println(annotation.annotationType().getName());
                        System.out.println(parameters[0].toString());
                        if (parameters[0].toString().startsWith("com.xbaimiao.plugins.event.GroupMessageEvent")){

                            GROUP_MESSAGE.put(l,method);
                            System.out.println("注册cg");
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取日志输出器
     *
     * @return 日志输出器
     */
    public static Logger getLogger() {
        return logger;
    }

}
