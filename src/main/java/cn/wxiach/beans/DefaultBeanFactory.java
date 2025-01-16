package cn.wxiach.beans;

import cn.wxiach.annotations.Autowired;
import cn.wxiach.beans.support.BeanFactoryAware;
import cn.wxiach.core.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

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
        Object beanInstance = this.createBeanInstance(beanClass);

        if (beanDefinition.isSingleton() && this.isSingletonCurrentlyInCreation(beanName)) {
            this.addEarlySingletonObjects(beanName, beanInstance);
        }

        populateBean(beanClass, beanInstance);
        initializeBean(beanInstance);
        return beanInstance;
    }

    private Object createBeanInstance(Class<?> beanClass) {
        Constructor<?>[] candidatesConstructors = beanClass.getDeclaredConstructors();
        List<Constructor<?>> autowiredConstructors = Arrays.stream(candidatesConstructors)
                .filter(constructor -> constructor.isAnnotationPresent(Autowired.class))
                .collect(Collectors.toList());

        if (autowiredConstructors.isEmpty() && candidatesConstructors.length > 1) {
            throw new BeansCreateException("Multiple constructors found for class " + beanClass.getName()
                    + " but none are annotated with @Autowired");
        }

        if (autowiredConstructors.size() > 1) {
            throw new BeansCreateException("Multiple constructors annotated with @Autowired found for class " + beanClass.getName());
        }

        Constructor<?> constructorToUse = autowiredConstructors.isEmpty() ? candidatesConstructors[0] : autowiredConstructors.get(0);

        try {
            // To obtain the correct parameter names, '-parameters' should be added to the compile command.
            Object[] constructorArguments = Arrays.stream(constructorToUse.getParameters())
                    .map(parameter -> this.getBean(parameter.getName())).toArray();
            constructorToUse.setAccessible(true);
            return constructorToUse.newInstance(constructorArguments);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new BeansCreateException("Failed to create bean instance while instantiating class " + beanClass.getName(), e);
        }
    }

    private void populateBean(Class<?> beanClass, Object beanInstance) {
        for (Field field : beanClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);

                Autowired autowired = field.getAnnotation(Autowired.class);
                String beanName = StringUtils.hasText(autowired.value()) ? autowired.value() : field.getName();

                Object bean = getBean(beanName, field.getType());

                try {
                    field.set(beanInstance, bean);
                } catch (IllegalAccessException e) {
                    throw new BeansCreateException("Failed to inject dependency into field "
                            + field.getName() + " of bean " + beanClass.getName(), e);
                }
            }
        }
    }

    private void initializeBean(Object beanInstance) {
        if (beanInstance instanceof BeanFactoryAware) {
            ((BeanFactoryAware) beanInstance).setBeanFactory(this);
        }
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
