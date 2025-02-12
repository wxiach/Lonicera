package cn.wxiach.beans.support;

import cn.wxiach.beans.BeanFactoryAware;
import cn.wxiach.beans.BeansCreateException;
import cn.wxiach.beans.BeansException;
import cn.wxiach.beans.annotation.Autowired;
import cn.wxiach.beans.config.AutowireCapableBeanFactory;
import cn.wxiach.beans.config.BeanDefinition;
import cn.wxiach.beans.config.BeanPostProcessor;
import cn.wxiach.beans.config.InstantiationAwareBeanPostProcessor;
import cn.wxiach.core.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wxiach 2025/1/20
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Object beanInstance = this.createBeanInstance(beanClass);

        // Early expose the singleton object to resolve circular dependency
        if (beanDefinition.isSingleton() && this.isSingletonCurrentlyInCreation(beanName)) {
            this.addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, beanDefinition, beanInstance));
        }

        Object exposedObject = beanInstance;
        populateBean(beanClass, beanInstance);
        exposedObject = initializeBean(beanName, exposedObject);
        return exposedObject;
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
                    throw new BeansCreateException(
                            "Failed to inject dependency into field " + field.getName() + " of bean " + beanClass.getName(), e);
                }
            }
        }
    }

    private Object initializeBean(String beanName, Object beanInstance) {
        if (beanInstance instanceof BeanFactoryAware) {
            ((BeanFactoryAware) beanInstance).setBeanFactory(this);
        }

        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(beanInstance, beanName);

        return applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) {
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            existingBean = processor.postProcessBeforeInitialization(existingBean, beanName);
        }
        return existingBean;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            existingBean = processor.postProcessAfterInitialization(existingBean, beanName);
        }
        return existingBean;
    }

    /**
     * Obtain a reference for early access to the specified bean,
     * typically for the purpose of resolving a circular reference.
     *
     * @param beanName
     * @param beanDefinition
     * @param bean
     * @return
     */
    protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean) {
        Object exposedObject = bean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                exposedObject = ((InstantiationAwareBeanPostProcessor) processor).getEarlyBeanReference(exposedObject, beanName);
            }
        }
        return exposedObject;
    }
}
