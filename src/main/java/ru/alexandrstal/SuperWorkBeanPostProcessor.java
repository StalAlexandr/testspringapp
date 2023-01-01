package ru.alexandrstal;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SuperWorkBeanPostProcessor implements BeanPostProcessor {

    Map<String, List<Method>> methodsToBeWrapped = new HashMap<>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        List<Method> annotated = Arrays.stream(bean.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(SuperWork.class))
                .collect(Collectors.toList());

        if (!annotated.isEmpty()) {
            methodsToBeWrapped.put(beanName, annotated);
        }

        return bean;
    }

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (!methodsToBeWrapped.containsKey(beanName)) {
            return bean;
        }

        List<Method> annotated = methodsToBeWrapped.get(beanName);

        List<String> names = annotated.stream().map(Method::getName).collect(Collectors.toList());

        return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(),
                (o, method, objects) -> {
                    if (names.contains(method.getName())) {
                        System.out.println("Start wrapping...");
                        var res = method.invoke(bean, objects);
                        System.out.println("End wrapping...");
                        return res;
                    }
                    return method.invoke(bean, objects);
                });
    }
}
