package cn.wxiach.beans.support;

import cn.wxiach.beans.ObjectFactory;
import cn.wxiach.beans.config.SingletonBeanRegistry;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wxiach 2025/1/9
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(16);

    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);

    private final Map<String, ObjectFactory<Object>> singletonFactories = new ConcurrentHashMap<>(16);

    // Store the singleton object, which is in creation
    private final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    private void addSingleton(String beanName, Object singletonObject) {
        this.singletonObjects.put(beanName, singletonObject);
        this.earlySingletonObjects.remove(beanName);
        this.singletonFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<Object> singletonFactory) {
        this.singletonFactories.put(beanName, singletonFactory);
    }

    @Override
    public Object getSingleton(String beanName) {
        // First-level cache: Check if the bean is already created.
        Object singletonObject = this.singletonObjects.get(beanName);

        // If not found and the bean is currently being created, check the second-level cache (earlySingletonObjects).
        if (singletonObject == null && this.isSingletonCurrentlyInCreation(beanName)) {
            singletonObject = this.earlySingletonObjects.get(beanName);

            // If still not found, check the third-level cache (singletonFactories) for the bean creation factory.
            if (singletonObject == null) {
                ObjectFactory<Object> singletonFactory = this.singletonFactories.get(beanName);

                // If a factory exists, create the bean, store it in the second-level cache, and remove the factory.
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    this.singletonFactories.remove(beanName);
                    this.earlySingletonObjects.put(beanName, singletonObject);
                }
            }
        }

        // Return the bean from any of the caches.
        return singletonObject;
    }


    public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
        beforeSingletonCreation(beanName);
        Object singletonObject = singletonFactory.getObject();
        afterSingletonCreation(beanName);
        this.addSingleton(beanName, singletonObject);
        return singletonObject;
    }

    protected boolean isSingletonCurrentlyInCreation(String beanName) {
        return singletonsCurrentlyInCreation.contains(beanName);
    }

    private void beforeSingletonCreation(String beanName) {
        if (!isSingletonCurrentlyInCreation(beanName)) {
            singletonsCurrentlyInCreation.add(beanName);
        }
    }

    private void afterSingletonCreation(String beanName) {
        if (isSingletonCurrentlyInCreation(beanName)) {
            singletonsCurrentlyInCreation.remove(beanName);
        }
    }
}
