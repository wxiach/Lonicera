package cn.wxiach.beans;

import cn.wxiach.annotations.Autowired;
import cn.wxiach.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxiach 2025/1/8
 */
public class DefaultBeanFactory extends AbstractBeanFactory implements ListableBeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>(16);

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            Constructor<?>[] candidates = beanClass.getDeclaredConstructors();
            if (candidates.length == 1 && candidates[0].getParameterTypes().length == 0) {
                return candidates[0].newInstance();
            }
            ClassUtils.sortConstructors(candidates);
            for (Constructor<?> constructor : candidates) {
                if (constructor.isAnnotationPresent(Autowired.class)) {
                    return createInstanceWithAutowiredConstructor(constructor);
                }
            }
            throw new BeansCreateException("Failed to create bean: " + beanName);
        } catch (Exception e) {
            throw new BeansCreateException("Failed to create bean: " + beanName, e);
        }
    }

    private Object createInstanceWithAutowiredConstructor(Constructor<?> constructor) throws Exception {
        Parameter[] parameters = constructor.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            // To obtain the correct parameter names, '-parameters' should be added to the compile command.
            args[i] = this.getBean(parameters[i].getName());
        }
        constructor.setAccessible(true);
        return constructor.newInstance(args);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            if (type.isAssignableFrom(entry.getValue().getBeanClass())) {
                result.add(entry.getKey());
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> result = new HashMap<>(16);
        String[] beanNames = getBeanNamesForType(type);
        for (String beanName : beanNames) {
            result.put(beanName, type.cast(this.getBean(beanName)));
        }
        return result;
    }
}
