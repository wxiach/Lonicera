package cn.wxiach.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author wxiach 2025/1/9
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{

    private final Map<String, Object> singletonObjects = new HashMap<>(16);
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
    private final Set<String> singletonsCurrentlyInCreation = new HashSet<>(16);

    private void addSingleton(String beanName, Object singletonObject) {
        this.singletonObjects.put(beanName, singletonObject);
        this.earlySingletonObjects.remove(beanName);
    }

    protected void addEarlySingletonObjects(String beanName, Object singletonObjects) {
        this.earlySingletonObjects.put(beanName, singletonObjects);
    }

    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null && this.isSingletonCurrentlyInCreation(beanName)) {
            singletonObject = this.earlySingletonObjects.get(beanName);
        }
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
